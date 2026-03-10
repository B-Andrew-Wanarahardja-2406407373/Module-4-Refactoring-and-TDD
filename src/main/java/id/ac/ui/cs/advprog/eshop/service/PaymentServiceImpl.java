package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;
import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.paymentMethod.CashOnDelivery;
import id.ac.ui.cs.advprog.eshop.paymentMethod.PaymentMethodFactory;
import id.ac.ui.cs.advprog.eshop.paymentMethod.PaymentMethodStrategy;
import id.ac.ui.cs.advprog.eshop.paymentMethod.VoucherCode;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service @Getter
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    private PaymentRepository paymentRepository = new PaymentRepository();
    private PaymentMethodStrategy strategy;

    @Override
    public Payment addPayment(Order order, String method, Map<String, String> paymentData) {
        Payment payment = new Payment();
        payment.setId(UUID.randomUUID().toString());
        payment.setMethod(method);
        payment.setPaymentData(paymentData);
        payment.setOrder(order);

        strategy = PaymentMethodFactory.getStrategy(method);
        payment = strategy.checkDetails(payment);

        paymentRepository.addPayment(payment);

        order.setPayment(payment);
        return payment;
    }

    @Override
    public Payment setStatus(Payment payment, String status) {
        Payment foundPayment = paymentRepository.getPayment(payment.getId());
        if (foundPayment.equals(null)) return null;

        Order order = foundPayment.getOrder();
        if (status.equals(PaymentStatus.SUCCESS.getValue())) {
            foundPayment.setStatus(status);
            order.setStatus(OrderStatus.SUCCESS.getValue());

        } else if (status.equals(PaymentStatus.REJECTED.getValue())) {
            foundPayment.setStatus(status);
            order.setStatus(OrderStatus.FAILED.getValue());

        } else if (status.equals(PaymentStatus.CANCELLED.getValue())) {
            foundPayment.setStatus(status);
            order.setStatus(OrderStatus.CANCELLED.getValue());
        }

        return foundPayment;
    }

    @Override
    public Payment getPayment(String paymentId) {
        return paymentRepository.getPayment(paymentId);
    }

    @Override
    public List<Payment> getAllPayments() {
        return this.paymentRepository.getAllPayments();
    }

    @Override
    public int getSize() {
        return this.paymentRepository.getSize();
    }
}
