package com.example.weather.mvc.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.google.common.reflect.TypeToken;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;

@SuppressWarnings("serial")
@JsonRootName("resultInfo")
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ResultListDTO<T> implements Serializable {

    private long totalCnt;
    private List<T> list;

    @JsonIgnore
    public Type getListType() {
        Type listType = new TypeToken<List<T>>() {}.getType();
        return listType;
    }
}
