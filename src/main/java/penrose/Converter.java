package penrose;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Converter {
    public static Graph convert(String svg, int width, int height, int distance){
        int counter =0;
        BufferedReader br = null;
        String line = "";
        boolean header = true;
        double xMin = Double.MAX_VALUE;
        double yMin = Double.MAX_VALUE;
        double xMax = -Double.MAX_VALUE;
        double yMax = -Double.MAX_VALUE;
        double diffX = 0;
        double diffY = 0;
        double ratio = 0;
        boolean nextToParse = false;
        ArrayList<double[]> res = new ArrayList<double[]>();
        ArrayList<String> strokes = new ArrayList<String>();
        try {
            br = new BufferedReader(new FileReader(svg));
            while ((line = br.readLine()) != null) {
                if (line.contains("<!-- Line -->")) {
                    nextToParse = true;
                } else {
                    if (nextToParse && line.contains("path")) {
                        String p = line.substring(line.indexOf("M") + 1, line.lastIndexOf("\" style"));
                        String stroke = line.substring(line.indexOf("stroke:")+7,line.lastIndexOf(";stroke-width"));
                        strokes.add(stroke);
                        p = p.replace("L", ",");
                        String[] sp = p.split(",");
                        double[] xvar = new double[4];
                        xvar[0] = Double.parseDouble(sp[0]); //x1
                        xvar[1] = Double.parseDouble(sp[1]); //y1
                        xvar[2] = Double.parseDouble(sp[2]);
                        xvar[3] = Double.parseDouble(sp[3]);
                        if (xvar[0] < xMin) {
                            xMin = xvar[0];
                        }
                        if (xvar[2] < xMin) {
                            xMin = xvar[2];
                        }
                        if (xvar[0] > xMax) {
                            xMax = xvar[0];
                        }
                        if (xvar[2] > xMax) {
                            xMax = xvar[2];
                        }

                        if (xvar[1] < yMin) {
                            yMin = xvar[1];
                        }
                        if (xvar[3] < yMin) {
                            yMin = xvar[3];
                        }
                        if (xvar[1] > yMax) {
                            yMax = xvar[1];
                        }
                        if (xvar[3] > yMax) {
                            yMax = xvar[3];
                        }

                        res.add(xvar);
                    }
                    nextToParse = false;
                }
                counter++;
            }

            diffX = xMax - xMin;
            diffY = yMax - yMin;
            ratio = diffY / diffX;
            System.out.println("View point: " + xMin + "," + yMin + " -> " + xMax + "," + yMax + " ratio: " + ratio);

            for (int i = 0; i < res.size(); i++) {
                double[] xVar = res.get(i);
                double x1 = convert(xVar[0], xMin, diffX);
                double y1 = convert(xVar[1], yMin, diffY);
                double x2 = convert(xVar[2], xMin, diffX);
                double y2 = convert(xVar[3], yMin, diffY);
                xVar[0]=x1;
                xVar[1]=y1;
                xVar[2]=x2;
                xVar[3]=y2;
//                System.out.println(x1 + "," + y1 + "," + x2 + "," + y2);
            }
        } catch (Exception e) {
            System.out.println(res.size() + " line : "+ counter);
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }


        return Graph.parseArray(res,strokes,ratio,width,height, distance);
    }


    public static void main(String[] args) {
        String csv = "/media/assaad/Published/code/penroseAnimtion/tiles-conv.csv";
        String svg = "/media/assaad/Published/code/penroseAnimtion/tiles.txt";


        int counter =0;
        BufferedReader br = null;
        String line = "";
        boolean header = true;
        double xMin = Double.MAX_VALUE;
        double yMin = Double.MAX_VALUE;
        double xMax = -Double.MAX_VALUE;
        double yMax = -Double.MAX_VALUE;
        double diffX = 0;
        double diffY = 0;
        double ratio = 0;
        boolean nextToParse = false;
        ArrayList<double[]> res = new ArrayList<double[]>();
        ArrayList<String> strokes = new ArrayList<String>();
        try {
            PrintWriter pw = new PrintWriter(new FileWriter(csv));
            br = new BufferedReader(new FileReader(svg));
            while ((line = br.readLine()) != null) {
                if (line.contains("<!-- penrose.Line -->")) {
                    nextToParse = true;
                } else {
                    if (nextToParse && line.contains("path")) {
                        String p = line.substring(line.indexOf("M") + 1, line.lastIndexOf("\" style"));
                        String stroke = line.substring(line.indexOf("stroke:")+7,line.lastIndexOf(";stroke-width"));
                        strokes.add(stroke);
                        p = p.replace("L", ",");
                        String[] sp = p.split(",");
                        double[] xvar = new double[4];
                        xvar[0] = Double.parseDouble(sp[0]); //x1
                        xvar[1] = Double.parseDouble(sp[1]); //y1
                        xvar[2] = Double.parseDouble(sp[2]);
                        xvar[3] = Double.parseDouble(sp[3]);
                        if (xvar[0] < xMin) {
                            xMin = xvar[0];
                        }
                        if (xvar[2] < xMin) {
                            xMin = xvar[2];
                        }
                        if (xvar[0] > xMax) {
                            xMax = xvar[0];
                        }
                        if (xvar[2] > xMax) {
                            xMax = xvar[2];
                        }

                        if (xvar[1] < yMin) {
                            yMin = xvar[1];
                        }
                        if (xvar[3] < yMin) {
                            yMin = xvar[3];
                        }
                        if (xvar[1] > yMax) {
                            yMax = xvar[1];
                        }
                        if (xvar[3] > yMax) {
                            yMax = xvar[3];
                        }

                        res.add(xvar);
                    }
                    nextToParse = false;
                }
                counter++;
            }

            diffX = xMax - xMin;
            diffY = yMax - yMin;
            ratio = diffY / diffX;
            System.out.println("View point: " + xMin + "," + yMin + " -> " + xMax + "," + yMax + " ratio: " + ratio);
            pw.println(ratio);
            for (int i = 0; i < res.size(); i++) {
                double[] xVar = res.get(i);
                double x1 = convert(xVar[0], xMin, diffX);
                double y1 = convert(xVar[1], yMin, diffY);
                double x2 = convert(xVar[2], xMin, diffX);
                double y2 = convert(xVar[3], yMin, diffY);
                System.out.println(x1 + "," + y1 + "," + x2 + "," + y2);
                pw.println(x1 + "," + y1 + "," + x2 + "," + y2+","+strokes.get(i));
            }

            pw.close();
        } catch (Exception e) {
            System.out.println(res.size() + " line : "+ counter);
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }


    }

    private static double convert(double value, double min, double diff) {
        double res = (value - min) / diff;
        if (res < 0) {
            throw new RuntimeException("outside range");
        }
        return res;
    }
}
