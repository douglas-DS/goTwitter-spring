package org.gotwitter.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "tweets")
@Getter
@Setter
@ToString
public class Tweet {

    @Id
    private String id;

    private String author;
    private String content;
    private Integer likes;
    private Date createdAt;

    public Tweet() {
        this.likes = 0;
        this.createdAt = new Date();
    }
}

