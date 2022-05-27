package com.beenia.weather.config;

import java.util.Optional;

import com.beenia.weather.beans.MeteoblueResponse;
import com.beenia.weather.beans.Weather;
import com.beenia.weather.beans.MeteoblueResponse.DataOf1H;
import com.beenia.weather.repositories.WeatherRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableScheduling
public class Config extends WebSecurityConfigurerAdapter {
    @Autowired
    private WeatherRepository weatherDao;
    private final RestTemplate restTemplate;
    private final String urlMeteoblue = "https://my.meteoblue.com/packages/basic-1h?apikey={apiKey}&lat={latitude}&lon={longitude}&asl={height}&windspeed=ms-1&format=json";
    private final String API_KEY = "kCzdJRFvp7yXA1n4";
    private final double LATITUDE = 44.8125;
    private final double LONGITUDE = 20.4612;

    @Autowired
    public Config(RestTemplateBuilder restTemplateBuilder) {
        restTemplate = restTemplateBuilder.build();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .anonymous().authorities("ROLE_ANONYMOUS")
                .and()
                .authorizeRequests()
                .antMatchers("/**").permitAll();
    }

    @Scheduled(fixedDelay = 180000)
    public void scrapeMeteoblue() {
        MeteoblueResponse result = restTemplate.getForObject(urlMeteoblue, MeteoblueResponse.class, API_KEY, LATITUDE,
                LONGITUDE,
                322);
        DataOf1H data = result.getData1h();
        for (int i = 0; i < data.getTime().length; i++) {
            Weather weather = new Weather();
            weather.setTime(data.getTime()[i]);
            weather.setTemperature(data.getTemperature()[i]);
            // weather.setTemperatureMin(data.getTemperatureMin()[i]);
            // weather.setWindSpeedMax(data.getWindspeedMax()[i]);
            // weather.setWindSpeedMin(data.getWindspeedMin()[i]);
            weather.setWindDirection(data.getWinddirection()[i]);
            weather.setWindSpeed(data.getWindspeed()[i]);
            weather.setPrecipitation(data.getPrecipitation()[i]);
            weather.setSnowfraction(data.getSnowfraction()[i]);
            Optional<Weather> old = weatherDao.findByTime(weather.getTime());
            if (old.isPresent()) {
                weather.setId(old.get().getId());
            }
            weatherDao.save(weather);
        }
    }
}
