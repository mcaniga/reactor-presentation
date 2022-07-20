package org.example.presentation.hotcoldpublishers;

import org.example.presentation.util.Util;

public class HotColdPublishersDemo {

    public static void main(String[] args) {

        OrderService orderService = new OrderService();
        RevenueService revenueService = new RevenueService();
        InventoryService inventoryService = new InventoryService();

        // observe the order stream, when new element occurs, update inventory service
        orderService
                .orderStream()
                .subscribe(revenueService.increaseCategoryRevenue());

        // observe the order stream, when new element occurs, decrese category count
        orderService
                .orderStream()
                .subscribe(inventoryService.decreaseCategoryCount());

        inventoryService.streamInventoryDB()
                .subscribe(Util.subscriber("inventory"));

        revenueService.streamRevenueDB()
                .subscribe(Util.subscriber("revenue"));

        Util.sleepSeconds(60);
    }
}
