package com.example.WeatherForecast.controller;

import com.example.WeatherForecast.entity.WeatherForecast;
import com.example.WeatherForecast.service.WeatherService;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

@RestController

@RequestMapping("/weather")

public class WeatherController {

    @Autowired

    WeatherService weatherService;

    public WeatherController(WeatherService weatherService){

        this.weatherService=weatherService;

    }

    @GetMapping("/forecast")

    public List<WeatherForecast> getWeather(@RequestParam String latitude,@RequestParam String longitude) throws MalformedURLException, JSONException {

        return weatherService.fetchWeather(latitude,longitude);

    }

    @GetMapping("/compare")

    public String compareWeather(@RequestParam String lat1, @RequestParam String long1,

                                 @RequestParam String lat2, @RequestParam String long2) throws MalformedURLException, MalformedURLException, JSONException {

        return weatherService.compareWeather(lat1, long1, lat2, long2);

    }

}

