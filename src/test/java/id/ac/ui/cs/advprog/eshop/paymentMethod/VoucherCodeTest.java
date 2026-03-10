package id.ac.ui.cs.advprog.eshop.paymentMethod;

import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;
import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class VoucherCodeTest {
    private VoucherCode voucher;

    private Order validOrder;
    private Payment validPayment;

    private Order invalidOrder;
    private Payment invalidPayment;

    @BeforeEach
    void setup() {
        voucher = new VoucherCode();

        List<Product> products = new ArrayList<>();
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-440e-a860-71af6af63bd4");
        product1.setProductName("Sampo");
        product1.setProductQuantity(2);
        products.add(product1);

        validOrder = new Order("10000000-0000-0000-0000-000000000000", products,
                1003260000L, "Sampo Koski",
                OrderStatus.WAITING_PAYMENT.getValue());

        validPayment = new Payment();
        validPayment.setId("00000000-0000-0000-0000-000000000000");
        validPayment.setStatus(PaymentStatus.PENDING.getValue());
        validPayment.setMethod(PaymentMethod.VOUCHER_CODE.getValue());

        validPayment.setOrder(validOrder);
        validOrder.setPayment(validPayment);


        invalidOrder = new Order("10000000-0000-0000-0000-000000000001", products,
                1003260000L, "Cassius Bright",
                OrderStatus.WAITING_PAYMENT.getValue());

        invalidPayment = new Payment();
        invalidPayment.setId("00000000-0000-0000-0000-000000000001");
        invalidPayment.setStatus(PaymentStatus.PENDING.getValue());
        invalidPayment.setMethod(PaymentMethod.VOUCHER_CODE.getValue());

        invalidPayment.setOrder(invalidOrder);
        invalidOrder.setPayment(invalidPayment);
    }

    @Test
    void testCheckVoucherValid() {
        assertEquals(PaymentStatus.PENDING.getValue(), validPayment.getStatus());
        assertEquals(OrderStatus.WAITING_PAYMENT.getValue(), validOrder.getStatus());

        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode","ESHOP12345678abc");
        validPayment.setPaymentData(paymentData);

        Payment result = voucher.checkDetails(validPayment);
        Order orderResult = result.getOrder();

        assertEquals(PaymentStatus.SUCCESS.getValue(), result.getStatus());
        assertEquals(OrderStatus.SUCCESS.getValue(), orderResult.getStatus());
    }

    @Test
    void testCheckVoucherInvalid() {
        assertEquals(PaymentStatus.PENDING.getValue(), invalidPayment.getStatus());
        assertEquals(OrderStatus.WAITING_PAYMENT.getValue(),invalidOrder.getStatus());

        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode","ESHO012345678abcd");
        invalidPayment.setPaymentData(paymentData);

        Payment result = voucher.checkDetails(invalidPayment);
        Order orderResult = result.getOrder();

        assertEquals(PaymentStatus.REJECTED.getValue(), result.getStatus());
        assertEquals(OrderStatus.FAILED.getValue(), orderResult.getStatus());
    }

    @Test
    void testValidatePass() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode","ESHOP12345678abc");
        validPayment.setPaymentData(paymentData);

        assertTrue(voucher.validate(validPayment));
    }

    @Test
    void testValidateFailNoESHOP() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode","hello12345678abc");
        invalidPayment.setPaymentData(paymentData);

        assertFalse(voucher.validate(invalidPayment));
    }

    @Test
    void testValidateFailInvalidLength() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode","ESHOP12345678abc0000");
        invalidPayment.setPaymentData(paymentData);

        assertFalse(voucher.validate(invalidPayment));
    }

    @Test
    void testValidateFailTooManyNumbers() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode","ESHOP123456789ab");
        invalidPayment.setPaymentData(paymentData);

        assertFalse(voucher.validate(invalidPayment));
    }

    @Test
    void testValidateFailTooFewNumbers() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode","ESHOP1234567abcd");
        invalidPayment.setPaymentData(paymentData);

        assertFalse(voucher.validate(invalidPayment));
    }

    @Test
    void testChangePaymentStatusChanged() {
        boolean is_valid = true;
        Payment result = voucher.changePaymentStatus(validPayment, is_valid);
        Order orderResult = result.getOrder();

        assertEquals(PaymentStatus.SUCCESS.getValue(), result.getStatus());
        assertEquals(OrderStatus.SUCCESS.getValue(), orderResult.getStatus());
    }

    @Test
    void testChangePaymentStatusStays() {
        boolean is_valid = false;
        Payment result = voucher.changePaymentStatus(invalidPayment, is_valid);
        Order orderResult = result.getOrder();

        assertEquals(PaymentStatus.REJECTED.getValue(), result.getStatus());
        assertEquals(OrderStatus.FAILED.getValue(), orderResult.getStatus());
    }
}
