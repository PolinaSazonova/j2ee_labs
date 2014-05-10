package ru.nsu.fit.tables;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by Polina on 08.05.14.
 */

@Entity
public class AdminsEntity extends UsersEntity {

    @Column(nullable = true)
    private int adminRank;

    public AdminsEntity() {
        adminRank = 0;
    }

    public AdminsEntity(String name, String email, int adminRank) {
        super(name, email);
        this.adminRank = adminRank;
    }

    public int getAdminRank() {
        return adminRank;
    }

    public void setAdminRank(int adminRank1) {
        this.adminRank = adminRank1;
    }

}
