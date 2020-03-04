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
        List<Tweet> allTweets = tweetRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
        log.info("Listing all tweets by creation date");
        return ResponseEntity.ok(allTweets);
    }

    public ResponseEntity<Tweet> findTweet(String id) {
        if (tweetRepository.existsById(id)) {
            Tweet tweet = tweetRepository.findTweetById(id);
            log.info("Searching tweet by id: " + id);
            return ResponseEntity.ok(tweet);
        }
        log.error("Tweet not found by id: " + id);
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<Tweet> save(Tweet tweet) {
        Tweet savedTweet = tweetRepository.save(tweet);
        log.info("Tweet successfully saved");
        return ResponseEntity.ok(savedTweet);
    }

    public ResponseEntity<?> storeTweetLike(String id) {
        if (tweetRepository.existsById(id)) {
            Tweet tweet = tweetRepository.findTweetById(id);
            tweet.setLikes(tweet.getLikes() + 1);
            tweetRepository.save(tweet);
            log.info("Like add to tweet with id: " + id);
            return ResponseEntity.ok(tweet);
        }
        log.error("Tweet not found by id: " + id);
        return ResponseEntity.notFound().build();
    }

}
