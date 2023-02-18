import org.apache.commons.codec.binary.StringUtils;
import org.apache.commons.math3.util.Precision;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PodliczaczApp {
    private static final String APP_VERSION = "v_04";
    Scanner scanner;
    private String filepath;
    private List<PdfFile> list = new ArrayList<>();
    private PdfFileCalculator pdfFileCalculator;
    private SummaryStrategy summaryStrategy;

    public static void main(String[] args)  {
        PodliczaczApp testApp = new PodliczaczApp();
        System.out.println("\nWitaj w Podliczacz " + APP_VERSION + "\n");
        testApp.start();
    }

    private void start() {
        getFilepath();
        createPdfFileList();
        printListByOption(PdfFileOption.DRAWING_BLACK);
        printQuantitySummary();
        mainLoop();
        scanner.close();
    }

    private void getFilepath() {
        scanner = new Scanner(System.in);
        System.out.println("Podaj sciezke katalogu z plikami pdf: ");
        this.filepath = scanner.nextLine();
    }

    private void createPdfFileList() {
        PdfFileFactory pdfFileFactory = new PdfFileFactory(filepath);
        list = pdfFileFactory.createList();
    }

    private void printListByOption(PdfFileOption option) {      //plan to implement options to chose
        System.out.printf("Lista dla %s:\n", option.name());

        list.stream()
                .filter(x -> x.getOption() == option)
                .forEach(PdfFile::printInfo);
    }

    private void mainLoop()  {
        System.out.println("""

                1 ---> zacznij ponownie
                2 ---> skopiuj do excela
                3 ---> podlicz tutaj
                4 ---> PROTAN
                5 ---> nie radze
                0 ---> EXIT""");
        int option = scanner.nextInt();
        switch (option) {
            case 1 -> start();
            case 2, 3, 4 -> {
                createSummary(option);
                mainLoop();
            }
            case 5 -> {
                usuwanieWspolny();
                mainLoop();
            }
            case 0 -> exit();
            default -> {
                System.err.println("Bledny wybor");
                mainLoop();
            }
        }
    }

    private void createSummary(int option) {
        if (option == 2)
            summaryStrategy = new ExternalSummaryStrategy(filepath, list);
        if (option == 3)
            summaryStrategy = new InternalSummaryStrategy(list, pdfFileCalculator);
        if (option == 4)
            summaryStrategy = new ProtanStrategy(filepath, list);

        summaryStrategy.createSummary();
    }

    private void printQuantitySummary() {
        pdfFileCalculator = new PdfFileCalculator(list);
        long a4Quantity = pdfFileCalculator.getQuantityByOption(PdfFileOption.A4_BLACK);
        Double totalDrawingsArea = pdfFileCalculator.getAllDrawingsArea();

        System.out.println("\n>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>\n" +
                "Podsumowanie dla katalogu: " + filepath + "\n" +
                "Ilosc A4 [szt]: " + a4Quantity + "\n" +
                "Powierzchnia rysunkow [m2]: " + Precision.round((totalDrawingsArea), 2) +
                "\n>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    }


    private void exit() {
        scanner.nextLine();
        System.out.println("""
                  _                                                                                           \s
                 |_)  _  |_        |_|     ._ _  ._ _       _       \\    / |  _   _ _   _ _     _  ._   _.   |\s
                 |_) (_) |_)   o   | | |_| | | | | | | |_| _>   o    \\/\\/  | (_) _> /_ (_ /_ \\/ /_ | | (_|   o\s
                               /                                /                            /                \s
                """);
        System.exit(0);
    }

    private void usuwanieWspolny() {
        try {
            for (int i = 0; i <= 100; i++) {
                System.out.print("Usuwanie plikow //Nowy_Wspolny " + i + "%\r");
                Thread.sleep(1000);
            }
        } catch (InterruptedException e){
            e.getStackTrace();
        }


        System.out.println("folder //Nowy_Wspolny usuniety.");
    }


}
