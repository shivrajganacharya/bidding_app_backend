package com.techgeeknext.controller;

import com.techgeeknext.config.JwtTokenUtil;
import com.techgeeknext.model.ItemsDao;
import com.techgeeknext.model.ItemsDto;
import com.techgeeknext.model.UserDao;
import com.techgeeknext.repository.ItemsRepository;
import com.techgeeknext.repository.UserRepository;
import com.techgeeknext.service.UserInfo;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Date;
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
            itemsRepository.save(i);
        }
    }


}
