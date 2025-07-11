package com.management.mappers;

import com.management.dto.employeeDto.UpdateEmployee;
import com.management.models.EmployeeModel;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.stereotype.Component;

//@Mapper(componentModel = "spring")
public interface UpdateEmpMapper {
//
//
//    //update non null value
//    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
//    void updateEmpMap(UpdateEmployee request, @MappingTarget EmployeeModel model);
}
