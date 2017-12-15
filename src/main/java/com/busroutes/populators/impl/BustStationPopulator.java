package com.busroutes.populators.impl;

import com.busroutes.populators.StationPopulator;
import com.busroutes.entities.Station;

import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class BustStationPopulator implements StationPopulator {

	private static final int ROUTE_NUMBER_INDEX = 0;

	public void populate(final Map<Integer, Station> stations, final String[] routeInfo) {
		final Integer busRouteNumber = getBusRouteNumber(routeInfo);

		for (int i = 1; i < routeInfo.length; i++) {
			final Integer stationNumber = Integer.valueOf(routeInfo[i]);
			Station station = stations.computeIfAbsent(stationNumber, k -> new Station());
			station.getBuses().add(busRouteNumber);
		}
	}

	private Integer getBusRouteNumber(String[] routeInfo) {
		return Integer.valueOf(routeInfo[ROUTE_NUMBER_INDEX]);
	}

}
