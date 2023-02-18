import org.apache.commons.math3.util.Precision;

import java.util.List;

public class InternalSummaryStrategy implements SummaryStrategy{

    private ClientUnitData clientUnitData;
    private List<PdfFile> pdfFileList;

    

    @Override
    public void createSummary() {

    }

    private void printInfo() {
        int copiesQuantity = clientUnitData.getQuantity();
        double a4BlackUnitPrice = clientUnitData.getA4BlackUnitPrice();
        double a4ColorUnitPrice = clientUnitData.getA4ColorUnitPrice();
        double drawingBlackUnitPrice = clientUnitData.getDrawingBlackUnitPrice();
        double drawingColorUnitPrice = clientUnitData.getDrawingColorUnitPrice();
        long a4BlackQty = getQuantityByOption(PdfFileOption.A4_BLACK);
        long a4ColorQty = getQuantityByOption(PdfFileOption.A4_COLOR);
        long drawingColorQty = getQuantityByOption(PdfFileOption.DRAWING_COLOR);
        long drawingBlackQty = getQuantityByOption(PdfFileOption.DRAWING_BLACK);
        Double allBlackDrawingsArea = getAllDrawingsArea(PdfFileOption.DRAWING_BLACK);
        Double allColorDrawingsArea = getAllDrawingsArea(PdfFileOption.DRAWING_COLOR);


        System.out.println("\n>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>\n" +
                "Podsumowanie dla " + copiesQuantity + " kopii: \n" +
                "Sumaryczna ilosc A4 cz-b [szt]: " + (a4BlackQty   * copiesQuantity) + "\n" +
                "Sumaryczna ilosc A4 kolor [szt]: " + (a4ColorQty   * copiesQuantity) + "\n" +
                "Sumaryczna ilosc rysunkow cz-b [szt]: " + (drawingColorQty   * copiesQuantity) + "\n" +
                "Sumaryczna ilosc rysunkow kolor [szt]: " + (drawingBlackQty   * copiesQuantity) + "\n" +
                "Sumaryczna powierzchnia rysunkow cz-b [m2]: " + (allBlackDrawingsArea   * copiesQuantity) + "\n" +
                "Sumaryczna powierzchnia rysunkow kolor [m2]: " + (allColorDrawingsArea   * copiesQuantity) + "\n" +


                "Sumaryczna powierzchnia rysunkow [m2]: " + Precision.round((totalDrawingsArea * copiesQuantity), 2) + "\n\n" +
                "Cena calosciowa [zl]: " + Precision.round((totalPrice * copiesQuantity), 2) +
                "\n>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    }

    private long getQuantityByOption(PdfFileOption option) {
        return pdfFileList.stream()
                .filter(x -> x.getOption() == option)
                .count();
    }
    public Double getAllDrawingsArea(PdfFileOption option) {
        return pdfFileList.stream()
                .filter(x -> x.getOption() == option)
                .map(PdfFile::countAreaSqm)
                .reduce(0.0, Double::sum);
    }
}
