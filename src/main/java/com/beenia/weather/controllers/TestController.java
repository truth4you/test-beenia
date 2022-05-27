package com.beenia.weather.controllers;

import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import com.beenia.weather.beans.Weather;
import com.beenia.weather.repositories.WeatherRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/api")
public class TestController {
    @Autowired
    private WeatherRepository weatherDao;

    @GetMapping({ "data", "data/{date}", "data/{date}/{time}" })
    public Object data(@PathVariable(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date date,
            @PathVariable(required = false) Integer time) {
        try {
            if (date == null) {
                date = new Date();
            }
            Calendar c = Calendar.getInstance();
            if (time == null) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date startTime = sdf.parse(sdf.format(date));
                Date endTime = startTime;
                c.setTime(endTime);
                c.add(Calendar.DATE, 1);
                c.add(Calendar.SECOND, -1);
                endTime = c.getTime();
                List<Weather> rows = weatherDao.findAllByTimeBetween(startTime, endTime);
                double temperatureMax = -100;
                double temperatureMin = 100;
                double windSpeedMax = 0;
                double windSpeedMin = 100;
                double precipitation = 0;
                double snowfraction = 0;
                for (Weather weather : rows) {
                    if (temperatureMax < weather.getTemperature())
                        temperatureMax = weather.getTemperature();
                    if (temperatureMin > weather.getTemperature())
                        temperatureMin = weather.getTemperature();
                    if (windSpeedMax < weather.getWindSpeed())
                        windSpeedMax = weather.getWindSpeed();
                    if (windSpeedMin > weather.getWindSpeed())
                        windSpeedMin = weather.getWindSpeed();
                    precipitation += weather.getPrecipitation();
                    snowfraction += weather.getSnowfraction();
                }
                HashMap<String, Object> result = new HashMap<String, Object>();
                result.put("success", true);
                result.put("temperature_max", temperatureMax);
                result.put("temperature_min", temperatureMin);
                result.put("wind_speed_max", windSpeedMax);
                result.put("wind_speed_min", windSpeedMin);
                result.put("precipitation", precipitation);
                result.put("snowfraction", snowfraction);
                return result;
            } else {
                c.setTime(date);
                c.set(Calendar.HOUR, time);
                c.set(Calendar.MINUTE, 0);
                c.set(Calendar.SECOND, 0);
                Optional<Weather> opt = weatherDao.findByTime(c.getTime());
                if (opt.isPresent()) {
                    Weather weather = opt.get();
                    HashMap<String, Object> result = new HashMap<String, Object>();
                    result.put("success", true);
                    result.put("temperature", weather.getTemperature());
                    result.put("wind_direction", weather.getWindDirection());
                    result.put("wind_speed", weather.getWindSpeed());
                    result.put("precipitation", weather.getPrecipitation());
                    result.put("snowfraction", weather.getSnowfraction());
                    return result;
                }
            }
            return null;
        } catch (Exception e) {
            return new HashMap<String, Object>() {
                {
                    put("success", false);
                    put("message", e.getMessage());
                }
            };
        }
    }

    @GetMapping("shoes/{date}")
    public Object shoes(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) throws Exception {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date startTime = sdf.parse(sdf.format(date));
        Date endTime = startTime;
        c.setTime(endTime);
        c.add(Calendar.DATE, 1);
        c.add(Calendar.SECOND, -1);
        endTime = c.getTime();
        List<Weather> rows = weatherDao.findAllByTimeBetween(startTime, endTime);
        double temperatureMax = -100;
        double temperatureMin = 100;
        double windSpeedMax = 0;
        double windSpeedMin = 100;
        double precipitation = 0;
        // double snowfraction = 0;
        for (Weather weather : rows) {
            if (temperatureMax < weather.getTemperature())
                temperatureMax = weather.getTemperature();
            if (temperatureMin > weather.getTemperature())
                temperatureMin = weather.getTemperature();
            if (windSpeedMax < weather.getWindSpeed())
                windSpeedMax = weather.getWindSpeed();
            if (windSpeedMin > weather.getWindSpeed())
                windSpeedMin = weather.getWindSpeed();
            precipitation += weather.getPrecipitation();
            // snowfraction += weather.getSnowfraction();
        }
        String recomends = null;
        if (temperatureMax > 25)
            recomends = "Sandals";
        else if (temperatureMax < 5)
            recomends = "Winter shoes";
        else if (precipitation > 0)
            recomends = "Tall boots";
        else
            recomends = "Light boots";
        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put("success", true);
        result.put("recomends", recomends);
        return result;
    }

    @GetMapping("things/{date}")
    public Object things(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) throws Exception {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date startTime = sdf.parse(sdf.format(date));
        Date endTime = startTime;
        c.setTime(endTime);
        c.add(Calendar.DATE, 1);
        c.add(Calendar.SECOND, -1);
        endTime = c.getTime();
        List<Weather> rows = weatherDao.findAllByTimeBetween(startTime, endTime);
        double temperatureMax = -100;
        double temperatureMin = 100;
        double windSpeedMax = 0;
        double windSpeedMin = 100;
        double precipitation = 0;
        double snowfraction = 0;
        for (Weather weather : rows) {
            if (temperatureMax < weather.getTemperature())
                temperatureMax = weather.getTemperature();
            if (temperatureMin > weather.getTemperature())
                temperatureMin = weather.getTemperature();
            if (windSpeedMax < weather.getWindSpeed())
                windSpeedMax = weather.getWindSpeed();
            if (windSpeedMin > weather.getWindSpeed())
                windSpeedMin = weather.getWindSpeed();
            precipitation += weather.getPrecipitation();
            snowfraction += weather.getSnowfraction();
        }
        String recomends = null;
        if (snowfraction > 1)
            recomends = "Hat + gloves";
        else if (precipitation > 300)
            recomends = "Boar";
        else if (precipitation > 5)
            recomends = "Umbrella";
        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put("success", true);
        result.put("recomends", recomends);
        return result;
    }

    @GetMapping("wind/chill/{temperature}/{windSpeed}")
    public Object windChill(@PathVariable double temperature, @PathVariable double windSpeed) {
        final double wct = 13.12 + 0.6215 * temperature - 11.37 * Math.pow(windSpeed, 0.16)
                + 0.3965 * temperature * Math.pow(windSpeed, 0.16);
        return new HashMap<String, Object>() {
            {
                put("success", true);
                put("wind_chill", wct);
            }
        };
    }
}
