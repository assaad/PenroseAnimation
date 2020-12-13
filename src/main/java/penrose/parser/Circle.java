package penrose.parser;

public class Circle extends Shape {
    public static String header = "Type,cx,cy,r,stroke,stroke-width";
    public double cx;
    public double cy;
    public double r;

    @Override
    public String toCsv() {
        return "Circle," + this.cx + "," + this.cy + "," + this.r + "," + this.stroke+ "," + this.srokeWidth;
    }

    @Override
    public String toSvg() {
        String line = "        <!-- Circle -->\n";
        line += "        <circle cx=\"" + this.cx + "\" cy=\"" + this.cy + "\" r=\"" + this.r + this.postProcess();
        return line;
    }
}
