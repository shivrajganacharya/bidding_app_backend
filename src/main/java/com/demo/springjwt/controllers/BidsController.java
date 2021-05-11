package com.demo.springjwt.controllers;

import com.demo.springjwt.models.Bids;
import com.demo.springjwt.models.Items;
import com.demo.springjwt.BidsRepository;
import com.demo.springjwt.ItemsRepository;
import com.demo.springjwt.UserRepository;
import com.demo.springjwt.security.jwt.JwtUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class BidsController {

    private static final Logger logger = LogManager.getLogger(BidsController.class);

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
        logger.info("New bid added  for item with id " + item_id);

        Optional<Items> items = itemsRepository.findById(item_id);
        System.out.println(item_id);
        bid.setUsername(jwtUtils.getUserNameFromJwtToken(request.getHeader("Authorization").substring(7)));
        items.get().getBids().add(bid);
        itemsRepository.save(items.get());
    }

    @GetMapping("/getItemBids/{item_id}")
    public List<Bids> getItemsBids(@PathVariable Integer item_id) {
        logger.info("get item bids method accessed");
        Items itemsDao = itemsRepository.findById(item_id).orElseThrow(() -> new RuntimeException("Error: ItemBids not found."));
        List<Bids> bids = itemsDao.getBids();
        return bids;
    }

    @GetMapping("/hello")
    public String greeting() {
        return "Hello, World!";
    }

}
