package com.management.dto.employeeDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UpdateEmployee {

    private String firstName;
    private String lastName;
    private String phoneNumber;

}
