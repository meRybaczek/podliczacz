package version2;

public class drawingBlackPdfFile implements PdfFile{

    PdfFileOption option = PdfFileOption.DRAWING_BLACK;

    private final String name;

    private final double width;

    private final double height;

    private double unitPrice;


    public drawingBlackPdfFile(String name, double width, double height) {
        this.name = name;
        this.width = width;
        this.height = height;
    }
    @Override
    public PdfFileOption getOption() {
        return option;
    }
    @Override
    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Override
    public double countAreaSqm() {
        return (width/1000) * (height/1000);
    }

    @Override
    public double countPrice() {
        return countAreaSqm() * unitPrice;
    }

    @Override
    public void printInfo() {
        System.out.printf("%.0f x %.0f --- %s -----> %s\n", width, height, option.name(), name);
    }
}