package ru.dolya.t1_taskmanager.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.dolya.t1_taskmanager.model.dto.TaskRequestDto;
import ru.dolya.t1_taskmanager.model.dto.TaskResponseDto;
import ru.dolya.t1_taskmanager.service.TaskService;

import java.util.List;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @Operation(summary = "Получить список всех задач.")
    @GetMapping
    public List<TaskResponseDto> getAllTasks() {
        return taskService.getAllTasks();
    }

    @Operation(summary = "Получить информацию о задаче по её id.")
    @GetMapping("/{id}")
    public TaskResponseDto getTaskById(@PathVariable Long id) {
        return taskService.getTaskById(id);
    }

    @Operation(summary = "Создать новую задачу.")
    @PostMapping
    public TaskResponseDto createTask(@RequestBody TaskRequestDto taskDto) {
        return taskService.createTask(taskDto);
    }

    @Operation(summary = "Обновить информацию о задаче.")
    @PutMapping("/{id}")
    public TaskResponseDto updateTask(@PathVariable Long id, @RequestBody TaskRequestDto taskDto) {
        return taskService.updateTask(id, taskDto);
    }

    @Operation(summary = "Удалить задачу.")
    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
    }

}