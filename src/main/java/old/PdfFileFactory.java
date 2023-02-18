package old;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PdfFileFactory {

    private final String filepath;

    public PdfFileFactory(String filepath) {
        this.filepath = filepath;
    }

    public List<PdfFile> createList() {
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

                    PdfFile pdfFile = createPdfFile(name, width, height, isColor);
                    pdfFileList.add(pdfFile);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return pdfFileList;
    }

    private PdfFile createPdfFile(String name, double width, double height, boolean isColor) {
        boolean isA4 = isA4Format(width, height);

        if(isA4 && !isColor)
            return new A4BlackPdfFile(name, width, height);
        else if(isA4)
            return new A4ColorPdfFile(name, width, height);

        return isColor ? new DrawingColorPdfFile(name, width, height) : new DrawingBlackPdfFile(name, width, height);
    }

    private boolean isA4Format(double width, double height) {
        double A4Area = 298 * 211;
        return width * height <= A4Area;
    }

    private File[] getFileArray() {
        File file = new File(filepath);
        if (!file.exists()) {
            System.err.println("Katalog nie istnieje.");
        }
        if (file.listFiles() == null) {
            System.err.println("Katalog jest pusty.");
        }
        return file.listFiles();
    }
}
