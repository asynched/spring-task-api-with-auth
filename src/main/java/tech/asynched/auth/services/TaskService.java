package tech.asynched.auth.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import tech.asynched.auth.dto.CreateTaskDto;
import tech.asynched.auth.entities.TaskEntity;
import tech.asynched.auth.repositories.TaskRepository;

@Slf4j()
@Service()
public class TaskService {
  private final TaskRepository taskRepository;

  @Autowired()
  public TaskService(TaskRepository taskRepository) {
    this.taskRepository = taskRepository;
  }

  public List<TaskEntity> findByUserId(Long userId) {
    return this.taskRepository.findByUserId(userId);
  }

  public TaskEntity findById(Long id, Long userId) {
    return this.taskRepository.findByIdAndUserId(id, userId).orElse(null);
  }

  public TaskEntity create(CreateTaskDto data, Long userId) {
    TaskEntity task = new TaskEntity();

    task.setContent(data.content());
    task.setUserId(userId);

    log.info("Dto: {}", data);
    log.info("Creating task: {}", task);

    return this.taskRepository.save(task);
  }
}
