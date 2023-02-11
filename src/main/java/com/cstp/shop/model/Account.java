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
    private Long user_id;

    @Column(name="email", nullable=false)
    private String email;
    @Column(name="pass_hash", nullable=false)
    private String pass_hash;
    @Column(name="group_access", nullable=false)
    @Enumerated(EnumType.STRING)
    private Group group_access;


    protected Account() {}

    public Account(String email, String pass_hash, Group group_access) {
        this.email = email;
        this.pass_hash = pass_hash;
        this.group_access = group_access;
    }

    public Long getUser_id() {
        return user_id;
    }

    /*
        Function names correlate to POST parameters
        email, pass_hash, group_access
     */
    public String getEmail() {
        return email;
    }

    public String getPass_hash() {
        return pass_hash;
    }

    public Group getGroup_access() {
        return group_access;
    }

}