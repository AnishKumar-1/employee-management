package com.management.mappers;

import com.management.dto.employeeDto.EmployeeRequest;
import com.management.dto.employeeDto.EmployeeResponse;
import com.management.models.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {


//    @Mapping(source = "user.email", target = "email")
//    @Mapping(source = "department.name", target = "departmentName")
//    @Mapping(source = "designation.title", target = "designation")
//    @Mapping(target = "projects", expression = "java(mapProjectNames(employeeModel.getProjects()))")
    EmployeeResponse toResponse(EmployeeModel employeeModel);

    // custom method to extract project names
//    default Set<String> mapProjectNames(Set<ProjectModel> projects) {
//        if (projects == null) return Set.of();
//        return projects.stream().map(ProjectModel::getName).collect(Collectors.toSet());
//    }

    // Custom method to build entity with external objects
//

}
