public class ClientUnitData {
    private double a4BlackUnitPrice;
    private double a4ColorUnitPrice;
    private double drawingBlackUnitPrice;
    private double drawingColorUnitPrice;
    private int quantity;

    public double getA4BlackUnitPrice() {
        return a4BlackUnitPrice;
    }

    public double getA4ColorUnitPrice() {
        return a4ColorUnitPrice;
    }

    public double getDrawingBlackUnitPrice() {
        return drawingBlackUnitPrice;
    }

    public double getDrawingColorUnitPrice() {
        return drawingColorUnitPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    private ClientUnitData(Builder builder) {
        this.a4BlackUnitPrice = builder.a4BlackUnitPrice;
        this.a4ColorUnitPrice = builder.a4ColorUnitPrice;
        this.drawingBlackUnitPrice = builder.drawingBlackUnitPrice;
        this.drawingColorUnitPrice = builder.drawingColorUnitPrice;
        this.quantity = builder.quantity;
    }
    public static class Builder {
        private double a4BlackUnitPrice;
        private double a4ColorUnitPrice;
        private double drawingBlackUnitPrice;
        private double drawingColorUnitPrice;
        private int quantity;

        public Builder a4BlackUnitPrice(double a4BlackUnitPrice) {
            this.a4BlackUnitPrice = a4BlackUnitPrice;
            return this;
        }
        public Builder a4ColorUnitPrice(double a4ColorUnitPrice) {
            this.a4ColorUnitPrice = a4ColorUnitPrice;
            return this;
        }

        public Builder drawingBlackUnitPrice(double drawingBlackUnitPrice) {
            this.drawingBlackUnitPrice = drawingBlackUnitPrice;
            return this;
        }
        public Builder drawingColorUnitPrice(double drawingColorUnitPrice) {
            this.drawingColorUnitPrice = drawingColorUnitPrice;
            return this;
        }

        public Builder quantity(int quantity) {
            this.quantity = quantity;
            return this;
        }

        public ClientUnitData build() {
            return new ClientUnitData(this);
        }
    }

}
