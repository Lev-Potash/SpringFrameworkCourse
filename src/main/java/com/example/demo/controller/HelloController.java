package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Для начала, создадим класс HelloController в пакете
 *
 * com.example.demo.controller
 *
 * Класс, который обрабатывает рест запросы, необходимо обозначить аннотацией @RestController.
 *
 * Создаем публичный метод, который отдает String.
 *
 * public String sayHello() {
 *
 * return "Hello!";
 *
 * }
 *
 * В данный момент, этого не достаточно. Для того,
 * чтобы этот метод получил свой rest адрес,
 * нужно добавить @GetMapping.
 *
 * Аннотация @GetMapping означает, что метод работает по HTTP методу GET.
 * В @GetMapping необходимо указать строку,
 * чтобы фреймворк мог сопоставить ресты с java методами.
 */

@RestController
public class HelloController {

    /**
     * Для того, чтобы этот метод получил свой rest адрес, с помощью которого будет обработан запрос
     * и отправлен клиенту GET запрос нужно добавить @GetMapping.
     *
     * Аннотация @GetMapping означает, что метод работает по HTTP методу GET.
     * В @GetMapping необходимо указать строку,
     * чтобы фреймворк мог сопоставить ресты с java методами.
     */
    @GetMapping("/hello")
    public String sayHello() {
        return "Hello!";
  }

}
