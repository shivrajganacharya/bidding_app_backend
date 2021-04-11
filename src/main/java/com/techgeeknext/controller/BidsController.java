package com.techgeeknext.controller;

import com.techgeeknext.model.BidsDao;
import com.techgeeknext.model.BidsDto;
import com.techgeeknext.model.ItemsDao;
import com.techgeeknext.model.UserDao;
import com.techgeeknext.repository.BidsRepository;
import com.techgeeknext.repository.ItemsRepository;
import com.techgeeknext.repository.UserRepository;
import com.techgeeknext.service.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
