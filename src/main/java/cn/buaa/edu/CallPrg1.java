package cn.buaa.edu;

import java.sql.SQLOutput;
import java.util.Scanner;

public class CallPrg1 {
    public static void main(String[] args) {
        Prg1 p=new Prg1(1,2,3,4);
        System.out.print(p.getSum());
        Scanner scan=new Scanner(System.in);
       if( scan.hasNextFloat())
       {
           float f=scan.nextFloat();
           System.out.print(f);
       }
       else if(scan.hasNextInt())
       {
           int i=scan.nextInt();
           System.out.print(i);
       }
       else
       {
           for(int i=0;i<3;i++) {
               String s = scan.next();
               System.out.print(s);
           }
       }
    }
}
