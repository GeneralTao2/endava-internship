package collections;

import com.endava.internship.collections.Student;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestIterator extends StudentSamples {
    @Test
    public void next_ThrowsException() {
        students.addAll(unorderedStudentList);
        Iterator<Student> iterator = students.iterator();

        while (iterator.hasNext()) {
            iterator.next();
        }

        assertThrows(NoSuchElementException.class, iterator::next);
    }

    @Test
    public void next_Works() {
        students.addAll(unorderedStudentList);
        Iterator<Student> testedIterator = students.iterator();
        Iterator<Student> orderedIterator = orderedStudentList.iterator();

        while (testedIterator.hasNext()) {
            assertEquals(orderedIterator.next(), testedIterator.next());
        }
    }

    @Test
    public void remove_Works() {
        students.addAll(unorderedStudentList);
        Iterator<Student> iterator = students.iterator();

        assertEquals(orderedStudentList.get(0), iterator.next());
        iterator.remove();
        assertEquals(orderedStudentList.get(1), iterator.next());
        assertEquals(orderedStudentList.get(2), iterator.next());
        iterator.remove();
        assertEquals(orderedStudentList.get(3), iterator.next());
        assertEquals(orderedStudentList.get(4), iterator.next());
        assertEquals(orderedStudentList.get(5), iterator.next());
        iterator.remove();
        assertEquals(orderedStudentList.get(6), iterator.next());
        assertEquals(orderedStudentList.get(7), iterator.next());
        iterator.remove();
        assertFalse(students.contains(orderedStudentList.get(0)));
        assertFalse(students.contains(orderedStudentList.get(2)));
        assertFalse(students.contains(orderedStudentList.get(5)));
        assertTrue(students.contains(orderedStudentList.get(1)));
        assertTrue(students.contains(orderedStudentList.get(3)));
        assertTrue(students.contains(orderedStudentList.get(4)));
        assertTrue(students.contains(orderedStudentList.get(6)));
    }
}
