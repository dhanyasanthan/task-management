package com.cn.taskmanagement.mapper;

import com.cn.taskmanagement.dto.TaskDto;
import com.cn.taskmanagement.model.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TaskUpdateMapper {
    TaskUpdateMapper INSTANCE = Mappers.getMapper(TaskUpdateMapper.class);

}