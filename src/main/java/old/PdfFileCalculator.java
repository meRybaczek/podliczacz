package old;

import java.util.List;

public class PdfFileCalculator {
    private List<PdfFile> list;

    public PdfFileCalculator(List<PdfFile> list) {
        this.list = list;
    }

    public void setList(List<PdfFile> list) {
        this.list = list;
    }

    public Double getTotalPrice() {
        return list.stream()
                .map(PdfFile::countPrice)
                .reduce(0.0, Double::sum);
    }
    public Double getAllDrawingsArea() {
        return list.stream()
                .filter(x -> x.getOption() == PdfFileOption.DRAWING_BLACK || x.getOption() == PdfFileOption.DRAWING_COLOR)
                .map(PdfFile::countAreaSqm)
                .reduce(0.0, Double::sum);
    }
    public long getQuantityByOption(PdfFileOption option) {
        return list.stream()
                .filter(x -> x.getOption() == option)
                .count();
    }
}
