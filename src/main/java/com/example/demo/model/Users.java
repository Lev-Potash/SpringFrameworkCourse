package com.example.demo.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Data
@Entity
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String login;
    private String password;

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    /*public void setDate_(LocalDate date_) {
        this.date_ = date_;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDate_() {
        return date_;
    }

    public String getDescription() {
        return description;
    }

    public boolean isDone() {
        return done;
    }*/
}
