package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;
import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PaymentServiceImplTest {
    @InjectMocks
    private PaymentService paymentService = new PaymentServiceImpl();
    private Order order;

    @BeforeEach
    void setup() {
        List<Product> products = new ArrayList<>();

        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-440e-a860-71af6af63bd4");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(2);

        products.add(product1);
        order = new Order("10000000-0000-0000-0000-000000000000",
            products, 1708260000L,"Sampo Koski");
    }

    @Test
    void testAddPayment() {
        assertNull(order.getPayment());
        assertEquals(0, paymentService.getSize());

        Payment added = paymentService.addPayment(order,
                PaymentMethod.CASH_ON_DELIVERY.getValue(), new HashMap<String,String>());

        assertEquals(PaymentMethod.CASH_ON_DELIVERY.getValue(),added.getMethod());
        assertEquals(PaymentStatus.REJECTED.getValue(), added.getStatus());
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
                PaymentMethod.VOUCHER_CODE.getValue(), new HashMap<String,String>());

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
                PaymentMethod.VOUCHER_CODE.getValue(), new HashMap<String,String>());

        assertEquals(PaymentStatus.PENDING.getValue(), payment.getStatus());
        Payment updated = paymentService.setStatus(result, PaymentStatus.SUCCESS.getValue());

        assertEquals(PaymentStatus.SUCCESS.getValue(), result.getStatus());
        assertEquals(OrderStatus.SUCCESS.getValue(), order.getStatus());
    }

    @Test
    void testGetPaymentFound() {
        Payment result = paymentService.addPayment(order,
                PaymentMethod.CASH_ON_DELIVERY.getValue(), new HashMap<String,String>());

        Payment notFound = paymentService.getPayment("00000000-0000-0000-0000-000000000001");

        String idInRepo = paymentService.getAllPayments().get(0).getId();
        Payment found = paymentService.getPayment(idInRepo);
        assertEquals(PaymentStatus.REJECTED.getValue(), found.getStatus());
        assertEquals(PaymentMethod.CASH_ON_DELIVERY.getValue(), found.getMethod());
    }

    @Test
    void testGetPaymentNotFound() {
        Payment result = paymentService.addPayment(order,
                PaymentMethod.CASH_ON_DELIVERY.getValue(), new HashMap<String,String>());

        Payment NotFound = paymentService.getPayment("00000000-0000-0000-0000-000000000002");
        assertNull(NotFound);
    }


    @Test
    void testGetAllPayment() {
        List<Payment> paymentList = paymentService.getAllPayments();
        assertEquals(0, paymentList.size());

        Payment result1 = paymentService.addPayment(order,
                PaymentMethod.CASH_ON_DELIVERY.getValue(), new HashMap<String,String>());

        paymentList = paymentService.getAllPayments();
        assertEquals(1, paymentList.size());

        Payment result2 = paymentService.addPayment(order,
                PaymentMethod.CASH_ON_DELIVERY.getValue(), new HashMap<String,String>());

        paymentList = paymentService.getAllPayments();
        assertEquals(2, paymentList.size());
    }

    @Test
    void testGetSize() {
        assertEquals(0, paymentService.getSize());

        Payment result1 = paymentService.addPayment(order,
                PaymentMethod.CASH_ON_DELIVERY.getValue(), new HashMap<String,String>());

        assertEquals(1, paymentService.getSize());

        Payment result2 = paymentService.addPayment(order,
                PaymentMethod.CASH_ON_DELIVERY.getValue(), new HashMap<String,String>());

        assertEquals(2, paymentService.getSize());
    }
}
