package NeuralNetwork;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.axis.*;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * @Author: 金任任
 * @Class: 计科1604
 * @Number: 2016014537
 */
public class MyChart extends ChartPanel implements Runnable {

    private DefaultCategoryDataset dataset;
    private String first;
    private String second;
    private boolean flag;
    private int cnt;
    private java.util.List<Double> X=new ArrayList<Double>();
    private java.util.List<Double> Y=new ArrayList<Double>();
    private java.util.List<Double> pre_Y;
    private int tot;

    public MyChart(String title,DefaultCategoryDataset dataset,String first,String second,boolean f){
        super(getJFreeChart(title,dataset,f));
        this.dataset=dataset;
        this.first=first;
        this.second=second;
        flag=false;
        cnt=0;
        pre_Y=new ArrayList<Double>();
    }

    public MyChart(String title,DefaultCategoryDataset dataset,String first,boolean f){
        super(getJFreeChart(title,dataset,f));
        this.dataset=dataset;
        this.first=first;
        flag=true;
        cnt=0;
    }

    public static double RandomNumber(){
        double tmp;
        tmp=Math.random();
        return tmp;
    }

    public static JFreeChart getJFreeChart(String title,DefaultCategoryDataset dataset,boolean f){
        StandardChartTheme standardChartTheme=new StandardChartTheme("CN");

        standardChartTheme.setExtraLargeFont(new Font("隶书",Font.BOLD,20));

        standardChartTheme.setRegularFont(new Font("宋书",Font.PLAIN,15));

        standardChartTheme.setLargeFont(new Font("宋书",Font.PLAIN,15));

        ChartFactory.setChartTheme(standardChartTheme);

        JFreeChart jFreeChart=ChartFactory.createLineChart(title,null,null,dataset, PlotOrientation.VERTICAL,true,true,true);

        CategoryPlot plot=jFreeChart.getCategoryPlot();
//        plot.setBackgroundPaint(Color.black); // 设置绘图区背景色
        plot.setRangeGridlinePaint(Color.lightGray); // 设置水平方向背景线颜色
        plot.setBackgroundPaint(Color.WHITE);
        plot.setRangeGridlinesVisible(true);// 设置是否显示水平方向背景线,默认值为true
        plot.setDomainGridlinePaint(Color.WHITE); // 设置垂直方向背景线颜色
        plot.setDomainGridlinesVisible(false); // 设置是否显示垂直方向背景线,默认值为false

        if(f){
            NumberAxis Y=(NumberAxis)plot.getRangeAxis();
            Y.setLowerBound(-1.5);
            Y.setUpperBound(1.5);
        }

        return jFreeChart;
    }

    public void Add(double x,double y){
        X.add(x);
        Y.add(y);
    }

    public void Add(double x,double y1,double y2){
        X.add(x);
        pre_Y.add(y1);
        Y.add(y2);
    }

    void setTot(int tot){
        this.tot=tot;
    }

    @Override
    public void run(){
        while(cnt<tot){
            try{
                if(flag){
                    if(cnt<X.size()){
                        dataset.addValue(Y.get(cnt),first,String.valueOf(X.get(cnt)));
                        cnt++;
                    }
                }
                else{
                    if(cnt<X.size()){
                        dataset.addValue(pre_Y.get(cnt),first,String.valueOf(X.get(cnt)));
                        dataset.addValue(Y.get(cnt),second,String.valueOf(X.get(cnt)));
                        cnt++;
                    }
                }
                Thread.sleep(10);
            }
            catch (InterruptedException e){
                e.printStackTrace();
            }
        }

    }

}
