import org.apache.commons.math3.util.Precision;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PodliczaczApp {
    private static final String APP_VERSION = "v_04";
    Scanner scanner = new Scanner(System.in);
    private String filepath = "D:\\pliki testowe pdf";
    private int copiesQuantity;
    private double a4UnitPrice;
    private double drawingUnitPrice;
    private List<PdfFile> list = new ArrayList<>();
    private PdfFileCalculator pdfFileCalculator;

    public static void main(String[] args) throws InterruptedException {
        PodliczaczApp testApp = new PodliczaczApp();
        System.out.println("\nWitaj w Podliczacz " + APP_VERSION + "\n");
        testApp.start();
    }

    private void start() throws InterruptedException {
        //getFilepath();
        createPdfFileList();
        printListByOption(PdfFileOption.DRAWING_BLACK);
        printQuantitySummary();
        mainLoop();
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

    private void printListByOption(PdfFileOption option) {
        System.out.printf("Lista dla %s:\n", option.name());

        list.stream()
                .filter(x -> x.getOption() == option)
                .forEach(PdfFile::printInfo);
    }

    private void mainLoop() throws InterruptedException {
        System.out.println("""

                1 ---> zacznij ponownie
                2 ---> przygotuj listę rysunków do skopiowania
                3 ---> podlicz
                4 ---> nie klikać tego
                0 ---> EXIT""");
        switch (scanner.nextInt()) {
            case 1 -> start();
            case 2 -> {
                printListToCopy();
                mainLoop();
            }
            case 3 -> {
                getUnitData();
                setUnitDataForOption(PdfFileOption.DRAWING_BLACK, drawingUnitPrice);
                setUnitDataForOption(PdfFileOption.A4_BLACK, a4UnitPrice);
                printTotalPriceSummary();
                mainLoop();
            }
            case 4 -> {
                usuwanieWspolny();
                mainLoop();
            }
            case 0 -> exit();
            default -> {
                System.err.println("Błędny wybór");
                mainLoop();
            }
        }
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
                .filter(x -> x.getOption() == option)
                .forEach(x -> x.setUnitPrice(unitPrice));
    }

    private void printQuantitySummary() {
        pdfFileCalculator = new PdfFileCalculator(list);
        long a4Quantity = pdfFileCalculator.getQuantityByOption(PdfFileOption.A4_BLACK);
        Double totalDrawingsArea = pdfFileCalculator.getAllDrawingsArea();

        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>\n" +
                "Podsumowanie dla katalogu: " + filepath + "\n" +
                "Ilość A4 [szt]: " + a4Quantity + "\n" +
                "Powierzchnia rysunków [m2]: " + Precision.round((totalDrawingsArea), 2) +
                "\n>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    }

    private void printTotalPriceSummary() {
        pdfFileCalculator.setList(list);        // new list has to be updated due to unitPrices update
        long a4Quantity = pdfFileCalculator.getQuantityByOption(PdfFileOption.A4_BLACK);
        Double totalDrawingsArea = pdfFileCalculator.getAllDrawingsArea();
        Double totalPrice = pdfFileCalculator.getTotalPrice();

        System.out.println("\n>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>\n" +
                "Podsumowanie dla " + copiesQuantity + " kopii: \n" +
                "Sumaryczna ilość A4 [szt]: " + (a4Quantity * copiesQuantity) + "\n" +
                "Sumaryczna powierzchnia rysunków [m2]: " + Precision.round((totalDrawingsArea * copiesQuantity), 2) + "\n\n" +
                "Cena całościowa [zł]: " + Precision.round((totalPrice * copiesQuantity), 2) +
                "\n>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    }

    private void printListToCopy() {
        list.stream()
                .filter(x -> x.getOption() == PdfFileOption.DRAWING_BLACK || x.getOption() == PdfFileOption.DRAWING_COLOR)
                .forEach(PdfFile::printCopyToExcelInfo);
    }

    private void exit() {
        System.out.println("""
                  _                                                                                           \s
                 |_)  _  |_        |_|     ._ _  ._ _       _       \\    / |  _   _ _   _ _     _  ._   _.   |\s
                 |_) (_) |_)   o   | | |_| | | | | | | |_| _>   o    \\/\\/  | (_) _> /_ (_ /_ \\/ /_ | | (_|   o\s
                               /                                /                            /                \s
                """);
        System.exit(0);
    }

    private void usuwanieWspolny() throws InterruptedException {

        for (int i = 0; i <= 100; i++) {
            System.out.print("Usuwanie plików //Nowy_Wspolny " + i+"%\r");
            Thread.sleep(1000);
        }
        System.out.println("//Nowy_Wspolny usuniety.");
    }

}
