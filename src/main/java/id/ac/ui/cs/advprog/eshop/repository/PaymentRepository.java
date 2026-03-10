package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class PaymentRepository {
    private List<Payment> payments;

    public Payment addPayment(Payment payment) {
        return null;
    }

    public Payment setStatus(Payment payment, String status) {
        return null;
    }

    public Payment getPayment(String paymentId) {
        return null;
    }

    public List<Payment> getAllPayments() {
        return null;
    }

    public int getSize() {
        return 0;
    }
}
