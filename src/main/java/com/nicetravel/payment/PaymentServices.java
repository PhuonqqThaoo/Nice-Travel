package com.nicetravel.payment;

import java.util.*;

import com.paypal.api.payments.*;
import com.paypal.base.rest.*;

public class PaymentServices {
    private static final String CLIENT_ID = "ARsKvLA38WYZtglt1L8WzwETeWGivBZS8oWFfhcgNdxt0IHAP3KtDb8LAEJdByqBYArz1byi5_aZLaAr";
    private static final String CLIENT_SECRET = "EBf-ik9IZSUjVF_ypHnSjOTTlHdf26_hA7Bq-rXuOsKp_wt0z8NsnfPBSchJjMi0kiZbfM7UtSHV6AOg";
    private static final String MODE = "sandbox";

    public String authorizePayment(com.nicetravel.entity.Payment orderDetail)
            throws PayPalRESTException {

        Payer payer = getPayerInformation();
        RedirectUrls redirectUrls = getRedirectURLs();
        List<Transaction> listTransaction = getTransactionInformation(orderDetail);

        Payment requestPayment = new Payment();
        requestPayment.setTransactions(listTransaction);
        requestPayment.setRedirectUrls(redirectUrls);
        requestPayment.setPayer(payer);
        requestPayment.setIntent("authorize");

        APIContext apiContext = new APIContext(CLIENT_ID, CLIENT_SECRET, MODE);

        Payment approvedPayment = requestPayment.create(apiContext);

        return getApprovalLink(approvedPayment);

    }

    public Payment getPaymentDetails(String paymentId) throws PayPalRESTException {
        APIContext apiContext = new APIContext(CLIENT_ID, CLIENT_SECRET, MODE);
        return Payment.get(apiContext, paymentId);
    }

    public Payment executePayment(String paymentId, String payerId)
            throws PayPalRESTException {
        PaymentExecution paymentExecution = new PaymentExecution();
        paymentExecution.setPayerId(payerId);

        Payment payment = new Payment().setId(paymentId);

        APIContext apiContext = new APIContext(CLIENT_ID, CLIENT_SECRET, MODE);

        return payment.execute(apiContext, paymentExecution);
    }

    private Payer getPayerInformation() {
        Payer payer = new Payer();
        payer.setPaymentMethod("paypal");

        PayerInfo payerInfo = new PayerInfo();
        payerInfo.setFirstName("William")
                .setLastName("Peterson")
                .setEmail("william.peterson@company.com");

        payer.setPayerInfo(payerInfo);

        return payer;
    }

    private RedirectUrls getRedirectURLs() {
        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl("http://localhost:8081/PaypalTest/cancel.html");
        redirectUrls.setReturnUrl("http://localhost:8081/PaypalTest/review_payment");

        return redirectUrls;
    }

    private List<Transaction> getTransactionInformation(com.nicetravel.entity.Payment payment) {
        Details details = new Details();
        details.setShipping(payment.getShipping());
        details.setSubtotal(payment.getSubtotal());
        details.setTax(payment.getTax());

        Amount amount = new Amount();
        amount.setCurrency("USD");
        amount.setTotal(payment.getTotal());
        amount.setDetails(details);

        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setDescription(payment.getTourName());

        ItemList itemList = new ItemList();
        List<Item> items = new ArrayList<>();

        Item item = new Item();
        item.setCurrency("USD");
        item.setName(payment.getTourName());
        item.setPrice(payment.getSubtotal());
        item.setTax(payment.getTax());
        item.setQuantity("1");

        items.add(item);
        itemList.setItems(items);
        transaction.setItemList(itemList);

        List<Transaction> listTransaction = new ArrayList<>();
        listTransaction.add(transaction);

        return listTransaction;
    }

    private String getApprovalLink(Payment approvedPayment) {
        List<Links> links = approvedPayment.getLinks();
        String approvalLink = null;

        for (Links link : links) {
            if (link.getRel().equalsIgnoreCase("approval_url")) {
                approvalLink = link.getHref();
                break;
            }
        }

        return approvalLink;
    }
}