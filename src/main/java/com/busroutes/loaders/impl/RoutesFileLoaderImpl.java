package com.busroutes.loaders.impl;


import com.busroutes.populators.StationPopulator;
import com.busroutes.entities.Station;
import com.busroutes.exceptions.RouteException;
import com.busroutes.loaders.RoutesFileLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.StringUtils;


public class RoutesFileLoaderImpl implements RoutesFileLoader {

    private static final String REGEX = " +";

    private static final int BUS_ROUTE_AMOUNT_INDEX = 0;
    private int numbersOfBusStations = 0;

    private int busRoutesLimit;
    private int busStationsLimit;
    private int busOneRouteStationsLimit;

    private Map<Integer, Station> stations;

    private StationPopulator stationPopulator;

    public RoutesFileLoaderImpl() {
        stations = new HashMap<>();
    }

    public void setBusRoutesLimit(final Integer busRoutesLimit) {
        this.busRoutesLimit = busRoutesLimit;
    }

    public void setBusStationsLimit(final Integer busStationsLimit) {
        this.busStationsLimit = busStationsLimit;
    }

    public void setBusOneRouteStationsLimit(final Integer busOneRouteStationsLimit) {
        this.busOneRouteStationsLimit = busOneRouteStationsLimit;
    }

    public void setStationPopulator(final StationPopulator stationPopulator) {
        this.stationPopulator = stationPopulator;
    }

    public Map<Integer, Station> getStations() {
        return stations;
    }

    public void loadRoutesFromFile(final String filePath) throws IOException {
        final List<String> lines = getFileData(filePath);

        try {
            validateLimitOfBusRoutes(Integer.parseInt(lines.remove(BUS_ROUTE_AMOUNT_INDEX)));
            lines.stream().filter(StringUtils::hasText).forEach(this::populateStations);
        }
        catch (NumberFormatException e) {
            throw new NumberFormatException("Invalid content in the file. Please check the file format.");
        }
    }


    protected List<String> getFileData(final String filePath) throws IOException {
        return Files.readAllLines(Paths.get(filePath));
    }

    private void populateStations(final String line) {
        final String[] routeInfo = line.split(REGEX);

        incrementTotalAmountOfStations(routeInfo.length - 1);
        stationPopulator.populate(stations, routeInfo);
    }

    protected void incrementTotalAmountOfStations(final int busRoutesAmount) {
        validateTotalAmountOfStationForBusRoute(busRoutesAmount);

        numbersOfBusStations += busRoutesAmount;

        validateNumbersOfStations();
    }

    protected void validateNumbersOfStations() {
        if (numbersOfBusStations > busStationsLimit) {
            throwRouteException("Invalid file. Upper limit of the bus stations is " + busStationsLimit);
        }
    }

    protected void validateLimitOfBusRoutes(final int numberOfBusRoutes) {
        if (numberOfBusRoutes > busRoutesLimit) {
            throwRouteException("Invalid file. Upper limit for the number of bus routes is " + busRoutesLimit);
        }
    }

    protected void validateTotalAmountOfStationForBusRoute(final int busRoutesAmount) {
        if (busRoutesAmount > busOneRouteStationsLimit) {
            throwRouteException(
                    "Invalid file. Upper limit for the number of station of one bus route is " + busOneRouteStationsLimit);
        }
    }

    private void throwRouteException(final String message) {
        throw new RouteException(message);
    }
}
