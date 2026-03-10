package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class PaymentRepository {
    private List<Payment> payments = new ArrayList<>();

    public Payment addPayment(Payment payment) {
        payments.add(payment);
        return payment;
    }

    public Payment setStatus(Payment payment, String status) {
        for (Payment repoPayment : payments) {
            if (repoPayment.getId().equals(payment.getId())) {
                repoPayment.setStatus(status);
                return repoPayment;
            }
        }
        return null;
    }

    public Payment getPayment(String paymentId) {
        for (Payment payment : payments) {
            if (payment.getId().equals(paymentId)) {
                return payment;
            }
        }
        return null;
    }

    public List<Payment> getAllPayments() {
        return this.payments;
    }

    public int getSize() {
        return this.payments.size();
    }
}
