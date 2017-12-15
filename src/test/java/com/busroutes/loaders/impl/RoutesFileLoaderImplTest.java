package com.busroutes.loaders.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.busroutes.populators.StationPopulator;
import com.busroutes.exceptions.RouteException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class RoutesFileLoaderImplTest {

    private static final String FILE_PATH = "filePath";
    private static final int BUS_ROUTES_LIMIT = 5;
    private static final int BUS_STATIONS_LIMIT = 30;
    private static final int BUS_ONE_ROUTE_STATIONS_LIMIT = 6;

    @Mock
    private StationPopulator stationPopulator;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private RoutesFileLoaderImpl routes;

    @Before
    public void setUp() {
        routes = spy(new RoutesFileLoaderImpl());
        routes.setStationPopulator(stationPopulator);
        doNothing().when(stationPopulator).populate(any(), any());
    }

    @Test
    public void shouldLoadRoutes() throws IOException {
        routes.setBusRoutesLimit(BUS_ROUTES_LIMIT);
        routes.setBusStationsLimit(BUS_STATIONS_LIMIT);
        routes.setBusOneRouteStationsLimit(BUS_ONE_ROUTE_STATIONS_LIMIT);
        doReturn(generateData(5, 6)).when(routes).getFileData(FILE_PATH);

        routes.loadRoutesFromFile(FILE_PATH);
        verify(stationPopulator, times(5)).populate(any(), any());
    }

    @Test
    public void shouldReturnEmptyMap() {
        assertTrue(routes.getStations().isEmpty());
    }

    @Test
    public void shouldThrowRouteExceptionWhenNumberOfBusRoutesIsTooBig() throws IOException {
        routes.setBusRoutesLimit(BUS_ROUTES_LIMIT);
        routes.setBusStationsLimit(BUS_STATIONS_LIMIT);
        routes.setBusOneRouteStationsLimit(BUS_ONE_ROUTE_STATIONS_LIMIT);
        doReturn(generateData(6, 6)).when(routes).getFileData(FILE_PATH);

        expectedException.expect(RouteException.class);
        expectedException.expectMessage("Invalid file. Upper limit for the number of bus routes is " + BUS_ROUTES_LIMIT);

        routes.loadRoutesFromFile(FILE_PATH);

    }

    @Test
    public void shouldThrowRouteExceptionWhenNumberOfStationsIsTooBig() throws IOException {
        routes.setBusRoutesLimit(BUS_ROUTES_LIMIT);
        routes.setBusStationsLimit(BUS_STATIONS_LIMIT);
        routes.setBusOneRouteStationsLimit(7);
        doReturn(generateData(5, 7)).when(routes).getFileData(FILE_PATH);

        expectedException.expect(RouteException.class);
        expectedException.expectMessage("Invalid file. Upper limit of the bus stations is " + BUS_STATIONS_LIMIT);

        routes.loadRoutesFromFile(FILE_PATH);
    }

    @Test
    public void shouldThrowRouteExceptionWhenNumberOfBusRouteStationsIsTooBig() throws IOException {
        routes.setBusRoutesLimit(BUS_ROUTES_LIMIT);
        routes.setBusStationsLimit(BUS_STATIONS_LIMIT);
        routes.setBusOneRouteStationsLimit(BUS_ONE_ROUTE_STATIONS_LIMIT);
        doReturn(generateData(5, 7)).when(routes).getFileData(FILE_PATH);

        expectedException.expect(RouteException.class);
        expectedException.expectMessage(
                "Invalid file. Upper limit for the number of station of one bus route is " + BUS_ONE_ROUTE_STATIONS_LIMIT);

        routes.loadRoutesFromFile(FILE_PATH);
    }

    @Test
    public void shouldThrowNumberFormatExceptionWhenFileHasSymbols() throws IOException {
        final List<String> wrongData = new ArrayList<>();
        wrongData.add("a");
        doReturn(wrongData).when(routes).getFileData(FILE_PATH);

        expectedException.expect(NumberFormatException.class);
        expectedException.expectMessage("Invalid content in the file. Please check the file format.");

        routes.loadRoutesFromFile(FILE_PATH);
    }

    @Test
    public void shouldThrowIOExceptionWhenFileNotFound() throws IOException {
        expectedException.expect(IOException.class);

        routes.loadRoutesFromFile(FILE_PATH);
    }

    private List<String> generateData(int busRoutes, int busOneRouteStations) {
        final List<String> data = new ArrayList<>();
        data.add(Integer.toString(busRoutes));
        for (int i = 0; i < busRoutes; i++) {
            final StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(busRoutes - i);
            for (int j = 0; j < busOneRouteStations; j++) {
                stringBuilder.append(" ");
                stringBuilder.append(j + i);
            }
            data.add(stringBuilder.toString());
        }
        return data;
    }

}