package com.project.onlineauction.controller;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.project.onlineauction.dto.BidItemDto;
import com.project.onlineauction.dto.UserBidDto;
import com.project.onlineauction.service.BidService;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/auction")
public class BidController {

    @Autowired
    private BidService bidService;

    @GetMapping("/bids")
    public List<BidItemDto> getAllBids(@RequestParam("tabType") String tabType) {
        return bidService.getAllBids(tabType);
    }
    
    @GetMapping("/user/bids")
    public List<UserBidDto> getUserBidsByUserId() {
    	return bidService.getUserBidsByUserId();
    }

    @PostMapping("/Bid")
    @CrossOrigin(origins = "http://localhost:5173/Bid")
    public ResponseEntity<BidItemDto> createBid(@RequestPart BidItemDto bidDto,@RequestPart MultipartFile imageFile) throws IOException {
        BidItemDto createdBid = bidService.createBid(bidDto,imageFile);
        return new ResponseEntity<>(createdBid, HttpStatus.CREATED);
    }

    @PostMapping("/Bid/place-bid/{id}")
    public ResponseEntity<BidItemDto> updateBidAmount(@PathVariable Long id, @RequestBody UserBidDto userBidDto) {
        BidItemDto updatedBid = bidService.placeBid(id, userBidDto);
        if (updatedBid != null) {
            return new ResponseEntity<>(updatedBid, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @PutMapping("/bid/item/sell")
    public ResponseEntity<String> sellBidItem(@RequestParam("bidItemId") Long bidItemId) {
    	bidService.sellBidItem(bidItemId);
    	return new ResponseEntity<>(HttpStatus.OK);
    }
}

