package penrose.parser;

public class Line extends Shape {
    public double x1;
    public double y1;
    public double x2;
    public double y2;


    public Line() {

    }

    public Line(double x1, double y1, double x2, double y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    @Override
    public String toCsv() {
        return null;
    }

    @Override
    public String toSvg() {
        String line = "        <!-- Line -->\n";
        line += "        <path d=\"M" + this.x1 + "," + this.y1 + " L" + this.x2 + "," + this.y2 + " " + this.postProcess();
        return line;
    }
}
