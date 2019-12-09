package com.company;

import java.util.Map;

public class Main {

    private static StockList stockList = new StockList();

    public static void main(String[] args) {
        StockItem temp = new StockItem("bread", 0.45, 100);
        stockList.addStock(temp);

        temp = new StockItem("cake", 1.50, 7);
        stockList.addStock(temp);

        temp = new StockItem("rice", 2.10, 70);
        stockList.addStock(temp);

        temp = new StockItem("tea", 1.15, 115);
        stockList.addStock(temp);
        temp = new StockItem("tea", 2.55, 70);
        stockList.addStock(temp);

        temp = new StockItem("shampoo", 3.70, 45);
        stockList.addStock(temp);

        temp = new StockItem("chips", 1.90, 70);
        stockList.addStock(temp);

        temp = new StockItem("oreo", 0.80, 5);
        stockList.addStock(temp);

        System.out.println(stockList);

        for (String s : stockList.Items().keySet()){
            System.out.println(s);
        }

        Basket injisBasket = new Basket("Inji");

        sellItem(injisBasket, "oreo", 3);
        System.out.println(injisBasket);

        sellItem(injisBasket, "oreo", 2);
//        System.out.println(injisBasket);

        if(sellItem(injisBasket, "oreo", 2) != 1){
            System.out.println("There are no more oreos in stock");
        }

        sellItem(injisBasket, "pen", 1);
//        System.out.println(injisBasket);

        sellItem(injisBasket, "chips", 4);
        sellItem(injisBasket, "rice", 12);
        sellItem(injisBasket, "bread", 1);
//        System.out.println(injisBasket);

//        System.out.println(stockList);

        Basket basket = new Basket("customer");
        sellItem(basket,"shampoo", 40);
        sellItem(basket, "rice", 1);
        removeItem(basket, "shampoo", 1);
        System.out.println(basket);

        removeItem(injisBasket, "chips", 1);
        removeItem(injisBasket, "rice", 3);
        removeItem(injisBasket, "chips", 1);
        System.out.println("chips removed " + removeItem(injisBasket, "chips", 1)); // should not remove any

        System.out.println(injisBasket);

        //remove all items from InjisBasker
        removeItem(injisBasket, "oreo", 5);
        removeItem(injisBasket, "rice", 10);
        removeItem(injisBasket, "bread", 1);
        removeItem(injisBasket, "chips", 2);
        System.out.println(injisBasket);

        System.out.println("\nDisplay stocklist before and after checkout");
        System.out.println(basket);
        System.out.println(stockList);
        checkOut(basket);
        System.out.println(basket);
        System.out.println(stockList);

//        temp = new StockItem("cup", 1.12);
//        stockList.Items().put(temp.getName(), temp);
        StockItem chips = stockList.Items().get("chips");
        if (chips != null){
            chips.adjustStock(2000);
        } if (chips != null) {
            stockList.get("chips").adjustStock(-1000);
        }

        System.out.println(stockList);
//        for (Map.Entry<String, Double> price : stockList.PriceList().entrySet()){
//            System.out.println(price.getKey() + " costs" + price.getValue());
//        }

        checkOut(injisBasket);
        System.out.println(injisBasket);
    }

    public static int sellItem(Basket basket, String item, int quantity){
        // retrieve the item from the stock list
        StockItem stockItem = stockList.get(item);
        if (stockItem == null){
            System.out.println("We don't sell " + item);
            return 0;
        }
        if (stockList.reserveStock(item, quantity) != 0){
            return basket.addToBasket(stockItem, quantity);
        }
        return 0;
    }

    public static int removeItem(Basket basket, String item, int quantity){
        // retrieve the item from the stock list
        StockItem stockItem = stockList.get(item);
        if (stockItem == null){
            System.out.println("We don't sell " + item);
            return 0;
        }
        if (basket.removeFromBasket(stockItem, quantity) == quantity){
            return stockList.unreserveStock(item, quantity);
        }
        return 0;
    }

    public static void checkOut(Basket basket){
        for (Map.Entry<StockItem, Integer> item : basket.Items().entrySet()){
            stockList.sellStock(item.getKey().getName(), item.getValue());
        }
        basket.clearBasket();
    }
}
