package com.pfc2.weather.pruebatecnica.repository;

import com.pfc2.weather.pruebatecnica.model.WeatherHistory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.Date;

@Repository
public interface WeatherHistoryRepository extends ReactiveCrudRepository<WeatherHistory, Long> {

    @Transactional(readOnly = true, isolation = Isolation.READ_UNCOMMITTED)
    @Query("SELECT wh FROM WeatherHistory wh WHERE wh.lat = :lat AND wh.lon = :lon " +
            "AND wh.created >= :tenMinutesAgo")
    Mono<WeatherHistory> findByLatAndLon(@Param("lat") Double lat, @Param("lon") Double lon,
                                         @Param("tenMinutesAgo") Date tenMinutesAgo);
}
