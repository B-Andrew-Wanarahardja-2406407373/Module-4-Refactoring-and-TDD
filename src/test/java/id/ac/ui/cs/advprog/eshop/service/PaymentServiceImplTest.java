package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;
import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class PaymentServiceImplTest {
    @InjectMocks
    private PaymentService paymentService = new PaymentServiceImpl();
    private Order order = new Order("10000000-0000-0000-0000-000000000000",
            new ArrayList<>(), 1708260000L,"Sampo Koski");

    @Test
    void testAddPayment() {
        assertNull(order.getPayment());
        assertEquals(0, paymentService.getSize());

        Payment added = paymentService.addPayment(order,
                PaymentMethod.CASH_ON_DELIVERY.getValue(), new HashMap<String,String>());

        assertFalse(order.getPayment().equals(null));
        assertEquals(1, paymentService.getSize());
    }

    @Test
    void testSetStatusChangeToRejected() {
        Payment payment = new Payment();
        payment.setId("00000000-0000-0000-0000-000000000001");
        payment.setStatus(PaymentStatus.PENDING.getValue());
        payment.setMethod(PaymentMethod.VOUCHER_CODE.getValue());
        payment.setOrder(order);
        Payment result = paymentService.addPayment(order,
                PaymentMethod.CASH_ON_DELIVERY.getValue(), new HashMap<String,String>());

        assertEquals(PaymentStatus.PENDING.getValue(), payment.getStatus());
        Payment updated = paymentService.setStatus(result, PaymentStatus.REJECTED.getValue());

        assertEquals(PaymentStatus.REJECTED.getValue(), result.getStatus());
        assertEquals(OrderStatus.FAILED.getValue(), order.getStatus());
    }

    @Test
    void testSetStatusChangeToSuccess() {
        Payment payment = new Payment();
        payment.setId("00000000-0000-0000-0000-000000000001");
        payment.setStatus(PaymentStatus.PENDING.getValue());
        payment.setMethod(PaymentMethod.VOUCHER_CODE.getValue());
        payment.setOrder(order);
        Payment result = paymentService.addPayment(order,
                PaymentMethod.CASH_ON_DELIVERY.getValue(), new HashMap<String,String>());

        assertEquals(PaymentStatus.PENDING.getValue(), payment.getStatus());
        Payment updated = paymentService.setStatus(result, PaymentStatus.SUCCESS.getValue());

        assertEquals(PaymentStatus.SUCCESS.getValue(), result.getStatus());
        assertEquals(OrderStatus.SUCCESS.getValue(), order.getStatus());
    }

    @Test
    void testGetPaymentFound() {
        Payment payment = new Payment();
        payment.setId("00000000-0000-0000-0000-000000000001");
        payment.setStatus(PaymentStatus.PENDING.getValue());
        payment.setMethod(PaymentMethod.VOUCHER_CODE.getValue());
        Payment result = paymentService.addPayment(order,
                PaymentMethod.CASH_ON_DELIVERY.getValue(), new HashMap<String,String>());

        Payment found = paymentService.getPayment("00000000-0000-0000-0000-000000000001");
        assertEquals(PaymentStatus.PENDING.getValue(), found.getStatus());
        assertEquals(PaymentMethod.VOUCHER_CODE.getValue(), found.getMethod());
    }

    @Test
    void testGetPaymentNotFound() {
        Payment payment = new Payment();
        payment.setId("00000000-0000-0000-0000-000000000001");
        payment.setStatus(PaymentStatus.PENDING.getValue());
        payment.setMethod(PaymentMethod.VOUCHER_CODE.getValue());
        Payment result = paymentService.addPayment(order,
                PaymentMethod.CASH_ON_DELIVERY.getValue(), new HashMap<String,String>());

        Payment NotFound = paymentService.getPayment("00000000-0000-0000-0000-000000000002");
        assertNull(NotFound);
    }


    @Test
    void testGetAllPayment() {
        List<Payment> paymentList = paymentService.getAllPayments();
        assertEquals(0, paymentList.size());

        Payment payment1 = new Payment();
        payment1.setId("00000000-0000-0000-0000-000000000001");
        payment1.setStatus(PaymentStatus.PENDING.getValue());
        payment1.setMethod(PaymentMethod.VOUCHER_CODE.getValue());
        Payment result1 = paymentService.addPayment(order,
                PaymentMethod.CASH_ON_DELIVERY.getValue(), new HashMap<String,String>());

        paymentList = paymentService.getAllPayments();
        assertEquals(1, paymentList.size());

        Payment payment2 = new Payment();
        payment2.setId("00000000-0000-0000-0000-000000000002");
        payment2.setStatus(PaymentStatus.PENDING.getValue());
        payment2.setMethod(PaymentMethod.VOUCHER_CODE.getValue());
        Payment result2 = paymentService.addPayment(order,
                PaymentMethod.CASH_ON_DELIVERY.getValue(), new HashMap<String,String>());

        paymentList = paymentService.getAllPayments();
        assertEquals(2, paymentList.size());
    }

    @Test
    void testGetSize() {
        assertEquals(0, paymentService.getSize());

        Payment payment1 = new Payment();
        payment1.setId("00000000-0000-0000-0000-000000000001");
        payment1.setStatus(PaymentStatus.PENDING.getValue());
        payment1.setMethod(PaymentMethod.VOUCHER_CODE.getValue());
        Payment result1 = paymentService.addPayment(order,
                PaymentMethod.CASH_ON_DELIVERY.getValue(), new HashMap<String,String>());

        assertEquals(1, paymentService.getSize());

        Payment payment2 = new Payment();
        payment2.setId("00000000-0000-0000-0000-000000000002");
        payment2.setStatus(PaymentStatus.PENDING.getValue());
        payment2.setMethod(PaymentMethod.VOUCHER_CODE.getValue());
        Payment result2 = paymentService.addPayment(order,
                PaymentMethod.CASH_ON_DELIVERY.getValue(), new HashMap<String,String>());

        assertEquals(2, paymentService.getSize());
    }
}
