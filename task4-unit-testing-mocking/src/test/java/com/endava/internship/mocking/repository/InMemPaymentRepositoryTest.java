package com.endava.internship.mocking.repository;

import com.endava.internship.mocking.model.Payment;
import com.endava.internship.mocking.model.Status;
import com.endava.internship.mocking.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

class InMemPaymentRepositoryTest {
    Payment p1, p2, p3, p4, p5, copyOfp1;
    InMemPaymentRepository imp;

    @BeforeEach
    void setUp() {
        //u1 = new User(0, "user0", Status.ACTIVE);
        //copyOfu1 = new User(0, "user0", Status.ACTIVE);
        //u2 = new User(1, "user1", Status.ACTIVE);
        //u3 = new User(2, "user2", Status.ACTIVE);
        //u4 = new User(3, "user3", Status.ACTIVE);
        //u5 = new User(4, "user4", Status.ACTIVE);
        p1 = new Payment(0, 1.0, "payment1");
        p2 = new Payment(0, 2.0, "payment2");
        p3 = new Payment(1, 3.0, "payment3");
        p4 = new Payment(2, 4.0, "payment4");
        p5 = new Payment(3, 5.0, "payment5");
        copyOfp1 = Payment.copyOf(p1);
        imp = new InMemPaymentRepository();
    }

    @Test
    void findById_GiveExistingUserUUID_ReturnRightPayment() {
        imp.save(p1);
        imp.save(p2);
        imp.save(p3);

        Optional<Payment> op1 = imp.findById(p2.getPaymentId());

        assertThat(op1).contains(p2);
    }

    @Test
    void findById_GiveExistingUserUUID_ReturnEmptyOptional() {
        imp.save(p1);
        imp.save(p2);
        imp.save(p3);

        Optional<Payment> op1 = imp.findById(p4.getPaymentId());

        assertThat(op1).isEmpty();
    }

    @Test
    void findById_GiveExistingUserUUID_ThrowsException() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> imp.findById(null))
                .withMessage("Payment id must not be null")
                .withNoCause();
    }

    @Test
    void findAll_FilledRepo_ReturnAllPayments() {
        imp.save(p1);
        imp.save(p2);
        imp.save(p3);

        List<Payment> paymentList = imp.findAll();

        assertThat(paymentList).contains(p1, p2, p3);
    }

    @Test
    void findAll_EmptyRepo_ReturnEmptyList() {
        List<Payment> paymentList = imp.findAll();

        assertThat(paymentList).isEmpty();
    }

    @Test
    void save_SavePayment_ReturnSavedPayment() {
        Payment returned_p1 = imp.save(p1);
        Optional<Payment> op1 = imp.findById(p1.getPaymentId());

        assertThat(returned_p1).isEqualTo(p1);
        assertThat(op1).contains(p1);

    }

    @Test
    void save_SaveNull_ThrowException() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> imp.save(null))
                .withMessage("Payment must not be null")
                .withNoCause();
    }

    @Test
    void save_SaveAlreadySavedNonNullIdPayment_ThrowException() {
        imp.save(p1);
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> imp.save(p1))
                .withMessage("Payment with id " + p1.getPaymentId() + "already saved")
                .withNoCause();
    }


    @Test
    void editMessage_ExistingPayment_ReturnEditedPayment() {
        imp.save(p1);
        String newMessage = "editedP1";

        Payment edited_p1 = imp.editMessage(p1.getPaymentId(), newMessage);

        assertThat(edited_p1.getMessage()).isEqualTo(newMessage);
        assertThat(edited_p1).isEqualTo(p1);
    }

    @Test
    void editMessage_NotExistingPayment_ThrowsException() {
        assertThatExceptionOfType(NoSuchElementException.class)
                .isThrownBy(() -> imp.editMessage(p1.getPaymentId(), "newMessage"))
                .withMessage("Payment with id " + p1.getPaymentId() + " not found")
                .withNoCause();
    }
}