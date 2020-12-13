package penrose.gui;

import penrose.Graph;
import penrose.Line;
import penrose.Point;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class PenroseFrame extends JFrame {

    // the following avoids a "warning" with Java 1.5.0 complier
    static final long serialVersionUID = 42L;
    public static final int DEFAULT_LINE_STEP = 4;
    public static final int DEFAULT_TIME_STEP = 30;

    private final int CONTENT_PANE_WIDTH = 1300;
    private final int CONTENT_PANE_HEIGHT = 700;
    private final Color background = Color.GRAY;
    //fields
    private JPanel contentPane;
    public PaintPanel inkPanel;
    private JMenuBar menuBar;
    private JToolBar toolBar;
    private JToolBar cc1;
    private CoordinateBar coordinateBar;
    private JScrollPane sp;
    private int inkPanelWidth;
    private int inkPanelHeight;
    final public PenroseManager penroseManager;


    //ContentPane > Toolbar,  cc, InkPanel,
    public PenroseFrame() {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        inkPanelWidth = dim.width - 150;
        inkPanelHeight = dim.height - 160;

        penroseManager=new PenroseManager(this, DEFAULT_LINE_STEP,DEFAULT_TIME_STEP,inkPanelWidth,inkPanelHeight);

        penroseManager.animationTimer=new Timer(DEFAULT_TIME_STEP, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                penroseManager.nextStep();

            }
        });


        // construct our layout manager.
        contentPane = new JPanel();
        contentPane.setLayout(null);

        // create a menu bar
        //	menuBar = (new MenuBar(this, this.inkPanel )).getMenuBar();

        // create a tool bar
        toolBar = (new ToolBar(this)).getToolBar();

        // create coordinate bar at the bottom
        coordinateBar = new CoordinateBar();

        // create color chooser 1 (minas)
        cc1 = (new ColorChooser1(this)).getToolBar();

        // create default color chooser
        //	cc2 = (new ColorChooser2(this)).getColorChooser();

        // construct the panels needed. (INKPANEL COMES LAST)
        inkPanel = new PaintPanel(0, this, inkPanelWidth, inkPanelHeight);


        // configure components and add them to the frame.
        this.add(cc1, BorderLayout.PAGE_START);
        sp = new JScrollPane();
        sp.setLocation(10, 10);
        sp.setViewportView(inkPanel);
        sp.setSize(inkPanelWidth, inkPanelHeight);
        sp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        contentPane.add(sp);
        //contentPane.add(cc1);
        //contentPane.add(cc2);
        contentPane.setBackground(background);

        // add listeners to buttons
        this.addWindowListener(new PenroseFrame.WindowCloser());

        // set components into the contentPane
        this.setJMenuBar(menuBar);
        this.add(coordinateBar, BorderLayout.PAGE_END);
        this.add(toolBar, BorderLayout.WEST);
        this.add(contentPane);

        this.setSize(CONTENT_PANE_WIDTH, CONTENT_PANE_HEIGHT);
        this.setPreferredSize(new Dimension(CONTENT_PANE_WIDTH, CONTENT_PANE_HEIGHT));

    }

    public CoordinateBar getCoordinateBar() {
        return this.coordinateBar;
    }

    public PaintPanel getInkPanel() {
        return this.inkPanel;
    }

    public PenroseFrame getPenroseFrame() {
        return this;
    }

    public JScrollPane getSP() {
        return this.sp;
    }

    private class WindowCloser extends WindowAdapter {
        @Override
        public void windowClosing(WindowEvent event) {
            System.exit(0);
        }
    }

}
