package collections;

import com.endava.internship.collections.Student;
import com.endava.internship.collections.StudentSet;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestStudentSetToString {
    @Test
    public void toString_Works() {
        Set<Student> students = new StudentSet() {{
            addAll(Arrays.asList(
                    new Student("e", LocalDate.of(2005, 5, 5), "st1"),
                    new Student("c", LocalDate.of(2004, 4, 4), "st2"),
                    new Student("g", LocalDate.of(2003, 3, 3), "st3"),
                    new Student("b", LocalDate.of(2002, 2, 2), "st4"),
                    new Student("d", LocalDate.of(2002, 2, 2), "st5"),
                    new Student("h", LocalDate.of(2002, 2, 2), "st6"),
                    new Student("d", LocalDate.of(2003, 2, 2), "st7"),
                    new Student("d", LocalDate.of(2001, 2, 2), "st8")
            ));
        }};
        String output = "StudentSet{tree=StudentSet.StudentNode(student=Student(name=e, dateOfBirth=2005-05-05, details=st1), greater=StudentSet.StudentNode(student=Student(name=g, dateOfBirth=2003-03-03, details=st3), greater=StudentSet.StudentNode(student=Student(name=h, dateOfBirth=2002-02-02, details=st6), greater=null, lesser=null), lesser=null), lesser=StudentSet.StudentNode(student=Student(name=c, dateOfBirth=2004-04-04, details=st2), greater=StudentSet.StudentNode(student=Student(name=d, dateOfBirth=2002-02-02, details=st5), greater=StudentSet.StudentNode(student=Student(name=d, dateOfBirth=2003-02-02, details=st7), greater=null, lesser=null), lesser=StudentSet.StudentNode(student=Student(name=d, dateOfBirth=2001-02-02, details=st8), greater=null, lesser=null)), lesser=StudentSet.StudentNode(student=Student(name=b, dateOfBirth=2002-02-02, details=st4), greater=null, lesser=null)))}";
        assertEquals(output, students.toString());
    }
}
