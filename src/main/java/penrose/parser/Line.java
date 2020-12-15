package penrose.parser;

public class Line extends Shape {
    public static String header = "Type,x1,y1,x2,y2,stroke,stroke-width";
    public double x1;
    public double y1;
    public double x2;
    public double y2;

    public Line(Point p1, Point p2) {
        this.x1 = p1.x;
        this.y1 = p1.y;

        this.x2 = p2.x;
        this.y2 = p2.y;
    }


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
        return "Line," + this.x1 + "," + this.y1 + "," + this.x2 + "," + this.y2 + "," + this.stroke + "," + this.strokeWidth;
    }

    @Override
    public String toSvg() {
        String line = "        <!-- Line -->\n";
        line += "        <path d=\"M" + this.x1 + "," + this.y1 + " L" + this.x2 + "," + this.y2 + " " + this.postProcess();
        return line;
    }
}
