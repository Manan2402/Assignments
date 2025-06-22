package com.example.WeatherForecast.service;


import com.example.WeatherForecast.entity.WeatherForecast;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.MalformedURLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WeatherService {

    RestTemplate restTemplate=new RestTemplate();

    public List<WeatherForecast> fetchWeather(String latitude, String longitude) throws MalformedURLException, JSONException {

        String url = "https://api.open-meteo.com/v1/forecast?latitude=" + latitude +

                "&longitude=" + longitude + "&daily=temperature_2m_max,temperature_2m_min&timezone=auto";

        String response=restTemplate.getForObject(url,String.class);

        JSONObject json=new JSONObject(response);

        JSONObject daily=json.getJSONObject("daily");

        List<WeatherForecast> forecast=new ArrayList<>();

        for(int i=0;i<7;i++){

            LocalDate date=LocalDate.now().plusDays(i);

            double temp=daily.getJSONArray("temperature_2m_max").getDouble(i);

            forecast.add(new WeatherForecast(date,temp));

        }

        return forecast;

    }

    public String compareWeather(String lat1,String lat2,String long1,String long2) throws MalformedURLException, JSONException {

        List<WeatherForecast> city1=fetchWeather(lat1,long1);

        List<WeatherForecast> city2=fetchWeather(lat2,long2);

        StringBuilder comparison=new StringBuilder(":::Weather Comparison:::");

        for (int i=0;i<7;i++){

            comparison.append(city1.get(i).getDate()).append("=>")

                    .append("City 1 Temp: ").append(city1.get(i).getTemperature()).append("°C | ")

                    .append("City 2 Temp: ").append(city2.get(i).getTemperature()).append("°C\n");

        }

        return comparison.toString();

    }
}

