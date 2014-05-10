package ru.nsu.fit.tables;

import javax.persistence.*;

/**
 * Created by Polina on 09.05.14.
 */

@Entity
@Table (name = "MESSAGES")
public class MessagesEntity {

    @Id
    @GeneratedValue
    protected Long id;

    @ManyToOne
    private UsersEntity userId;

    @ManyToOne
    private TopicsEntity topicId;

    @Column(nullable = false)
    private String message;

    public MessagesEntity() {}

    public MessagesEntity(UsersEntity userId1, TopicsEntity topicId1, String message1) {
        this.userId = userId1;
        this.topicId = topicId1;
        this.message = message1;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UsersEntity getUserId() {
        return userId;
    }

    public void setUserId(UsersEntity userId) {
        this.userId = userId;
    }

    public TopicsEntity getTopicId() {
        return topicId;
    }

    public void setTopicId(TopicsEntity topicId) {
        this.topicId = topicId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Message [id=" + id + ", creater=" + userId + ", topic="
                + topicId + ", content=" + message + "]";
    }
}
