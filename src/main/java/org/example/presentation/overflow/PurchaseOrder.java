package org.example.presentation.overflow;

import lombok.Data;
import lombok.ToString;
import org.example.presentation.util.Util;

@Data
@ToString
public class PurchaseOrder {

    private String item;
    private double price;
    private int quantity;
    private String category;

    public PurchaseOrder() {
        this.item = Util.faker().commerce().productName();
        this.price = Double.parseDouble(Util.faker().commerce().price());
        this.category = Util.faker().commerce().department();
        this.quantity = Util.faker().number().numberBetween(1, 10);
    }

}
