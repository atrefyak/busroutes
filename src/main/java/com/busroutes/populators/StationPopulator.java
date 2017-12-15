package com.busroutes.populators;


import java.util.Map;

import com.busroutes.entities.Station;


public interface StationPopulator {

	void populate(Map<Integer, Station> map, String[] routeInfo);
}
