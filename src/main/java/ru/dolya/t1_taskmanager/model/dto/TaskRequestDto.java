package ru.dolya.t1_taskmanager.model.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class TaskRequestDto {

    private String title;

    private String description;

    private LocalDateTime dueDate;

    private boolean completed;

}
