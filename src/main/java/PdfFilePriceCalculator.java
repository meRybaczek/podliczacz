import org.apache.commons.math3.util.Precision;

public class PdfFilePriceCalculator {

    private double a4UnitPrice;
    private double drawingUnitPrice;
    private int copiesQty;
    private long a4Quantity;
    private double drawingsAreaSqm;


    public PdfFilePriceCalculator(double a4UnitPrice, double drawingUnitPrice, int copiesQty, long a4Quantity, double drawingsAreaSqm) {
        this.a4UnitPrice = a4UnitPrice;
        this.drawingUnitPrice = drawingUnitPrice;
        this.copiesQty = copiesQty;
        this.a4Quantity = a4Quantity;
        this.drawingsAreaSqm = drawingsAreaSqm;
    }

    public double calculateTotalA4Price() {
        return Precision.round((a4Quantity * a4UnitPrice * copiesQty), 2);
    }

    public double calculateTotalDrawingsPrice() {
        return Precision.round((drawingsAreaSqm * drawingUnitPrice * copiesQty), 2);
    }

}
