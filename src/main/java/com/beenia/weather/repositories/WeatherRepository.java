package com.beenia.weather.repositories;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.beenia.weather.beans.Weather;

import org.springframework.data.repository.CrudRepository;

public interface WeatherRepository extends CrudRepository<Weather, Long> {
    Optional<Weather> findByTime(Date time);

    List<Weather> findAllByTimeBetween(Date start, Date end);
}