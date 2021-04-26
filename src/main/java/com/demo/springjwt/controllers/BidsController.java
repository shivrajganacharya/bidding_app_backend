package com.demo.springjwt.controllers;

import com.demo.springjwt.models.Bids;
import com.demo.springjwt.models.Items;
import com.demo.springjwt.repository.BidsRepository;
import com.demo.springjwt.repository.ItemsRepository;
import com.demo.springjwt.repository.UserRepository;
import com.demo.springjwt.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class BidsController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    BidsRepository bidsRepository;

    @Autowired
    ItemsRepository itemsRepository;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/makeBid/{item_id}")
    public void makeBit(HttpServletRequest request, @RequestBody Bids bid, @PathVariable Integer item_id) {
        Optional<Items> items = itemsRepository.findById(item_id);
        System.out.println(item_id);
        bid.setUsername(jwtUtils.getUserNameFromJwtToken(request.getHeader("Authorization").substring(7)));
        items.get().getBids().add(bid);
        itemsRepository.save(items.get());
    }

    @GetMapping("/getItemBids/{item_id}")
    public List<Bids> getItemsBids(@PathVariable Integer item_id) {
        Optional<Items> itemsDao = itemsRepository.findById(item_id);
        List<Bids> bids = itemsDao.get().getBids();
        return bids;
    }

}
