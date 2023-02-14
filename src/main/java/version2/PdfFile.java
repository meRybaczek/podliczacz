package version2;

public interface PdfFile {

    double countAreaSqm();
    double countPrice();
    void printInfo();
    PdfFileOption getOption();
    void setUnitPrice(double unitPrice);
    default void printCopyToExcelInfo(){};

}
