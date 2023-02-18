public class PdfFileFactory {

    public static PdfFile createPdfFile(String name, double width, double height, boolean isColor) {
        boolean isA4 = isA4Format(width, height);

        if(isA4 && !isColor)
            return new A4BlackPdfFile(name, width, height);
        else if(isA4)
            return new A4ColorPdfFile(name, width, height);
        else if(isColor)
            return new DrawingColorPdfFile(name, width, height);

        return new DrawingBlackPdfFile(name, width, height);
    }

    private static boolean isA4Format(double width, double height) {
        double A4Area = 298 * 211;
        return width * height <= A4Area;
    }
}
