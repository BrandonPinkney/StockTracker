import java.util.HashMap;

import org.json.simple.JSONObject;

public class stockPorfolio {

    HashMap<String,Stock> stocks;


    public stockPorfolio(){
        stocks = new HashMap<>();
    }

    public boolean addStock(Stock stock){
      if(!stocks.containsKey(stock.getSymbol())){
         stocks.put(stock.getSymbol(), stock);
         return true;
      }
       return false;
    }

    public boolean removeStock(Stock stock){
        if(stocks.containsKey(stock.getSymbol())){
            stocks.remove(stock.getSymbol());
            return true;

        }
            return false;
    }

    public void addToPortfolio(Stock stock){

        // create object of another class to call method
        JSONObject stockStuff = stockAPI.getStockQuote(stock.getSymbol());

       if(stockStuff!=null){
         String symbol = (String) stockStuff.get("symbol").toString();
       String timeSeries = (String) stockStuff.get("time_series").toString();
       Stock  newStock = new Stock(symbol,timeSeries);
         addStock(newStock);
       }else{
        System.out.println("nothing");
       }
       


    }
    public void clearPortfolio() {
        stocks.clear();
    }
    
    

    
    
}
