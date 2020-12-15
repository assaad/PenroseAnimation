package penrose.gui;

import penrose.Converter;
import penrose.Graph;
import penrose.Line;
import penrose.Point;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class PenroseManager {
    private PenroseFrame frame;
    private int lineStep = PenroseFrame.DEFAULT_LINE_STEP;
    private int timeStep = PenroseFrame.DEFAULT_TIME_STEP;
    private int imgWidth = 1700;
    private int imgHeight = 900;
    private int frameNumber = 0;  // A counter that increases by one in each frame.
    private long startTime;
    private long elapsedTimeMillis;  // The time, in milliseconds, since the animation started.
    private Graph graph;
    private Random random;
    private Point previous;
    private Point current;
    private Point next;

    public Point previousPixel;
    public Point pixel;
    public Color stroke;
    private boolean ready = false;
    public Timer animationTimer;

    public PenroseManager(PenroseFrame frame, int lineStep, int timeStep, int imgWidth, int imgHeight) {
        this.frame = frame;
        this.lineStep = lineStep;
        this.timeStep = timeStep;
        this.imgHeight = imgHeight;
        this.imgWidth = imgWidth;
    }


    public void setGraph(String path, int distance) {
        graph = Converter.convert(path, imgWidth, imgHeight, distance);
        long seed = System.currentTimeMillis();
        System.out.println("Seed: " + seed);
        random = new Random(seed);
        current = graph.getRandomStart(random);
        previous = current;
        Line l = current.randomWalk(previous, random, false);
        next = l.to;
        stroke = l.color;
        previousPixel = current;
        pixel = current;
        ready = true;
    }

    public boolean isReady() {
        return ready;
    }

    public void start() {
        this.startTime = System.currentTimeMillis();
        animationTimer.start();
    }

    public void stop() {
        animationTimer.stop();
    }

    public void nextStep() {
        frameNumber++;
        elapsedTimeMillis = System.currentTimeMillis() - startTime;
        if (frameNumber % lineStep == 0) {
            previous = current;
            current = next;
            Line l = current.randomWalk(previous, random, false);
            if (l == null) {
                current = graph.getRandomStart(random);
                next = current;
                previous = current;
                pixel = Point.from(current, next, frameNumber % lineStep, lineStep);
            } else {
                next = l.to;
                stroke = l.color;
                pixel = current;
            }

        }
        previousPixel = pixel;
        pixel = Point.from(current, next, frameNumber % lineStep, lineStep);
        frame.inkPanel.repaint();


//        System.out.println(previousPixel.getX()+","+ previousPixel.getY()+ " => "+pixel.getX()+","+ pixel.getY());
    }

    public void setDelay(int time) {
        this.timeStep = time;
        this.lineStep = this.timeStep / 10 + 1;
        this.animationTimer.setDelay(time);
    }
}
