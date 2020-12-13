package penrose.parser;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class SVGForm extends JFrame {
    private JButton openSVG;
    private JPanel panel;
    private JCheckBox checkSVG;
    private JCheckBox checkSorted;
    private JCheckBox checkHeader;
    private JButton save;
    private JLabel lblStat;
    private JFileChooser fc;

    private JFileChooser fs;

    private SVGForm frame;
    private File svgFile;
    private SVG svg;
    private File saveFile;

    public static String convertToMultiline(String orig) {
        return "<html>" + orig.replaceAll("\n", "<br>");
    }

    public SVGForm() {
        fc = new JFileChooser(new File("."));
        fc.setFileFilter(new FileNameExtensionFilter("Svg Files", "svg"));

        fs = new JFileChooser(new File("."));

        fs.setFileFilter(new FileNameExtensionFilter("Svg or Csv Files", "svg", "csv"));

        frame = this;

        openSVG.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (fc.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
                    svgFile = fc.getSelectedFile();
                    svg = SVGParser.parse(svgFile.getAbsolutePath());
                    save.setEnabled(true);

                    lblStat.setText(convertToMultiline(
                            "Lines: " + svg.lines.size() + "\n" +
                                    "Poly: " + svg.poly.size() + "\n" +
                                    "Circles: " + svg.circles.size() + "\n" +
                                    "Arcs: " + svg.arcs.size() + "\n" +
                                    "Unknown: " + svg.unknownShapes + "\n"
                            )
                    );
                }
            }
        });

        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (fs.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION) {
                    saveFile = fs.getSelectedFile();
                    String filepath = saveFile.getAbsolutePath();
                    if (checkSVG.isSelected()) {
                        filepath = filepath + ".svg";
                        SVGParser.export(svg, filepath, checkSorted.isSelected());
                    } else {
                        filepath = filepath + ".csv";
                        SVGParser.exportCSV(svg, filepath, checkSorted.isSelected(), checkHeader.isSelected());
                    }
                    JOptionPane.showMessageDialog(frame, "Saved successfully");

                }
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("SVG converter");
        frame.setPreferredSize(new Dimension(400, 400));
        frame.setContentPane(new SVGForm().panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
