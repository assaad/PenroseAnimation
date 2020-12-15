package penrose.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ToolBar implements ActionListener {
    private JLabel lblTolerance;
    private JSlider tolerance;
    private JButton open;
    private JButton save;

    private JButton start;
    private JButton stop;
    private JLabel lblSpeed;
    private JSlider speed;

    private JButton clear;
    private JFileChooser fc;
    private JFileChooser fsave;
    private JToolBar toolBar;
    private Dimension newDimensions = new Dimension(700, 500);
    private File f;
    private PenroseFrame frame;


    private JButton pencil;
    private JButton line;
    private JButton rectangle;
    private JButton circle;
    private JButton erase;
    private JButton fill;
    private JButton undo;
    private JButton redo;
    private JCheckBox chkSave;

    private JComboBox comboBox;


    TextDialog td;

    public ToolBar(PenroseFrame frame) {
        this.frame = frame;
        fc = new JFileChooser(new File("."));
        fc.setFileFilter(new FileNameExtensionFilter("Svg Files", "svg"));

        fsave = new JFileChooser(new File("."));
        fsave.setFileFilter(new FileNameExtensionFilter("Image Files", "png", "jpg"));
        this.initializeToolBar();
        open.addActionListener(this);
        save.addActionListener(this);

        start.addActionListener(this);
        stop.addActionListener(this);
        clear.addActionListener(this);

        rectangle.addActionListener(this);
        line.addActionListener(this);
        circle.addActionListener(this);
        erase.addActionListener(this);
        pencil.addActionListener(this);
        comboBox.addActionListener(this);
    }

    private void initializeToolBar() {
        // ----------------
        // create buttons for the tool bar
        // ----------------
        toolBar = new JToolBar(JToolBar.VERTICAL);
        toolBar.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.BLACK));
        toolBar.setFloatable(false);
        toolBar.setLayout(new GridLayout(18, 0));

        //toolBar.setBackground( new Color(0, 153, 204));
        //ImageIO.read( ClassLoader.getSystemResource( "image/button1.png" ) )
        open = new JButton("Open", new ImageIcon(ClassLoader.getSystemResource("icons/Add Folder-24.png")));
        save = new JButton("Save", new ImageIcon(ClassLoader.getSystemResource("icons/Save-24.png")));
        start = new JButton("Start", new ImageIcon(ClassLoader.getSystemResource("icons/Triangle-24.png")));
        stop = new JButton("Stop", new ImageIcon(ClassLoader.getSystemResource("icons/Octagon-24.png")));

        clear = new JButton("Clear", new ImageIcon(ClassLoader.getSystemResource("icons/Trash-24.png")));

        pencil = new JButton("Pencil", new ImageIcon(ClassLoader.getSystemResource("icons/Pencil-24.png")));
        line = new JButton("Line", new ImageIcon(ClassLoader.getSystemResource("icons/Line-24.png")));
        rectangle = new JButton("Rectangle", new ImageIcon(ClassLoader.getSystemResource("icons/Rectangle-24.png")));
        circle = new JButton("Circle", new ImageIcon(ClassLoader.getSystemResource("icons/Circled.png")));
        erase = new JButton("Erase", new ImageIcon(ClassLoader.getSystemResource("icons/Eraser-24.png")));
        fill = new JButton("Fill", new ImageIcon(ClassLoader.getSystemResource("icons/Fill Color-24.png")));
        undo = new JButton("Undo", new ImageIcon(ClassLoader.getSystemResource("icons/Undo-24.png")));
        redo = new JButton("Redo", new ImageIcon(ClassLoader.getSystemResource("icons/Redo-24.png")));

        chkSave = new JCheckBox("Save animation");
        chkSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                frame.penroseManager.saveAnimation = chkSave.isSelected();
            }
        });

        speed = new JSlider(1, 100, 101 - PenroseFrame.DEFAULT_TIME_STEP);
        speed.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                int time = 101 - speed.getValue();
                lblSpeed.setText("Speed: " + speed.getValue() + " %");
                frame.penroseManager.setDelay(time);
            }
        });

        tolerance = new JSlider(1, 200, 30);
        tolerance.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                lblTolerance.setText("Tolerance: " + tolerance.getValue());
            }
        });

        lblTolerance = new JLabel("Tolerance: " + tolerance.getValue());
        lblSpeed = new JLabel("Speed: " + speed.getValue() + " %");

        String[] items = {"Line Width", "1", "2", "3", "4", "5", "6", "7", "8"};

        //open.setPreferredSize(new Dimension(300,50));

        comboBox = new JComboBox(items);
        comboBox.setMaximumSize(new Dimension(100, 25));
        // ----------------
        // add buttons to the tool bar
        // ----------------
        toolBar.add(lblTolerance);
        toolBar.add(tolerance);
        toolBar.addSeparator();
        toolBar.addSeparator();

        toolBar.add(open);
        toolBar.add(clear);
        toolBar.add(save);
        toolBar.add(chkSave);

        toolBar.addSeparator();
        toolBar.addSeparator();
        toolBar.add(start);
        toolBar.add(stop);

        toolBar.add(lblSpeed);
        toolBar.add(speed);
        toolBar.addSeparator();
        toolBar.addSeparator();

        toolBar.add(pencil);
        toolBar.add(line);
        toolBar.add(rectangle);
        toolBar.add(circle);
        toolBar.add(erase);
        toolBar.add(fill);
        toolBar.add(comboBox);
        toolBar.addSeparator();
        toolBar.add(undo);
        toolBar.add(redo);
        toolBar.setPreferredSize(new Dimension(200, 800));
    }

    public void actionPerformed(ActionEvent ae) {

        Object source = ae.getSource();

        if (source == clear) {
            frame.getInkPanel().clear();
        } else if (source == open) {
            if (fc.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
                f = fc.getSelectedFile();
                openFile(f);
            }
        } else if (source == save) {
            // open file saver
            if (fsave.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION) {
                f = new File(fsave.getSelectedFile() + ".png");
                try {
                    saveFile(f);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        } else if (source == start) {
            if (frame.penroseManager.isReady()) {
                frame.penroseManager.start();
            } else {
                JOptionPane.showMessageDialog(new JFrame(), "Please open an svg first", "Dialog",
                        JOptionPane.ERROR_MESSAGE);
            }

        } else if (source == stop) {
            if (frame.penroseManager.isReady()) {
                frame.penroseManager.stop();
            } else {
                JOptionPane.showMessageDialog(new JFrame(), "Please open an svg first", "Dialog",
                        JOptionPane.ERROR_MESSAGE);
            }
        } else if (source == undo) {
            frame.getInkPanel().undo();
        } else if (source == redo) {
            frame.getInkPanel().redo();
        } else if (source == pencil) {
            frame.getInkPanel().setTool(0);
        } else if (source == line) {
            frame.getInkPanel().setTool(1);
        } else if (source == rectangle) {
            frame.getInkPanel().setTool(2);
        } else if (source == circle) {
            frame.getInkPanel().setTool(3);
        } else if (source == erase) {
            frame.getInkPanel().setTool(6);
        } else if (source == fill) {
            frame.getInkPanel().setTool(7);
        } else if (source == comboBox) {
            try {
                JComboBox combo = (JComboBox) ae.getSource();
                String current = (String) combo.getSelectedItem();
                frame.getInkPanel().setThickness(Float.valueOf(current));
            } catch (NumberFormatException e) {

            }
        } else {
            JButton b = (JButton) source;
            frame.getInkPanel().setColor(b.getBackground());
        }
    }

    public JToolBar getToolBar() {
        return this.toolBar;
    }

    private void setDimensions(int width, int height) {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        if (height > dim.height - 160 && width > dim.width - 150) {
            frame.getSP().setSize(dim.width - 150, dim.height - 160);
        } else if (width > dim.width - 150) {
            frame.getSP().setSize(dim.width - 150, height);
        } else if (height > dim.height - 160) {
            frame.getSP().setSize(width, dim.height - 160);
        } else {
            frame.getSP().setSize(width, height);
        }
    }

    private void newFile() {
        final JFrame newFileFrame = new JFrame();
        newFileFrame.setTitle("New");
        newFileFrame.setBackground(Color.GRAY);
        newFileFrame.setSize(400, 200);
        newFileFrame.setPreferredSize(new Dimension(400, 200));
        newFileFrame.setLayout(null);
        newFileFrame.setResizable(false);
        newFileFrame.pack();

        // put the frame in the middle of the display
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        newFileFrame.setLocation(dim.width / 2 - newFileFrame.getSize().width / 2, dim.height / 2 - newFileFrame.getSize().height / 2);

        newFileFrame.setVisible(true);

        final JTextField width = new JTextField();
        width.setSize(100, 25);
        width.setLocation(100, 25);

        JLabel widthLabel = new JLabel("Width (px):");
        widthLabel.setSize(75, 25);
        widthLabel.setLocation(25, 25);

        JLabel heightLabel = new JLabel("Height (px):");
        heightLabel.setSize(75, 25);
        heightLabel.setLocation(25, 75);

        final JTextField height = new JTextField();
        height.setLocation(100, 75);
        height.setSize(100, 25);

        JButton okay = new JButton("OK");
        okay.setLocation(250, 25);
        okay.setSize(75, 25);
        okay.addActionListener(new ActionListener() {
                                   public void actionPerformed(ActionEvent e) {
                                       try {
                                           newDimensions = new Dimension(Integer.parseInt(width.getText()),
                                                   Integer.parseInt(height.getText()));
                                           System.out.println(newDimensions);
                                           frame.getInkPanel().setInkPanel(newDimensions.width, newDimensions.height);
                                           Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
                                           setDimensions(newDimensions.width, newDimensions.height);
                                           newFileFrame.dispose();
                                       } catch (NumberFormatException nfe) {
                                           JOptionPane.showMessageDialog(null,
                                                   "Invalid numeric entry. A proper integer is required.",
                                                   "New",
                                                   JOptionPane.ERROR_MESSAGE);
                                       }
                                   }
                               }
        );

        JButton cancel = new JButton("Cancel");
        cancel.setSize(75, 25);
        cancel.setLocation(250, 75);
        cancel.addActionListener(new ActionListener() {
                                     public void actionPerformed(ActionEvent e) {
                                         newFileFrame.dispose();
                                     }
                                 }
        );

        newFileFrame.add(heightLabel);
        newFileFrame.add(widthLabel);
        newFileFrame.add(width);
        newFileFrame.add(height);
        newFileFrame.add(okay);
        newFileFrame.add(cancel);
    }

    private void openFile(File f) {

        // ----------------
        // update the contents of the jlabel to be the image from the selected file
        // ----------------

        //	Image image = Toolkit.getDefaultToolkit().getImage(f.getPath());
        try {
            frame.penroseManager.setGraph(f.getAbsolutePath(), tolerance.getValue());
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void saveFile(File f) throws IOException {

        // ----------------
        // Take all the contents of the jpanel and save them to a png
        // 		destination is the file they selected via the filechooser
        // ----------------
        BufferedImage im = makePanel(frame.getInkPanel());
        ImageIO.write(im, "png", f);
    }

    private BufferedImage makePanel(JPanel panel) {
        int w = panel.getWidth();
        int h = panel.getHeight();
        BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = bi.createGraphics();
        panel.print(g);
        return bi;
    }
}