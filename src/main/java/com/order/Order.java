package main.java.com.order;

import java.time.LocalDateTime;

public class Order {
    private long id;
    private double price;
    private char side; // B "Bid" or O "Offer "
    private long size;
    private LocalDateTime timestamp;

    public Order(long id, double price, char side, long size, LocalDateTime timestamp) {
        this.id = id;
        this.price = price;
        this.side = side;
        this.size = size;
        this.timestamp = timestamp;
    }

    public long getId() {
        return id;
    }

    public double getPrice() {
        return price;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public char getSide() {
        return side;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

}
