package com.busroutes.populators.impl;

import static org.junit.Assert.assertEquals;

import com.busroutes.entities.Station;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;


public class BustStationPopulatorTest {

    private static final String FIRST_STATION = "3";
    private static final String SECOND_STATION = "4";
    private static final String BUS_NUMBER = "143";
    private static final int EXPECTED_MAP_SIZE = 2;
	private static final int EXPECTED_BUS_AMOUNT = 1;

    private BustStationPopulator bustStationPopulator;

    @Before
    public void setUp() {
        bustStationPopulator = new BustStationPopulator();
    }

    @Test
    public void shouldPopulateStations() {
        Map<Integer, Station> stationMap = new HashMap<>();
        final String[] routeInfo = { BUS_NUMBER, FIRST_STATION, SECOND_STATION };

        bustStationPopulator.populate(stationMap, routeInfo);

        assertEquals(EXPECTED_MAP_SIZE, stationMap.size());

        assertEquals(EXPECTED_BUS_AMOUNT, stationMap.get(Integer.valueOf(FIRST_STATION)).getBuses().size());
        assertEquals(EXPECTED_BUS_AMOUNT, stationMap.get(Integer.valueOf(FIRST_STATION)).getBuses().size());

        assertEquals(Integer.valueOf(BUS_NUMBER), stationMap.get(Integer.valueOf(FIRST_STATION)).getBuses().iterator().next());
        assertEquals(Integer.valueOf(BUS_NUMBER), stationMap.get(Integer.valueOf(FIRST_STATION)).getBuses().iterator().next());
    }

}