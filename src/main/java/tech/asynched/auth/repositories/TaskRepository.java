package tech.asynched.auth.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tech.asynched.auth.entities.TaskEntity;

@Repository()
public interface TaskRepository extends JpaRepository<TaskEntity, Long> {
  List<TaskEntity> findByUserId(Long userId);

  Optional<TaskEntity> findByIdAndUserId(Long id, Long userId);
}
