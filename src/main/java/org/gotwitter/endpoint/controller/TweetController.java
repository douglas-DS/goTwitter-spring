package org.gotwitter.endpoint.controller;

import lombok.AllArgsConstructor;
import org.gotwitter.endpoint.service.TweetService;
import org.gotwitter.model.Tweet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("tweet")
@CrossOrigin
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class TweetController {

    private TweetService tweetService;

    @GetMapping
    public ResponseEntity<?> index() {
        return tweetService.listAllToIndex();
    }

    @PostMapping
    @MessageMapping("/handler")
    @SendTo("/topic/tweet")
    public ResponseEntity<?> storeTweet(@RequestBody Tweet tweet) {
        return tweetService.save(tweet);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> findTweet(@PathVariable String id) {
        return tweetService.findTweet(id);
    }

    @PutMapping("like/{id}")
    @MessageMapping("/handler/{id}")
    @SendTo("/topic/like")
    public ResponseEntity<?> storeLike(@DestinationVariable("id") @PathVariable String id) {
        return tweetService.storeTweetLike(id);
    }
}
