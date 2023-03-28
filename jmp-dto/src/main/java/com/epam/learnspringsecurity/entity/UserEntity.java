package com.epam.learnspringsecurity.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/***
 * Entity, that represents application's user entity
 */
@Entity
@Table(name = "user_entity")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private long id;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "user_password")
    private String userPassword;

    @Column(name="user_authorities")
    private String userAuthorities;

    public UserEntity(long id, String userName, String userPassword, String userAuthorities) {
        this.id = id;
        this.userName = userName;
        this.userPassword = userPassword;
        this.userAuthorities = userAuthorities;
    }

    public UserEntity() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserAuthorities() {
        return userAuthorities;
    }

    public void setUserAuthorities(String userAuthorities) {
        this.userAuthorities = userAuthorities;
    }

}
