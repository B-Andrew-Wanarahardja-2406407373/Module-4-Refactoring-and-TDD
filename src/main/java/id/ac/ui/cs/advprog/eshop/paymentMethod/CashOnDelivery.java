package id.ac.ui.cs.advprog.eshop.paymentMethod;

import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;

import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;

public class CashOnDelivery implements PaymentMethodStrategy {
    public Payment checkDetails(Payment payment) {
        boolean is_valid = validate(payment);
        Payment result = changePaymentStatus(payment, is_valid);
        return result;
    }

    public boolean validate(Payment payment) {
        String address = payment.getPaymentData().get("address");
        String fee = payment.getPaymentData().get("deliveryFee");

        if (address == null || fee == null) return false;
        if (address.isBlank() || fee.isBlank()) return false;
        return true;
    }

    public Payment changePaymentStatus(Payment payment, boolean is_valid) {
        Order order = payment.getOrder();
        if (is_valid) {
            payment.setStatus(PaymentStatus.SUCCESS.getValue());
            order.setStatus(OrderStatus.SUCCESS.getValue());
        } else {
            payment.setStatus(PaymentStatus.REJECTED.getValue());
            order.setStatus(OrderStatus.FAILED.getValue());
        }
        return payment;
    }
}
