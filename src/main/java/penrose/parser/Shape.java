package penrose.parser;

public abstract class Shape {
    public String stroke;
    public String strokeWidth;

    public static String POST_TEXT = "\" style=\"stroke:STROKE_VALUE;stroke-width:STROKE_WIDTH;\"/>";

    public abstract String toCsv();
    public abstract String toSvg();

    public String postProcess(){
        String p = POST_TEXT+"";
        p=p.replace("STROKE_VALUE",stroke);
        p=p.replace("STROKE_WIDTH", strokeWidth);
        return p;
    }

}
