package com.example.reservasSpring.domain.mapper;


import com.example.reservasSpring.domain.dto.JobDto;
import com.example.reservasSpring.domain.model.Job;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel  = MappingConstants.ComponentModel.SPRING)
public interface JobMapper {

    @Mappings({
            @Mapping(source = "name",target = "name"),
            @Mapping(source = "id",target = "id")
    })
    JobDto toJobDto(Job job);

    @InheritInverseConfiguration
    Job toJob (JobDto jobDto);

    List<JobDto> JobDtoList(List<Job> jobList);
    List<Job> JobList(List<JobDto> jobDtoList);
}


