package com.example.demo.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

/**
 * Аннотация @Entity должна быть на каждой сущности(entity).
 * Аннотация @Data, от библиотеки lombok, позволяет не писать руками getter'ы, setter'ы .
 * Аннотация @Id указывает на primary key таблицы. Должна быть в каждой сущности.
 * Аннотация @GeneratedValue говорит о том, как заполнять поле. В данном случае, за автозаполнение отвечает тип поля bigserial в Postgresql.
 * Для дат будем использовать класс LocalDate, а для даты со временем LocalDateTime
 *
 */

@Data
@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate date_;
    private String description;
    private boolean done;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setDate_(LocalDate date_) {
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
    }
}
