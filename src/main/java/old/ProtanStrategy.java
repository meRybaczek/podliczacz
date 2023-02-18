package old;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class ProtanStrategy implements SummaryStrategy{

    private final String clientName = "Protan";
    private final int copiesQuantity = 5;
    private final int drawingType = 1;
    private final int foldDrawing = 1;
    private double discount = 10;
    private String filepath;
    private List<PdfFile> list;


    public ProtanStrategy(String filepath, List<PdfFile> list) {
        this.filepath = filepath;
        this.list = list;

    }

    @Override
    public void createSummary() {
        extractToExcelFile();
    }

    private void extractToExcelFile() {
        File xlsxFile = new File("./podliczanie.xls");

        File file = new File(filepath);
        String folderName = file.getName();

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
            sheet.getRow(3).createCell(8).setCellValue(folderName);


            long totalA4Qty = (getA4Quantity() - 1) * copiesQuantity;
            sheet.getRow(rowCount +1).createCell(1).setCellValue("A4 cz-b");
            sheet.getRow(rowCount +1).createCell(6).setCellValue(totalA4Qty);

            sheet.getRow(rowCount +2).createCell(1).setCellValue("A4 kolor");
            sheet.getRow(rowCount +2).createCell(6).setCellValue(copiesQuantity);

            sheet.getRow(rowCount +3).createCell(1).setCellValue("oprawa");
            sheet.getRow(rowCount +3).createCell(6).setCellValue(copiesQuantity);

            inputStream.close();
            FileOutputStream os = new FileOutputStream(filepath+"\\Protan_podliczenie.xls");

            workbook.write(os);


            workbook.close();
            os.close();

            System.out.println("Excel z obliczeniem znajdziesz tu " + filepath +"\n" +
                    "*** UWAGA *** w pliku excel dodaj cene za A4 i oprawy!");

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
