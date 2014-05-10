package ru.nsu.fit.tables;

import javax.persistence.*;

/**
 * Created by Polina on 08.05.14.
 */

@Entity
@Table (name = "TOPICS")
public class TopicsEntity {

    @Id
    @GeneratedValue
    protected Long id;

    @Column(nullable = false)
    private String name;

    public TopicsEntity() {}

    public TopicsEntity(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Topic [id=" + id + ", name=" + name + "]";
    }
}
