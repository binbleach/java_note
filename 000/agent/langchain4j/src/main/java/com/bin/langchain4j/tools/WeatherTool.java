package com.bin.langchain4j.tools;

import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;

public class WeatherTool {
    @Tool("Returns the weather forecast for a given city")
    public String getWeather(
            @P("The city for which the weather forecast should be returned") String city,
            @P("The date for which the weather forecast should be returned") String date
    ){
        System.out.println("WeatherTools==============="+city+date);
        return "雨天";
    }
}
