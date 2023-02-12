import org.apache.commons.math3.util.Precision;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PdfApp {
    private static final String APP_VERSION = "v_03";

    private final Scanner scanner = new Scanner(System.in);

    private String filepath;

    private long a4Quantity;

    private double drawingsAreaSqm;

    private double a4UnitPrice;

    private double drawingUnitPrice;

    private int copiesQuantity;

    private double totalDrawingPrice;

    private double totalA4Price;

    private List<PdfFile> pdfFileList = new ArrayList<>();

    public static void main(String[] args) {
        PdfApp pdf3 = new PdfApp();
        System.out.println("Witaj w Podliczacz " + APP_VERSION + "\n");
        pdf3.mainLoop();
    }

    private void mainLoop() {
        getFilepath();
        createPdfList();
        createStatistic();
        printPdfFileList();
        printPdfListShort();
        printQuantitySummary();
        setUnitData();
        calculate();
        printTotalPrice();
        scanner.close();
    }

    private void getFilepath() {
        System.out.println("Podaj ścieżkę katalogu z plikami pdf: ");
        this.filepath = scanner.nextLine();
    }

    private void createPdfList() {
        PdfFileListCreator pdfListCreator = new PdfFileListCreator(filepath);
        this.pdfFileList = pdfListCreator.createList();
    }
    private void createStatistic() {
        PdfFileStatistic pdfFileStatistic = new PdfFileStatistic(pdfFileList);
        this.a4Quantity = pdfFileStatistic.countA4Quantity();
        this.drawingsAreaSqm = pdfFileStatistic.countAllDrawingsAreaSqm();
    }
    private void printPdfFileList() {
        System.out.println("Lista rysunków: ");
        pdfFileList.stream()
                .filter(x -> !x.isA4format())
                .forEach(PdfFile::printDetailInfo);

        System.out.println("Ilość A4 :" + a4Quantity +"\n");
    }

    private void printPdfListShort() {
        System.out.println(">>> Zaznacz i skopiuj do Excela <<<");
        pdfFileList.stream()
                .filter(x -> !x.isA4format())
                .forEach(PdfFile::printInfo);

        System.out.println("Ilość A4 :" + a4Quantity +"\n");
    }

    private void printQuantitySummary() {
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>\n" +
                "Podsumowanie dla katalogu: " + filepath + "\n" +
                "Ilość A4 [szt]: " + a4Quantity + "\n" +
                "Powierzchnia rysunków [m2]: " + Precision.round((drawingsAreaSqm), 2) +
                "\n>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>\n");
    }

    private void setUnitData() {
        System.out.println("Podaj liczbę kopii: ");
        this.copiesQuantity = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Wprowadź cenę jednostkową A4: ");
        this.a4UnitPrice = scanner.nextDouble();
        scanner.nextLine();
        System.out.println("Wprowadź cenę za m2 rysunku: ");
        this.drawingUnitPrice = scanner.nextDouble();
        scanner.nextLine();
    }

    private void calculate() {//buider
        PdfFilePriceCalculator pdfCalculator = new PdfFilePriceCalculator(a4UnitPrice, drawingUnitPrice, copiesQuantity, a4Quantity, drawingsAreaSqm);
        this.totalDrawingPrice = pdfCalculator.calculateTotalDrawingsPrice();
        this.totalA4Price = pdfCalculator.calculateTotalA4Price();
    }

    private void printTotalPrice() {
        System.out.println("\n>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>\n" +
                "Podsumowanie dla "+ copiesQuantity + " kopii: \n" +
                "Sumaryczna ilość A4 [szt]: " + (a4Quantity * copiesQuantity) + "\n" +
                "Sumaryczna powierzchnia rysunków [m2]: " + Precision.round((drawingsAreaSqm * copiesQuantity), 2) + "\n\n" +
                "Cena całościowa [zł]: " + (totalA4Price + totalDrawingPrice) +
                "\n>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>\n");
    }
}
