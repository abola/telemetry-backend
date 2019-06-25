package com.systex.microservice.demo.telemetry;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

@RestController
@RequestMapping(path = "/")
public class BackendExample {

    Logger log = LogManager.getLogger(BackendExample.class);


    /**
     * 回傳品項ID、品項名稱與報價
     * @return json
     * @throws Exception
     */
    @GetMapping(path="/get/listQuote")
    public String getListQuote() throws Exception{
        return yamlToJson(
                readFromInputStream(getClass().getClassLoader().getResourceAsStream("listQuote.yaml")));
    }

    /**
     * 回傳品項庫存
     * @param item
     * @return json
     * @throws Exception
     */
    @GetMapping(path="/get/inStock/{item}")
    public String getInStock(@PathVariable("item") String item) throws Exception{
        // 隨機回傳
        return "{\"inStock\": "+ Double.toString(Math.random()*10).substring(0,1) +"}";
    }

    private String readFromInputStream(InputStream inputStream)
            throws Exception {
        StringBuilder resultStringBuilder = new StringBuilder();
        try (BufferedReader br
                     = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                resultStringBuilder.append(line).append("\n");
            }
        }
        return resultStringBuilder.toString();
    }

    private String yamlToJson(String yaml) throws Exception{
        ObjectMapper yamlReader = new ObjectMapper(new YAMLFactory());
        Object obj = yamlReader.readValue(yaml, Object.class);

        ObjectMapper jsonWriter = new ObjectMapper();
        return jsonWriter.writeValueAsString(obj);
    }
}
