package penrose.parser;

public class Point extends Shape {
    public double x;
    public double y;

    public Point(){

    }
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toCsv() {
        return null;
    }

    @Override
    public String toSvg() {
        String line = "        <!-- Point -->\n";
        return line;
    }
}
