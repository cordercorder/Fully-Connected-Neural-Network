package NeuralNetwork;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @Author: 金任任
 * @Class: 计科1604
 * @Number: 2016014537
 */
public class SinxShow extends JFrame {

    private JButton start=new JButton("");
    private JButton exit=new JButton("退出");
    private JLabel label1=new JLabel("隐藏层1节点数");
    private JTextField hiddencnt1=new JTextField();
    private JLabel label2=new JLabel("隐藏层2节点数");
    private JTextField hiddencnt2=new JTextField();
    private JLabel label3=new JLabel("训练次数");
    private JTextField traincnt=new JTextField();
    private JLabel label4=new JLabel("学习率");
    private JTextField learningrate=new JTextField();
    private FittingSinx call_back=new FittingSinx(1,20,20,1,0.8,500,"回想曲线",false);
    private FittingSinx generalization=new FittingSinx(1,20,20,1,0.8,500,"泛化曲线",false);
    private FittingSinx loss=new FittingSinx(1,20,20,1,0.8,500,"Loss曲线",true);

    public SinxShow(){
        setLayout(null);

        label1.setBounds(20,20,100,30);
        add(label1);

        hiddencnt1.setBounds(110,20,60,30);
        add(hiddencnt1);

        label2.setBounds(190,20,100,30);
        add(label2);

        hiddencnt2.setBounds(282,20,60,30);
        add(hiddencnt2);

        label3.setBounds(362,20,60,30);
        add(label3);

        traincnt.setBounds(422,20,60,30);
        add(traincnt);

        label4.setBounds(502,20,50,30);
        add(label4);

        learningrate.setBounds(552,20,60,30);
        add(learningrate);

        ImageIcon imageIcon=new ImageIcon("start.png");
        start.setIcon(imageIcon);
        start.setBounds(672,20,50,32);
        add(start);
        start.addActionListener(new Start());

        call_back.getMyChart().setVisible(true);
        call_back.getMyChart().setBounds(20,60,400,300);
        add(call_back.getMyChart());

        generalization.getMyChart().setVisible(true);
        generalization.getMyChart().setBounds(440,60,400,300);
        add(generalization.getMyChart());

        loss.getMyChart().setVisible(true);
        loss.getMyChart().setBounds(20,370,820,280);
        add(loss.getMyChart());

        setTitle("拟合sin(x)");
        setSize(870,730);
        setVisible(true);
        setResizable(false);

        Toolkit toolkit=Toolkit.getDefaultToolkit();
        Dimension screenSize=toolkit.getScreenSize();
        int x=(screenSize.width-getWidth())/2;
        int y=(screenSize.height-getHeight())/2;
        setLocation(x,y);
    }

    class Start implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            int Hiddencnt1=Integer.valueOf(hiddencnt1.getText());
            int Hiddencnt2=Integer.valueOf(hiddencnt2.getText());
            int Traincnt=Integer.valueOf(traincnt.getText());
            double Learningrate=Double.valueOf(learningrate.getText());

            call_back=new FittingSinx(1,Hiddencnt1,Hiddencnt2,1,Learningrate,Traincnt,"回想曲线",false);
            call_back.setStatus(0);
            call_back.getMyChart().setVisible(true);
            call_back.getMyChart().setBounds(20,60,400,300);
            add(call_back.getMyChart());

            generalization=new FittingSinx(1,Hiddencnt1,Hiddencnt2,1,Learningrate,Traincnt,"泛化曲线",false);
            generalization.setStatus(1);
            generalization.getMyChart().setVisible(true);
            generalization.getMyChart().setBounds(440,60,400,300);
            add(generalization.getMyChart());

            loss=new FittingSinx(1,Hiddencnt1,Hiddencnt2,1,Learningrate,Traincnt,"Loss曲线",true);
            loss.setStatus(2);
            loss.getMyChart().setVisible(true);
            loss.getMyChart().setBounds(20,370,820,280);
            add(loss.getMyChart());

            Thread thread1=new Thread(call_back);
            thread1.start();

            Thread thread2=new Thread(generalization);
            thread2.start();

            Thread thread3=new Thread(loss);
            thread3.start();

            repaint();
        }
    }

    public static void main(String[] args){


        new SinxShow();
    }


}
