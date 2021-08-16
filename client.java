package chatting_application;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;
import javax.swing.plaf.*;
import javax.swing.plaf.basic.*;

import java.util.Calendar;
import java.text.SimpleDateFormat;import javax.swing.*;
import javax.swing.plaf.ScrollBarUI;
import javax.swing.plaf.basic.BasicScrollBarUI;

public class client implements ActionListener{

    JPanel p1;
    JTextField t1;
    JButton b1;
    static JPanel a1;
    static JFrame f1 = new JFrame();

    static Box vertical = Box.createVerticalBox();


    static Socket s;
    static DataInputStream din;
    static DataOutputStream dout;

    Boolean typing;

    client(){
        f1.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        p1 = new JPanel();
        p1.setLayout(null);
        p1.setBackground(new Color(7, 94, 84));
        p1.setBounds(0, 0, 350, 70);
        f1.add(p1);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("chatting_application/icons/3.png"));
        Image i2 = i1.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel l1 = new JLabel(i3);
        l1.setBounds(5, 17, 30, 30);
        p1.add(l1);

        l1.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent ae){
                System.exit(0);
            }
        });

        ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("chatting_application/icons/2.png"));
        Image i5 = i4.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
        ImageIcon i6 = new ImageIcon(i5);
        JLabel l2 = new JLabel(i6);
        l2.setBounds(43, 8, 50, 50);
        p1.add(l2);

        ImageIcon i7 = new ImageIcon(ClassLoader.getSystemResource("chatting_application/icons/video.png"));
        Image i8 = i7.getImage().getScaledInstance(30, 35, Image.SCALE_DEFAULT);
        ImageIcon i9 = new ImageIcon(i8);
        JLabel l5 = new JLabel(i9);
        l5.setBounds(230, 16, 30, 35);
        p1.add(l5);

        ImageIcon i11 = new ImageIcon(ClassLoader.getSystemResource("chatting_application/icons/phone.png"));
        Image i12 = i11.getImage().getScaledInstance(30, 35, Image.SCALE_DEFAULT);
        ImageIcon i13 = new ImageIcon(i12);
        JLabel l6 = new JLabel(i13);
        l6.setBounds(270, 16, 30, 35);
        p1.add(l6);

        ImageIcon i14 = new ImageIcon(ClassLoader.getSystemResource("chatting_application/icons/3icon.png"));
        Image i15 = i14.getImage().getScaledInstance(10, 25, Image.SCALE_DEFAULT);
        ImageIcon i16 = new ImageIcon(i15);
        JLabel l7 = new JLabel(i16);
        l7.setBounds(310, 17, 10, 25);
        p1.add(l7);


        JLabel l3 = new JLabel("Drishti");
        l3.setFont(new Font("SAN_SERIF", Font.BOLD, 20));
        l3.setForeground(Color.WHITE);
        l3.setBounds(105, 20, 100, 20);
        p1.add(l3);


        JLabel l4 = new JLabel("Active Now");
        l4.setFont(new Font("SAN_SERIF", Font.PLAIN, 10));
        l4.setForeground(Color.WHITE);
        l4.setBounds(105, 40, 100, 20);
        p1.add(l4);

        Timer t = new Timer(1, new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                if(!typing){
                    l4.setText("Active Now");
                }
            }
        });

        t.setInitialDelay(2000);

        a1 = new JPanel();
 //       a1.setBounds(5, 75, 340, 470);
        a1.setFont(new Font("SAN_SERIF", Font.PLAIN, 20));
   //     f1.add(a1);
        JScrollPane sp=new JScrollPane(a1);
        sp.setBounds(5, 75, 340, 470);
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


        t1 = new JTextField();
        t1.setBounds(8, 550, 230, 30);
        t1.setFont(new Font("SAN_SERIF", Font.PLAIN, 20));
        f1.add(t1);

        t1.addKeyListener(new KeyAdapter(){
            public void keyPressed(KeyEvent ke){
                l4.setText("typing...");

                t.stop();

                typing = true;
            }

            public void keyReleased(KeyEvent ke){
                typing = false;

                if(!t.isRunning()){
                    t.start();
                }
            }
        });

        b1 = new JButton("Send");
        b1.setBounds(240, 550, 90, 30);
        b1.setBackground(new Color(7, 94, 84));
        b1.setForeground(Color.WHITE);
        b1.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
        b1.addActionListener(this);
        f1.add(b1);

        f1.getContentPane().setBackground(Color.WHITE);
        f1.setLayout(null);
        f1.setSize(350, 600);
        f1.setLocation(800, 100);
        f1.setUndecorated(true);
        f1.setVisible(true);

    }

    public void actionPerformed(ActionEvent ae){

        try{
            String out = t1.getText();
            sendTextToFile(out);

            JPanel p2 = formatLabel(out);

            a1.setLayout(new BorderLayout());

            JPanel right = new JPanel(new BorderLayout());
            right.add(p2, BorderLayout.LINE_END);
            vertical.add(right);
            vertical.add(Box.createVerticalStrut(15));

            a1.add(vertical, BorderLayout.PAGE_START);

            //a1.add(p2);
            dout.writeUTF(out);
            t1.setText("");
        }catch(Exception e){
            System.out.println(e);
        }
    }
    public void sendTextToFile(String message) throws FileNotFoundException {
        try(FileWriter f = new FileWriter("chat.txt");
            PrintWriter p=new PrintWriter(new BufferedWriter(f));){
            p.println("Drishti: "+message);

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static JPanel formatLabel(String out){
        JPanel p3 = new JPanel();
        p3.setLayout(new BoxLayout(p3, BoxLayout.Y_AXIS));

        JLabel l1 = new JLabel("<html><p style = \"width : 150px\">"+out+"</p></html>");
        l1.setFont(new Font("Tahoma", Font.PLAIN, 16));
        l1.setBackground(new Color(37, 211, 102));
        l1.setOpaque(true);
        l1.setBorder(new EmptyBorder(5,5,5,5));

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

        JLabel l2 = new JLabel();
        l2.setText(sdf.format(cal.getTime()));

        p3.add(l1);
        p3.add(l2);
        return p3;
    }

    public static void main(String[] args){
        new client().f1.setVisible(true);

        try{

            s = new Socket("127.0.0.1", 6001);
            din  = new DataInputStream(s.getInputStream());
            dout = new DataOutputStream(s.getOutputStream());

            String msginput = "";

            while(true){
                a1.setLayout(new BorderLayout());
                msginput = din.readUTF();
                JPanel p2 = formatLabel(msginput);
                JPanel left = new JPanel(new BorderLayout());
                left.add(p2, BorderLayout.LINE_START);

                vertical.add(left);
                vertical.add(Box.createVerticalStrut(15));
                a1.add(vertical, BorderLayout.PAGE_START);
                f1.validate();
            }

        }catch(Exception e){}
    }
}