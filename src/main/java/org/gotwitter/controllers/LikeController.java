package org.gotwitter.controllers;

import org.gotwitter.models.Tweet;
import org.gotwitter.repository.TweetRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("likes")
public class LikeController {

    private TweetRepo repository;

    @Autowired
    public LikeController(TweetRepo repository) {
        this.repository = repository;
    }

    @PostMapping("{id}")
    @ResponseBody
    public Tweet store(@PathVariable String id) {
        Tweet tweet = repository.findTweetById(id);

        tweet.setLikes(tweet.getLikes() + 1);
        repository.save(tweet);

        return tweet;
    }
}
