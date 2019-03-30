package org.gotwitter.service;

import org.gotwitter.model.Tweet;
import org.gotwitter.repository.TweetRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TweetService {
    private TweetRepo tweetRepository;

    @Autowired
    public TweetService(TweetRepo tweetRepository) {
        this.tweetRepository = tweetRepository;
    }

    public Tweet findTweetById(String id) {
        return tweetRepository.findTweetById(id);
    }

    public Tweet save(Tweet tweet) {
        tweetRepository.save(tweet);
        return tweet;
    }
}
