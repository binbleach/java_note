package com.huangjiabin.webservice.impl;

import com.huangjiabin.webservice.WebserviceCall;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class WeatherCall extends WebserviceCall {
    final String WSDL_URL ="http://127.0.0.1:12345/weather?wsdl";
    @Override
    public String invoke(String methodName, Object... param) {
        String result = "";
        try {
            result = cxfClient.invoke(WSDL_URL, methodName, (String) param[0], "", null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
