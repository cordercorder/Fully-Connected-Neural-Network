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
public class Main extends JFrame {
    private JMenuItem item1=new JMenuItem("拟合sin(x)");
    private JMenuItem item2=new JMenuItem("拟合多项式");
    private JMenuBar menuBar=new JMenuBar();
    private JMenu menu=new JMenu("神经网络拟合函数");
    private JButton button1=new JButton("拟合sin(x)");
    private JButton button2=new JButton("拟合多项式");

    public Main(){
        item1.addActionListener(new FS());
        item2.addActionListener(new FX());

        JPanel panel=new JPanel();
        ImageIcon imageIcon1=new ImageIcon("sinx.png");
        ImageIcon imageIcon2=new ImageIcon("fx.png");
        button1.setIcon(imageIcon1);
        panel.add(button1);
        button2.setIcon(imageIcon2);
        panel.add(button2);
        add(panel,BorderLayout.CENTER);
        button1.addActionListener(new FS());
        button2.addActionListener(new FX());

        menu.add(item1);
        menu.add(item2);
        menuBar.add(menu);
        setJMenuBar(menuBar);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setSize(500,500);

        Toolkit toolkit=Toolkit.getDefaultToolkit();
        Dimension screenSize=toolkit.getScreenSize();
        int x=(screenSize.width-getWidth())/2;
        int y=(screenSize.height-getHeight())/2;
        setLocation(x,y);
    }

    class FS implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent event){
            new SinxShow();
        }

    }

    class FX implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent event){
            new PolynomialShow();
        }
    }

    public static void main(String[] args){
//        FittingSinx sinx=new FittingSinx(1,20,20,1,0.5);
//        sinx.train_self(1000);
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Main();
            }
        });

    }

}
