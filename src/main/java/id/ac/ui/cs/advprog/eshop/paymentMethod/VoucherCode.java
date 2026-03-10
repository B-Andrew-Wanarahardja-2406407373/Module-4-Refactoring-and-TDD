package id.ac.ui.cs.advprog.eshop.paymentMethod;

import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;import id.ac.ui.cs.advprog.eshop.model.Order;import id.ac.ui.cs.advprog.eshop.model.Payment;

public class VoucherCode {
    public Payment checkVoucher(Payment payment) {
        boolean isValid = validate(payment);
        Payment result = changePaymentStatus(payment, isValid);
        return result;
    }

    public boolean validate(Payment payment) {
        String voucher = payment.getPaymentData().get("voucherCode");
        if (voucher == null) return false;

        int count = 0;
        for (int index = 0; index < voucher.length(); index++) {
            if (Character.isDigit(voucher.charAt(index))) {
                count++;
            }
        }

        boolean startsWithESHOP = voucher.startsWith("ESHOP");
        boolean is16LettersLong = (voucher.length() == 16);
        boolean has8NumbersInIt = (count == 8);
        return startsWithESHOP && is16LettersLong && has8NumbersInIt;
    }

    public Payment changePaymentStatus(Payment payment, boolean valid) {
        Order order = payment.getOrder();
        if (valid) {
            payment.setStatus(PaymentStatus.SUCCESS.getValue());
            order.setStatus(OrderStatus.SUCCESS.getValue());
        } else {
            payment.setStatus(PaymentStatus.REJECTED.getValue());
            order.setStatus(OrderStatus.FAILED.getValue());
        }
        return payment;
    }
}
