package ru.dolya.t1_taskmanager.service.impl;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.dolya.t1_taskmanager.exception.TaskNotFoundException;
import ru.dolya.t1_taskmanager.model.dto.TaskRequestDto;
import ru.dolya.t1_taskmanager.model.dto.TaskResponseDto;
import ru.dolya.t1_taskmanager.model.entity.Task;
import ru.dolya.t1_taskmanager.repository.TaskRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskServiceImpl taskService;

    private static List<Task> taskList;

    private static Task task;

    @BeforeAll
    public static void init() {
        taskList = List.of(new Task().setTitle("FirstTaskTitle")
                        .setDescription("FirstTaskDescription1")
                        .setDueDate(LocalDateTime.of(2024, 4, 13, 8, 0, 0))
                        .setCompleted(false),
                new Task().setTitle("SecondTaskTitle")
                        .setDescription("SecondTaskDescription")
                        .setDueDate(LocalDateTime.of(2024, 4, 13, 8, 0, 0))
                        .setCompleted(false));
        task = new Task().setTitle("TaskTitle")
                .setDescription("TaskDescription")
                .setDueDate(LocalDateTime.of(2024, 4, 13, 8, 0, 0))
                .setCompleted(false);
    }

    @Test
    void getAllTasksSuccess() {
        doReturn(taskList).when(taskRepository).findAll();

        List<TaskResponseDto> allTasks = taskService.getAllTasks();

        assertEquals(2, allTasks.size());
        verify(taskRepository, times(1)).findAll();
    }

    @Test
    void getTaskByIdSuccess() {
        doReturn(Optional.of(new Task().setTitle("TaskTitle")
                .setDescription("TaskDescription")
                .setDueDate(LocalDateTime.of(2024, 4, 13, 8, 0, 0))
                .setCompleted(false))).when(taskRepository).findById(anyLong());

        TaskResponseDto taskResponseDto = taskService.getTaskById(anyLong());

        assertEquals("TaskTitle", taskResponseDto.getTitle());
        assertEquals("TaskDescription", taskResponseDto.getDescription());
        assertEquals(LocalDateTime.of(2024, 4, 13, 8, 0, 0), taskResponseDto.getDueDate());
        assertFalse(taskResponseDto.isCompleted());
        verify(taskRepository, times(1)).findById(anyLong());
    }

    @Test
    void getTaskByIdThrowsException() {
        doReturn(Optional.empty()).when(taskRepository).findById(anyLong());

        assertThrows(TaskNotFoundException.class, () -> taskService.getTaskById(anyLong()));
        verify(taskRepository, times(1)).findById(anyLong());
    }

    @Test
    void createTaskSuccess() {
        doReturn(task).when(taskRepository).save(any(Task.class));

        TaskResponseDto taskResponseDto = taskService.createTask(new TaskRequestDto());

        assertEquals("TaskTitle", taskResponseDto.getTitle());
        assertEquals("TaskDescription", taskResponseDto.getDescription());
        assertEquals(LocalDateTime.of(2024, 4, 13, 8, 0, 0), taskResponseDto.getDueDate());
        assertFalse(taskResponseDto.isCompleted());
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    void updateTaskSuccess() {
        doReturn(Optional.of(new Task())).when(taskRepository).findById(anyLong());
        doReturn(task).when(taskRepository).save(any(Task.class));

        TaskResponseDto taskResponseDto = taskService.updateTask(1L, new TaskRequestDto().setTitle("TaskTitle")
                .setDescription("TaskDescription")
                .setDueDate(LocalDateTime.of(2024, 4, 13, 8, 0, 0))
                .setCompleted(false));

        verify(taskRepository, times(1)).findById(anyLong());
        verify(taskRepository, times(1)).save(any(Task.class));
        assertEquals("TaskTitle", taskResponseDto.getTitle());
        assertEquals("TaskDescription", taskResponseDto.getDescription());
        assertEquals(LocalDateTime.of(2024, 4, 13, 8, 0, 0), taskResponseDto.getDueDate());
        assertFalse(taskResponseDto.isCompleted());
    }

    @Test
    void updateTaskThrowsException() {
        doReturn(Optional.empty()).when(taskRepository).findById(anyLong());

        assertThrows(TaskNotFoundException.class, () -> taskService.updateTask(1L, new TaskRequestDto().setTitle("TaskTitle")
                .setDescription("TaskDescription")
                .setDueDate(LocalDateTime.of(2024, 4, 13, 8, 0, 0))
                .setCompleted(false)));
        verify(taskRepository, times(1)).findById(anyLong());
        verify(taskRepository, times(0)).save(any(Task.class));
    }

    @Test
    void deleteTaskSuccess() {
        doReturn(Optional.of(task)).when(taskRepository).findById(anyLong());
        taskService.deleteTask(1L);
        verify(taskRepository, times(1)).findById(anyLong());
        verify(taskRepository, times(0)).delete(any(Task.class));
    }

    @Test
    void deleteTaskThrowsException() {
        doReturn(Optional.empty()).when(taskRepository).findById(anyLong());
        assertThrows(TaskNotFoundException.class, () -> taskService.deleteTask(1L));
        verify(taskRepository, times(1)).findById(anyLong());
        verify(taskRepository, times(0)).delete(any(Task.class));
    }

}