package test.java.com.order;

import main.java.com.order.Order;
import main.java.com.order.OrderBook;

import java.time.LocalDateTime;
import java.util.Optional;

public class OrderBookTest {

    public static void main(String[] args) {
        // 1. Add orders
        // ****************************************
        OrderBook book = addOrders();
        System.out.println("1. Added orders bids and offers" + ", size of orders: " + book.orders.size());

        // 2. Remove order
        // ****************************************
        book.removeOrder(9);
        System.out.println("2. Removed id 9" + ", size of orders: " + book.orders.size());

        // 3. Modify Size
        // ****************************************
        System.out.println("3. Modify size");
        Optional<Order> optionalOrder = book.orders.stream().filter(o -> o.getId() == 2).findFirst();
        if (optionalOrder.isPresent()) {
            Order updatedOrder = optionalOrder.get();
            System.out.println("Old size: " + updatedOrder.getSize());
            book.modifyOrderSize(2, 5L);
            System.out.println("New size: " + updatedOrder.getSize());
        }

        // 4. Return price for level, based on side
        // ****************************************
        System.out.println("4. Get price for provided level and side");
        book.getPrice('B', 1);
        book.getPrice('B', 2);
        book.getPrice('B', 3);
        book.getPrice('B', 4);
        book.getPrice('B', 5);// error for level not available

        book.getPrice('O', 1);
        book.getPrice('O', 2);
        book.getPrice('O', 3);
        book.getPrice('O', 4);
        book.getPrice('O', 5);// error for level not available

        // 5. Get size based on side and level
        // ****************************************
        System.out.println("5. Get total size for provided level and side");
        book.getSize('B', 1);
        book.getSize('B', 2);
        book.getSize('B', 3);
        book.getSize('B', 4);

        book.getSize('O', 1);
        book.getSize('O', 2);
        book.getSize('O', 3);
        book.getSize('O', 4);

        // 6. Get all orders of given side, in level and time order (will be first sorted based on level, than on time)
        // ****************************************
        System.out.println("6. Get all orders of given side, in level and time order - ascending");
        book.getOrders('B');
        book.getOrders('O');
    }

    private static OrderBook addOrders() {
        final LocalDateTime bidTime10h30min = LocalDateTime.of(2022, 3, 18, 10, 30);
        final LocalDateTime bidTime19h30min = LocalDateTime.of(2022, 3, 18, 19, 30);
        final LocalDateTime bidTime16h30min = LocalDateTime.of(2022, 3, 18, 16, 30);
        final LocalDateTime bidTime14h30min = LocalDateTime.of(2022, 3, 18, 14, 30);
        final LocalDateTime bidTime13h30min = LocalDateTime.of(2022, 3, 18, 13, 30);

        final LocalDateTime offerTime10h40min = LocalDateTime.of(2022, 3, 19, 10, 40);
        final LocalDateTime offerTime10h32min = LocalDateTime.of(2022, 3, 19, 10, 32);
        final LocalDateTime offerTime10h31min = LocalDateTime.of(2022, 3, 19, 10, 31);
        final LocalDateTime offerTime10h39min = LocalDateTime.of(2022, 3, 19, 10, 39);
        final LocalDateTime offerTime10h35min = LocalDateTime.of(2022, 3, 19, 10, 35);
        final LocalDateTime offerTime10h30min = LocalDateTime.of(2022, 3, 19, 10, 30);

        Order orderBid1 = new Order(1, 120, 'B', 2L, bidTime10h30min);
        Order orderBid2 = new Order(2, 230, 'B', 3L, bidTime19h30min);
        Order orderBid3 = new Order(3, 340, 'B', 4L, bidTime16h30min);
        Order orderBid4 = new Order(4, 1000, 'B', 2L, bidTime14h30min);
        Order orderBid5 = new Order(5, 1000, 'B', 5L, bidTime13h30min);

        Order orderOffer1 = new Order(6, 100, 'O', 2L, offerTime10h40min);
        Order orderOffer2 = new Order(7, 200, 'O', 3L, offerTime10h32min);
        Order orderOffer3 = new Order(8, 300, 'O', 4L, offerTime10h31min);
        Order orderOffer4 = new Order(9, 600, 'O', 6L, offerTime10h39min);//removed
        Order orderOffer5 = new Order(10, 500, 'O', 3L, offerTime10h35min);
        Order orderOffer6 = new Order(11, 500, 'O', 6L, offerTime10h30min);

        OrderBook book = new OrderBook();

        book.addOrder(orderBid1);
        book.addOrder(orderBid2);
        book.addOrder(orderBid3);
        book.addOrder(orderBid4);
        book.addOrder(orderBid5);

        book.addOrder(orderOffer1);
        book.addOrder(orderOffer2);
        book.addOrder(orderOffer3);
        book.addOrder(orderOffer4);
        book.addOrder(orderOffer5);
        book.addOrder(orderOffer6);

        return book;
    }
}
