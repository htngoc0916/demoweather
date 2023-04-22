package com.example.weather.mvc.dto;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

@SuppressWarnings("serial")
@JsonRootName("resultInfo")
@SuperBuilder
@NoArgsConstructor
@Data
@Slf4j
public class ResultDTO<T> implements Serializable {

    protected ResultHeaderDTO header;
    protected T body;

    public ResultDTO(ResultHeaderDTO header, T body) {
        this.header = header;
        this.body = body;
        setHeaderBodyType(header, body);
    }

    protected void setHeaderBodyType(ResultHeaderDTO header, T body) {
        header.setBodyType(body.getClass().getSimpleName());
    }

    public ResultDTO(HttpStatus status, T body) {
        this(status, body, null);
    }

    public ResultDTO(HttpStatus status, T body, String message) {
        header = ResultHeaderDTO.create(status, message);
        this.body = body;
        setHeaderBodyType(header, body);
    }

    // 성공: body 세팅
    public ResultDTO(T successBody) {
        this(successBody, null);
    }

    public ResultDTO(T successBody, String message) {
        header = ResultHeaderDTO.success(message);
        this.body = successBody;
        setHeaderBodyType(header, body);
    }

    // 실패: 헤더만 세팅
    public ResultDTO(int resultCode, String failMessage) {
        header = ResultHeaderDTO.fail(resultCode, failMessage);
        setHeaderBodyType(header, body);
    }

}

