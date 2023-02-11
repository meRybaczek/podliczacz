import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PdfFileListCreator {
    private String filepath;

    public PdfFileListCreator(String filepath) {
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

                for (int i = 0; i < pdf.getNumberOfPages(); i++) {
                    PDPage page = pdf.getPage(i);

                    double width = ((page.getMediaBox().getWidth()) * CONVERT_TO_MM_FACTOR);
                    double height = ((page.getMediaBox().getHeight()) * CONVERT_TO_MM_FACTOR);
                    String name = file.getName();

                    PdfFile pdfFile = new PdfFile(name, width, height);
                    pdfFileList.add(pdfFile);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return pdfFileList;
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
