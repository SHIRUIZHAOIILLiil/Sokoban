package cn.buaa.edu;

import java.util.Arrays;
import java.util.Scanner;

public class Nt {
    public static void main(String[] args) {
        String []arr1=new String[100];
        String []arr2=new String[100];
        int count=0;
        int sum=0;
        Scanner s=new Scanner(System.in);
        System.out.println("plz input integers");
        while (s.hasNext())
        {
            arr1[count++]=s.next();
            if(arr1[count-1].equals("end"))
            {
                ;
                break;
            }
        }
    for(int i=0;i<count-1;i++)
    {
        System.out.println(i+1+": "+arr1[i]);
    }


    }

}
