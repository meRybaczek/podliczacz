import org.apache.commons.math3.util.Precision;

import java.util.List;

public class InternalSummaryStrategy implements SummaryStrategy{

    private int copiesQuantity;
    private double a4UnitPrice;
    private double drawingUnitPrice;
    private List<PdfFile> list;
    private PdfFileCalculator pdfFileCalculator;

    public InternalSummaryStrategy(List<PdfFile> list, PdfFileCalculator pdfFileCalculator) {
        this.list = list;
        this.pdfFileCalculator = pdfFileCalculator;
    }

    private void getDataFromClient() {
        System.out.println("Podaj liczbe kopii: ");
        this.copiesQuantity = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Wprowadz cene jednostkowa A4: ");
        this.a4UnitPrice = scanner.nextDouble();
        scanner.nextLine();
        System.out.println("Wprowadx cene za m2 rysunku: ");
        this.drawingUnitPrice = scanner.nextDouble();
        scanner.nextLine();
    }

    @Override
    public void createSummary() {
        getDataFromClient();
        setUnitDataForPdfFile(PdfFileOption.DRAWING_BLACK, drawingUnitPrice);
        setUnitDataForPdfFile(PdfFileOption.A4_BLACK, a4UnitPrice);
        printTotalPriceSummary();
    }

    private void setUnitDataForPdfFile(PdfFileOption option, double unitPrice) {
        list.stream()
                .filter(x -> x.getOption() == option)
                .forEach(x -> x.setUnitPrice(unitPrice));

    }

    private void printTotalPriceSummary() {
        pdfFileCalculator.setList(list);        // new list has to be updated in pdfCalculator object due to setUnit prices above
        long a4Quantity = pdfFileCalculator.getQuantityByOption(PdfFileOption.A4_BLACK);
        Double totalDrawingsArea = pdfFileCalculator.getAllDrawingsArea();
        Double totalPrice = pdfFileCalculator.getTotalPrice();

        System.out.println("\n>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>\n" +
                "Podsumowanie dla " + copiesQuantity + " kopii: \n" +
                "Sumaryczna ilosc A4 [szt]: " + (a4Quantity * copiesQuantity) + "\n" +
                "Sumaryczna powierzchnia rysunkow [m2]: " + Precision.round((totalDrawingsArea * copiesQuantity), 2) + "\n\n" +
                "Cena calosciowa [zł]: " + Precision.round((totalPrice * copiesQuantity), 2) +
                "\n>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    }
}
