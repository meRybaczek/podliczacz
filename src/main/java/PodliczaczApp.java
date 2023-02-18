
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PodliczaczApp {
    Scanner scanner = new Scanner(System.in);

    private List<PdfFile> pdfFileList;

    private SummaryStrategy summaryStrategy;

    private PdfFileCalculator pdfFileCalculator;
    private ClientUnitData clientUnitData;

    private String filepath;

    public List<PdfFile> createPdfFileList() {
        double CONVERT_TO_MM_FACTOR = 0.35277;
        List<PdfFile> pdfFileList = new ArrayList<>();

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
        return pdfFileList;
    }

    private File[] getFileArray() {
        System.out.println("Podaj sciezke katalogu z plikami pdf: ");
        filepath = scanner.nextLine();

        File file = new File(filepath);
        if (!file.exists()) {
            System.err.println("Katalog nie istnieje.");
        }
        else if (file.listFiles() == null) {
            System.err.println("Katalog jest pusty.");
        }
        return file.listFiles();
    }

    public void getClientUnitData() {
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

        clientUnitData =  ClientUnitData.builder()
                .a4BlackUnitPrice(a4BlackUnitPrice)
                .a4ColorUnitPrice(a4ColorUnitPrice)
                .drawingBlackUnitPrice(drawingBlackUnitPrice)
                .drawingColorUnitPrice(drawingColorUnitPrice)
                .quantity(quantity)
                .build();
    }


}
