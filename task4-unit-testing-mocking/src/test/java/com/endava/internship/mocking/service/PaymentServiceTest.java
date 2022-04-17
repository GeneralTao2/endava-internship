package com.endava.internship.mocking.service;

import com.endava.internship.mocking.model.Payment;
import com.endava.internship.mocking.model.Status;
import com.endava.internship.mocking.model.User;
import com.endava.internship.mocking.repository.PaymentRepository;
import com.endava.internship.mocking.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.*;


import java.util.*;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
class PaymentServiceTest {

    @Mock
    private UserRepository userRepositoryMock;

    @Mock
    private PaymentRepository paymentRepositoryMock;

    @Mock
    private ValidationService validationServiceMock;

    private PaymentService paymentService;

    private List<Payment> paymentList;


    @BeforeEach
    void setUp() {
        reset(userRepositoryMock, paymentRepositoryMock, validationServiceMock);
        paymentService = new PaymentService(userRepositoryMock, paymentRepositoryMock, validationServiceMock);
        paymentList = new ArrayList<>();
    }

    private Payment createPaymentMock(User user, Double amount) {
        final Integer userId = user.getId();

        reset(userRepositoryMock, userRepositoryMock);
        when(userRepositoryMock.findById(userId)).thenReturn(Optional.of(user));
        when(paymentRepositoryMock.save(any(Payment.class))).thenAnswer(
                invocation -> {
                    Payment paymentToBeSave = (Payment) invocation.getArguments()[0];
                    paymentList.add(Payment.copyOf(paymentToBeSave));
                    return paymentToBeSave;
                }
        );

        return paymentService.createPayment(userId, amount);
    }

    @Test
    void createPayment_WithExistingUser_CreateReturnPayment() {
        final Integer userId1 = 1;
        final User user1 = new User(userId1, "John", Status.ACTIVE);
        final Double amount1 = 10.0;

        Payment returnedPayment1 = createPaymentMock(user1, amount1);

        verify(validationServiceMock).validateUserId(userId1);
        verify(validationServiceMock).validateAmount(amount1);
        verify(validationServiceMock).validateUser(user1);

        assertThat(returnedPayment1.getUserId()).isEqualTo(userId1);
        assertThat(returnedPayment1.getAmount()).isEqualTo(amount1);
        assertThat(returnedPayment1.getMessage()).isEqualTo("Payment from user " + user1.getName());
    }

    @Test
    void createPayment_WithNotExistingUser_ThrowException() {
        final Integer userId3 = 100;
        final Double amount1 = 10.0;


        when(userRepositoryMock.findById(userId3)).thenReturn(Optional.empty());

        assertThatExceptionOfType(NoSuchElementException.class).isThrownBy(
                () -> paymentService.createPayment(userId3, amount1)
        );
    }

    @Test
    void editMessage_WithExistingUserAndValidMessage_ReturnEditedPayment() {
        final String newMessage = "newMassage";
        final User user1 = new User(1, "John", Status.ACTIVE);
        final Double amount1 = 10.0;

        Payment createdPayment1 = createPaymentMock(user1, amount1);

        Payment editedPayment1 = Payment.copyOf(createdPayment1);
        editedPayment1.setMessage(newMessage);

        when(paymentRepositoryMock.editMessage(createdPayment1.getPaymentId(), newMessage))
                .thenReturn(editedPayment1);

        Payment returnedPayment = paymentService.editPaymentMessage(createdPayment1.getPaymentId(), newMessage);

        verify(validationServiceMock).validatePaymentId(createdPayment1.getPaymentId());
        verify(validationServiceMock).validateMessage(newMessage);

        assertThat(returnedPayment).isEqualTo(editedPayment1);

    }

    @Test
    void getAllByAmountExceeding() {
        final User user1 = new User(1, "John", Status.ACTIVE);
        final User user2 = new User(2, "Maria", Status.ACTIVE);
        final Double amount1 = 10.0;
        final Double amount2 = 15.0;
        final Double amount3 = 20.0;
        final Double amount4 = 25.0;
        final Double amount5 = 70.0;

        Payment returnedPayment1 = createPaymentMock(user1, amount1),
            returnedPayment2 = createPaymentMock(user1, amount2),
            returnedPayment3 = createPaymentMock(user2, amount3),
            returnedPayment4 = createPaymentMock(user2, amount4),
            returnedPayment5 = createPaymentMock(user2, amount5);

        when(paymentRepositoryMock.findAll()).thenReturn(paymentList);

        List<Payment> paymentList = paymentService.getAllByAmountExceeding(20.0);

        assertThat(paymentList).contains(returnedPayment4, returnedPayment5)
                .doesNotContain(returnedPayment1, returnedPayment2, returnedPayment3);
    }
}
