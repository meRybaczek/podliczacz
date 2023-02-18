package old;

public class A4ColorPdfFile implements PdfFile{

    PdfFileOption option = PdfFileOption.A4_COLOR;
    private boolean isA4 = true;
    private static final double A4_AREA_SQM = 0.298 * 0.211;
    private String name;

    private double width;

    private double height;

    private double unitPrice;

    public A4ColorPdfFile(String name, double width, double height) {
        this.name = name;
        this.width = width;
        this.height = height;
    }

    @Override
    public double getWidth() {
        return width;
    }

    @Override
    public double getHeight() {
        return height;
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
        return A4_AREA_SQM;
    }

    @Override
    public double countPrice() {
        return unitPrice;
    }

    @Override
    public void printInfo() {
        System.out.printf("A4 black:  -----> %s\n", name);

    }
}
