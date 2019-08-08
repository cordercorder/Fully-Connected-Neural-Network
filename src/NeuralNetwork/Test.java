package NeuralNetwork;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

/**
 * @Author: 金任任
 * @Class: 计科1604
 * @Number: 2016014537
 */
public class Test {
    public static void main(String[] args){
        Map<Integer,Integer> mp=new TreeMap<Integer, Integer>();
        for(int i=0;i<10;i++){
            mp.put(1,i);
        }
        System.out.println(mp.get(1));
    }


}
