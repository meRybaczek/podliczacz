
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
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
        System.out.println("Wprowadz rabat");
        this.discount = scanner.nextDouble();
        scanner.nextLine();
        System.out.println("Podaj liczbe kopii: ");
        this.copiesQuantity = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Skladac rysunki (0 - nie, 1 - tak)");
        this.foldDrawing = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Wpisz rodzaj rysunku");
        printDrawingTypes();
        drawingType = scanner.nextInt();
    }

    private void printDrawingTypes() {
        Arrays.stream(DrawingType.values()).toList()
                .forEach(System.out::println);
    }

    private void extractToExcelFile() {
        File xlsxFile = new File("D:\\plikipdf\\drawings.xls");

        List<PdfFile> onlyDrawings = getAllDrawings(list);

        try {
            FileInputStream inputStream = new FileInputStream(xlsxFile);
            Workbook workbook = WorkbookFactory.create(inputStream);
            Sheet sheet = workbook.getSheetAt(0);

            int rowCount = 1;

            for (PdfFile pdfFile : onlyDrawings) {
                Row row = sheet.createRow(rowCount++);

                row.createCell(1).setCellValue(Math.round(pdfFile.getHeight()));
                row.createCell(2).setCellValue(Math.round(pdfFile.getWidth()));
                row.createCell(3).setCellValue(drawingType);
                row.createCell(4).setCellValue(foldDrawing);
                row.createCell(5).setCellValue(copiesQuantity);

            }
            sheet.createRow(1).createCell(8).setCellValue(clientName);
            sheet.createRow(2).createCell(8).setCellValue(discount);
            sheet.createRow(3).createCell(8).setCellValue("A4 szt " + getA4Quantity());

            inputStream.close();
            FileOutputStream os = new FileOutputStream(filepath+"\\summary.xls");
            workbook.write(os);


            workbook.close();
            os.close();

            System.out.println("Excel z obliczeniem znajdziesz tu " + filepath);

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
