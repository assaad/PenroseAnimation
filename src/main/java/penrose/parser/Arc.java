package penrose.parser;

public class Arc extends Shape{
    public static String header = "Type,x,y,p0,p1,p2,p3,p4,p5,p6,stroke,stroke-width";

    Point start;
    double[] pos = new double[7];

    @Override
    public String toCsv() {
        return "Arc," + this.start.x + "," + this.start.y + "," + this.pos[0] + "," + this.pos[1] + "," + (int)(this.pos[2]) + "," + (int)(this.pos[3]) + "," + (int)(this.pos[4]) + "," + this.pos[5] + "," + this.pos[6] + "," + this.stroke+ "," + this.strokeWidth;
    }

    @Override
    public String toSvg() {
        String line = "        <!-- Arc -->\n";
        // <path d="M338.15883028274806,-1213.9504333858122 A217.60500000001693,217.60500000001693 0 0,1 375.43286029948365,-1222.527074649358 " style="stroke:#ffff00;stroke-width:0;"/>
//        line += "        <path d=\"M"+this.start.x+","+this.start.y +" A"+this.pos[0]+","+this.pos[1]+" "+(this.pos[2])+" "+this.pos[3]+","+this.pos[4]+" "+this.pos[5]+","+this.pos[6]+" "+this.postText;
        line += "        <path d=\"M"+this.start.x+","+this.start.y +" A"+this.pos[0]+","+this.pos[1]+" "+(int)(this.pos[2])+" "+(int)(this.pos[3])+","+(int)(this.pos[4])+" "+this.pos[5]+","+this.pos[6]+" "+this.postProcess();

        return line;
    }
}
