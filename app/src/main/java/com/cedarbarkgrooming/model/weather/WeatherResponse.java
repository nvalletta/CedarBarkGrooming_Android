package com.cedarbarkgrooming.model.weather;

import java.util.List;

/**
 * Created by Nora on 5/16/2016.
 */
public class WeatherResponse {

    public Coord coord;
    public List<Weather> weather;
    public Main main;
    public Wind wind;
    public Clouds clouds;
    public Sys sys;

    public long dt;
    public String base;
    public int id;
    public String name;
    public int cod;

}
