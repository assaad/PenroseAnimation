package penrose.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ToolBar implements ActionListener {
    private JButton open;
    private JButton save;

    private JButton start;
    private JButton stop;

    private JButton clear;
    private JFileChooser fc;
    private JFileChooser fsave;
    private JToolBar toolBar;

    private Dimension newDimensions = new Dimension(700, 500);


    private File f;
    private PenroseFrame frame;

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

        // ----------------
        // add buttons to the tool bar
        // ----------------
        toolBar.add(open);
        toolBar.add(save);
        toolBar.addSeparator();
        toolBar.add(start);
        toolBar.add(stop);
        toolBar.addSeparator();
        toolBar.add(clear);
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
        } else {
//            JButton b = (JButton) source;
//            frame.getInkPanel().setColor(b.getBackground());
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
        JFrame newFileFrame = new JFrame();
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

        JTextField width = new JTextField();
        width.setSize(100, 25);
        width.setLocation(100, 25);

        JLabel widthLabel = new JLabel("Width (px):");
        widthLabel.setSize(75, 25);
        widthLabel.setLocation(25, 25);

        JLabel heightLabel = new JLabel("Height (px):");
        heightLabel.setSize(75, 25);
        heightLabel.setLocation(25, 75);

        JTextField height = new JTextField();
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
            frame.penroseManager.setGraph(f.getAbsolutePath());
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