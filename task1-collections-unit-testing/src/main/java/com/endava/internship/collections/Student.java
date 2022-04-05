package com.endava.internship.collections;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.Objects;

/**
 * The class that defines the element that will be contained by your collection
 */
@Data
@AllArgsConstructor
public class Student implements Comparable<Student>
{
    private String name;
    private LocalDate dateOfBirth;
    private String details;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return name.equals(student.name) && dateOfBirth.equals(student.dateOfBirth);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, dateOfBirth);
    }

    @Override
    public int compareTo(Student o) {
        return Comparator.comparing(Student::getName)
                .thenComparing(Student::getDateOfBirth)
                .compare(this, o);
    }
}
