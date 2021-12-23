import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.io.StringReader;

public class ReguestSender {

    private static OkHttpClient okHttpClient = new OkHttpClient();
    private static ObjectMapper objectMapper = new ObjectMapper();
    private static final String API_KEY = "oFkFaQZt0GYykcr9foj6plm3qjs20xE0";

    static public String sendCityIdRequest(String cityName) throws IOException {

        String cityId;

        // http://dataservice.accuweather.com/locations/v1/cities/search

        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme("http")
                .host("dataservice.accuweather.com")
                .addPathSegment("locations")
                .addPathSegment("v1")
                .addPathSegment("cities")
                .addPathSegment("search")
                .addQueryParameter("apikey", API_KEY)
                .addQueryParameter("q",cityName)
                .build();

        Request reguest = new Request.Builder()
                .addHeader("accept", "application/json")
                .url(httpUrl)
                .build();

        Response response = okHttpClient.newCall(reguest).execute();

        String responseHeaderJson = response.headers().toString();
        String responseHeaderCookieJson = response.header("cookie");

        String responseJson = response.body().string();

        JsonNode cityIdNode = objectMapper
                .readTree(responseJson)
                        .at("/0/Key");

        cityId = cityIdNode.asText();


      //  System.out.println(responseHeaderCookieJson);
      //  System.out.println("---------------------------------------");
       System.out.println(responseHeaderJson);
        System.out.println(responseJson);
        return  cityId;

    }
    static public String sendTempRequest(String cityId){
        return  null;
    }
}
