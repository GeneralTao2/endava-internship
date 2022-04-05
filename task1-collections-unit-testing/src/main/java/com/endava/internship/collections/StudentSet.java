package com.endava.internship.collections;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.*;
import java.util.function.Consumer;


public class StudentSet implements Set<Student> {

    @Getter
    @Setter
    @ToString
    private static class StudentNode {
        private Student student;
        private StudentNode greater;
        private StudentNode lesser;

        public StudentNode(Student student) {
            this.student = student;
        }

    }

    private class StudentSetIterator implements Iterator<Student> {
        private final Stack<StudentNode> stack;
        private Student lastReturned;

        public StudentSetIterator(StudentNode node) {
            stack = new Stack<>();
            StudentNode currentNode = node;

            while (currentNode != null) {
                stack.push(currentNode);
                currentNode = currentNode.getLesser();
            }
        }

        @Override
        public boolean hasNext() {
            return !stack.isEmpty();
        }

        @Override
        public Student next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            lastReturned = stack.peek().getStudent();
            StudentNode tmp = stack.peek().getGreater();
            stack.pop();

            while (tmp != null) {
                stack.push(tmp);
                tmp = tmp.getLesser();
            }
            return lastReturned;
        }

        @Override
        public void remove() {
            if (lastReturned == null) {
                throw new IllegalStateException();
            }
            tree = recursiveRemove(tree, lastReturned);
        }

        @Override
        public void forEachRemaining(Consumer<? super Student> action) {
            Iterator.super.forEachRemaining(action);
        }
    }

    private StudentNode tree;

    private boolean recursiveContains(StudentNode node, Student student) {
        if (node == null) {
            return false;
        }
        if (student.equals(node.getStudent())) {
            return true;
        }
        return student.compareTo(node.getStudent()) > 0
                ? recursiveContains(node.getGreater(), student)
                : recursiveContains(node.getLesser(), student);
    }

    private int recursiveCount(StudentNode node) {
        if (node == null) {
            return 0;
        }
        return 1 + recursiveCount(node.getGreater()) + recursiveCount(node.getLesser());
    }

    private StudentNode recursiveAdd(StudentNode node, Student student) {
        if (node == null) {
            return new StudentNode(student);
        }

        if (student.compareTo(node.getStudent()) < 0) {
            node.setLesser(recursiveAdd(node.getLesser(), student));
        } else if (student.compareTo(node.getStudent()) > 0) {
            node.setGreater(recursiveAdd(node.getGreater(), student));
        } else {
            return node;
        }

        return node;
    }

    private Student findSmallestStudent(StudentNode node) {
        return node.getLesser() == null ? node.getStudent() : findSmallestStudent(node.getLesser());
    }

    private StudentNode recursiveRemove(StudentNode node, Student student) {
        if (node == null) {
            return null;
        }
        if (student.equals(node.getStudent())) {
            if (node.getLesser() == null && node.getGreater() == null) {
                return null;
            }
            if (node.getLesser() == null) {
                return node.getGreater();
            }
            if (node.getGreater() == null) {
                return node.getLesser();
            }
            Student smallestStudent = findSmallestStudent(node.getGreater());
            node.setStudent(smallestStudent);
            node.setGreater(recursiveRemove(node.getGreater(), smallestStudent));
            return node;
        }
        if (student.compareTo(node.getStudent()) < 0) {
            node.setLesser(recursiveRemove(node.getLesser(), student));
        }
        node.setGreater(recursiveRemove(node.getGreater(), student));
        return node;
    }

    @Override
    public int size() {
        return recursiveCount(tree);
    }

    @Override
    public boolean isEmpty() {
        return tree.getStudent() == null;
    }

    @Override
    public boolean contains(Object o) {
        Student student = (Student) o;
        return recursiveContains(tree, student);
    }

    @Override
    public Iterator<Student> iterator() {
        return new StudentSetIterator(tree);
    }

    @Override
    @SuppressWarnings("UseBulkOperation")
    public Object[] toArray() {
        List<Student> list = new ArrayList<>();
        // if replace this
        for (Student student : this) {
            list.add(student);
        }
        // with this
        // list.addAll(this);
        // it will cause StackOverflowError
        return list.toArray();
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T[] toArray(T[] ts) {
        List<T> list = new ArrayList<>();
        for (Student student : this) {
            list.add((T)student);
        }
        return list.toArray(ts);
    }

    @Override
    public boolean add(Student student) {
        if (student == null) {
            throw new NullPointerException();
        }
        if (contains(student)) {
            return false;
        } else {
            tree = recursiveAdd(tree, student);
            return true;
        }
    }

    @Override
    public boolean remove(Object o) {
        Student student = (Student) o;
        if (contains(student)) {
            tree = recursiveRemove(tree, student);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void clear() {
        tree = null;
    }

    @Override
    public boolean addAll(Collection<? extends Student> collection) {
        Iterator<? extends Student> iterator = collection.iterator();
        boolean changed = false;
        while (iterator.hasNext()) {
            if (add(iterator.next())) {
                changed = true;
            }
        }
        return changed;
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        for(Object student: collection) {
            if(!contains(student)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        boolean retVal = false;
        for(Student student: this) {
            if (!collection.contains(student)) {
                tree = recursiveRemove(tree, student);
                retVal = true;
            }
        }
        return retVal;
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        boolean retVal = false;
        for(Object collectionStudent: collection) {
            if(remove(collectionStudent)) {
                retVal = true;
            }
        }
        return retVal;
    }

    @Override
    public String toString() {
        return "StudentSet{" +
                "tree=" + tree +
                '}';
    }
}
