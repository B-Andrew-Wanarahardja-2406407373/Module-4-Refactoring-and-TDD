package id.ac.ui.cs.advprog.eshop.model;

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
        payment.setMethod("Voucher Code");
        payment.setStatus("PENDING");

        this.payment = payment;
    }

    @Test
    void testGetPaymentId() {
        assertEquals("00000000-0000-0000-0000-000000000000", this.payment.getId());
    }

    @Test
    void testPaymentmethod() {
        assertEquals("Voucher Code", this.payment.getMethod());
    }

    @Test
    void testPaymentStatus() {
        assertEquals("PENDING", this.payment.getStatus());
    }
}
