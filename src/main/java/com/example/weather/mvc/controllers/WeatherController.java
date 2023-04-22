package com.example.weather.mvc.controllers;

import com.example.weather.mvc.dto.ResultListDTO;
import com.example.weather.mvc.dto.WeatherDTO;
import com.example.weather.mvc.dto.ResultDTO;
import com.example.weather.mvc.entity.Weather;
import com.example.weather.mvc.services.WeatherService;
import io.swagger.annotations.ApiOperation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/weather")
public class WeatherController {
    @Autowired
    WeatherService weatherService;

    @Autowired
    private ModelMapper modelMapper;

    @ApiOperation(value = "전체 조회")
    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> searchAllWeather(){
        try {
            List<Weather> weatherList = weatherService.findByAll();

            ResultDTO<ResultListDTO<WeatherDTO>> resultDTO = null;

            if(weatherList != null) {
                List<WeatherDTO> dtoList = modelMapper.map(weatherList,WeatherDTO.TYPE);

                ResultListDTO<WeatherDTO> resultListMessage = new ResultListDTO<>();
                resultListMessage.setTotalCnt(dtoList.size());
                resultListMessage.setList(dtoList);

                resultDTO = new ResultDTO<>(resultListMessage);
            }

            return new ResponseEntity<>(resultDTO, HttpStatus.OK);
        } catch (Exception ex) {
            ResultDTO<WeatherDTO> resultDTO = new ResultDTO<>(400, ex.toString());
            return new ResponseEntity<>(resultDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "페이지 목록")
    @GetMapping(value = "/items", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> searchPageWeather(@RequestParam("page") int page, @RequestParam("size") int size){
        try{
            List<Weather> weatherList = weatherService.getWeatherList(page, size);

            ResultDTO<ResultListDTO<WeatherDTO>> resultDTO = null;

            if(weatherList != null) {
                List<WeatherDTO> dtoList = modelMapper.map(weatherList,WeatherDTO.TYPE);

                ResultListDTO<WeatherDTO> resultListMessage = new ResultListDTO<>();
                resultListMessage.setTotalCnt(dtoList.size());
                resultListMessage.setList(dtoList);

                resultDTO = new ResultDTO<>(resultListMessage);
            }

            return new ResponseEntity<>(resultDTO, HttpStatus.OK);
        }
        catch (Exception ex){
            ResultDTO<WeatherDTO> resultDTO = new ResultDTO<>(400, ex.toString());
            return new ResponseEntity<>(resultDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation("추가")
    @PostMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> insertWeather(@Valid WeatherDTO weatherDTO){
        try {
            if(weatherDTO.getArea() == null || weatherDTO.getArea().isEmpty()){
                throw new Exception("지역 입력되지 않았음.");
            }

            Weather weather = modelMapper.map(weatherDTO, Weather.class);
            weatherService.insert(weather);

            ResultDTO<Boolean> resultDTO = new ResultDTO<>(true, "success");
            return new ResponseEntity<>(resultDTO, HttpStatus.OK);

        }catch (Exception ex){
            ResultDTO<Boolean> resultDTO = new ResultDTO<>(400, ex.toString());
            return new ResponseEntity<>(resultDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation("List 추가")
    @PostMapping(value = "/listAdd", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> insertListWeather(@RequestBody List<WeatherDTO> weatherDTOList){
        try {
            for(WeatherDTO weatherDTO : weatherDTOList){
                if(weatherDTO.getArea() == null || weatherDTO.getArea().isEmpty()){
                    throw new Exception("지역 입력되지 않았음.");
                }

                Weather weather = modelMapper.map(weatherDTO, Weather.class);
                weatherService.insert(weather);
            }
            ResultDTO<Boolean> resultDTO = new ResultDTO<>(true, "success");
            return new ResponseEntity<>(resultDTO, HttpStatus.OK);
        }catch (Exception ex){
            ResultDTO<Boolean> resultDTO = new ResultDTO<>(400, ex.toString());
            return new ResponseEntity<>(resultDTO, HttpStatus.BAD_REQUEST);
        }
    }
}
