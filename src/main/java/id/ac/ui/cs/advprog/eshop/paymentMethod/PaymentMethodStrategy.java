package id.ac.ui.cs.advprog.eshop.paymentMethod;

import id.ac.ui.cs.advprog.eshop.model.Payment;

public interface PaymentMethodStrategy {
    Payment checkDetails(Payment payment);
}
