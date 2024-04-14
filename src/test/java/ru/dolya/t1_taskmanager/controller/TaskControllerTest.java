package ru.dolya.t1_taskmanager.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.dolya.t1_taskmanager.exception.TaskNotFoundException;
import ru.dolya.t1_taskmanager.model.dto.TaskRequestDto;
import ru.dolya.t1_taskmanager.model.dto.TaskResponseDto;
import ru.dolya.t1_taskmanager.service.TaskService;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskControllerTest {

    @Mock
    TaskService taskService;

    @InjectMocks
    TaskController taskController;

    @Test
    void getAllTasksSuccess() {
        doReturn(List.of(new TaskResponseDto().setTitle("FirstTaskTitle")
                        .setDescription("FirstTaskDescription1")
                        .setDueDate(LocalDateTime.of(2024, 4, 13, 8, 0, 0))
                        .setCompleted(false),
                new TaskResponseDto().setTitle("SecondTaskTitle")
                        .setDescription("SecondTaskDescription")
                        .setDueDate(LocalDateTime.of(2024, 4, 13, 8, 0, 0))
                        .setCompleted(false)))
                .when(taskService).getAllTasks();

        List<TaskResponseDto> allTasks = taskController.getAllTasks();

        assertEquals(2, allTasks.size());
        verify(taskService, times(1)).getAllTasks();
    }

    @Test
    void getTaskByIdSuccess() {
        doReturn(new TaskResponseDto().setTitle("FirstTaskTitle")
                .setDescription("FirstTaskDescription")
                .setDueDate(LocalDateTime.of(2024, 4, 13, 8, 0, 0))
                .setCompleted(false))
                .when(taskService).getTaskById(anyLong());

        TaskResponseDto taskResponseDto = taskController.getTaskById(anyLong());

        assertEquals("FirstTaskTitle", taskResponseDto.getTitle());
        assertEquals("FirstTaskDescription", taskResponseDto.getDescription());
        assertEquals(LocalDateTime.of(2024, 4, 13, 8, 0, 0), taskResponseDto.getDueDate());
        assertFalse(taskResponseDto.isCompleted());
        verify(taskService, times(1)).getTaskById(anyLong());
    }

    @Test
    void getTaskByIdThrowsException() {
        doThrow(new TaskNotFoundException("Задача не найдена."))
                .when(taskService).getTaskById(anyLong());

        assertThrows(TaskNotFoundException.class, () -> taskController.getTaskById(anyLong()));
        verify(taskService, times(1)).getTaskById(anyLong());
    }

    @Test
    void createTaskSuccess() {
        TaskRequestDto taskRequestDto = new TaskRequestDto().setTitle("TaskTitle")
                .setDescription("TaskDescription")
                .setDueDate(LocalDateTime.of(2024, 4, 13, 8, 0, 0))
                .setCompleted(false);
        doReturn(new TaskResponseDto().setTitle("TaskTitle")
                .setDescription("TaskDescription")
                .setDueDate(LocalDateTime.of(2024, 4, 13, 8, 0, 0))
                .setCompleted(false))
                .when(taskService).createTask(taskRequestDto);

        TaskResponseDto taskResponseDto = taskController.createTask(taskRequestDto);

        assertEquals(taskRequestDto.getTitle(), taskResponseDto.getTitle());
        assertEquals(taskRequestDto.getDescription(), taskResponseDto.getDescription());
        assertEquals(taskRequestDto.getDueDate(), taskResponseDto.getDueDate());
        assertEquals(taskRequestDto.isCompleted(), taskResponseDto.isCompleted());

        verify(taskService, times(1)).createTask(taskRequestDto);
    }

    @Test
    void updateTaskSuccess() {
        TaskRequestDto taskRequestDto = new TaskRequestDto().setTitle("TaskTitle")
                .setDescription("TaskDescription")
                .setDueDate(LocalDateTime.of(2024, 4, 13, 8, 0, 0))
                .setCompleted(false);
        doReturn(new TaskResponseDto().setTitle("TaskTitle")
                .setDescription("TaskDescription")
                .setDueDate(LocalDateTime.of(2024, 4, 13, 8, 0, 0))
                .setCompleted(false))
                .when(taskService).updateTask(1L, taskRequestDto);

        TaskResponseDto taskResponseDto = taskController.updateTask(1L, taskRequestDto);

        assertEquals(taskRequestDto.getTitle(), taskResponseDto.getTitle());
        assertEquals(taskRequestDto.getDescription(), taskResponseDto.getDescription());
        assertEquals(taskRequestDto.getDueDate(), taskResponseDto.getDueDate());
        assertEquals(taskRequestDto.isCompleted(), taskResponseDto.isCompleted());
        verify(taskService, times(1)).updateTask(1L, taskRequestDto);
    }

    @Test
    void updateTaskThrowsException() {
        doThrow(new TaskNotFoundException("Задача не найдена."))
                .when(taskService).updateTask(anyLong(), any());

        assertThrows(TaskNotFoundException.class, () -> taskController.updateTask(anyLong(), any()));
        verify(taskService, times(1)).updateTask(anyLong(), any());
    }

    @Test
    void deleteTaskSuccess() {
        taskController.deleteTask(anyLong());
        verify(taskService, times(1)).deleteTask(anyLong());
    }

    @Test
    void deleteTaskThrowsException() {
        doThrow(new TaskNotFoundException("Задача не найдена."))
                .when(taskService).deleteTask(anyLong());

        assertThrows(TaskNotFoundException.class, () -> taskController.deleteTask(anyLong()));
        verify(taskService, times(1)).deleteTask(anyLong());
    }
}