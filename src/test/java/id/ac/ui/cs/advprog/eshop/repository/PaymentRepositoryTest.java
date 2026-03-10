package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PaymentRepositoryTest {
    @InjectMocks
    PaymentRepository paymentRepository;

    @BeforeEach
    void setup() {
        paymentRepository = new PaymentRepository();
    }

    @Test
    void testAddPayment() {
        assertEquals(0, paymentRepository.getSize());

        Payment payment = new Payment();
        payment.setId("00000000-0000-0000-0000-000000000001");
        payment.setStatus(PaymentStatus.PENDING.getValue());
        payment.setMethod(PaymentMethod.VOUCHER_CODE.getValue());
        Payment result = paymentRepository.addPayment(payment);

        assertEquals("00000000-0000-0000-0000-000000000001", result.getId());
        assertEquals(PaymentStatus.PENDING.getValue(), result.getStatus());
        assertEquals(PaymentMethod.VOUCHER_CODE.getValue(), result.getMethod());
        assertEquals(1, paymentRepository.getSize());
    }

    @Test
    void testSetStatusFound() {
        Payment payment = new Payment();
        payment.setId("00000000-0000-0000-0000-000000000001");
        payment.setStatus(PaymentStatus.PENDING.getValue());
        payment.setMethod(PaymentMethod.VOUCHER_CODE.getValue());
        Payment result = paymentRepository.addPayment(payment);

        assertEquals(PaymentStatus.PENDING.getValue(), result.getStatus());
        Payment edited = paymentRepository.setStatus(result, PaymentStatus.SUCCESS.getValue());
        assertEquals(PaymentStatus.SUCCESS.getValue(), result.getStatus());
    }

    @Test
    void testSetStatusNotFound() {
        Payment payment = new Payment();
        payment.setId("00000000-0000-0000-0000-000000000001");
        payment.setStatus(PaymentStatus.PENDING.getValue());
        payment.setMethod(PaymentMethod.VOUCHER_CODE.getValue());

        Payment edited = paymentRepository.setStatus(payment, "00000000-0000-0000-0000-000000000000");
        assertNull(edited);
    }

    @Test
    void testGetPaymentByIdFound() {
        Payment payment = new Payment();
        payment.setId("00000000-0000-0000-0000-000000000001");
        payment.setStatus(PaymentStatus.PENDING.getValue());
        payment.setMethod(PaymentMethod.VOUCHER_CODE.getValue());
        Payment result = paymentRepository.addPayment(payment);

        Payment get = paymentRepository.getPayment("00000000-0000-0000-0000-000000000001");
        assertEquals(PaymentStatus.PENDING.getValue(), get.getStatus());
        assertEquals(PaymentMethod.VOUCHER_CODE.getValue(), get.getMethod());
    }

    @Test
    void testGetPaymentByIdNotFound() {
        Payment get = paymentRepository.getPayment("00000000-0000-0000-0000-000000000001");
        assertNull(get);
    }

    @Test
    void testGetAllPayments() {
        Payment payment1 = new Payment();
        payment1.setId("00000000-0000-0000-0000-000000000001");
        payment1.setStatus(PaymentStatus.PENDING.getValue());
        payment1.setMethod(PaymentMethod.VOUCHER_CODE.getValue());
        Payment result1 = paymentRepository.addPayment(payment1);

        Payment payment2 = new Payment();
        payment2.setId("00000000-0000-0000-0000-000000000002");
        payment2.setStatus(PaymentStatus.PENDING.getValue());
        payment2.setMethod(PaymentMethod.VOUCHER_CODE.getValue());
        Payment result2 = paymentRepository.addPayment(payment2);

        List<Payment> payments = paymentRepository.getAllPayments();
        assertEquals(paymentRepository.getSize(), payments.size());
        assertEquals("00000000-0000-0000-0000-000000000001", payments.get(0).getId());
        assertEquals("00000000-0000-0000-0000-000000000002", payments.get(1).getId());
    }

    @Test
    void testGetSize() {
        assertEquals(0, paymentRepository.getSize());
        Payment payment = new Payment();
        payment.setId("00000000-0000-0000-0000-000000000001");
        payment.setStatus(PaymentStatus.PENDING.getValue());
        payment.setMethod(PaymentMethod.VOUCHER_CODE.getValue());
        Payment result = paymentRepository.addPayment(payment);
        assertEquals(1, paymentRepository.getSize());
    }
}
