package NeuralNetwork;

/**
 * @Author: 金任任
 * @Class: 计科1604
 * @Number: 2016014537
 */
public class Matrix {
    private double[][] ma;
    private int n,m;

    public Matrix(int n,int m){
        ma=new double[n][m];
        this.n=n;
        this.m=m;
    }

    public Matrix(double[] input){
        ma=new double[input.length][1];
        n=input.length;
        m=1;
        for(int i=0;i<input.length;i++){
            ma[i][0]=input[i];
        }
    }

    public double[][] getMa() {
        return ma;
    }

    public int getN() {
        return n;
    }

    public int getM() {
        return m;
    }

    public Matrix mul(Matrix a){
        if(m!=a.n){
            System.out.println("Matrix ERROE1");
            return null;
        }
        Matrix ans=new Matrix(n,a.m);
        for(int i=0;i<n;i++){
            for(int j=0;j<a.m;j++){
                for(int k=0;k<m;k++){
                    ans.ma[i][j]+=ma[i][k]*a.ma[k][j];
                }
            }
        }
        return ans;
    }

    public Matrix Trans(){
        Matrix ans=new Matrix(m,n);
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                ans.ma[j][i]=ma[i][j];
            }
        }
        return ans;
    }

    public Matrix activate(){
        if(m!=1){
            System.out.println("Matrix ERROE2");
            return null;
        }
        Matrix ans=new Matrix(n,m);
        for(int i=0;i<n;i++){
            ans.ma[i][0]=1.0/(Math.exp(-ma[i][0])+1.0);
        }
        return ans;
    }

    public void Plus(Matrix a) throws Exception{
        if(a.n!=n||a.m!=m){
            throw new Exception("Matrix ERROR3");
        }
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                ma[i][j]=ma[i][j]+a.ma[i][j];
            }
        }
    }

    public void handle(double learningrate){
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                ma[i][j]=ma[i][j]*learningrate;
            }
        }
    }

    public void show(){
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                System.out.print(ma[i][j]+" ");
            }
            System.out.println();
        }
    }

}
