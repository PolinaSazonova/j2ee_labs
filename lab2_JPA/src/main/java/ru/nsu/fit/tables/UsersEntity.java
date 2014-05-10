package ru.nsu.fit.tables;

import javax.persistence.*;

/**
 * Created by Polina on 08.05.14.
 */


@Entity
@Table (name = "USERS")
public class UsersEntity {

    @Id
    @GeneratedValue
    protected Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    public UsersEntity () {}

    public UsersEntity (String name, String email) {
        this.name = name;
        this.email = email;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", name=" + name + ", email= " + email + "]";
    }
}
