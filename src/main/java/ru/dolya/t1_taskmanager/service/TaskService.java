package ru.dolya.t1_taskmanager.service;


import ru.dolya.t1_taskmanager.model.dto.TaskRequestDto;
import ru.dolya.t1_taskmanager.model.dto.TaskResponseDto;

import java.util.List;

public interface TaskService {
    List<TaskResponseDto> getAllTasks();

    TaskResponseDto getTaskById(Long id);


    TaskResponseDto createTask(TaskRequestDto taskDto);


    TaskResponseDto updateTask(Long id, TaskRequestDto taskDto);

    void deleteTask(Long id);
}
