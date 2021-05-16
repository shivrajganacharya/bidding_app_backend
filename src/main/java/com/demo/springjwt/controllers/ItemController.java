package com.demo.springjwt.controllers;

import com.demo.springjwt.models.Bids;
import com.demo.springjwt.models.Items;
import com.demo.springjwt.models.User;
import com.demo.springjwt.payload.ItemBid;
import com.demo.springjwt.BidsRepository;
import com.demo.springjwt.ItemsRepository;
import com.demo.springjwt.UserRepository;
import com.demo.springjwt.security.jwt.JwtUtils;
import com.demo.springjwt.utils.JavaMailUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class ItemController {

    private static final Logger logger = LogManager.getLogger(ItemController.class);

    @Autowired
    UserRepository userRepository;

    @Autowired
    ItemsRepository itemsRepository;

    @Autowired
    BidsRepository bidsRepository;

    @Autowired
    JwtUtils jwtUtils;


    @PostMapping("/addItem")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> addItem(HttpServletRequest request, @RequestBody Items items) throws ParseException {
        logger.info("Item added by user. Item name is " + items.getItem_name());

        Optional<User> user = userRepository.findByUsername(jwtUtils.getUserNameFromJwtToken(request.getHeader("Authorization").substring(7)));

        items.setOn_sale(1);

        user.get().getItems().add(items);

        return ResponseEntity.ok(userRepository.save(user.get()));
    }

    @GetMapping("/getAllItems")
    @PreAuthorize("hasRole('MODERATOR')")
    public List<Items> getAllItems() {
        logger.info("Get all items method accessed");
        return itemsRepository.findAllByOn_saleEquals();
    }

    @GetMapping("/getUserItems")
    public List<Items> getUserItems(HttpServletRequest request) {
        logger.info("get user items method accessed");
        Optional<User> user = userRepository.findByUsername(jwtUtils.getUserNameFromJwtToken(request.getHeader("Authorization").substring(7)));
        List<Items> items = user.get().getItems();
        return items;
    }

    @GetMapping("/getUserBids")
    public List<ItemBid> getUserBids(HttpServletRequest request) {
        logger.info("get user bids method accessed");

        String username = jwtUtils.getUserNameFromJwtToken(request.getHeader("Authorization").substring(7));
        List<ItemBid> itemBidsList = new ArrayList<>();
        List<Items> itemsList = itemsRepository.findAll();

        for(Items item : itemsList) {
            List<Bids> bidsList = item.getBids();
            for(Bids bid : bidsList) {
                if(bid.getUsername().equals(username)) {
                    System.out.println(item.getItem_name() + " " + bid.getBid_value());
                    ItemBid itemBid = new ItemBid();
                    itemBid.setItem_id(item.getItem_id());
                    itemBid.setItem_name(item.getItem_name());
                    itemBid.setDescription(item.getDescription());
                    itemBid.setBid_value(bid.getBid_value());
                    itemBid.setImage(item.getImage());
                    itemBid.setOn_sale(item.getOn_sale());
                    itemBid.setSource_address(item.getSource_address());
                    itemBid.setDestination_address(item.getDestination_address());
                    itemBidsList.add(itemBid);

                }
            }
        }

        return itemBidsList;
    }

    @GetMapping("/calculateBidResult")
    public void getTimeOverItems(HttpServletRequest request) throws ParseException, MessagingException, javax.mail.MessagingException {
        logger.info("highest bidder calculated for time over items");

        List<User> userList = userRepository.findAll();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime now = LocalDateTime.now();
        Date currDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(dtf.format(now));

        for(User user : userList) {
            List<Items> itemsList = user.getItems();
            for(Items item : itemsList) {
                if(item.getOn_sale() == 1 && item.getDatetime().before(currDateTime)) {
                    item.setOn_sale(0);
                    List<Bids> bidsList = item.getBids();
                    double max_bid = -1;
                    String max_bidder_username = "";
                    int max_bid_id = -1;
                    for(Bids bid : bidsList) {
                        if(bid.getBid_value() > max_bid) {
                            max_bid = bid.getBid_value();
                            max_bidder_username = bid.getUsername();
                            max_bid_id = bid.getId();
                        }
                    }

                    item.setMax_bid_id(max_bid_id);

                    if(max_bid == -1) {
                        String message = "No bid on following item. \n";
                        message += "Item Name - " + item.getItem_name();
                        message += "Item Description - " + item.getDescription();
                        JavaMailUtil.sendMail(user.getEmail(), message);
                    } else {
                        System.out.println("Max bid value - " + max_bid);
                        System.out.println("Max bidder username - " + max_bidder_username);
                        Optional<User> max_bidder = userRepository.findByUsername(max_bidder_username);
                        String message1 = "Hello " + user.getUsername() + "!\n";
                        message1 += "Highest bidder is " + max_bidder.get().getUsername() + ".\n";
                        message1 += "Item name - " + item.getItem_name() +"\n";
                        message1 += "Item description - " + item.getDescription()+"\n";
                        message1 += "Bid value is " + max_bid + ".\n";
                        message1 += "Email of bidder is " + max_bidder.get().getEmail();
                        JavaMailUtil.sendMail(user.getEmail(), message1);

                        String message2 = "Hello " + max_bidder.get().getUsername() + "!\n";
                        message2 += "You are the highest bidder for following item \n";
                        message2 += "Item name - " + item.getItem_name() +"\n";
                        message2 += "Item description - " + item.getDescription()+"\n";
                        message2 += "Item owner name - " + user.getUsername()+"\n";
                        message2 += "Items owner email - " + user.getEmail();
                        JavaMailUtil.sendMail(max_bidder.get().getEmail(), message2);
                    }
                }
            }
            userRepository.save(user);
        }
    }


}

