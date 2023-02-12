package com.cstp.shop.model;

import jakarta.persistence.*;
import org.springframework.web.bind.annotation.RequestBody;

@Entity
@Table(name = "account")
public class Account {

    public enum Group {
        CUSTOMER,
        ADMIN
    }

    @Id
    @GeneratedValue
    @Column(name="user_id", nullable=false)
    private Long userID;

    @Column(name="email", nullable=false, unique=true)
    private String email;
    @Column(name="pass_hash", nullable=false)
    private String passHash;
    @Column(name="group_access", nullable=false)
    @Enumerated(EnumType.STRING)
    private Group group;

    protected Account() {}

    public Account(String email, String pass_hash, Group group_access) {
        this.email = email;
        this.passHash = pass_hash;
        this.group = group_access;
    }

    public Long getUser() {
        return userID;
    }

    public String getEmail() {
        return email;
    }

    public String getPassHash() {
        return passHash;
    }

    public Group getGroup() {
        return group;
    }

}