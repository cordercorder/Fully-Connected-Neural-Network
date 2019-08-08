package NeuralNetwork;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


/**
 * @Author: 金任任
 * @Class: 计科1604
 * @Number: 2016014537
 */
public class PolynomialShow extends JFrame{

    private JLabel label0=new JLabel("输入多项式");
    private JTextField pre_fun=new JTextField();
    private JButton start=new JButton("");
    private JButton exit=new JButton("退出");
    private JLabel label1=new JLabel("隐藏层1节点数");
    private JTextField hiddencnt1=new JTextField();
    private JLabel label2=new JLabel("隐藏层2节点数");
    private JTextField hiddencnt2=new JTextField();
    private JLabel label3=new JLabel("训练次数");
    private JTextField traincnt=new JTextField();
    private JLabel label4=new JLabel("学习率");
    private JTextField fun=new JTextField();
    private JLabel label5=new JLabel("整合后的多项式");

    private JTextField learningrate=new JTextField();
    private List<Integer> coe;
    private List<Integer> exponent;
    private FittingPolynomial call_back=new FittingPolynomial(1,20,20,1,0.8,500,"回想曲线",false,coe,exponent);
    private FittingPolynomial generalization=new FittingPolynomial(1,20,20,1,0.8,500,"泛化曲线",false,coe,exponent);
    private FittingPolynomial loss=new FittingPolynomial(1,20,20,1,0.8,500,"Loss曲线",true,coe,exponent);

    public PolynomialShow(){
        setLayout(null);

        label0.setBounds(20,20,100,30);
        add(label0);
        pre_fun.setBounds(110,20,502,30);
        add(pre_fun);

        label5.setBounds(622,20,100,30);
        add(label5);

        fun.setBounds(722,20,130,30);
        add(fun);

        label1.setBounds(20,70,100,30);
        add(label1);

        hiddencnt1.setBounds(110,70,60,30);
        add(hiddencnt1);

        label2.setBounds(190,70,100,30);
        add(label2);

        hiddencnt2.setBounds(282,70,60,30);
        add(hiddencnt2);

        label3.setBounds(362,70,60,30);
        add(label3);

        traincnt.setBounds(422,70,60,30);
        add(traincnt);

        label4.setBounds(502,70,50,30);
        add(label4);

        learningrate.setBounds(552,70,60,30);
        add(learningrate);

        ImageIcon imageIcon=new ImageIcon("start.png");
        start.setIcon(imageIcon);
        start.setBounds(672,70,50,32);
        add(start);
        start.addActionListener(new Start());

        call_back.getMyChart().setVisible(true);
        call_back.getMyChart().setBounds(20,110,400,280);
        add(call_back.getMyChart());

        generalization.getMyChart().setVisible(true);
        generalization.getMyChart().setBounds(440,110,400,280);
        add(generalization.getMyChart());

        loss.getMyChart().setVisible(true);
        loss.getMyChart().setBounds(20,410,820,260);
        add(loss.getMyChart());

        setTitle("拟合多项式");
        setSize(870,730);
        setVisible(true);
        setResizable(false);

        Toolkit toolkit=Toolkit.getDefaultToolkit();
        Dimension screenSize=toolkit.getScreenSize();
        int x=(screenSize.width-getWidth())/2;
        int y=(screenSize.height-getHeight())/2;
        setLocation(x,y);
    }

