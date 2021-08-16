package chatting_application;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.plaf.ScrollBarUI;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.awt.event.*;
import java.io.DataInputStream;
import java.net.ServerSocket;
import java.net.*;
import java.io.*;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.text.SimpleDateFormat;

public class server implements ActionListener {
    JPanel p1;
    JTextField t1;
    JButton b1;
    static JPanel a1;
    static JFrame f1=new JFrame();
    static Box vertical=Box.createVerticalBox();
    static DataInputStream din;
    static DataOutputStream dout;
    static ServerSocket skt;
    static Socket s;
    Boolean typing;


    server(){
        f1.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        p1=new JPanel();
        p1.setLayout(null);
        p1.setBackground(new Color(7,94,84));
        p1.setBounds(0, 0, 350, 70);
        f1.add(p1);
        ImageIcon i1=new ImageIcon(ClassLoader.getSystemResource("chatting_application/icons/3.png"));
        Image i2=i1.getImage().getScaledInstance(30,30, Image.SCALE_DEFAULT);
        ImageIcon i3=new ImageIcon(i2);
        JLabel l1=new JLabel(i3);
        l1.setBounds(5,17,30,30);
        p1.add(l1);

        l1.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent ae){
                System.exit(0);
            }
        });


        ImageIcon i4=new ImageIcon(ClassLoader.getSystemResource("chatting_application/icons/1.png"));
        Image i5=i4.getImage().getScaledInstance(50,50, Image.SCALE_DEFAULT);
        ImageIcon i6=new ImageIcon(i5);
        JLabel l2=new JLabel(i6);
        l2.setBounds(43,8,50,50);
        p1.add(l2);

        JLabel l3=new JLabel("Harsh");
        l3.setFont(new Font("SAN_SERIF", Font.PLAIN,20));
        l3.setForeground(Color.WHITE);
        l3.setBounds(105,20,100,20);
        p1.add(l3);


        ImageIcon i7=new ImageIcon(ClassLoader.getSystemResource("chatting_application/icons/video.png"));
        Image i8=i7.getImage().getScaledInstance(30,35, Image.SCALE_DEFAULT);
        ImageIcon i9=new ImageIcon(i8);
        JLabel l7=new JLabel(i9);
        l7.setBounds(230,16,30,35);
        p1.add(l7);


        ImageIcon i10=new ImageIcon(ClassLoader.getSystemResource("chatting_application/icons/phone.png"));
        Image i11=i10.getImage().getScaledInstance(30,35, Image.SCALE_DEFAULT);
        ImageIcon i12=new ImageIcon(i11);
        JLabel l5=new JLabel(i12);
        l5.setBounds(270,16,30,35);
        p1.add(l5);


        ImageIcon i13=new ImageIcon(ClassLoader.getSystemResource("chatting_application/icons/3icon.png"));
        Image i14=i13.getImage().getScaledInstance(10,25, Image.SCALE_DEFAULT);
        ImageIcon i15=new ImageIcon(i14);
        JLabel l6=new JLabel(i15);
        l6.setBounds(310,17,10,25);
        p1.add(l6);


        JLabel l4=new JLabel("Active now");
        l4.setFont(new Font("SAN_SERIF", Font.PLAIN,10));
        l4.setForeground(Color.WHITE);
        l4.setBounds(105,40,100,20);
        p1.add(l4);

        Timer t=new Timer(1, new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                if(!typing){
                    l4.setText("Active now");

                }

            }
        });
        t.setInitialDelay(2000);

        a1 = new JPanel();
        //       a1.setBounds(5, 75, 340, 470);
        a1.setFont(new Font("SAN_SERIF", Font.PLAIN, 20));
        //     f1.add(a1);
        JScrollPane sp=new JScrollPane(a1);
        sp.setBounds(5, 75, 320, 430);
        sp.setBorder(BorderFactory.createEmptyBorder());
        f1.add(sp);
        ScrollBarUI ui=new BasicScrollBarUI(){
            protected JButton  createDecreaseButton(int Orientation){
                JButton button=super.createDecreaseButton(Orientation);
                button.setBackground(new Color(7,94,84));
                button.setForeground(Color.WHITE);
                this.thumbColor=new Color(7,94,84);
                return button;
            }
            protected JButton  createIncreaseButton(int Orientation){
                JButton button=super.createDecreaseButton(Orientation);
                button.setBackground(new Color(7,94,84));
                button.setForeground(Color.WHITE);
                this.thumbColor=new Color(7,94,84);
                return button;
            }
        };
        sp.getVerticalScrollBar().setUI(ui);
        f1.add(sp);


        t1=new JTextField();
        t1.setBounds(8,520,230,30);
        t1.setFont(new Font("SANS_SERIF",Font.PLAIN,20));
        f1.add(t1);
        t1.addKeyListener(new KeyAdapter(){
            public void keyPressed(KeyEvent ke){
                l4.setText("typing...");
                t.stop();
                typing=true;
            }
            public void keyReleased(KeyEvent ke){
                typing=false;
                if(!t.isRunning()){
                    t.start();
                }
            }

        });
        b1=new JButton("Send");
        b1.setBounds(240, 520, 90, 30);
        b1.setBackground(new Color(7,94,84));
        b1.setForeground(Color.WHITE);
        b1.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
        b1.addActionListener(this);
        f1.add(b1);
        f1.getContentPane().setBackground(Color.WHITE);
        f1.setLayout(null);
        f1.setSize(350,600);
        f1.setLocation(400,100);
        //       f1.setUndecorated(true);
        f1.setVisible(true);
    }
    public void actionPerformed(ActionEvent ae){
        try {
            String out = t1.getText();
            sendTextToFile(out);
            JPanel p2=formatLabel(out);
            a1.setLayout(new BorderLayout());
            JPanel right=new JPanel(new BorderLayout());
            right.add(p2,BorderLayout.LINE_END);
            vertical.add(right);
            vertical.add(Box.createVerticalStrut(15));
            a1.add(vertical,BorderLayout.PAGE_START);
            dout.writeUTF(out);
            t1.setText("");
        }catch(Exception e){
            System.out.println(e);

        }

    }
    public void sendTextToFile(String message) throws FileNotFoundException {
        try(FileWriter f = new FileWriter("chat.txt");
            PrintWriter p=new PrintWriter(new BufferedWriter(f));){
            p.println("Harsh: "+message);

        }catch(Exception e){
            e.printStackTrace();
        }
    }


    public static JPanel formatLabel(String out) {
        JPanel p3=new JPanel();
        p3.setLayout(new BoxLayout(p3, BoxLayout.Y_AXIS));
        JLabel l1=new JLabel("<html><p style = \"width : 150px\">"+out+"</p></html>");

        l1.setFont(new Font("Tahoma", Font.PLAIN,16));
        l1.setBackground(new Color(37,211,102));
        l1.setOpaque(true);
        l1.setBorder(new EmptyBorder(5,5,5,5));
        Calendar cal=Calendar.getInstance();
        SimpleDateFormat sdf=new SimpleDateFormat("HH:mm");
        JLabel l2=new JLabel();
        l2.setText(sdf.format(cal.getTime()));
        p3.add(l1);
        p3.add(l2);
        return p3;
    }


    public static void main(String[] args) {
        new server().f1.setVisible(true);
        String msginput= "";
        try {
            skt = new ServerSocket(6001);
            while (true) {
                s = skt.accept();
                din = new DataInputStream(s.getInputStream());
                dout = new DataOutputStream(s.getOutputStream());
                while (true) {
                    msginput = din.readUTF();
                    JPanel p2 = formatLabel(msginput);
                    JPanel left=new JPanel(new BorderLayout());
                    left.add(p2,BorderLayout.LINE_START);
                    vertical.add(left);
                    f1.validate();
                }
            }

        }catch(Exception e){

        }
    }
}
