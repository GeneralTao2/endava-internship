package com.endava.internship.webapp.validation.dto;

import com.endava.internship.webapp.model.Department;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DepartmentDto {

    Long id = 0L;

    @NotNull(message = "The location cannot be null")
    @NotBlank(message = "The name cannot be blank")
    String name;

    @NotNull(message = "The location cannot be null")
    @NotBlank(message = "The location cannot be blank")
    String location;

    public Department toDepartment() {
        return Department.builder()
                .id(getId())
                .name(name)
                .location(location)
                .build();

    }
}