    class Start implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e){
            int Hiddencnt1=Integer.valueOf(hiddencnt1.getText());
            int Hiddencnt2=Integer.valueOf(hiddencnt2.getText());
            int Traincnt=Integer.valueOf(traincnt.getText());
            double Learningrate=Double.valueOf(learningrate.getText());
            String Function=pre_fun.getText();
            GetFunction(Function);

            call_back=new FittingPolynomial(1,Hiddencnt1,Hiddencnt2,1,Learningrate,Traincnt,"回想曲线",false,coe,exponent);
            call_back.setStatus(0);
            call_back.getMyChart().setVisible(true);
            call_back.getMyChart().setBounds(20,110,400,280);
            add(call_back.getMyChart());

            generalization=new FittingPolynomial(1,Hiddencnt1,Hiddencnt2,1,Learningrate,Traincnt,"泛化曲线",false,coe,exponent);
            generalization.setStatus(1);
            generalization.getMyChart().setVisible(true);
            generalization.getMyChart().setBounds(440,110,400,280);
            add(generalization.getMyChart());

            loss=new FittingPolynomial(1,Hiddencnt1,Hiddencnt2,1,Learningrate,Traincnt,"Loss曲线",true,coe,exponent);
            loss.setStatus(2);
            loss.getMyChart().setVisible(true);
            loss.getMyChart().setBounds(20,410,820,260);
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

    public void GetFunction(String s){
        Map<Integer,Integer> mp=new TreeMap<Integer,Integer>();//指数和系数
        int Coe,tmp;
        for(int i=0;i<s.length();i++){
            if(s.charAt(i)=='x'||s.charAt(i)=='X'){
                if(i>0){
                    char ch=s.charAt(i-1);
                    if(ch=='+'||ch=='-'){
                        boolean f;
                        if(ch=='+'){
                            f=true;
                        }
                        else{
                            f=false;
                        }
                        if(i+1<s.length()&&s.charAt(i+1)=='^'){
                            if(i+2<s.length()&&s.charAt(i+2)>='0'&&s.charAt(i+2)<='9'){
                                int j=i+2;
                                tmp=0;
                                while(j<s.length()&&s.charAt(j)>='0'&&s.charAt(j)<='9'){
                                    tmp=tmp*10+(int)(s.charAt(j)-'0');
                                    j++;
                                }
                                if(mp.get(tmp)==null){
                                    if(f){
                                        mp.put(tmp,1);
                                    }
                                    else{
                                        mp.put(tmp,-1);
                                    }
                                }
                                else{
                                    if(f){
                                        Coe=mp.get(tmp)+1;
                                    }
                                    else{
                                        Coe=mp.get(tmp)-1;
                                    }
                                    mp.put(tmp,Coe);
                                }
                                i=j-1;
                            }
                        }
                        else{
                            if(mp.get(1)==null){
                                if(f){
                                    mp.put(1,1);
                                }
                                else{
                                    mp.put(1,-1);
                                }
                            }
                            else{
                                if(f){
                                    tmp=1;
                                }
                                else{
                                    tmp=-1;
                                }
                                tmp+=mp.get(1);
                                mp.put(1,tmp);
                            }
                        }
                    }
                    else if(ch>='0'&&ch<='9'){
                        int j=i-1;
                        String new_s=new String();
                        while(j>=0&&s.charAt(j)>='0'&&s.charAt(j)<='9'){
                            j--;
                        }
                        for(int k=j+1;k<i;k++){
                            new_s+=s.charAt(k);
                        }
                        Coe=Integer.valueOf(new_s);
                        if(j>=0&&s.charAt(j)=='-'){
                            Coe=-Coe;
                        }
                        if(i+1<s.length()&&s.charAt(i+1)=='^'){
                            if(i+2<s.length()&&s.charAt(i+2)>='0'&&s.charAt(i+2)<='9'){
                                j=i+2;
                                tmp=0;
                                while(j<s.length()&&s.charAt(j)>='0'&&s.charAt(j)<='9'){
                                    tmp=tmp*10+(int)(s.charAt(j)-'0');
                                    j++;
                                }
                                if(mp.get(tmp)==null){
                                    mp.put(tmp,Coe);
                                }
                                else{
                                    Coe=Coe+mp.get(tmp);
                                    mp.put(tmp,Coe);
                                }
                                i=j-1;
                            }
                        }
                        else{
                            if(mp.get(1)==null){
                                mp.put(1,Coe);
                            }
                            else{
                                Coe=Coe+mp.get(1);
                                mp.put(1,Coe);
                            }
                        }
                    }
                }
                else{
                    if(i+1<s.length()&&s.charAt(i+1)=='^'&&i+2<s.length()&&s.charAt(i+2)>='0'&&s.charAt(i+2)<='9'){
                        int j=i+2;
                        tmp=0;
                        while(j<s.length()&&s.charAt(j)>='0'&&s.charAt(j)<='9'){
                            tmp=tmp*10+(int)(s.charAt(j)-'0');
                            j++;
                        }
                        if(mp.get(tmp)==null){
                            mp.put(tmp,1);
                        }
                        else{
                            Coe=1+mp.get(tmp);
                            mp.put(tmp,Coe);
                        }
                        i=j-1;
                    }
                    else{
                        if(mp.get(1)==null){
                            mp.put(1,1);
                        }
                        else{
                            Coe=1+mp.get(1);
                            mp.put(1,Coe);
                        }
                    }
                }
            }
            else if(s.charAt(i)>='0'&&s.charAt(i)<='9'){
                int j=i;
                tmp=0;
                while(j<s.length()&&s.charAt(j)>='0'&&s.charAt(j)<='9'){
                    tmp=tmp*10+(int)(s.charAt(j)-'0');
                    j++;
                }
                if((j<s.length()&&s.charAt(j)!='x'&&s.charAt(j)!='X')||(j==s.length())){
                    if(i>0&&s.charAt(i-1)=='-'){
                        tmp=-tmp;
                    }
                    if(mp.get(0)==null){
                        mp.put(0,tmp);
                    }
                    else{
                        Coe=tmp+mp.get(0);
                        mp.put(0,Coe);
                    }
                    i=j-1;
                }
            }
        }
        String str=new String();
        coe=new ArrayList<Integer>();
        exponent=new ArrayList<Integer>();
        int sum=0;
        for(Integer e:mp.keySet()){
            Coe=mp.get(e);
            if(Coe!=0){
                if(Coe==1||Coe==-1){
                    if(Coe==-1){
                        str+="-";
                    }
                    else if(sum>0){
                        str+="+";
                    }
                    if(e==1){
                        str+="x";
                    }
                    else if(e==0){
                        str+=Coe;
                    }
                    else{
                        str+="x^"+e;
                    }
                }
                else{
                    if(Coe<0){
                        str+="-";
                    }
                    else if(sum>0){
                        str+="+";
                    }
                    if(e==1){
                        str+=Coe+"x";
                    }
                    else if(e==0){
                        str+=Coe;
                    }
                    else{
                        str+=Coe+"x^"+e;
                    }
                }
                sum++;
            }
            if(Coe!=0){
                coe.add(Coe);
                exponent.add(e);
            }
        }
        fun.setText(str);
        System.out.println(str);
    }

    public static void main(String[] args){



        new PolynomialShow();
    }

}
