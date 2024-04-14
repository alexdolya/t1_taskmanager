package ru.dolya.t1_taskmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.dolya.t1_taskmanager.model.entity.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
