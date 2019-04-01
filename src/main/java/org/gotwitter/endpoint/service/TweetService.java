package org.gotwitter.endpoint.service;

import lombok.extern.slf4j.Slf4j;
import org.gotwitter.model.Tweet;
import org.gotwitter.repository.TweetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class TweetService {
    private TweetRepository tweetRepository;

    @Autowired
    public TweetService(TweetRepository tweetRepository) {
        this.tweetRepository = tweetRepository;
    }

    public ResponseEntity<List<Tweet>> listAllToIndex() {
        List<Tweet> allTweets = tweetRepository.findAll(new Sort(Sort.Direction.DESC, "createdAt"));
        return ResponseEntity.ok(allTweets);
    }

    public ResponseEntity<Tweet> findTweet(String id) {
        if (tweetRepository.existsById(id)) {
            Tweet tweet = tweetRepository.findTweetById(id);
            return ResponseEntity.ok(tweet);
        }
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<Tweet> save(Tweet tweet) {
        Tweet savedTweet = tweetRepository.save(tweet);
        return ResponseEntity.ok(savedTweet);
    }

    public ResponseEntity<?> storeTweetLike(String id) {
        if (tweetRepository.existsById(id)) {
            Tweet tweet = tweetRepository.findTweetById(id);
            tweet.setLikes(tweet.getLikes() + 1);
            tweetRepository.save(tweet);
            return ResponseEntity.ok(tweet);
        }
        return ResponseEntity.notFound().build();
    }

}
