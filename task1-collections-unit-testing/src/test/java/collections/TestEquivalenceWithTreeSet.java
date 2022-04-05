package collections;

import com.endava.internship.collections.Student;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestEquivalenceWithTreeSet extends StudentSamples {
    Set<Student> treeSet;

    @BeforeEach
    public void setUp() {
        super.setUp();
        treeSet = new TreeSet<>();
        students.addAll(unorderedStudentList);
        treeSet.addAll(unorderedStudentList);
    }

    @Test
    public void iterator_Equals() {
        Iterator<Student> studentIterator = students.iterator();
        Iterator<Student> treeSetIterator = treeSet.iterator();

        while (studentIterator.hasNext()) {
            assertEquals(treeSetIterator.next(), studentIterator.next());
        }
    }

    @Test
    public void dateOfBirth_Equals() {
        Iterator<Student> studentIterator = students.iterator();
        Iterator<Student> treeSetIterator = treeSet.iterator();

        while (studentIterator.hasNext()) {
            assertEquals(treeSetIterator.next().getDateOfBirth(), studentIterator.next().getDateOfBirth());
        }
    }

    @Test
    public void toObjectArray_Equals() {
        assertArrayEquals(students.toArray(), treeSet.toArray());
    }

    @Test
    public void toGenericArray_Equals() {
        Student[] studentArray = new Student[10];

        assertArrayEquals(students.toArray(studentArray), treeSet.toArray(studentArray));
    }
}
