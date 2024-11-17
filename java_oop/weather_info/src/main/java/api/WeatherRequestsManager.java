package api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class WeatherRequestsManager {

    RequestsManager requests = new RequestsManager();
    String api_token;

    public  WeatherRequestsManager(String api_token){
        this.api_token = api_token;
    }

    private Map<String, String> getLatAndnLonByCityName(String cityName) throws IOException {
        String filename = "src\\main\\java\\UI\\city_coordinates.json";

        ObjectMapper objectMapper = new ObjectMapper();

        File jsonFile = new File(filename);

        Map<String, Map<String, String>> cityData = objectMapper.readValue(jsonFile, Map.class);
        return cityData.get(cityName);
    };

    public Map<String, String> getWeatherByCityName(String cityName) throws IOException, InterruptedException {

        Map<String, List<String>> headers = Map.of(
                "X-Yandex-Weather-Key", List.of("6a323067-4110-4675-9647-6e3e45f73059"),
                "Accept", List.of("application/json")
        );

        Map<String, String> LatAndLon = getLatAndnLonByCityName(cityName);

        String response = requests.get("https://api.weather.yandex.ru/v2/forecast", Optional.of(headers), Optional.of(LatAndLon));
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(response);

        String condition = jsonNode.get("fact").get("condition").textValue();
        String temp = String.valueOf(jsonNode.get("fact").get("temp"));
        String feels_like = String.valueOf(jsonNode.get("fact").get("feels_like"));
        String wind_speed = String.valueOf(jsonNode.get("fact").get("wind_speed"));
        String wind_gust = String.valueOf(jsonNode.get("fact").get("wind_gust"));
        String wind_dir = jsonNode.get("fact").get("wind_dir").textValue();
        String pressure_mm = String.valueOf(jsonNode.get("fact").get("pressure_mm"));

        return WeatherResponse.build(condition, temp, feels_like, wind_speed, wind_gust, wind_dir, pressure_mm);
    };
}

class WeatherResponse {

    static Map<String, String> conditions = new HashMap<String, String>(){{
        put("clear", "Ясно");
        put("partly-cloudy", "Малооблачно");
        put("cloudy", "Облачно с прояснениями");
        put("overcast", "Пасмурно");
        put("light-rain", "Небольшой дождь");
        put("rain", "Дождь");
        put("heavy-rain", "сильный дождь");
        put("showers", " Ливень");
        put("wet-snow", "дождь со снегом");
        put("light-snow", "Небольшой снег");
        put("snow", "Снег");
        put("snow-showers", "Снегопад");
        put("hail", "Град");
        put("thunderstorm", "Гроза");
        put("thunderstorm-with-rain", "Дождь с грозой");
        put("thunderstorm-with-hail", "Гроза с градом");
    }};

    static Map<String, String> windDirs = new HashMap<String, String>(){
        {
            put("nw", "Северо-западное");
            put("n", "Северное");
            put("ne", "Северо-восточное");
            put("e", "Восточное");
            put("se", "Юго-восточное");
            put("s", "Южное");
            put("sw", "Юго-западное");
            put("w", "Западное");
        }};

    public static Map<String, String> build(String condition, String temp, String feels_like, String wind_speed ,String wind_gust, String wind_dir, String pressure_mm){

        Map<String, String> response = new HashMap<>(){
            {
                put("condition", conditions.get(condition));
                put("temp", temp);
                put("feels_like", feels_like);
                put("wind_speed", wind_speed);
                put("wind_gust", wind_gust);
                put("wind_dir", windDirs.get(wind_dir));
                put("pressure_mm", pressure_mm);
            }
        };
        return response;
    }
}