import org.apache.commons.math3.util.Precision;

import java.util.List;
public class InternalSummaryStrategy implements SummaryStrategy {

    private List<PdfFile> pdfFileList;

    private ClientUnitData clientUnitData;

    private String filepath;

    public InternalSummaryStrategy(List<PdfFile> pdfFileList, ClientUnitData clientUnitData, String filepath) {
        this.pdfFileList = pdfFileList;
        this.clientUnitData = clientUnitData;
        this.filepath = filepath;
    }

    @Override
    public void createSummary() {

        int copiesQuantity = clientUnitData.getQuantity();
        double a4BlackUnitPrice = clientUnitData.getA4BlackUnitPrice();
        double a4ColorUnitPrice = clientUnitData.getA4ColorUnitPrice();
        double drawingBlackUnitPrice = clientUnitData.getDrawingBlackUnitPrice();
        double drawingColorUnitPrice = clientUnitData.getDrawingColorUnitPrice();

        long a4BlackQty = getQuantityByOption(PdfFileOption.A4_BLACK);
        long a4ColorQty = getQuantityByOption(PdfFileOption.A4_COLOR);
        long drawingColorQty = getQuantityByOption(PdfFileOption.DRAWING_COLOR);
        long drawingBlackQty = getQuantityByOption(PdfFileOption.DRAWING_BLACK);
        double allBlackDrawingsArea = getAllDrawingsArea(PdfFileOption.DRAWING_BLACK);
        double allColorDrawingsArea = getAllDrawingsArea(PdfFileOption.DRAWING_COLOR);
        double allDrawings = allBlackDrawingsArea + allColorDrawingsArea;

        double totalPrice = (a4BlackUnitPrice * a4BlackQty)+ (a4ColorUnitPrice * a4ColorQty) +
                (drawingBlackUnitPrice * allBlackDrawingsArea) + (drawingColorUnitPrice * allColorDrawingsArea);

        System.out.println("\n>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>\n" +
                "Katalog wejsciowy: " + filepath + "\n" +
                "Podsumowanie dla " + copiesQuantity + " kopii: \n" +
                "Sumaryczna ilosc A4 cz-b [szt]: " + (a4BlackQty * copiesQuantity) + "\n" +
                "Sumaryczna ilosc A4 kolor [szt]: " + (a4ColorQty * copiesQuantity) + "\n" +
                "Sumaryczna ilosc rysunkow cz-b [szt]: " + (drawingBlackQty * copiesQuantity) + "\n" +
                "Sumaryczna ilosc rysunkow kolor [szt]: " + (drawingColorQty * copiesQuantity) + "\n" +
                "Sumaryczna powierzchnia rysunkow cz-b [m2]: " + Precision.round((allBlackDrawingsArea * copiesQuantity), 2) + "\n" +
                "Sumaryczna powierzchnia rysunkow kolor [m2]: " + Precision.round((allColorDrawingsArea * copiesQuantity), 2) + "\n" +
                "Sumaryczna powierzchnia rysunkow [m2]: " + Precision.round((allDrawings * copiesQuantity), 2) + "\n\n" +
                "Cena calosciowa [zl]: " + Precision.round((totalPrice * copiesQuantity), 2) +
                "\n>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>\n");
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
