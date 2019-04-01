package org.gotwitter.endpoint.controller;

import org.gotwitter.endpoint.service.TweetService;
import org.gotwitter.model.Tweet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("tweet")
public class TweetController {

    private TweetService tweetService;

    @Autowired
    public TweetController(TweetService tweetService) {
        this.tweetService = tweetService;
    }

    @GetMapping
    public ResponseEntity<?> index() {
        return tweetService.listAll();
    }

    @PostMapping
    public ResponseEntity<?> store(@RequestBody Tweet tweet) {
        return tweetService.save(tweet);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> findTweet(@PathVariable String id) {
        return tweetService.findTweet(id);
    }

    @PostMapping("like/{id}")
    public ResponseEntity<?> store(@PathVariable String id) {
        return tweetService.storeTweetLike(id);
    }

}
