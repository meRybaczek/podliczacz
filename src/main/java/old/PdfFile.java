package old;

public interface PdfFile {

    double countAreaSqm();
    double countPrice();
    void printInfo();
    PdfFileOption getOption();
    void setUnitPrice(double unitPrice);
    double getWidth();
    double getHeight();

}
