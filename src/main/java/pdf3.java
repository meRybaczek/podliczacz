import org.apache.commons.math3.util.Precision;
import org.apache.pdfbox.pdmodel.PDDocument;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class pdf3 {
    private static final String VERSION = "Podliczacz v_03";
    private final Scanner scanner = new Scanner(System.in);
    private String filepath;
    private long totalA4Qty;
    private double a4UnitPrice;
    private double totalAreaSqm;
    private double drawingUnitPrice;
    private int copiesQty;
    private List<PdfFile> pdfFileList = new ArrayList<>();

    public static void main(String[] args) {
        pdf3 pdf3 = new pdf3();
        System.out.println("Witaj w " + VERSION +"\n");
        pdf3.mainLoop();
    }
    public void mainLoop(){

        createPdfFileList();
        printPdfFileList();
        printQuantitySummary();
        printTotalPrice();
        scanner.close();
    }

    private File[] getFileArray() {
        System.out.println("Podaj sciezke katalogu: ");
        filepath = scanner.nextLine();

        File file = new File(filepath);
        if (!file.isDirectory()){
            System.out.println("Katalog nie istnieje.");
            mainLoop();
        }
        if (file.listFiles() == null){
            System.out.println("Katalog jest pusty.");
            mainLoop();
        }
        return file.listFiles();
    }
    private void createPdfFileList() {
        File[] fileArray = getFileArray();
        Arrays.stream(fileArray)
                .forEach(this::addPdfFileToList);
    }
    private void addPdfFileToList(File file) {
        try (
                PDDocument pdf = PDDocument.load(file);
        ) {
            for (int i = 0; i < pdf.getNumberOfPages(); i++) {

                double CONVERT_TO_MM_FACTOR = 0.35277;
                double width = ((pdf.getPage(i).getMediaBox().getWidth()) * CONVERT_TO_MM_FACTOR);
                double height = ((pdf.getPage(i).getMediaBox().getHeight()) * CONVERT_TO_MM_FACTOR);
                String name = file.getName();

                pdfFileList.add(new PdfFile(name, width, height));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
    private void countTotalA4Quantity() {
        totalA4Qty = pdfFileList.stream()
                .filter(PdfFile::isA4format)
                .count();
    }
    private void countTotalAreaSqm() {
        totalAreaSqm = pdfFileList.stream()
                .filter(x -> !x.isA4format())
                .map(PdfFile::areaSqm)
                .reduce(0.0, Double::sum);
    }

    private void setUnitPrices() {
        System.out.println("Wprowadź cenę jednostkową A4: ");
        a4UnitPrice = scanner.nextDouble();
        scanner.nextLine();
        System.out.println("Wprowadź cenę za m2 rysunku: ");
        drawingUnitPrice = scanner.nextDouble();
        scanner.nextLine();
        System.out.println("Podaj liczbę kopii: ");
        copiesQty = scanner.nextInt();
    }
    private void printTotalPrice() {
        setUnitPrices();
        double totalA4price = Precision.round((totalA4Qty * a4UnitPrice * copiesQty),2);
        double totalDrawingPrice = Precision.round((totalAreaSqm * drawingUnitPrice * copiesQty),2);
        double total = Precision.round((totalA4price + totalDrawingPrice),2);

        System.out.println("\n>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>\n" +
                "Liczba A4 [szt]: " + (totalA4Qty * copiesQty) + "\n" +
                "Powierzchnia rysunków [m2]: " + Precision.round((totalAreaSqm * copiesQty),2) + "\n\n" +
                "Cena całościowa [zł]: " + total
        );
    }
    private void printPdfFileList() {
        System.out.println("Lista rysunków: ");
        pdfFileList
                .stream().filter(x -> !x.isA4format())
                .forEach(System.out::println);
    }
    private void printQuantitySummary() {
        countTotalA4Quantity();
        countTotalAreaSqm();

        System.out.println("\n>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>\n" +
                "Podsumowanie dla katalogu: " + filepath + "\n" +
                "Liczba A4 [szt]: " + totalA4Qty + "\n" +
                "Powierzchnia rysunków [m2]: " + Precision.round(totalAreaSqm,2));
    }
}
