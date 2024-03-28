public class Stock {
    // attributes of stock
    String symbol;
    String name;
   String prices;
    double change;
    double percentChange;


    // constructor for stock
    public Stock(String symbol, String prices) {
        this.symbol = symbol;

        this.prices = prices;
    }

    // setters
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(String prices) {
        this.prices = prices;
    }

    public void setChange(double change) {
        this.change = change;
    }

    // getters
    public String getSymbol() {
        return symbol;
    }

    public String getName() {
        return name;
    }

    public String getPrices() {
        return prices;
    }

    public double getChange() {
        return change;
    }
}
