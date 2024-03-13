package com.leo.todosimple.services;

import com.leo.todosimple.models.Task;
import com.leo.todosimple.models.User;
import com.leo.todosimple.repositories.TaskRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserService userService;

    public Task findById(Long id) {
        Optional<Task> task = taskRepository.findById(id);
        return task.orElseThrow(() -> new RuntimeException("Tarefa não encontrada."));
    }

    @Transactional
    public Task create(Task obj) {
        User user = userService.findById(obj.getUser().getId());
        obj.setId(null);
        obj.setUser(user);
        obj = taskRepository.save(obj);
        return obj;
    }

    @Transactional
    public Task update(Task obj) {
        Task newObj = findById(obj.getId());
        newObj.setDescription(obj.getDescription());
        return taskRepository.save(newObj);
    }

    public void delete(Long id) {
        findById(id);
        try {
            taskRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Não é possível deletar pois há entidades relacionadas!");
        }
    }

}
