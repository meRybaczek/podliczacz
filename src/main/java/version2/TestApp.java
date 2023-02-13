package version2;

import org.apache.commons.math3.util.Precision;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TestApp {
    Scanner scanner = new Scanner(System.in);
    private String filepath = "D:\\pliki testowe pdf";
    private int copiesQuantity;
    private double a4UnitPrice;
    private double drawingUnitPrice;
    private List<PdfFile> list = new ArrayList<>();
    private PdfFileCalculator pdfFileCalculator;

    public static void main(String[] args) {
        TestApp testApp = new TestApp();
        testApp.mainLoop();

    }

    private void mainLoop() {
   //     getFilepath();
        createPdfFileList();
        printListByOption(PdfFileOption.DRAWING_BLACK);
        printQuantitySummary();
        getUnitData();
        setUnitDataForOption(PdfFileOption.DRAWING_BLACK, drawingUnitPrice);
        setUnitDataForOption(PdfFileOption.A4_BLACK, a4UnitPrice);
        printTotalPriceSummary();
        scanner.close();

    }

    private void getFilepath() {
        System.out.println("Podaj ścieżkę katalogu z plikami pdf: ");
        this.filepath = scanner.nextLine();
    }
    private void createPdfFileList() {
        PdfFileFactory pdfFileFactory = new PdfFileFactory(filepath);
        list = pdfFileFactory.createList();
    }

    private void getUnitData() {
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
    private void setUnitDataForOption(PdfFileOption option, double unitPrice) {
        list.stream()
                .filter(x->x.getOption() == option)
                .forEach(x-> x.setUnitPrice(unitPrice));
    }

    private void printListByOption(PdfFileOption option) {
        System.out.printf("Lista dla %s\n", option.name());

        list.stream()
                .filter(x->x.getOption() == option)
                .forEach(PdfFile::printInfo);
    }

    private void printQuantitySummary() {
        pdfFileCalculator = new PdfFileCalculator(list);
        long a4Quantity = pdfFileCalculator.getQuantityByOption(PdfFileOption.A4_BLACK);
        Double totalDrawingsArea = pdfFileCalculator.getAllDrawingsArea();

        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>\n" +
                "Podsumowanie dla katalogu: " + filepath + "\n" +
                "Ilość A4 [szt]: " + a4Quantity + "\n" +
                "Powierzchnia rysunków [m2]: " + Precision.round((totalDrawingsArea), 2) +
                "\n>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>\n");
    }
    private void printTotalPriceSummary() {
        pdfFileCalculator.setList(list);        // new list has to updated due to unitPrices update
        long a4Quantity = pdfFileCalculator.getQuantityByOption(PdfFileOption.A4_BLACK);
        Double totalDrawingsArea = pdfFileCalculator.getAllDrawingsArea();
        Double totalPrice = pdfFileCalculator.getTotalPrice();

        System.out.println("\n>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>\n" +
                "Podsumowanie dla "+ copiesQuantity + " kopii: \n" +
                "Sumaryczna ilość A4 [szt]: " + (a4Quantity * copiesQuantity) + "\n" +
                "Sumaryczna powierzchnia rysunków [m2]: " + Precision.round((totalDrawingsArea * copiesQuantity), 2) + "\n\n" +
                "Cena całościowa [zł]: " + Precision.round((totalPrice * copiesQuantity),2) +
                "\n>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>\n");
    }

}
