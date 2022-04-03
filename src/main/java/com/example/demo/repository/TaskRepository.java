package com.example.demo.repository;

import com.example.demo.model.Task;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


/**
 * Классы репозиториев необходимо аннотировать как @Repository.
 *
 * PagingAndSortingRepository - это интерфейс, который обеспечивает основные операции по поиску, сохранения,
 * удалению данных (CRUD операции), а также получение пагинированного списка.
 *
 * В угловых скобках указаны класс сущности и класс Id сущности.
 *
 */
@Repository
@Transactional
public interface TaskRepository extends PagingAndSortingRepository<Task, Long> {
    /**
     * Метод должен принимать id задачи, и менять поле done в true.
     *
     * Запрос будет на языке JPQL. Язык очень удобный и легкий. Советуем почитать про него побольше,
     * так как будете очень много сталкиваться с ним.
     *
     * Важно: Методы, меняющие данные, должны быть в транзакции.
     * Аннотация @Transactional обеспечивает работу в транзакции.
     * Аннотацию можно добавить на весь репозиторий, чтобы не указывать на каждом методе.
     * Добавляем аннотацию от spring, не от javax.
     */
    @Modifying
    @Query("UPDATE Task t SET t.done = TRUE " +
            "WHERE t.id = :id")
    void markAsDone(@Param("id") Long id);

}


