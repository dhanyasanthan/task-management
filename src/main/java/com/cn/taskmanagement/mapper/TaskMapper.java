package com.cn.taskmanagement.mapper;

import com.cn.taskmanagement.dto.ProjectDto;
import com.cn.taskmanagement.dto.TaskDto;
import com.cn.taskmanagement.model.Project;
import com.cn.taskmanagement.model.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TaskMapper {
    TaskMapper INSTANCE = Mappers.getMapper(TaskMapper.class);

    @Mappings({
            @Mapping(target = "project", source = "project"),
    })
    TaskDto taskToTaskDto(Task task);

    Task taskDtoToTask(TaskDto taskDto);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "project", ignore = true),
            @Mapping(target = "title", source = "taskDto.title"),
            @Mapping(target = "description", source = "taskDto.description"),
            @Mapping(target = "status", source = "taskDto.status"),
            @Mapping(target = "priority", source = "taskDto.priority"),
            @Mapping(target = "deadline", source = "taskDto.deadline")
            // Map other properties as needed
    })

    Task updateTaskFromDto(Task task, TaskDto taskDto);


    ProjectDto projectToProjectDto(Project project);

    Project projectDtoToProject(ProjectDto projectDto);


    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "project", ignore = true)
    })
    void updateTaskProjectFromDto(@MappingTarget Task task, TaskDto taskDto);

}