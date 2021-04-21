package com.demo.controller;

import com.demo.model.BidsDao;
import com.demo.model.ItemsDao;
import com.demo.model.ItemsDto;
import com.demo.model.UserDao;
import com.demo.repository.BidsRepository;
import com.demo.repository.ItemsRepository;
import com.demo.repository.UserRepository;
import com.demo.service.UserInfoService;
import com.demo.utils.JavaMailUtil;
import com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
public class ItemController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    ItemsRepository itemsRepository;

    @Autowired
    BidsRepository bidsRepository;

    @Autowired
    UserInfoService userInfoService;

    @PostMapping("/addItem")
    public ResponseEntity<?> addItem(HttpServletRequest request, @RequestBody ItemsDto items) throws ParseException {
        UserDao userDao = userRepository.findByUsername(userInfoService.getUserName(request));

        System.out.println(userDao.getEmail());

        ItemsDao itemsDao = new ItemsDao();
        itemsDao.setBase_price(items.getBase_price());
        itemsDao.setItem_name(items.getItem_name());
        itemsDao.setDescription(items.getDescription());
        itemsDao.setDatetime(items.getDatetime());
        itemsDao.setOn_sale(1);

        userDao.getItems().add(itemsDao);

        return ResponseEntity.ok(userRepository.save(userDao));
    }

    @GetMapping("/getAllItems")
    public List<ItemsDao> getAllItems() {
        System.out.println("In getAllItems");
        return itemsRepository.findAllByOn_saleEquals();
    }

    @GetMapping("/getUserItems")
    public List<ItemsDao> getUserItems(HttpServletRequest request) {
        UserDao userDao = userRepository.findByUsername(userInfoService.getUserName(request));
        List<ItemsDao> items = userDao.getItems();
        return items;
    }

    @GetMapping("/getTimeOverItems")
    public void getTimeOverItems(HttpServletRequest request) throws ParseException, MessagingException, javax.mail.MessagingException {

        UserDao userDao = userRepository.findByUsername(userInfoService.getUserName(request));

        System.out.println("in getTimeOverItems");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime now = LocalDateTime.now();
        List<ItemsDao> itemsDao = itemsRepository.findAllWithDatetimeBefore(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(dtf.format(now)));

        for(ItemsDao i : itemsDao) {
            if(i.getOn_sale() == 1) {
                i.setOn_sale(0);
                List<BidsDao> bidsDao = i.getBids();
                Integer max_bid_id = -1;
                double max_bid = 0;
                for(BidsDao b : bidsDao) {
                    if(max_bid < b.getBid_value()) {
                        max_bid = b.getBid_value();
                        max_bid_id = b.getId();
                    }
                }
                if(max_bid_id != -1) {
                    Optional<BidsDao> bid = bidsRepository.findById(max_bid_id);
                    String message = "";
                    UserDao max_bidder = userRepository.findByUsername(bid.get().getUsername());
                    message += "Higgest bidder is " + max_bidder.getFirstName() + " " + max_bidder.getLastName() + ".\n";
                    message += "Bid value is " + Double.toString(max_bid) + ".\n";
                    message += "Email of Bidder - " + max_bidder.getEmail();
                    JavaMailUtil.sendMail(userDao.getEmail(), message);
                }
                i.setMax_bid_id(max_bid_id);
                itemsRepository.save(i);
            }
        }
    }


}
