package penrose.parser;


public class SVGconverter {


    public static void main(String[] args) {
        try {
            String svgPath = "/home/assaad/Downloads/tile.svg";
            SVG svg = SVGParser.parse(svgPath);

        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }
}
