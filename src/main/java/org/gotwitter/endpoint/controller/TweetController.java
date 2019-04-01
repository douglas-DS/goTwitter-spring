package org.gotwitter.endpoint.controller;

import lombok.AllArgsConstructor;
import org.gotwitter.endpoint.service.TweetService;
import org.gotwitter.model.Tweet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("tweet")
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class TweetController {

    private TweetService tweetService;

    @GetMapping
    public ResponseEntity<?> index() {
        return tweetService.listAllToIndex();
    }

    @PostMapping
    public ResponseEntity<?> store(@RequestBody Tweet tweet) {
        return tweetService.save(tweet);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> findTweet(@PathVariable String id) {
        return tweetService.findTweet(id);
    }

    @PutMapping("like/{id}")
    public ResponseEntity<?> store(@PathVariable String id) {
        return tweetService.storeTweetLike(id);
    }
}
