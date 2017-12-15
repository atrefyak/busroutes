package com.busroutes.entities;

import java.util.HashSet;
import java.util.Set;


public class Station {

    private Set<Integer> buses;

    public Station() {
        buses = new HashSet<>();
    }

    public Set<Integer> getBuses() {
        return buses;
    }

    public void setBuses(final Set<Integer> buses) {
        this.buses = buses;
    }
}
