package POM.Invoice;

public enum CommodityMeasures {
    ART("szt."),
    HOUR("godz."),
    SERVICE("usł."),
    DAY("doba");

    private final String measure;

    CommodityMeasures(String measure) {
        this.measure = measure;
    }

    public String getMeasure() {
        return measure;
    }
}
