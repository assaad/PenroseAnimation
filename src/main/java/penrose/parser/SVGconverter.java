package penrose.parser;


public class SVGconverter {


    public static void main(String[] args) {
        try {
            //Parse svg
            String svgPath = "/home/assaad/Downloads/tile.svg";
            SVG svg = SVGParser.parse(svgPath);

            //Export svg
            String svgPathOutput = "/home/assaad/Downloads/tileOutput.svg";
            SVGParser.export(svg,svgPathOutput, false);


        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }
}
