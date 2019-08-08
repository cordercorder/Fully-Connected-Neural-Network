package NeuralNetwork;

import org.jfree.data.category.DefaultCategoryDataset;

import java.util.ArrayList;
import java.util.List;

import static NeuralNetwork.FittingSinx.Init_w;

/**
 * @Author: 金任任
 * @Class: 计科1604
 * @Number: 2016014537
 */
public class FittingPolynomial implements Runnable{
    private int inputcnt;
    private int hiddencnt1;
    private int hiddencnt2;
    private int outputcnt;
    private double learningrate;
    private DefaultCategoryDataset dataset=new DefaultCategoryDataset();
    private MyChart myChart=null;
    private int cnt;
    private int status;//0回想曲线，1泛化曲线，2Loss曲线

    private Matrix input;
    private Matrix w1;
    private Matrix w2;
    private Matrix w3;
    private Matrix output;
    private double []Loss;

    private List<Integer> coe=new ArrayList<Integer>();
    private List<Integer> exponent=new ArrayList<Integer>();

    public FittingPolynomial(int inputcnt,int hiddencnt1,int hiddencnt2,int outputcnt,double learningrate,int cnt,String title,boolean f,List<Integer> coe,List<Integer> exponent){
        this.inputcnt=inputcnt;
        this.hiddencnt1=hiddencnt1;
        this.hiddencnt2=hiddencnt2;
        this.outputcnt=outputcnt;
        this.learningrate=learningrate;
        this.cnt=cnt;
        if(!f){
            myChart=new MyChart(title,dataset,"预测的多项式曲线","真实的多项式曲线",false);
        }
        else{
            myChart=new MyChart(title,dataset,"Loss曲线",false);
        }
        w1=new Matrix(inputcnt,hiddencnt1);
        w2=new Matrix(hiddencnt1,hiddencnt2);
        w3=new Matrix(hiddencnt2,outputcnt);
        this.coe=coe;
        this.exponent=exponent;
        Init_w(w1);
        Init_w(w2);
        Init_w(w3);
        Loss=new double[cnt];
    }

    void UpdateWeight(int pos,double[] input_list,double[] target_list){
        input=new Matrix(input_list);
        Matrix target_out=new Matrix(target_list);

        Matrix hidden_in1=w1.Trans().mul(input);
        Matrix hidden_out1=hidden_in1.activate();

        Matrix hidden_in2=w2.Trans().mul(hidden_out1);
        Matrix hidden_out2=hidden_in2.activate();

        output=w3.Trans().mul(hidden_out2);

        double tmp;
        Matrix output_error=new Matrix(output.getN(),1);
        Matrix hiddent_error1=null,hiddent_error2;

        Matrix res=new Matrix(output.getN(),1);
        for(int i=0;i<output.getN();i++){
            tmp=target_out.getMa()[i][0]-output.getMa()[i][0];
            output_error.getMa()[i][0]=tmp;
            res.getMa()[i][0]=tmp;
        }

        Loss[pos]+=(target_out.getMa()[0][0]-output.getMa()[0][0])*(target_out.getMa()[0][0]-output.getMa()[0][0])/2.0;

        hiddent_error2=w3.mul(output_error);
        hiddent_error1=w2.mul(hiddent_error2);

        res=res.mul(hidden_out2.Trans());
        res.handle(learningrate);
        try{
            w3.Plus(res.Trans());
        }
        catch (Exception e){
            e.printStackTrace();
        }

        res=new Matrix(hiddent_error2.getN(),1);
        for(int i=0;i<hiddent_error2.getN();i++){
            res.getMa()[i][0]=(hiddent_error2.getMa()[i][0]*(1.0-hidden_out2.getMa()[i][0])*hidden_out2.getMa()[i][0]);
        }
        res=res.mul(hidden_out1.Trans());
        res.handle(learningrate);
        try{
            w2.Plus(res.Trans());
        }
        catch (Exception e){
            e.printStackTrace();
        }

        res=new Matrix(hiddent_error1.getN(),1);
        for(int i=0;i<hiddent_error1.getN();i++){
            res.getMa()[i][0]=(hiddent_error1.getMa()[i][0]*(1.0-hidden_out1.getMa()[i][0])*hidden_out1.getMa()[i][0]);
        }
        res=res.mul(input.Trans());
        res.handle(learningrate);
        try{
            w1.Plus(res.Trans());
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void Test(double[] input_list,double[] target_list){
        input=new Matrix(input_list);
        Matrix target_out=new Matrix(target_list);

        Matrix hidden_in1=w1.Trans().mul(input);
        Matrix hidden_out1=hidden_in1.activate();

        Matrix hidden_in2=w2.Trans().mul(hidden_out1);
        Matrix hidden_out2=hidden_in2.activate();

        output=w3.Trans().mul(hidden_out2);

        System.out.println("预测值:  "+output.getMa()[0][0]+",   实际值: "+target_out.getMa()[0][0]);
    }

    public MyChart getMyChart(){
        return myChart;
    }

    public double getIn(){
        return input.getMa()[0][0];
    }

    public double getOut(){
        return output.getMa()[0][0];
    }

    public void setStatus(int status){
        this.status=status;
    }

    double function(double x){
        double ans=0;
        for(int i=0;i<coe.size();i++){
            ans=ans+(double)coe.get(i)*Math.pow(x,(double)exponent.get(i));
        }
        return ans;
    }

    @Override
    public void run(){
        Thread thread=new Thread(myChart);
        thread.start();
        if(status==0){
            double x=-1,X=1;
            double[] input_list=new double[1];
            double[] target_list=new double[1];
            myChart.setTot(200);
            while(x<X){
                input_list[0]=x;
                target_list[0]=function(x);
                for(int i=0;i<cnt;i++){
                    UpdateWeight(i,input_list,target_list);
                }
                Test(input_list,target_list);
                myChart.Add(x,getOut(),target_list[0]);
                x+=0.01;
            }
        }
        else if(status==1){
            double x=-1,X=1;
            double[] input_list=new double[1];
            double[] target_list=new double[1];
            myChart.setTot(200);
            while(x<X){
                input_list[0]=x;
                target_list[0]=function(x);
                for(int i=0;i<cnt;i++){
                    UpdateWeight(i,input_list,target_list);
                }
                input_list[0]=x+0.005;
                target_list[0]=function(x+0.005);
                Test(input_list,target_list);
                myChart.Add(x,getOut(),target_list[0]);
                x+=0.01;
            }
        }
        else{
            double x=-1,X=1;
            double[] input_list=new double[1];
            double[] target_list=new double[1];
            myChart.setTot(200);
            while(x<X){
                input_list[0]=x;
                target_list[0]=function(x);
                for(int i=0;i<cnt;i++){
                    UpdateWeight(i,input_list,target_list);
                }
                Test(input_list,target_list);
                x+=0.01;
            }
            for(int i=0;i<cnt;i++){
                Loss[i]=Loss[i]/(double)cnt;
                Loss[i]=Math.log10(Loss[i]);
                myChart.Add(i,Loss[i]);
                System.out.println("Loss=="+Loss[i]);
            }

        }
    }
}
