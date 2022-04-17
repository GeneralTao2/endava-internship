package com.endava.internship.mocking.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void testEquals() {
        User u1 = new User(0, "user1", Status.ACTIVE);
        User u2 = new User(0, "user1", Status.ACTIVE);
        User u3 = new User(1, "user1", Status.ACTIVE);
        User u4 = new User(0, "user2", Status.ACTIVE);
        User u5 = new User(0, "user1", Status.INACTIVE);

        assertThat(u1).isEqualTo(u2)
                .isNotEqualTo(u3)
                .isNotEqualTo(u4)
                .isNotEqualTo(u5);
    }

    @Test
    void testHashCode() {
        User u1 = new User(0, "user1", Status.ACTIVE);
        User u2 = new User(0, "user1", Status.ACTIVE);
        User u3 = new User(1, "user1", Status.ACTIVE);
        User u4 = new User(0, "user2", Status.ACTIVE);
        User u5 = new User(0, "user1", Status.INACTIVE);

        assertThat(u1).isEqualTo(u2)
                .isNotEqualTo(u3)
                .isNotEqualTo(u4)
                .isNotEqualTo(u5);
    }
}