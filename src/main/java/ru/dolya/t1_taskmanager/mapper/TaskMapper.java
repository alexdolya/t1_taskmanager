package ru.dolya.t1_taskmanager.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.dolya.t1_taskmanager.model.dto.TaskRequestDto;
import ru.dolya.t1_taskmanager.model.dto.TaskResponseDto;
import ru.dolya.t1_taskmanager.model.entity.Task;

@Mapper
public interface TaskMapper {
    TaskMapper INSTANCE = Mappers.getMapper(TaskMapper.class);

    TaskResponseDto taskToTaskResponseDto(Task task);

    Task taskRequesDtoToTask(TaskRequestDto taskDto);

}
