package com.endava.internship.webapp.validation.dto;

import com.endava.internship.webapp.model.Department;
import com.endava.internship.webapp.validation.constraints.ClearNotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DepartmentDto {

    public final static String NAME_NULL_MESSAGE =  "The name cannot be null";
    public final static String NAME_BLANK_MESSAGE =  "The name cannot be blank";
    public final static String LOCATION_NULL_MESSAGE =  "The location cannot be null";
    public final static String LOCATION_BLANK_MESSAGE =  "The location cannot be blank";

    Long id = 0L;

    @NotNull(message = NAME_NULL_MESSAGE)
    @ClearNotBlank(message = NAME_BLANK_MESSAGE)
    String name;

    @NotNull(message = LOCATION_NULL_MESSAGE)
    @ClearNotBlank(message = LOCATION_BLANK_MESSAGE)
    String location;

    public Department toDepartment() {
        return Department.builder()
                .id(getId())
                .name(name)
                .location(location)
                .build();

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DepartmentDto that = (DepartmentDto) o;
        return Objects.equals(id, that.id) && name.equals(that.name) && location.equals(that.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "DepartmentDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}
