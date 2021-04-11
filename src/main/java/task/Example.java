package task;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Example {
    private static class RunnableTask implements Runnable{

        private String task;
        public RunnableTask(String s){
            this.task = s;
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName()+" Start Time for "+task+" "+new Date());
            // Обрабатываем задачу здесь
            try {
                Thread.sleep(3000); // обработка задачи занимает три секунды
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName()+" End Time for "+task+" "+new Date());
        }

        @Override
        public String toString(){
            return this.task;
        }
    }

        public static void main(String[] args) throws InterruptedException {
            System.out.println("Current Time = "+new Date());

            ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(3);

            for(int i=1; i<=3; i++){
                Thread.sleep(2000);
                RunnableTask task = new RunnableTask("Task "+i);
                scheduledThreadPool.schedule(task,3, TimeUnit.SECONDS); // планируется задержка выполнения задачи на три секунды
            }

            Thread.sleep(6000); // добавляем некоторую задержку
            scheduledThreadPool.shutdown();
            System.out.println("Completed all threads");
        }

}
