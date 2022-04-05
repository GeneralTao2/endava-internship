package collections;

import com.endava.internship.collections.Student;
import org.junit.jupiter.api.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class TestStudentSet extends StudentSamples {

    @Test
    public void contains_Works() {
        students.addAll(Arrays.asList(st1, st3, st4, st6, st7, st8));
        assertTrue(students.contains(st1));
        assertTrue(students.contains(st3));
        assertFalse(students.contains(st2));
        assertFalse(students.contains(st5));
    }

    @Test
    public void add_Works() {
        for(Student student: unorderedStudentList) {
            assertTrue(students.add(student));
        }
        assertFalse(students.add(st1));
        assertFalse(students.add(st3));
    }

    @Test
    public void addAll_Works() {
        assertTrue(students.addAll(Arrays.asList(st1, st2, st3, st4, st5, st6)));
        assertTrue(students.addAll(Arrays.asList(st1, st2, st3, st4, st8)));
        assertFalse(students.addAll(Arrays.asList(st1, st2, st3, st4)));
        assertTrue(students.contains(st2));
    }

    @Test
    public void remove_Works() {
        students.addAll(unorderedStudentList);

        assertTrue(students.remove(st2));
        assertFalse(students.contains(st2));
        assertTrue(students.remove(st4));
        assertTrue(students.contains(st1));
        assertTrue(students.contains(st3));
        assertTrue(students.contains(st8));
    }

    @Test
    public void size_Works() {
        students.addAll(Arrays.asList(st1, st2, st3, st4, st5));

        assertEquals(5, students.size());
        students.addAll(Arrays.asList(st6, st7, st8));
        assertEquals(8, students.size());
    }

    @Test
    @SuppressWarnings("ConstantConditions")
    public void clear_Works() {
        students.addAll(unorderedStudentList);
        students.clear();

        assertEquals(0, students.size());
    }

    @Test
    public void toObjectArray_Works() {
        students.addAll(unorderedStudentList);

        assertArrayEquals(students.toArray(), orderedStudentList.toArray());
    }

    @Test
    public void toGenericArray_Works() {
        students.addAll(unorderedStudentList);

        assertArrayEquals(students.toArray(new Object[]{}), orderedStudentList.toArray());
    }

    @Test
    public void containsAll_Works() {
        students.addAll(Arrays.asList(st1, st2, st3, st4, st5, st6, st7));

        assertTrue(students.containsAll(Arrays.asList(st1, st2, st3, st4, st5, st6, st7)));
        assertTrue(students.containsAll(Arrays.asList(st1, st3, st4)));
        assertFalse(students.containsAll(unorderedStudentList));
        assertFalse(students.containsAll(Arrays.asList(st4, st8)));
    }

    @Test
    public void retainsAll_Works() {
        students.addAll(unorderedStudentList);

        assertTrue(students.retainAll(Arrays.asList(st1, st2, st3, st4, st5, st6)));
        assertFalse(students.retainAll(Arrays.asList(st1, st2, st3, st4, st5, st6)));
        assertTrue(students.containsAll(Arrays.asList(st1, st2, st3, st4, st5, st6)));
        assertTrue(students.retainAll(Arrays.asList(st1, st2, st3, st4)));
        assertFalse(students.containsAll(Arrays.asList(st7, st8, st5, st6)));
        assertTrue(students.retainAll(Arrays.asList(st6, st7)));
        assertEquals(0, students.size());
    }

    @Test
    @SuppressWarnings("SlowAbstractSetRemoveAll")
    public void removeAll_Works() {
        students.addAll(unorderedStudentList);

        assertTrue(students.removeAll(Arrays.asList(st1, st2, st3, st4, st5)));
        assertFalse(students.removeAll(Arrays.asList(st1, st2, st3, st4, st5)));
        assertTrue(students.containsAll(Arrays.asList(st6, st7, st8)));
        assertTrue(students.removeAll(Arrays.asList(st6, st7, st8)));
        assertEquals(0, students.size());
    }


}
