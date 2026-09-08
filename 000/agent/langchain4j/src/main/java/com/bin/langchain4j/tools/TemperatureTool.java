package com.bin.langchain4j.tools;

import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;

public class TemperatureTool {

    @Tool("通过城市和日期获取温度")
    public String getTemperature(@P("城市") String city, @P("日期") String date){
        System.out.println("=======================城市:："+city+"，日期"+date);
        return "24摄氏度";
    }

}
