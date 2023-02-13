public class PdfFile {
    private String name;
    private double width;
    private double height;

    public PdfFile(String name, double width, double height) {
        this.name = name;
        this.width = width;
        this.height = height;
    }

    public void printInfo() {
        System.out.printf("%.0f\t%.0f%n", height, width);         // \t-tabulator space
    }

    public void printDetailInfo() {
        System.out.printf("%.0f x %.0f [mm] --------> %s%n", height, width, name);
    }

    public double areaSqm() {
        return width/1000 * height/1000;
    }
    public boolean isA4format() {
        return (height <= 298 && width <= 211) || (height <= 211 && width <= 298);
   }
}
