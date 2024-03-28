
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class stockAPI {
    
    public static JSONObject gettimeSeries(JSONObject dailyTime){
        JSONObject dailyData = new JSONObject();
        for (Object date : dailyTime.keySet()) {
            JSONObject data = (JSONObject) dailyTime.get(date);
            String open = (String) data.get("1. open");
            String high = (String) data.get("2. high");
            String low = (String) data.get("3. low");
            String close = (String) data.get("4. close");
            String volume = (String) data.get("5. volume");

            JSONObject timeData = new JSONObject();
            timeData.put("open", open);
            timeData.put("high", high);
            timeData.put("low", low);
            timeData.put("close", close);
            timeData.put("volume", volume);

            dailyData.put(date.toString(), timeData);

        }
            return dailyData;
    }
    
    public static JSONObject getStockQuote(String symbol) {
        String api = "QETC01Q21P0BB3RG";
        String urlString = "https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol=" + symbol
                + "&apikey=" + api + "&datatype=JSON";
        try {
            // call api and get response
            HttpURLConnection conn = fetchApiResponse(urlString);

            // check for response status
            // 200 - means that the connection was a success
            if (conn.getResponseCode() != 200) {
                System.out.println("Error: Could not connect to API");
                return null;
            }

            // read resulting json data
            StringBuilder resultJson = new StringBuilder();
            Scanner scanner = new Scanner(conn.getInputStream());
            while (scanner.hasNext()) {
                // read and store into the string builder
                resultJson.append(scanner.nextLine());
            }

            // close scanner
            scanner.close();

            // close url connection
            conn.disconnect();

            // parse through our data
            JSONParser parser = new JSONParser();
            JSONObject resultJsonObj = (JSONObject) parser.parse(String.valueOf(resultJson));

            // get metaData
            JSONObject metaData = (JSONObject) resultJsonObj.get("Meta Data");

            //get timeSeries
            JSONObject dt = (JSONObject) resultJsonObj.get("Time Series (Daily)");
             // make timeseries method into object to be read in metadataJSON
            JSONObject pdt = gettimeSeries(dt);
            
            // get information
            String information = (String) metaData.get("1. Information");
            symbol = (String) metaData.get("2. Symbol");
            String lastRefreshed = (String) metaData.get("3. Last Refreshed");
            String outputSize = (String) metaData.get("4. Output Size");
            String timeZone = (String) metaData.get("5. Time Zone");
    
            JSONObject metadataJson = new JSONObject();
        metadataJson.put("information", information);
        metadataJson.put("symbol", symbol);
        metadataJson.put("last_refreshed", lastRefreshed);
        metadataJson.put("output_size", outputSize);
        metadataJson.put("time_zone", timeZone);
        metadataJson.put("time_series",pdt);

        return metadataJson;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("not found :(");
        }
        return null;
     }

    // fetches response data making http request to api
    private static HttpURLConnection fetchApiResponse(String urlString) {
        try {
            // attempt to create connection
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // set request method to get
            conn.setRequestMethod("GET");

            // connect to our API
            conn.connect();
            return conn;
        } catch (IOException e) {
            e.printStackTrace();
        }

        // could not make connection
        return null;
    }

}
