package id.ac.ui.cs.advprog.eshop.paymentMethod;

import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;

import java.util.Map;

public class PaymentMethodFactory {

    private static final Map<String, PaymentMethodStrategy> strategies = Map.of(
            PaymentMethod.VOUCHER_CODE.getValue(), new VoucherCode(),
            PaymentMethod.CASH_ON_DELIVERY.getValue(), new CashOnDelivery()
    );

    public static PaymentMethodStrategy getStrategy(String method) {
        return strategies.get(method);
    }
}
