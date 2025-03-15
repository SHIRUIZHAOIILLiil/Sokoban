package cn.buaa.edu;

public class Prg1 {
    public int getSum() {
        return sum;
    }

    public Prg1(int...num)
    {
        for(int i=0;i< num.length;i++)
        {
            sum+=num[i];
        }
    }


    private  int sum;

}
