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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
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
    public ResponseEntity<?> addItem(HttpServletRequest request, @RequestBody ItemsDto items) {
        UserDao userDao = userRepository.findByUsername(userInfo.getUserName(request));

        System.out.println(userDao.getEmail());

        ItemsDao itemsDao = new ItemsDao();
        itemsDao.setBase_price(items.getBase_price());
        itemsDao.setItem_name(items.getItem_name());
        itemsDao.setDescription(items.getDescription());

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


}
