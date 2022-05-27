package com.beenia.weather.beans;

import java.util.Date;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class MeteoblueResponse {
    @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
    @Data
    public class DataOfDay {
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        private Date[] time;
        private float[] temperatureMax;
        private float[] temperatureMin;
        private int[] winddirection;
        private float[] windspeedMax;
        private float[] windspeedMin;
        private float[] windspeedMean;
        private float[] precipitation;
        private float[] snowfraction;
    }

    @Data
    public class DataOf1H {
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm")
        private Date[] time;
        private float[] temperature;
        private int[] winddirection;
        private float[] windspeed;
        private float[] precipitation;
        private float[] snowfraction;
    }

    private Map<?, ?> metadata;
    private Map<?, ?> units;
    @JsonProperty("data_1h")
    private DataOf1H data1h;
}
