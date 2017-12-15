package com.busroutes.loaders;

import com.busroutes.entities.Station;

import java.io.IOException;
import java.util.Map;


public interface RoutesFileLoader {

    void loadRoutesFromFile(final String filePath) throws IOException;

    Map<Integer, Station> getStations();
}
