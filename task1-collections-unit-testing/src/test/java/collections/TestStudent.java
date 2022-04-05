package collections;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestStudent extends StudentSamples {
    @Test
    public void compareTo_Works() {
        assertTrue(orderedStudentList.get(7).compareTo(orderedStudentList.get(6)) > 0);
        assertTrue(orderedStudentList.get(6).compareTo(orderedStudentList.get(5)) > 0);
        assertTrue(orderedStudentList.get(5).compareTo(orderedStudentList.get(4)) > 0);
    }
}
