package com.endava.internship.mocking.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class PaymentTest {

    @Test
    void copyOf_CopyOfPayment_IsNewObject() {
        Payment p1 = new Payment(0, 1.0, "payment1");
        Payment copyOfp1 = Payment.copyOf(p1);

        // TODO: How to test?
    }

    @Test
    void testEquals() {
        Payment p1 = new Payment(1, 1.0, "payment1");
        Payment p2 = new Payment(1, 1.0, "payment1");
        Payment p3 = new Payment(2, 1.0, "payment2");
        Payment p4 = new Payment(1, 2.0, "payment3");
        Payment copyOfp1 = Payment.copyOf(p1);

        assertThat(p1).isEqualTo(copyOfp1)
                .isNotEqualTo(p2)
                .isNotEqualTo(p3)
                .isNotEqualTo(p4);
    }

    @Test
    void testHashCode() {
        Payment p1 = new Payment(1, 1.0, "payment1");
        Payment p2 = new Payment(1, 1.0, "payment1");
        Payment p3 = new Payment(2, 1.0, "payment2");
        Payment p4 = new Payment(1, 2.0, "payment3");
        Payment copyOfp1 = Payment.copyOf(p1);

        assertThat(p1).isEqualTo(copyOfp1)
                .isNotEqualTo(p2)
                .isNotEqualTo(p3)
                .isNotEqualTo(p4);
    }
}