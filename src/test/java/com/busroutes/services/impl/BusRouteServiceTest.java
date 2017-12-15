package com.busroutes.services.impl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;

import com.busroutes.entities.Station;
import com.busroutes.loaders.RoutesFileLoader;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class BusRouteServiceTest {

    private static final int GENERATED_DEP_ID = 33;
    private static final int GENERATED_ARR_ID = 44;
    private static final int BUS_ROUTE_NUMBER = 23;
    private static final int WRONG_STATION_ID = 1345;

    @Mock
    private RoutesFileLoader routesFileLoader;

    @InjectMocks
    private BusRouteService busRouteService;

    @Before
    public void setUp() {
        given(routesFileLoader.getStations()).willReturn(generateBusRoutes());
    }

    @Test
    public void shouldFindDirectRoute() {
        assertTrue(busRouteService.isDirectRoutePresent(GENERATED_DEP_ID, GENERATED_ARR_ID));
    }

    @Test
    public void shouldNotFindDirectRouteIfDepStationIsNotPresent() {
        assertFalse(busRouteService.isDirectRoutePresent(WRONG_STATION_ID, GENERATED_ARR_ID));
    }

    @Test
    public void shouldNotFindDirectRouteIfArrStationIsNotPresent() {
        assertFalse(busRouteService.isDirectRoutePresent(GENERATED_DEP_ID, WRONG_STATION_ID));
    }

    private Map<Integer, Station> generateBusRoutes() {
        Map<Integer, Station> stations = new HashMap<>();

        final Station firstStation = new Station();
        firstStation.getBuses().add(BUS_ROUTE_NUMBER);

        final Station secondStation = new Station();
        secondStation.getBuses().add(BUS_ROUTE_NUMBER);

        stations.put(GENERATED_DEP_ID, firstStation);
        stations.put(GENERATED_ARR_ID, secondStation);

        return stations;
    }


}