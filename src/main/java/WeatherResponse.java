import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

public class WeatherResponse {

    private static OkHttpClient okHttpClient = new OkHttpClient();
    private static ObjectMapper objectMapper = new ObjectMapper();
    private static final String API_KEY ="1DKNzj6PQxZYfYO3w2DfQ5iCsApREdNj";
    //"oFkFaQZt0GYykcr9foj6plm3qjs20xE0";
    //"drYK9Ut6cNWnxXLKWBzjjJEqMGpluC9b" ;


     static public String WeatherForDay(String citiId) throws IOException {


         // http://dataservice.accuweather.com/forecasts/v1/daily/5day/{locationKey}

         HttpUrl httpUrl = new HttpUrl.Builder()
                 .scheme("http")
                 .host("dataservice.accuweather.com")
                 .addPathSegment("forecasts")
                 .addPathSegment("v1")
                 .addPathSegment("daily")
                 .addPathSegment("5day")
                 .addPathSegment(citiId)
                 .addQueryParameter("apikey", API_KEY)
                 .build();

         Request reguest = new Request.Builder()
                 .addHeader("accept", "application/json")
                 .url(httpUrl)
                 .build();

         Response response = okHttpClient.newCall(reguest).execute();

         String responseJson = response.body().string();

          JsonNode jsonDate = objectMapper
                 .readTree(responseJson)
                 .at("/DailyForecasts/0/Date");


         JsonNode jsonTemperatureMin = objectMapper
                 .readTree(responseJson)
                 .at("/DailyForecasts/0/Temperature/Minimum/Value");


         JsonNode jsonTemperatureMax = objectMapper
                 .readTree(responseJson)
                 .at("/DailyForecasts/0/Temperature/Maximum/Value");

         JsonNode jsonDescriptionOfTheWeather = objectMapper
                 .readTree(responseJson)
                 .at("/Headline/Text");


         String tmpDate = jsonDate.asText();
         String tmpDescriptionOfTheWeather = jsonDescriptionOfTheWeather.asText();
         String tmpTemperatureMin = jsonTemperatureMin.asText();
         String tmpTemperatureMax = jsonTemperatureMax.asText();

         System.out.println("Город: " +"Moscow");
         System.out.println("Дата: " + tmpDate);
         System.out.println("Описание погоды: " + tmpDescriptionOfTheWeather);

         // температура в F  переведем в Цельсия
         System.out.println("Температура min: " + (( Float.parseFloat(tmpTemperatureMin) - 32) *5/9)+ "C");
         System.out.println("Температура max: " +  (( Float.parseFloat(tmpTemperatureMax) - 32) *5/9)+ "C");


         return responseJson;


    }
}
