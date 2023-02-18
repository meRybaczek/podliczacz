import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ClientUnitData {
    private double a4BlackUnitPrice;
    private double a4ColorUnitPrice;
    private double drawingBlackUnitPrice;
    private double drawingColorUnitPrice;
    private int quantity;

    private ClientUnitData(double a4BlackUnitPrice, double a4ColorUnitPrice, double drawingBlackUnitPrice, double drawingWhiteUnitPrice, int quantity) {
        this.a4BlackUnitPrice = a4BlackUnitPrice;
        this.a4ColorUnitPrice = a4ColorUnitPrice;
        this.drawingBlackUnitPrice = drawingBlackUnitPrice;
        this.drawingColorUnitPrice = drawingWhiteUnitPrice;
        this.quantity = quantity;
    }


}
