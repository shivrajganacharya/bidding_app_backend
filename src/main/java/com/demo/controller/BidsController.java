package com.demo.controller;

import com.demo.model.BidsDao;
import com.demo.model.BidsDto;
import com.demo.model.ItemsDao;
import com.demo.repository.BidsRepository;
import com.demo.repository.ItemsRepository;
import com.demo.repository.UserRepository;
import com.demo.service.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
public class BidsController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    BidsRepository bidsRepository;

    @Autowired
    ItemsRepository itemsRepository;

    @Autowired
    UserInfo userInfo;

    @PostMapping("/makeBid/{item_id}")
    public void makeBit(HttpServletRequest request, @RequestBody BidsDto bidsDto, @PathVariable Integer item_id) {
        Optional<ItemsDao> itemsDao = itemsRepository.findById(item_id);
        System.out.println(item_id);
        BidsDao bidsDao = new BidsDao();
        System.out.println(bidsDto.getBid_value());
        bidsDao.setBid_value(bidsDto.getBid_value());
        bidsDao.setUsername(userInfo.getUserName(request));
        itemsDao.get().getBids().add(bidsDao);
        itemsRepository.save(itemsDao.get());
    }

    @GetMapping("/getItemBids/{item_id}")
    public List<BidsDao> getItemsBids(@PathVariable Integer item_id) {
        Optional<ItemsDao> itemsDao = itemsRepository.findById(item_id);
        List<BidsDao> bids = itemsDao.get().getBids();
        return bids;
    }

}
