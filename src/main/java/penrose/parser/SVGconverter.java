package penrose.parser;


public class SVGconverter {


    public static void main(String[] args) {
        try {
            //Parse svg
            String svgPath = "/home/assaad/Downloads/tile.svg";
            SVG svg = SVGParser.parse(svgPath);

            //Export svg
            String svgPathOutput = "/home/assaad/Downloads/tileOutput-sorted.svg";
            SVGParser.export(svg,svgPathOutput, true);

            svgPathOutput = "/home/assaad/Downloads/tileOutput-copy.svg";
            SVGParser.export(svg,svgPathOutput, false);

            svgPathOutput = "/home/assaad/Downloads/tile-csv.csv";
            SVGParser.exportCSV(svg,svgPathOutput);

        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }
}
