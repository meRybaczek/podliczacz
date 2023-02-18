
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.*;
import java.util.Arrays;
import java.util.List;


public class ExternalSummaryStrategy implements SummaryStrategy {

    private String clientName;
    private int copiesQuantity;
    private int drawingType;
    private int foldDrawing;
    private double discount;
    private String filepath;
    private List<PdfFile> list;

    public ExternalSummaryStrategy(String filepath, List<PdfFile> list) {
        this.filepath = filepath;
        this.list = list;
    }

    @Override
    public void createSummary() {
        getDataFromClient();
        extractToExcelFile();
    }

    private void getDataFromClient() {
        System.out.println("Podaj nazwe klienta:");
        this.clientName = scanner.nextLine();
        System.out.println("Wprowadz rabat[%]:");
        this.discount = scanner.nextDouble();
        scanner.nextLine();
        System.out.println("Podaj liczbe kopii: ");
        this.copiesQuantity = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Skladac rysunki (0 - NIE, 1 - TAK)");
        this.foldDrawing = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Podaj rodzaj rysunku:");
        printDrawingTypes();
        drawingType = scanner.nextInt();
    }

    private void printDrawingTypes() {
        Arrays.stream(DrawingType.values()).toList()
                .forEach(System.out::println);
    }

    private void extractToExcelFile() {
//        InputStream resourceAsStream = ExternalSummaryStrategy.class.getResourceAsStream("podliczanie.xls");
//        resourceAsStream.

        File xlsxFile = new File("./podliczanie.xls");

        List<PdfFile> onlyDrawings = getAllDrawings(list);

        try {
            FileInputStream inputStream = new FileInputStream(xlsxFile);
            Workbook workbook = WorkbookFactory.create(inputStream);
            Sheet sheet = workbook.getSheetAt(0);

            int rowCount = 1;

            for (PdfFile pdfFile : onlyDrawings) {
                Row row = sheet.getRow(rowCount++);

                row.createCell(2).setCellValue(Math.round(pdfFile.getHeight()));
                row.createCell(3).setCellValue(Math.round(pdfFile.getWidth()));
                row.createCell(4).setCellValue(drawingType);
                row.createCell(5).setCellValue(foldDrawing);
                row.createCell(6).setCellValue(copiesQuantity);

            }
            sheet.getRow(2).createCell(9).setCellValue(clientName);
            sheet.getRow(9).createCell(9).setCellValue(discount);
            sheet.getRow(rowCount +1).createCell(1).setCellValue("A4");

            long totalA4Qty = getA4Quantity() * copiesQuantity;
            sheet.getRow(rowCount +1).createCell(6).setCellValue(totalA4Qty);


            inputStream.close();
            FileOutputStream os = new FileOutputStream(filepath+"\\summary.xls");
            workbook.write(os);


            workbook.close();
            os.close();

            System.out.println("Excel z obliczeniem znajdziesz tu " + filepath +"\n" +
                    "*** UWAGA *** w pliku excel dodaj cene za A4");

        } catch (IOException e) {
            System.err.println("Exception while updating an existing excel file.");
            e.printStackTrace();
        }
    }

    private List<PdfFile> getAllDrawings(List<PdfFile> list) {
        return list.stream()
                .filter(x -> x.getOption() == PdfFileOption.DRAWING_BLACK || x.getOption() == PdfFileOption.DRAWING_COLOR)
                .toList();
    }
    private long getA4Quantity() {
        return list.stream()
                .filter(x -> x.getOption() == PdfFileOption.A4_BLACK || x.getOption() == PdfFileOption.A4_COLOR)
                .count();
    }
}
