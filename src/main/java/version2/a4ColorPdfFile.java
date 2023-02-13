package version2;

public class a4ColorPdfFile implements PdfFile{

    PdfFileOption option = PdfFileOption.A4_COLOR;
    private boolean isA4 = true;
    private static final double A4_AREA_SQM = 0.297 * 0.210;
    private String name;

    private double width;

    private double height;

    private double unitPrice;

    public a4ColorPdfFile(String name, double width, double height) {
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
