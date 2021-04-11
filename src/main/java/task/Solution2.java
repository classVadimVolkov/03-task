package task;

import lombok.ToString;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Solution2 {
    private static int numberOfUsers = 50;
    private static AtomicInteger coupons = new AtomicInteger(19);
    private static final int MAX_COUPONS_PER_USER = 5;
    private static Map<Runnable, Integer> usersWithCoupons = new ConcurrentHashMap<>();

    public static void main(String[] args) throws InterruptedException {
        ExecutorService service = Executors.newCachedThreadPool();
        for (int i = 0; i < numberOfUsers; i++) {
            service.execute(new User(i));
        }

        service.shutdown();
        service.awaitTermination(5, TimeUnit.SECONDS);

        for (Map.Entry entry : usersWithCoupons.entrySet()) {
            System.out.println("Key: " + entry.getKey().toString() + " Value: "
                    + entry.getValue());
        }

        System.out.println("Total coupons from users: " + usersWithCoupons.values().stream()
                .reduce(Integer::sum)
                .orElse(0));
    }

    @ToString
    private static class User implements Runnable {
        private int id;

        public User(int id) {
            this.id = id;
        }

        @Override
        public void run() {
            int userCoupons = usersWithCoupons.get(this) == null ? 0 : usersWithCoupons.get(this);
            while (userCoupons != MAX_COUPONS_PER_USER && coupons.get() != 0) {
                userCoupons++;
                usersWithCoupons.put(this, userCoupons);
                coupons.getAndDecrement();
            }
        }
    }
}
