package com.cn.taskmanagement.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TaskUpdateMapper {
    TaskUpdateMapper INSTANCE = Mappers.getMapper(TaskUpdateMapper.class);

}