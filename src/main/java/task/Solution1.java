package task;

import lombok.ToString;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Solution1 {
    private static final int NUMBER_OF_USERS = 50;
    private static final AtomicInteger coupons = new AtomicInteger(19);
    private static final int MAX_COUPONS_PER_USER = 5;
    private static final Map<Runnable, Integer> usersWithCoupons = new ConcurrentHashMap<>();

    public static void main(String[] args) throws InterruptedException {
        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(NUMBER_OF_USERS);

        for (int i = 0; i < NUMBER_OF_USERS; i++) {
            scheduledThreadPool.scheduleAtFixedRate(new User(i), 0, 1, TimeUnit.MILLISECONDS);

        }

        scheduledThreadPool.shutdown();
        scheduledThreadPool.awaitTermination(3, TimeUnit.SECONDS);

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
            if (coupons.get() == 0) {
                Thread.currentThread().interrupt();
            }
            int userCoupons = usersWithCoupons.get(this) == null ? 0 : usersWithCoupons.get(this);
            if (userCoupons != MAX_COUPONS_PER_USER && coupons.get() != 0) {
                userCoupons++;
                usersWithCoupons.put(this, userCoupons);
                coupons.getAndDecrement();
            }
        }
    }
}
