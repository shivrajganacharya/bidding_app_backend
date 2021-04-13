package com.demo.controller;

import com.demo.model.BidsDao;
import com.demo.model.ItemsDao;
import com.demo.model.ItemsDto;
import com.demo.model.UserDao;
import com.demo.repository.ItemsRepository;
import com.demo.repository.UserRepository;
import com.demo.service.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class ItemController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    ItemsRepository itemsRepository;

    @Autowired
    UserInfo userInfo;

    @PostMapping("/addItem")
    public ResponseEntity<?> addItem(HttpServletRequest request, @RequestBody ItemsDto items) throws ParseException {
        UserDao userDao = userRepository.findByUsername(userInfo.getUserName(request));

        System.out.println(userDao.getEmail());

        ItemsDao itemsDao = new ItemsDao();
        itemsDao.setBase_price(items.getBase_price());
        itemsDao.setItem_name(items.getItem_name());
        itemsDao.setDescription(items.getDescription());
        itemsDao.setTransport_date(items.getTransport_date());

        userDao.getItems().add(itemsDao);

        return ResponseEntity.ok(userRepository.save(userDao));
    }

    @GetMapping("/getAllItems")
    public List<ItemsDao> getAllItems() {
        System.out.println("In getAllItems");
        return itemsRepository.findAll();
    }

    @GetMapping("/getUserItems")
    public List<ItemsDao> getUserItems(HttpServletRequest request) {
        UserDao userDao = userRepository.findByUsername(userInfo.getUserName(request));
        List<ItemsDao> items = userDao.getItems();
        return items;
    }

    @GetMapping("/getTimeOverItems")
    public void getTimeOverItems() throws ParseException {
        System.out.println("in getTimeOverItems");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime now = LocalDateTime.now();
        List<ItemsDao> itemsDao = itemsRepository.findAllWithDatetimeBefore(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(dtf.format(now)));

        for(ItemsDao i : itemsDao) {
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
            i.setMax_bid_id(max_bid_id);
            itemsRepository.save(i);
        }
    }


}
