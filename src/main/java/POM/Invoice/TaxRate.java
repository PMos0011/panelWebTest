package POM.Invoice;

public enum TaxRate {
    _23("23"),
    _8("8"),
    _0("0.00");

    private final String taxRate;

    TaxRate(String taxRate) {
        this.taxRate = taxRate;
    }

    public String getTaxRate() {
        return taxRate;
    }
}
