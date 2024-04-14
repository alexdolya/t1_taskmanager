package ru.dolya.t1_taskmanager.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.dolya.t1_taskmanager.exception.TaskNotFoundException;
import ru.dolya.t1_taskmanager.mapper.TaskMapper;
import ru.dolya.t1_taskmanager.model.dto.TaskRequestDto;
import ru.dolya.t1_taskmanager.model.dto.TaskResponseDto;
import ru.dolya.t1_taskmanager.model.entity.Task;
import ru.dolya.t1_taskmanager.repository.TaskRepository;
import ru.dolya.t1_taskmanager.service.TaskService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    @Override
    @Transactional
    public List<TaskResponseDto> getAllTasks() {
        return taskRepository.findAll().stream()
                .map(TaskMapper.INSTANCE::taskToTaskResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public TaskResponseDto getTaskById(Long id) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (optionalTask.isPresent()) {
            return TaskMapper.INSTANCE.taskToTaskResponseDto(optionalTask.get());
        } else {
            throw new TaskNotFoundException("Задача с id:" + id + " не найдена.");
        }
    }

    @Override
    @Transactional
    public TaskResponseDto createTask(TaskRequestDto taskDto) {
        Task task = TaskMapper.INSTANCE.taskRequesDtoToTask(taskDto);
        return TaskMapper.INSTANCE.taskToTaskResponseDto(taskRepository.save(task));
    }

    @Override
    @Transactional
    public TaskResponseDto updateTask(Long id, TaskRequestDto taskDto) {
        Optional<Task> existingTaskOptional = taskRepository.findById(id);
        if (existingTaskOptional.isPresent()) {
            Task task = TaskMapper.INSTANCE.taskRequesDtoToTask(taskDto);
            Task existingTask = existingTaskOptional.get();
            existingTask.setTitle(task.getTitle());
            existingTask.setDescription(task.getDescription());
            existingTask.setDueDate(task.getDueDate());
            existingTask.setCompleted(task.isCompleted());
            return TaskMapper.INSTANCE.taskToTaskResponseDto(taskRepository.save(existingTask));
        } else {
            throw new TaskNotFoundException("Задача с id:" + id + " не найдена.");
        }
    }

    @Override
    @Transactional
    public void deleteTask(Long id) {
        Optional<Task> taskOptional = taskRepository.findById(id);
        if (taskOptional.isPresent()) {
            taskRepository.deleteById(id);
        } else {
            throw new TaskNotFoundException("Задача с id:" + id + " не найдена.");
        }
    }

}
