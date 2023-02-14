package version2;

public class DrawingColorPdfFile implements PdfFile {

    PdfFileOption option = PdfFileOption.DRAWING_COLOR;

    private String name;

    private double width;

    private double height;

    private double unitPrice;


    public DrawingColorPdfFile(String name, double width, double height) {
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
        return (width / 1000) * (height / 1000);
    }

    @Override
    public double countPrice() {
        return countAreaSqm() * unitPrice;
    }

    @Override
    public void printInfo() {
        System.out.printf("%.0f x %.0f --- %s -----> %s\n", width, height, option.name(), name);
    }
    @Override
    public void printCopyToExcelInfo() {
        System.out.printf("%.0f\t%.0f\n", width, height);
    }
}