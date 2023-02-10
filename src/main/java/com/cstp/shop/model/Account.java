package com.cstp.shop.model;

import jakarta.persistence.*;

@Entity
@Table(name = "account")
public class Account {

    @Id
    @GeneratedValue
    private Long id;

    private String email;
    private String pass_hash;
    private String group_access;

    protected Account() {}

    public Account(String email, String pass_hash, String group_access) {
        this.email = email;
        this.pass_hash = pass_hash;
        this.group_access = group_access;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassHash() {
        return pass_hash;
    }

    public String getGroup() {
        return group_access;
    }

}