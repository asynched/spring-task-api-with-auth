package tech.asynched.auth.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import tech.asynched.auth.dto.CreateTaskDto;
import tech.asynched.auth.entities.TaskEntity;
import tech.asynched.auth.entities.UserEntity;
import tech.asynched.auth.services.TaskService;

@RestController()
@RequestMapping("/tasks")
public class TaskController {
  private final TaskService taskService;

  @Autowired()
  public TaskController(TaskService taskService) {
    this.taskService = taskService;
  }

  @GetMapping()
  public List<TaskEntity> findAll(
      @AuthenticationPrincipal UserEntity user) {
    return this.taskService.findByUserId(user.getId());
  }

  @GetMapping("/{id}")
  public TaskEntity findById(
      @AuthenticationPrincipal UserEntity user,
      Long id) {
    return this.taskService.findById(id, user.getId());
  }

  @PostMapping()
  public TaskEntity create(
      @AuthenticationPrincipal() UserEntity user, @RequestBody() @Valid() CreateTaskDto data) {
    return this.taskService.create(
        data,
        user.getId());
  }
}
