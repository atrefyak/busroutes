package com.busroutes.config;

import com.busroutes.populators.StationPopulator;
import com.busroutes.loaders.impl.RoutesFileLoaderImpl;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class AppConfig {

    @Value("${filePath}")
    private String filePath;

    @Value("${bus.routes.limit}")
    private Integer busRoutesLimit;

    @Value("${bus.stations.limit}")
    private Integer busStationsLimit;

    @Value("${bus.one.route.stations.limit}")
    private Integer busOneRouteStationsLimit;

    @Autowired
    private StationPopulator stationPopulator;

    @Bean
    public RoutesFileLoaderImpl routesFileLoader() throws IOException {
        RoutesFileLoaderImpl routesFileLoader = new RoutesFileLoaderImpl();
        routesFileLoader.setStationPopulator(stationPopulator);
        routesFileLoader.setBusRoutesLimit(busRoutesLimit);
        routesFileLoader.setBusStationsLimit(busStationsLimit);
        routesFileLoader.setBusOneRouteStationsLimit(busOneRouteStationsLimit);

        routesFileLoader.loadRoutesFromFile(filePath);
        return routesFileLoader;
    }


}
