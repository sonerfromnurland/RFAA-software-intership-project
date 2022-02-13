import java.awt.*;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

class PanelDemo {

    static final double version = 1.00;

    JLabel jlab;
    Demo makeConnection;
    String recLeftSide;
    String recRightSide;

    JButton jbtnUpArrowFirst;
    JButton jbtnDownArrowFirst;
    JButton jbtnUpArrowThird;
    JButton jbtnDownArrowThird;
    JButton jbtnConnect;

    JLabel jlblLedUpFirst;
    JLabel jlblLedDownFirst;
    JLabel jlblLedUpSec;
    JLabel jlblLedDownSec;
    JLabel jlblLedUpThird;
    JLabel jlblLedDownThird;
    JLabel jlblconnectionResult;
    JLabel jlblVersion;
    JLabel jlblLogo;

    JTextField jtxtIp;
    JTextField jtxtPort;

    private JComponent ui = null;
    private Insets buttonMargin = new Insets(10,10,10,10);

    PanelDemo() throws IOException, InterruptedException {
        InitComponents();
    }

    private void InitComponents() throws IOException, InterruptedException {

        if (ui != null) return;

        ui = new JPanel(new BorderLayout(4, 4));
        ui.setBorder(new EmptyBorder(4, 4, 4, 4));

        int gap = 5;


        JPanel jpnl = new JPanel(new GridLayout(2, 4, gap, gap));
        jpnl.setOpaque(true);
        jpnl.setBorder(
                new CompoundBorder(new EmptyBorder(0, 0, 0, 50),
                        BorderFactory.createTitledBorder("Yükselteç 1 - 2")));


        JPanel jpnl2 = new JPanel(new GridLayout(2, 2, gap, gap));
        jpnl2.setOpaque(true);
        jpnl2.setBorder(
                BorderFactory.createTitledBorder("Anten"));


        JPanel jpnl3 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        jpnl3.setOpaque(true);
        jpnl3.setBorder(
                BorderFactory.createTitledBorder("Bilgi"));

        JPanel jpnlConResult = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JPanel jpnlText = new JPanel(new FlowLayout());
        jpnlText.setOpaque(true);
        jpnlText.setBorder(BorderFactory.createTitledBorder("Bağlantı Kurulum"));


        JFrame jfrm = new JFrame("RFAA Control Software- Soner");
        jfrm.setLocationByPlatform(true);
        jfrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jfrm.setMinimumSize(jfrm.getSize());


        jbtnUpArrowFirst = setMyButton("up.png", "upfirst");
        jbtnUpArrowThird = setMyButton("up.png", "upthird");

        jbtnDownArrowFirst = setMyButton("down.png", "downfirst");
        jbtnDownArrowThird = setMyButton("down.png", "downthird");

        jlblLedUpFirst = setMyIcons("ledOff.png", "upledfirst");
        jlblLedUpSec = setMyIcons("ledOff.png", "upledsec");

        jlblLedDownFirst = setMyIcons("ledOff.png", "downledfirst");
        jlblLedDownSec = setMyIcons("ledOff.png", "downledsec");

        jlblLedUpThird = setMyIcons("ledOff.png", "upledthird");
        jlblLedDownThird = setMyIcons("ledOff.png", "downledthird");

        jlblLogo = setMyIcons("logo.png", "logo");

        jbtnConnect = new JButton("Bağlan");
        jlblconnectionResult = new JLabel();
        jlblVersion = new JLabel("Version: " + version);
        jlblVersion.setBorder(BorderFactory.createEmptyBorder(0, 67, 0, 70));

        jtxtIp = new JTextField("IP");
        jtxtIp.setPreferredSize(new Dimension(120,35));

        jtxtPort = new JTextField("Port");
        jtxtPort.setPreferredSize(new Dimension(60,35));

        jpnl.add(jbtnUpArrowFirst);
        jpnl.add(jlblLedUpFirst);
        jpnl.add(jlblLedUpSec);
        jpnl.add(jbtnDownArrowFirst);
        jpnl.add(jlblLedDownFirst);
        jpnl.add(jlblLedDownSec);

        jpnl2.add(jbtnUpArrowThird);
        jpnl2.add(jlblLedUpThird);
        jpnl2.add(jbtnDownArrowThird);
        jpnl2.add(jlblLedDownThird);

        jpnlText.add(jtxtIp);
        jpnlText.add(jtxtPort);
        jpnlText.add(jbtnConnect);

        jpnlConResult.add(jlblconnectionResult);

        jpnl3.add(jlblLogo);
        jpnl3.add(jlblVersion);
        jpnl3.add(jpnlText);

        ui.add(jpnl, BorderLayout.CENTER);
        ui.add(jpnl2, BorderLayout.LINE_END);
        ui.add(jpnl3, BorderLayout.PAGE_END);
        ui.add(jpnlConResult, BorderLayout.PAGE_START);

        jbtnConnect.addActionListener(e -> {
            String readIp = "";
            String readPort;
            int portNumerical;

            try {
                readIp = jtxtIp.getText();
                readPort = jtxtPort.getText();
                portNumerical = Integer.parseInt(readPort);
                InetAddress ip = InetAddress.getByName(readIp);
                makeConnection = new Demo(ip , portNumerical);

                jlblconnectionResult.setText("<html><font color=\"green\">Bağlandı!</font></html>");
                jbtnUpArrowFirst.setEnabled(true);
                jbtnDownArrowFirst.setEnabled(true);
                jbtnUpArrowThird.setEnabled(true);
                jbtnDownArrowThird.setEnabled(true);
            } catch (UnknownHostException e1) {
                jlblconnectionResult.setText("<html><font color=\"red\">Bulunamayan Ip!</font></html>");
                e1.printStackTrace();
            } catch (IOException e1) {
                jlblconnectionResult.setText("<html><br>IP" + " (" + readIp + ") " + "erişiemiyor!");
                e1.printStackTrace();
            } catch (NumberFormatException e1) {
                jlblconnectionResult.setText("!!\u2023  Ip ve port numarasını giriniz!");
            }

        });

        jbtnUpArrowThird.addActionListener(e -> {
            try {
                makeConnection.send("ANTSW=VERT\r\n");

                if (makeConnection.recv().equalsIgnoreCase("ANTSW=VERT,OK")) {
                    jlblLedUpThird.setIcon(new ImageIcon("ledOn.png"));
                    jlblLedDownThird.setIcon(new ImageIcon("ledOff.png"));
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });

        jbtnDownArrowThird.addActionListener(e -> {
            try {
                makeConnection.send("ANTSW=HORZ\r\n");

                if (makeConnection.recv().equalsIgnoreCase("ANTSW=HORZ,OK")) {
                    jlblLedUpThird.setIcon(new ImageIcon("ledOff.png"));
                    jlblLedDownThird.setIcon(new ImageIcon("ledOn.png"));
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });

        jbtnUpArrowFirst.addActionListener(e -> {
            try {
                makeConnection.send("AMPSW=TWO\r\n");

                if (makeConnection.recv().equalsIgnoreCase("AMPSW=TWO,OK")) {
                    jlblLedUpFirst.setIcon(new ImageIcon("ledOn.png"));
                    jlblLedDownFirst.setIcon(new ImageIcon("ledOff.png"));

                    jlblLedUpSec.setIcon(new ImageIcon("ledOn.png"));
                    jlblLedDownSec.setIcon(new ImageIcon("ledOff.png"));
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });

        jbtnDownArrowFirst.addActionListener(e -> {
            try {
                makeConnection.send("AMPSW=ONE\r\n");

                if (makeConnection.recv().equalsIgnoreCase("AMPSW=ONE,OK")) {
                    jlblLedUpFirst.setIcon(new ImageIcon("ledOff.png"));
                    jlblLedDownFirst.setIcon(new ImageIcon("ledOn.png"));

                    jlblLedUpSec.setIcon(new ImageIcon("ledOff.png"));
                    jlblLedDownSec.setIcon(new ImageIcon("ledOn.png"));
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });


        jfrm.setContentPane(getUI());
        jfrm.pack();
        jfrm.setVisible(true);
        jfrm.setResizable(false);
    }
    public JComponent getUI() {
        return ui;
    }

    private JButton setMyButton(String path,String text) {
        JButton b = new JButton("", new ImageIcon(path));
        b.setName(text);
        b.setVerticalTextPosition(SwingConstants.BOTTOM);
        b.setHorizontalTextPosition(SwingConstants.CENTER);
        b.setMargin(buttonMargin);
        b.setEnabled(false);
        return b;
    }

    private JLabel setMyIcons(String path, String text) {
        JLabel jlbl = new JLabel("", new ImageIcon(path), SwingConstants.CENTER);
        jlbl.setName(text);
        jlbl.setVerticalTextPosition(SwingConstants.BOTTOM);
        jlbl.setHorizontalTextPosition(SwingConstants.CENTER);
        return jlbl;
    }

    public static void main(String args[]) {
        EventQueue.invokeLater(() -> {
            try {
                new PanelDemo();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
}