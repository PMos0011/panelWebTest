package POM.Invoice;

public enum InvoiceInputs {

    AMOUNT("amount"),
    NET_PRICE("price"),
    DISCOUNT("discount"),
    GROSS_PRICE("singleBrutto"),
    NET_AMOUNT("nettoAmount"),
    TAX_AMOUNT("vatAmount"),
    GROSS_AMOUNT("brutto"),

    INVOICE_SUMMARY_NET("invoiceSummaryNetto"),
    INVOICE_SUMMARY_TAX("invoiceSummaryVat"),
    INVOICE_SUMMARY_GROSS("invoiceSummaryBrutto"),
    TAX_RATE_NET("invoiceVatRateNetto"),
    TAX_RATE("invoiceVatRate"),
    TAX_RATE_SUMMARY("invoiceVatRateSummary"),
    TAX_RATE_GROSS("invoiceVatRateBrutto"),

    SUMMARY_GROSS("SummaryBrutto");


    private final String input;

    InvoiceInputs(String input) {
        this.input = input;
    }

    public String getInput() {
        return input;
    }
}
