package id.ac.ui.cs.advprog.eshop.model;

import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PaymentTest {
    private Payment payment;

    @BeforeEach
    void setup() {
        Payment payment = new Payment();
        payment.setId("00000000-0000-0000-0000-000000000000");
        payment.setMethod(PaymentMethod.CASH_ON_DELIVERY.getValue());
        payment.setStatus(PaymentStatus.PENDING.getValue());

        this.payment = payment;
    }

    @Test
    void testGetPaymentId() {
        assertEquals("00000000-0000-0000-0000-000000000000", this.payment.getId());
    }

    @Test
    void testPaymentMethod() {
        assertEquals("CASH_ON_DELIVERY", this.payment.getMethod());
    }

    @Test
    void testPaymentStatus() {
        assertEquals("PENDING", this.payment.getStatus());
    }
}
