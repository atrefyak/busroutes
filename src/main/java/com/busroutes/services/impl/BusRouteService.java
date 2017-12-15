package com.busroutes.services.impl;

import com.busroutes.entities.Station;
import com.busroutes.loaders.RoutesFileLoader;
import com.busroutes.services.RouteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class BusRouteService implements RouteService {

    @Autowired
    private RoutesFileLoader routesFileLoader;

    @Override
    public boolean isDirectRoutePresent(final int depId, final int arrId) {
        return haveCommonBus(depId, arrId);
    }

    protected boolean haveCommonBus(final int depId, final int arrId) {
        final Station depStation = routesFileLoader.getStations().get(depId);
        if (depStation == null)
            return false;
        final Station arrStation = routesFileLoader.getStations().get(arrId);
        return arrStation != null && depStation.getBuses().stream().anyMatch(arrStation.getBuses()::contains);
    }

}
