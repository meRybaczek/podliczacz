import java.util.ArrayList;
import java.util.List;

public class PdfFileStatistic {

    private List<PdfFile> pdfFileList = new ArrayList<>();

    public PdfFileStatistic(List<PdfFile> pdfFileList) {
        this.pdfFileList = pdfFileList;
    }

    public long countA4Quantity() {
        return pdfFileList.stream()
                .filter(PdfFile::isA4format)
                .count();
    }
    public long countDrawingsQuantity() {
        return pdfFileList.stream()
                .filter(x -> !x.isA4format())
                .count();
    }
    public double countAllDrawingsAreaSqm() {
        return pdfFileList.stream()
                .filter(x -> !x.isA4format())
                .map(PdfFile::areaSqm)
                .reduce(0.0, Double::sum);
    }

}
