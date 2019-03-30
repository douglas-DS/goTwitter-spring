package org.gotwitter.controller;

import org.gotwitter.model.Tweet;
import org.gotwitter.service.TweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("likes")
public class LikeController {

    private TweetService tweetService;

    @Autowired
    public LikeController(TweetService tweetService) {
        this.tweetService = tweetService;
    }

    @PostMapping("{id}")
    public Tweet store(@PathVariable String id) {
        Tweet tweet = tweetService.findTweetById(id);

        tweet.setLikes(tweet.getLikes() + 1);
        tweetService.save(tweet);

        return tweet;
    }
}
