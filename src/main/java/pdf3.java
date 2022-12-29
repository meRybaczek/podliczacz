import org.apache.commons.math3.util.Precision;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.poi.POIDocument;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class pdf3 {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        String version = "Podliczacz v_02";
        System.out.println(version + "\nPodaj sciezke katalogu: ");
        String filepath = scanner.nextLine();

        File file = new File(""+filepath+"");
        File[] files = file.listFiles();

        int totalA4 = 0;
        double totalArea = 0;
        assert files != null;
        for (File file1 : files) {

            try (
                    PDDocument pdf = PDDocument.load(file1);
                    ) {
                for (int i = 0; i < pdf.getNumberOfPages(); i++){

                    double width = ((pdf.getPage(i).getMediaBox().getWidth()) * 0.35277);  //dlaczego tutaj dziala gdy damy 0 a nie i
                    double height = ((pdf.getPage(i).getMediaBox().getHeight()) * 0.35277);
                    if (isA4format(width, height)) {
                        totalA4++;
                    } else {
                        System.out.println(Math.round(width) + " x " + Math.round(height) + " >>>>> " + file1.getName());
                        totalArea += (width/1000 * height/1000);
                    }
                }
            }
        }
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>\n" +
                "Podsumowanie dla katalogu: " + file + "\n" +
                "Liczba A4 [szt]: " + totalA4 + "\n" +
                "Powierzchnia rysunkow [m2]: " + Precision.round(totalArea,2));
        System.out.println("\nPodaj liczbe kopii: ");
        int copyQty = scanner.nextInt();
        scanner.nextLine();

        double totalQtyA4 = totalA4 * copyQty;
        double totalDrawingArea = Precision.round(totalArea * copyQty,2);
        System.out.println("Podaj cenę jednostkową A4: ");
        double a4price = scanner.nextDouble();
        scanner.nextLine();
        System.out.println("Podaj cene za m2 rysunku: ");
        double drawingPrice = scanner.nextDouble();
        scanner.nextLine();

        double totalA4price = Precision.round((totalQtyA4 * a4price),2);
        double totalDrawingPrice = Precision.round((totalDrawingArea * drawingPrice),2);
        double total = Precision.round((totalA4price + totalDrawingPrice),2);
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>\n" +
                "Wycena dla katalogu: " + file + " dla " + copyQty + " kopii\n\n" +
                "Suma A4 [szt]: " + totalQtyA4 + "\n" + "Suma rysunkow [m2]: " + totalDrawingArea + "\n\n" +
                "Kwota za A4 [zl]: " + totalA4price + "\n" + "Kwota za rysunki [zl]: " + totalDrawingPrice + "\n\n" +
                ">>>>>>> Razem [zl]: " + total + " <<<<<<<\n\nRabacik ?? Ile %?: ");

        double discount = Precision.round(((total * (1 - (scanner.nextDouble()/100)))), 2);
        scanner.nextLine();
        System.out.println(">>>>>>> Cena po rabacie [zl]: " +discount+ " <<<<<<<");
        scanner.close();
    }
    public static boolean isA4format ( double width, double height){
        return (height <= 298 && width <= 211) || (height <= 211 && width <= 298);
    }
}
