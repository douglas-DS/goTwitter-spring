package org.gotwitter.controllers;

import org.gotwitter.models.Tweet;
import org.gotwitter.repository.TweetRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("tweets")
public class TweetController {

    private TweetRepo repository;

    @Autowired
    public TweetController(TweetRepo repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Tweet> index() {
        return repository.findAll();
    }

    @PostMapping
    @ResponseBody
    public Tweet store(Tweet tweet) {
        System.out.println(tweet.toString());
        repository.save(tweet);

        return tweet;
    }

    @GetMapping("{id}")
    public Tweet findTweet(@PathVariable String id) {
        return repository.findTweetById(id);
    }
}
