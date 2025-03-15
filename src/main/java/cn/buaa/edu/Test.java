package cn.buaa.edu;

public class Test {
    public static   void show(Person p)
    {
        p.eat();
        p.sleep();
        p.work();
    }

    public static void main(String[] args) {
        show(new Student());
        show(new Teachers());
    }
}
