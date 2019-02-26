package org.gotwitter.repository;

import org.gotwitter.models.Tweet;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TweetRepo extends MongoRepository<Tweet, String> {

    Tweet findTweetById(String id);

}
