## OrderBook Test
Introduction
A 'limit order book' stores customer orders on a price time priority basis. The highest bid and lowest offer
are considered "best" with all other orders stacked in price levels behind.
In this test, the best order is considered to be at level 1.

### Part A
Given the following Order class create an OrderBook class to provide efficient limit order book capabilities.
( see Order class)

Your OrderBook class should support the following use-cases:
* Given an Order, add it to the OrderBook (order additions are expected to occur extremely frequently)
* Given an order id, remove an Order from the OrderBook (order deletions are expected to occur at approximately 60% of the rate of order additions)
* Given an order id and a new size, modify an existing order in the book to use the new size (size modifications do not affect time priority)
* Given a side and a level (an integer value >0) return the price for that level (where level 1 represents the best price for a given side).
  For example, given side=B and level=2 return the second best bid price
* Given a side and a level return the total size available for that level
* Given a side return all the orders from that side of the book, in level- and time-order

### Part B
Please suggest (but do not implement) modifications or additions to the Order and/or OrderBook classes
to make them better suited to support real-life, latency-sensitive trading operations.

____________________________
## Solution:

### Part A - OrderBook
See OrderBook class.

The OrderBookTest class in the /test/java/com/order package defines a few test cases and the main method can be run to execute the defined tests.
The solution runs with at Java Runtime > 1.8

### Part B - Improvements

* In the Order class, a LocalDateTime field was added to store the timestamp, that is further used in the TreeMap objects for price that is ordered in ascending order - for usage in point 6, for by Level storage
* Different refactorings needed to be done, there are some duplicate codes that could be improved
* For better performance, some other data structures like PriorityQueue, LinkedList can be used, for storing the orders based on level
* In order to meet real life scenarios, the lists manipulations could be synchronized to become thread safe.
* In this particular implementation, the print method is used to show the results of execution but it should be changed to logging, in real-life scenarios it might not be needed in some cases
* Use primitives instead of object wrapping classes
* Currently the side types are considered to be only 2, in case a different type besides B or O are provided, the implementation might experience inconsistencies. 
* Error handling need to be added to handle edge cases
* In the test class, the orders are added with specific time for point 6 scope (to test the ascending order of timestamp after level ordering)