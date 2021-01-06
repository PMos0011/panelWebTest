package POM.Commodity;

public enum CommodityInputs {
    NET_PRICE("price"),
    DISCOUNT("discount"),
    AMOUNT("amount"),
    GROSS_PRICE("singleBrutto");

    private final String input;

    CommodityInputs(String input) {
        this.input = input;
    }

    public String getInput() {
        return input;
    }
}
