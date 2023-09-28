import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import pdfFileFactory.PdfFile;
import pdfFileFactory.PdfFileFactory;
import pdfFileFactory.PdfFileOption;
import summaryStrategy.ExternalSummaryStrategyImpl;
import summaryStrategy.InternalSummaryStrategyImpl;
import summaryStrategy.SummaryStrategy;
import summaryStrategy.UnitPriceData;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PodliczaczApp {
    Scanner scanner = new Scanner(System.in);

    private final List<PdfFile> pdfFileList = new ArrayList<>();

    private SummaryStrategy summaryStrategy;

    private UnitPriceData unitPriceData;

    private String filepath;

    public static void main(String[] args) {
        PodliczaczApp podliczaczApp = new PodliczaczApp();
        podliczaczApp.createPdfFileList();
        podliczaczApp.mainLoop();
    }

    private void mainLoop() {
        System.out.println("""
                1 ---> wyswietl liste rysunkow
                2 ---> generuj do szablonu excela
                3 ---> podlicz tutaj
                0 ---> EXIT""");

        int option = scanner.nextInt();
        switch (option) {
            case 1 -> {
                printListByOption(PdfFileOption.DRAWING_BLACK);
                printListByOption(PdfFileOption.DRAWING_COLOR);
                mainLoop();
            }
            case 2, 3 -> {
                setClientUnitData();
                setSummaryStrategy(option);
                summaryStrategy.createSummary();
                mainLoop();
            }
            case 0 -> exit();
            default -> {
                System.err.println("Bledny wybor");
                mainLoop();
            }
        }
    }

    private void createPdfFileList() {
        double CONVERT_TO_MM_FACTOR = 0.35277;

        File[] files = getFileArray();

        for (File file : files) {
            try (
                    PDDocument pdf = PDDocument.load(file);
            ) {
                String name = file.getName();
                for (int i = 0; i < pdf.getNumberOfPages(); i++) {
                    PDPage page = pdf.getPage(i);

                    double width = ((page.getMediaBox().getWidth()) * CONVERT_TO_MM_FACTOR);
                    double height = ((page.getMediaBox().getHeight()) * CONVERT_TO_MM_FACTOR);
                    boolean isColor = false;                                                    // narazie wszystkie są cz-b

                    PdfFile pdfFile = PdfFileFactory.createPdfFile(name, width, height, isColor);
                    pdfFileList.add(pdfFile);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

    }

    private File[] getFileArray() {
        System.out.println("Podaj sciezke katalogu z plikami pdf: ");
        filepath = scanner.nextLine();

        File file = new File(filepath);
        if (!file.exists()) {
            System.err.println("Katalog nie istnieje.");
        } else if (file.listFiles() == null) {
            System.err.println("Katalog jest pusty.");
        }
        return file.listFiles();
    }

    private void setClientUnitData() {
        System.out.println("Podaj cenę za A4 cz-b :");
        double a4BlackUnitPrice = scanner.nextDouble();
        scanner.nextLine();
        System.out.println("Podaj cenę za A4 kolor :");
        double a4ColorUnitPrice = scanner.nextDouble();
        scanner.nextLine();
        System.out.println("Podaj cenę za m2 rysunku cz-b :");
        double drawingBlackUnitPrice = scanner.nextDouble();
        scanner.nextLine();
        System.out.println("Podaj cenę za m2 rysunku kolor :");
        double drawingColorUnitPrice = scanner.nextDouble();
        scanner.nextLine();
        System.out.println("Podaj ilosc kopii");
        int quantity = scanner.nextInt();
        scanner.nextLine();

        unitPriceData = new UnitPriceData.Builder()
                .a4BlackUnitPrice(a4BlackUnitPrice)
                .a4ColorUnitPrice(a4ColorUnitPrice)
                .drawingBlackUnitPrice(drawingBlackUnitPrice)
                .drawingColorUnitPrice(drawingColorUnitPrice)
                .quantity(quantity)
                .build();
    }

    private void printListByOption(PdfFileOption option) {
        System.out.printf("Lista dla %s:\n", option.name());

        pdfFileList.stream()
                .filter(x -> x.getOption() == option)
                .forEach(PdfFile::printInfo);
    }

    private void setSummaryStrategy(int option) {
        if (option == 2)
            summaryStrategy = new ExternalSummaryStrategyImpl(pdfFileList, unitPriceData, filepath);
        if (option == 3)
            summaryStrategy = new InternalSummaryStrategyImpl(pdfFileList, unitPriceData, filepath);
    }

    private void exit() {
        scanner.nextLine();
        System.out.println("Program zakonczony");
        System.exit(0);
    }

}
