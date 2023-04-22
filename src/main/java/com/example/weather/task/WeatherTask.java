package com.example.weather.task;

import com.example.weather.mvc.entity.Weather;
import com.example.weather.support.ArchiveTask;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.FileWriter;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class WeatherTask implements ArchiveTask {
    @Override
    public void runTask(){
        System.out.println("WeatherTask start...");
        getWeatherData();
    }

    private void getWeatherData(){
        try {
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            ResponseEntity<String> response = null;
            URI url = null;

            //get data
            HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
            url = URI.create("http://dailysportsseoul.kweather.co.kr/banner/banner_data.xml");
            response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

            //convert xml to json
            String xmlData = response.getBody();
            XmlMapper xmlMapper = new XmlMapper();
            JsonNode node = xmlMapper.readTree(xmlData.getBytes());
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode datasNode = node.get("datas");
            String json = objectMapper.writeValueAsString(datasNode);
            System.out.println(json);

            //json save file
            LocalDateTime currentDateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
            String currentDateTimeString = currentDateTime.format(formatter);
            String path = "D:/savejson/" + currentDateTimeString + ".json";
            //String path = "/Users/htngoc/Documents/HOC_TAP/java/savedjson/" + currentDateTimeString + ".json";
            try (FileWriter fileWriter = new FileWriter(path)) { // create a file writer
                fileWriter.write(json); // write the JSON string to the file
            }

            //post data
            HttpEntity<String> request = new HttpEntity<>(json, headers);
            restTemplate = new RestTemplate();
            url = URI.create("http://localhost:8080/api/weather/listAdd");
            response = restTemplate.postForEntity(url, request, String.class);
            System.out.println(response.getBody());
            System.out.println(response.getStatusCodeValue());

        }catch (Exception ex){
            System.out.println(ex.toString());
        }
    }

}
