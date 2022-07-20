package org.example.presentation.stockprice;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.time.LocalDateTime;
import java.util.concurrent.CountDownLatch;

// custom subscriber
public class StockPriceObserver {

    private static final int COUNTDOWNS_TO_PASS_AWAIT = 1;

    public static void main(String[] args) throws InterruptedException {

        CountDownLatch latch = new CountDownLatch(COUNTDOWNS_TO_PASS_AWAIT);

        StockPricePublisher.generatePrices()
                .subscribeWith(new Subscriber<Integer>() {

                    private Subscription subscription;

                    @Override
                    public void onSubscribe(Subscription subscription) {
                        this.subscription = subscription;
                        subscription.request(Long.MAX_VALUE);
                    }

                    @Override
                    public void onNext(Integer price) {
                        System.out.println(LocalDateTime.now() + " : Price : " + price);
                        if(price > 110 || price < 90){ // if price is in given range, cancel subscription and decrease latch
                            this.subscription.cancel();
                            latch.countDown();
                        }
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        latch.countDown();
                    }

                    @Override
                    public void onComplete() {
                        latch.countDown();
                    }
                });

        // suspend current thread until count down is zero = until onNext, onError, onComplete is called
        latch.await();
    }
}
