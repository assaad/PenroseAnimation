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

    public double minX = Double.MAX_VALUE;
    public double minY = Double.MAX_VALUE;
    public double maxX = -Double.MAX_VALUE;
    public double maxY = -Double.MAX_VALUE;


    public ArrayList<Circle> circles = new ArrayList<Circle>();
    public ArrayList<Line> lines = new ArrayList<Line>();
    public ArrayList<Polyline> poly = new ArrayList<Polyline>();
    public ArrayList<Arc> arcs = new ArrayList<Arc>();
    public int unknownShapes = 0;


    public ArrayList<Shape> shapes = new ArrayList<Shape>();

    public void addShape(Shape shape) {
        shapes.add(shape);
        if (shape instanceof Circle) {
            circles.add((Circle) shape);
        } else if (shape instanceof Line) {
            Line l = (Line) shape;
            if(l.x1<minX){
                minX=l.x1;
            }
            if(l.x2<minX){
                minX=l.x2;
            }

            if(l.x1>maxX){
                maxX=l.x1;
            }
            if(l.x2>maxX){
                maxX=l.x2;
            }

            if(l.y1<minY){
                minY=l.y1;
            }
            if(l.y2<minY){
                minY=l.y2;
            }

            if(l.y1>maxY){
                maxY=l.y1;
            }
            if(l.y2>maxY){
                maxY=l.y2;
            }

            lines.add(l);
        } else if (shape instanceof Arc) {
            arcs.add((Arc) shape);
        } else if (shape instanceof Polyline) {
            poly.add((Polyline) shape);
        }
    }

    public int shapeSize() {
        return shapes.size();
    }


//    <svg width="4679.2896287410595mm" height="1801.7570258695282mm" viewBox="-1732.2491238421599 -450.6657857826617 4679.2896287410595 1801.7570258695282" version="1.1" xmlns="http://www.w3.org/2000/svg" style="stroke-linecap:round;stroke-linejoin:round;fill:none"


}
