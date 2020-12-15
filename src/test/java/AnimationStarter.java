import penrose.Converter;
import penrose.Graph;
import penrose.Line;
import penrose.Point;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.Random;

public class AnimationStarter extends JPanel {
    private static int STEPS = 3;
    private static int TIMESTEP = 10;
    private static int IMG_WIDTH = 1700;
    private static int IMG_HEIGHT = 900;
    private int frameNumber;  // A counter that increases by one in each frame.
    private long elapsedTimeMillis;  // The time, in milliseconds, since the animation started.
    private Graph graph;
    private Random random;
    private penrose.Point previous;
    private penrose.Point current;
    private penrose.Point next;
    private penrose.Point previousPixel;
    private penrose.Point pixel;
    private Color stroke;
    private BufferedImage image;
    private float pixelSize;  // This is the measure of a pixel in the coordinate system

    public AnimationStarter() {
        setPreferredSize(new Dimension(IMG_WIDTH, IMG_HEIGHT)); // Set size of drawing area, in pixels.
    }
    // set up by calling the applyLimits method.  It can be used
    // for setting line widths, for example.

    public static void main(String[] args) {
        JFrame window;
        window = new JFrame("Penrose Animation");  // The parameter shows in the window title bar.
        final AnimationStarter panel = new AnimationStarter(); // The drawing area.
        window.setContentPane(panel); // Show the panel in the window.
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // End program when window closes.
        window.pack();  // Set window size based on the preferred sizes of its contents.
        window.setResizable(false); // Don't let user resize window.
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        window.setLocation( // Center window on screen.
                (screen.width - window.getWidth()) / 2,
                (screen.height - window.getHeight()) / 2);
        Timer animationTimer;  // A Timer that will emit events to drive the animation.

        //Create a file chooser
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "SVG", "svg");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
//            System.out.println("You chose to open this file: " +
//                    chooser.getSelectedFile().getName());


//            String csv = "/media/assaad/Published/code/penroseAnimtion/tiles-conv.csv";
//            panel.graph = penrose.Graph.parseCsv(csv, IMG_WIDTH, IMG_HEIGHT);
            int distance = 30;
            panel.graph = Converter.convert(chooser.getSelectedFile().getAbsolutePath(), IMG_WIDTH, IMG_HEIGHT, distance);
            long seed = System.currentTimeMillis();
            System.out.println("Seed: " + seed);
            panel.random = new Random(seed);
            panel.current = panel.graph.getRandomStart(panel.random);
            panel.previous = panel.current;
            Line l = panel.current.randomWalk(panel.previous, panel.random, false);
            panel.next = l.to;
            panel.stroke = l.color;
            panel.previousPixel = panel.current;
            panel.pixel = panel.current;
            panel.image = new BufferedImage(IMG_WIDTH, IMG_HEIGHT, BufferedImage.TYPE_INT_ARGB);
            Graphics2D gPixel = panel.image.createGraphics();
            gPixel.setBackground(Color.WHITE);
            gPixel.fillRect(0, 0, panel.image.getWidth(), panel.image.getHeight());


            final long startTime = System.currentTimeMillis();
            animationTimer = new Timer(TIMESTEP, new ActionListener() {
                public void actionPerformed(ActionEvent arg0) {
                    panel.frameNumber++;
                    panel.elapsedTimeMillis = System.currentTimeMillis() - startTime;
                    if (panel.frameNumber % STEPS == 0) {
                        panel.previous = panel.current;
                        panel.current = panel.next;
                        Line l = panel.current.randomWalk(panel.previous, panel.random, false);
                        if (l != null) {
                            panel.next = l.to;
                            panel.stroke = l.color;
                            panel.pixel = panel.current;
                        } else {
                            panel.current = panel.graph.getRandomStart(panel.random);
                            panel.next = panel.current;
                            panel.previous = panel.current;
                        }

                    }
                    panel.previousPixel = panel.pixel;
                    panel.pixel = Point.from(panel.current, panel.next, panel.frameNumber % STEPS, STEPS);

                    panel.repaint();
                }
            });
            window.setVisible(true); // Open the window, making it visible on the screen.
            animationTimer.start();  // Start the animation running.
        } else {
            System.exit(0);
        }
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D gPixel = image.createGraphics();
        gPixel.setColor(stroke);
        BasicStroke bs = new BasicStroke(2);
        gPixel.setStroke(bs);
        gPixel.drawLine(previousPixel.getX(), previousPixel.getY(), pixel.getX(), pixel.getY());

        Graphics2D g2d = (Graphics2D) g.create();
        g2d.drawImage(image, 0, 0, this);
        g2d.dispose();


    }


}