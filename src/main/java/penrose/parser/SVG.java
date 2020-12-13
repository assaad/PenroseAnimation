package penrose.parser;

import java.util.ArrayList;

public class SVG {
    public String header; //<?xml version="1.0" encoding="UTF-8"?>
    public String headerComment;
    public double width;
    public double height;
    public Rectangle viewBox;
    public String viewBoxString;
    public String transform;

    public int circles = 0;
    public int lines = 0;
    public int poly = 0;
    public int arcs = 0;


    private ArrayList<Shape> shapes = new ArrayList<Shape>();

    public void addShape(Shape shape) {
        shapes.add(shape);
        if (shape instanceof Circle) {
            circles++;
        } else if (shape instanceof Line) {
            lines++;
        } else if (shape instanceof Arc) {
            arcs++;
        } else if (shape instanceof Polyline) {
            poly++;
        }
    }

    public int shapeSize(){
        return shapes.size();
    }


//    <svg width="4679.2896287410595mm" height="1801.7570258695282mm" viewBox="-1732.2491238421599 -450.6657857826617 4679.2896287410595 1801.7570258695282" version="1.1" xmlns="http://www.w3.org/2000/svg" style="stroke-linecap:round;stroke-linejoin:round;fill:none"


}
