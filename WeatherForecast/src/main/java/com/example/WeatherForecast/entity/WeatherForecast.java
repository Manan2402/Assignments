package com.example.WeatherForecast.entity;

import java.time.LocalDate;

public class WeatherForecast {

    private LocalDate date;
    private double temperature;

    public WeatherForecast() {}

    public WeatherForecast(LocalDate date, double temperature) {
        this.date = date;
        this.temperature = temperature;
    }

    // Getters and setters
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public double getTemperature() { return temperature; }
    public void setTemperature(double temperature) { this.temperature = temperature; }


    }

