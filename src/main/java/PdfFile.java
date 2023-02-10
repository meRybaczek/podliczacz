public class PdfFile {
    private String name;
    private double width;
    private double height;

    public PdfFile(String name, double width, double height) {
        this.name = name;
        this.width = width;
        this.height = height;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    @Override
    public String toString() {
        return Math.round(width) + " x " + Math.round(height) + " -----> " + name;
    }

    public double areaSqm() {
        return width/1000 * height/1000;
    }
    public boolean isA4format() {
        return (height <= 298 && width <= 211) || (height <= 211 && width <= 298);
   }
}
