package com.endava.internship.mocking.service;

import com.endava.internship.mocking.model.Status;
import com.endava.internship.mocking.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

class BasicValidationServiceTest {

    private BasicValidationService bvs;

    @BeforeEach
    void setUp() {
        bvs = new BasicValidationService();
    }

    @Test
    void validateAmount_AmountMoreThenZero_NoExceptions() {
        assertThatNoException()
                .isThrownBy(() -> bvs.validateAmount(10.0));
    }

    @Test
    void validateAmount_AmountLessOrZero_ThrowsException() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> bvs.validateAmount(0.0))
                .withMessage("Amount must be greater than 0")
                .withNoCause();
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> bvs.validateAmount(-10.0))
                .withMessage("Amount must be greater than 0")
                .withNoCause();
    }

    @Test
    void validateAmount_AmountNull_ThrowsException() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> bvs.validateAmount(null))
                .withMessage("Amount must not be null")
                .withNoCause();
    }

    @Test
    void validatePaymentId_PaymentIdNotNull_NoException() {
        assertThatNoException()
                .isThrownBy(() -> bvs.validatePaymentId(UUID.randomUUID()));

    }

    @Test
    void validatePaymentId_PaymentIdNull_ThrowsException() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> bvs.validatePaymentId(null))
                .withMessage("Payment id must not be null")
                .withNoCause();
    }


    @Test
    void validateUserId_UserIdNotNull_NoException() {
        assertThatNoException()
                .isThrownBy(() -> bvs.validateUserId(10));
    }

    @Test
    void validateUserId_UserIdNull_ThrowsException() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> bvs.validateUserId(null))
                .withMessage("User id must not be null")
                .withNoCause();
    }

    @Test
    void validateUser_UserActive_NoException() {
        User user = new User(0, "Artiom", Status.ACTIVE);

        assertThatNoException()
                .isThrownBy(() -> bvs.validateUser(user));
    }

    @Test
    void validateUser_UserNotActive_ThrowsException() {
        User user = new User(0, "Artiom", Status.INACTIVE);

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> bvs.validateUser(user))
                .withMessage("User with id " + user.getId() + " not in ACTIVE status")
                .withNoCause();

    }

    @Test
    void validateMessage_MessageNotNull_NoException() {
        assertThatNoException()
                .isThrownBy(() -> bvs.validateMessage("Message"));
    }

    @Test
    void validateMessage_MessageNull_ThrowsException() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> bvs.validateMessage(null))
                .withMessage("Payment message must not be null")
                .withNoCause();
    }
}