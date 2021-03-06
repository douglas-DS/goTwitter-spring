package org.gotwitter.repository;

import org.gotwitter.model.Tweet;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TweetRepository extends MongoRepository<Tweet, String> {
    Tweet findTweetById(String id);
}
