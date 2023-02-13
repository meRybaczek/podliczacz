import org.apache.commons.math3.util.Precision;

public class PdfFilePriceCalculator {

    private double a4UnitPrice;
    private double drawingUnitPrice;
    private int copiesQty;
    private long a4Quantity;
    private double drawingsAreaSqm;


    private PdfFilePriceCalculator(double a4UnitPrice, double drawingUnitPrice, int copiesQty, long a4Quantity, double drawingsAreaSqm) {
        this.a4UnitPrice = a4UnitPrice;
        this.drawingUnitPrice = drawingUnitPrice;
        this.copiesQty = copiesQty;
        this.a4Quantity = a4Quantity;
        this.drawingsAreaSqm = drawingsAreaSqm;
    }

    public static class Builder {
        private double a4UnitPrice;
        private double drawingUnitPrice;
        private int copiesQty;
        private long a4Quantity;
        private double drawingsAreaSqm;

        public Builder a4UnitPrice(double a4UnitPrice) {
            this.a4UnitPrice = a4UnitPrice;
            return this;
        }
        public Builder drawingUnitPrice(double drawingUnitPrice) {
            this.drawingUnitPrice = drawingUnitPrice;
            return this;
        }
        public Builder copiesQty(int copiesQty) {
            this.copiesQty = copiesQty;
            return this;
        }
        public Builder a4Quantity(long a4Quantity) {
            this.a4Quantity = a4Quantity;
            return this;
        }
        public Builder drawingsAreaSqm(double drawingsAreaSqm) {
            this.drawingsAreaSqm = drawingsAreaSqm;
            return this;
        }
        public PdfFilePriceCalculator build() {
            return new PdfFilePriceCalculator(a4UnitPrice, drawingUnitPrice, copiesQty, a4Quantity, drawingsAreaSqm );
        }
    }

    public double calculateTotalA4Price() {
        return Precision.round((a4Quantity * a4UnitPrice * copiesQty), 2);
    }

    public double calculateTotalDrawingsPrice() {
        return Precision.round((drawingsAreaSqm * drawingUnitPrice * copiesQty), 2);
    }

}
