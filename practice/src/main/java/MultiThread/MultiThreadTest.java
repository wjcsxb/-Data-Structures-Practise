package MultiThread;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/*
* wait() 方法一定在循环中
* put ,get 方法 while ,if 的区别
* */
public class MultiThreadTest {
    static class Storage{
        private final int max = 5;
        private final ArrayList<Integer> list = new ArrayList<Integer>();

        synchronized void put(int value) throws InterruptedException{
            while (list.size() == max) {//if  ?
                wait();
                //等待唤醒后 可能条件已改变，需重新check，必须用while循环或者double check
            }
            list.add(value);
            notifyAll();
        }

        synchronized int get() throws InterruptedException {
            // line 0
            while (list.size() == 0) {//line 1   if  ??
                wait();//line 2
                //line 3
            }
            int value = list.remove(0);//line 4
            notifyAll();//line 5
            return value;
        }

        synchronized int getListSize(){
            return list.size();
        }
    }

    public static void main(String[] args) {
        final Storage storge = new Storage();
        ExecutorService es = Executors.newFixedThreadPool(11);
        //用一个周期性任务来打印当前list的大小
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            public void run() {
                System.out.println(storge.getListSize());
            }
        }, 0, 1, TimeUnit.SECONDS);

        for (int i = 0; i < 10; i++) {
            es.execute(new Runnable() {
                public void run() {

                    while (true) {
                        try {
                            storge.put(1);
                            Thread.sleep(200);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            break;
                        }
                    }

                }
            });
        }

        for (int i = 0; i < 1; i++) {
            es.execute(new Runnable() {
                public void run() {

                    while (true) {
                        try {
                            storge.get();
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            break;
                        }
                    }
                }
            });
        }

        //停止接收新的任务并且等待已经提交的任务（包含提交正在执行和提交未执行）执行完成。当所有提交任务执行完毕，线程池即被关闭。
        es.shutdown();
        try {
            //主线程一直处于等待的状态，等待线程池中所有的线程都运行完毕后才继续运行，shutdown()方法必须在前
            //可以用awaitTermination()方法来判断线程池中是否有继续运行的线程
            //当等待超过设定时间时，会监测ExecutorService是否已经关闭，若关闭则返回true，否则返回false。
            while (!es.awaitTermination(1, TimeUnit.SECONDS)) {
                System.out.println("线程池没有关闭");
            }
            System.out.println("线程池已经关闭");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //notify ,notifyAll?
    //notify只唤醒任何一个线程，多线程执行时会导致死锁
}
