package main.java.com.order;

import java.util.*;
import java.util.stream.Collectors;

public class OrderBook {
    private static final char BID_TYPE = 'B';
    private static final char OFFER_TYPE = 'O';

    private final Map<Double, List<Order>> bids; // map of bid prices ordered reversed
    private final Map<Double, List<Order>> offers; // map of offer prices ordered ascending

    private Map<Double, List<Order>> bidsByPrice; // map of bids by price
    private Map<Double, List<Order>> offersByPrice; // map of offers by price

    private final Map<Integer, List<Order>> bidsByLevel; // map of bids by level
    private final Map<Integer, List<Order>> offersByLevel; // map of offers by level

    public final List<Order> orders = new ArrayList<>(); // full list of orders (bids or offers)

    public OrderBook() {
        bids = new TreeMap<>(Collections.reverseOrder()); // orders sorted in descending order of price
        offers = new TreeMap<>(); // orders sorted in ascending order of price

        bidsByPrice = new TreeMap<>();
        offersByPrice = new TreeMap<>();

        bidsByLevel = new TreeMap<>();
        offersByLevel = new TreeMap<>();
    }

    // 1. Given an Order, add it to the OrderBook
    public void addOrder(Order order) {
        orders.add(order);
    }

    // 2. Given an order id, remove an Order from the OrderBook
    public void removeOrder(long id) {
        orders.removeIf(order -> order.getId() == id);
    }

    // 3. Given an order id and a new size, modify an existing order in the book to use the new size
    public void modifyOrderSize(long id, long newSize) {
        for (Order order : orders) {
            if (order.getId() == id) {
                order.setSize(newSize);
                return;
            }
        }
    }

    // 4. Given a side and a level (an integer value > 0) return the price for that level
    public void getPrice(char side, int level) {
        if (level <= 0) {
            System.out.println("Please provide a value grater than 0 for level");
            return;
        }
        if (side == BID_TYPE) {
            createListOfBidsByLevel(level);
        } else {
            createListOfOffersByLevel(level);
        }
    }

    // 5. Given a side and a level return the total size available for that level
    public void getSize(char side, int level) {
        if (side == BID_TYPE) {
            getTotalSizeBids(level);
        } else {
            getTotalSizeOffers(level);
        }
    }

    // 6. Given a side return all the orders from that side of the book, in level- and time-order
    public void getOrders(char side) {
        Map<Integer, List<Order>> mapOrders = side == BID_TYPE ? bidsByLevel : offersByLevel;

        System.out.println("Side is: " + side);
        for (Map.Entry<Integer, List<Order>> entry : mapOrders.entrySet()) {
            for (Order order : mapOrders.get(entry.getKey())) {
                System.out.println("Level: " + entry.getKey() + ", Order Id: " + order.getId() + ", with price: " + order.getPrice() + ", with timestamp: " + order.getTimestamp());
            }
        }
    }

    private void createListOfBidsByLevel(int level) {
        double price;
        int levelRef = 1;

        bidsByPrice = orders.stream().filter(o -> o.getSide() == BID_TYPE)
                .sorted(Comparator.comparingDouble(Order::getPrice).reversed())
                .collect(Collectors.groupingBy(Order::getPrice));
        bids.putAll(bidsByPrice);

        for (Double key : bids.keySet()) {
            bidsByLevel.put(levelRef, bidsByPrice.get(key).stream()
                    .sorted(Comparator.comparing(Order::getTimestamp)).collect(Collectors.toList()));
            levelRef++;
        }

        if (level > bidsByLevel.size()) {
            System.out.println("Cannot find the requested level");
        } else {
            price = bidsByLevel.get(level).get(0).getPrice();
            System.out.println("For level: " + level + ", for side " + BID_TYPE + ", the price is: " + price);
        }
    }

    private void createListOfOffersByLevel(int level) {
        double price;
        int levelRef = 1;

        offersByPrice = orders.stream().filter(o -> o.getSide() == OFFER_TYPE)
                .sorted(Comparator.comparingDouble(Order::getPrice))
                .collect(Collectors.groupingBy(Order::getPrice));
        offers.putAll(offersByPrice);

        for (Double key : offers.keySet()) {
            offersByLevel.put(levelRef, offersByPrice.get(key).stream()
                    .sorted(Comparator.comparing(Order::getTimestamp)).collect(Collectors.toList()));
            levelRef++;
        }

        if (level > offersByLevel.size()) {
            System.out.println("Cannot find the requested level");
        } else {
            price = offersByLevel.get(level).get(0).getPrice();
            System.out.println("For level: " + level + ", for side " + OFFER_TYPE + ", the price is: " + price);
        }
    }

    private void getTotalSizeBids(int level) {
        long totalSizeBids = 0;
        if (level > bidsByLevel.size()) {
            System.out.println("Cannot find the requested level, please provide at most: " + bidsByLevel.size());
        } else {
            List<Order> bidsOrders = bidsByLevel.get(level);
            for (Order o : bidsOrders) {
                totalSizeBids += o.getSize();
            }
            System.out.println("Total Size - BID:  Level: " + level + ", total size is: " + totalSizeBids + ", orders size: " + bidsOrders.size());
        }
    }

    private void getTotalSizeOffers(int level) {
        long totalSizeOffers = 0;
        if (level > offersByLevel.size()) {
            System.out.println("Cannot find the requested level, please provide at most: " + offersByLevel.size());
        } else {
            List<Order> offersOrders = offersByLevel.get(level);
            for (Order o : offersOrders) {
                totalSizeOffers += o.getSize();
            }
            System.out.println("Total Size - OFFER:  Level: " + level + ", total size is: " + totalSizeOffers + ", orders size: " + offersOrders.size());
        }
    }

}

