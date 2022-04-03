package com.example.demo.controller;

import com.example.demo.Exception.ResourceNotFoundException;
import com.example.demo.model.Task;
import com.example.demo.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
/*-
fsfsfsf
ggg fdfgdrgt
 */
/**
 * @Autowired Аннотация нужна, чтобы Spring подставил bean класса TaskRepository в данный класс.
 *
 * @PostMapping указывает на то, что метод принимает POST запрос.
 *
 * @RequestBody Аннотация ставится, если в запросе есть json тело, которое нужно сериализовать в Java объект.
 *
 * @see Task В данном случае, мы ожидаем объект класса Task.
 *
 * @see TaskRepository В самом методе идет вызов метода save от taskRepository. Метод save работает как на создание, так и на обновление
 *
 */
@RestController
public class TaskController {
    public static final String SUCCESS_DELETE_MESSAGE = "Система выполнила успешне удаление задачи: ";

    @Autowired
    private TaskRepository taskRepository;

    @PostMapping("/tasks")
    public Task create(@RequestBody Task task) {
        return taskRepository.save(task);
    }

    /**
     * @see #getTasksList(Long )
     * @value "/tasks" оначает отображение всех задач
     * @value "/tasks/{id}" оначает отображение задачи с указанным в адресе {id}
     * @return List<Task> позволяет вернуть клиенту данные в виде формата json
     */
    @GetMapping(value = {"/tasks", "/tasks/{id}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Task> getTasksList(@PathVariable(required = false) Long id) {
        if(id != null){
            List<Task> task = new ArrayList<>();
            task.add(taskRepository.findById(id).orElse(null));
            return task;
        }
        return (List<Task>) taskRepository.findAll();
    }

    @PutMapping(value = "/tasks/{id}")
    @ResponseBody
    public ResponseEntity<Task> updateTask(@PathVariable(value = "id") Long id,
                                           @RequestBody Task taskDetails) throws ResourceNotFoundException {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("В базе не найдено записи о задаче с id : " + id));

        task.setDate_(taskDetails.getDate_());                 /*LocalDate.now()*/
        task.setDescription(taskDetails.getDescription());

        final Task taskUpdated = taskRepository.save(task);
        return ResponseEntity.ok(taskUpdated);
    }

    @DeleteMapping(value = "/tasks/{id}")
    @ResponseBody
    public ResponseEntity<String> deleteTask(@PathVariable(value = "id") Long id) throws ResourceNotFoundException {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("В базе не найдено записи о задаче с id : " + id));

        taskRepository.delete(task);
        return ResponseEntity.ok(SUCCESS_DELETE_MESSAGE + id);
    }

    /**
     * Вариант стандартным методом
     *
     * @PatchMapping Необходимо для того чтобы изменить данные в модели сущности Task,
     * без ответа на запрос
     *
     * @param id
     * @param task проверяется на булевское значение и если оно является FALSE,
     *             то тогда задача переводиться в раздел выполненой
     */
    @PatchMapping("/tasks/{id}")
    public void patchMethod(@PathVariable Long id,
                            @RequestBody Task task) {
        if (!task.isDone())
            taskRepository.markAsDone(id);
    }

    /**
     * Вариант c пользовательским методом от google
     * отличается заданием :mark-as-done в URI для выполнения пользовательского метода.
     *
     * @PatchMapping Необходимо для того чтобы изменить данные в модели сущности Task,
     * без ответа на запрос
     *
     * @param id
     * @param task проверяется на булевское значение и если оно является FALSE,
     *             то тогда задача переводиться в раздел выполненой
     */
    @PatchMapping("/tasks/{id}:mark-as-done")
    public void patchMethod(@PathVariable Long id) {
        taskRepository.markAsDone(id);
    }
}

/**
 * REST
 * У нас теперь есть rest по созданию задачи. Но так же, нам нужно:
 * смотреть список
 * редактировать задание
 * удалять задание
 * отмечать задание, как выполненное
 *
 * Давайте пройдемся по http методам
 * GET - получает данные
 * POST - создает данные
 * PUT - обновляет существующие данные
 * DELETE - удаляет данные
 *
 * Получается, что нужно реализовать такие ресты:
 * (GET) /tasks - Список всех задач
 * (GET) /tasks/{id} - Задача по id. Пример /tasks/1
 * (PUT) /tasks/{id} - Редактирование задачи
 * (DELETE) /tasks/{id} - Удаление задачи
 *
 * Рест 'по отметке задачи, как выполнено, давайте оставим на потом. А пока вам нужно будет реализовать эти ресты самостоятельно.
 * Будут использованы аннотации: @GetMapping, @PutMapping, @PathVariable, @RequestBody
 * После того, как реализуете ресты, проверьте их работу через Postman.
 * В следующей странице спойлер. Не открывайте, пока не реализуете эти ресты.
 */
