package pdfFileFactory;

public class PdfFileFactory {

    public static PdfFile createPdfFile(String name, double width, double height, boolean isColor) {
        boolean isA4 = isA4Format(width, height);

        if(isA4 && !isColor)
            return new A4BlackPdfFileImpl(name, width, height);
        else if(isA4)
            return new A4ColorPdfFileImpl(name, width, height);
        else if(isColor)
            return new DrawingColorPdfFileImpl(name, width, height);

        return new DrawingBlackPdfFileImpl(name, width, height);
    }

    private static boolean isA4Format(double width, double height) {
        double A4Area = 298 * 211;
        return width * height <= A4Area;
    }
}
