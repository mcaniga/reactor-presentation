package org.example.presentation.hotcoldpublishers;

import org.example.presentation.util.Util;

public class HotColdPublishersDemo {

    public static void main(String[] args) {

        OrderService orderService = new OrderService();
        RevenueService revenueService = new RevenueService();
        InventoryService inventoryService = new InventoryService();

        // revenue and inv - observe the order stream
        orderService.orderStream().subscribe(revenueService.subscribeOrderStream());
        orderService.orderStream().subscribe(inventoryService.subscribeOrderStream());

        inventoryService.inventoryStream()

                .subscribe(Util.subscriber("inventory"));

        revenueService.revenueStream()
                .subscribe(Util.subscriber("revenue"));

        Util.sleepSeconds(60);
    }
}
