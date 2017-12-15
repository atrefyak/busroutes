package com.busroutes.dto;


import com.fasterxml.jackson.annotation.JsonProperty;


public class RouteAnswer {

    @JsonProperty("dep_sid")
    private int depSid;

    @JsonProperty("arr_sid")
    private int arrSid;

    @JsonProperty("direct_bus_route")
    private boolean directBusRoute;

    public RouteAnswer(int depSid, int arrSid, boolean directBusRoute) {
        this.depSid = depSid;
        this.arrSid = arrSid;
        this.directBusRoute = directBusRoute;
    }

    public boolean isDirectBusRoute() {
        return directBusRoute;
    }

    public void setDirectBusRoute(final boolean directBusRoute) {
        this.directBusRoute = directBusRoute;
    }


    public int getDepSid() {
        return depSid;
    }

    public void setDepSid(final int depSid) {
        this.depSid = depSid;
    }

    public int getArrSid() {
        return arrSid;
    }

    public void setArrSid(final int arrSid) {
        this.arrSid = arrSid;
    }
}
