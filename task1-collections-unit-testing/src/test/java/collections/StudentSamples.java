package collections;

import com.endava.internship.collections.Student;
import com.endava.internship.collections.StudentSet;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class StudentSamples {
    Set<Student> students;
    static Student st1, st2, st3, st4, st5, st6, st7, st8;
    static List<Student> orderedStudentList;
    static List<Student> unorderedStudentList;

    @BeforeEach
    public void setUp() {
        st1 = new Student("w", LocalDate.of(2005, 5, 5), "st1");
        st2 = new Student("c", LocalDate.of(2004, 4, 4), "st2");
        st3 = new Student("j", LocalDate.of(2003, 3, 3), "st3");
        st4 = new Student("a", LocalDate.of(2002, 2, 2), "st4");
        st5 = new Student("z", LocalDate.of(2002, 2, 2), "st5");
        st6 = new Student("h", LocalDate.of(2002, 2, 2), "st6");
        st7 = new Student("a", LocalDate.of(2003, 2, 2), "st7");
        st8 = new Student("d", LocalDate.of(2001, 2, 2), "st8");

        unorderedStudentList = Arrays.asList(st1, st2, st3, st4, st5, st6, st7, st8);
        orderedStudentList = unorderedStudentList;
        Collections.sort(orderedStudentList);
        students = new StudentSet();
    }
}
