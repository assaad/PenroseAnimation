import penrose.Graph;
import penrose.Line;
import penrose.Point;

import java.util.Random;

public class Animation {
    public static void main(String[] args) {
        String csv = "/media/assaad/Published/code/penroseAnimtion/tiles-conv.csv";
        Graph graph = Graph.parseCsv(csv, 800, 600);
        Random random = new Random(1234);

        Point current = graph.getRandomStart(random);
        Point previous = current;
        Point next;
        int steps = 10;
        for (int i = 0; i < steps; i++) {
            Line l = current.randomWalk(previous, random);
            next = l.to;
            System.out.println("Moving from p1: " + current.print() + " to " + next.print());
            previous = current;
            current = next;
        }

    }
}
