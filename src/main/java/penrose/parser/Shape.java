package penrose.parser;

public abstract class Shape {
    public String stroke;
    public String srokeWidth;

    public String postText;

    public abstract String toCsv();
    public abstract String toSvg();
}
