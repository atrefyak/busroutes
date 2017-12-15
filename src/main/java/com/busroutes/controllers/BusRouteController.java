package com.busroutes.controllers;

import com.busroutes.dto.RouteAnswer;
import com.busroutes.services.RouteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class BusRouteController {

    @Autowired
    private RouteService busRouteService;

    @GetMapping("/direct")
    public RouteAnswer getDirectRoutes(@RequestParam("dep_sid") Integer depSid, @RequestParam("arr_sid") Integer arrSid) {
        boolean isDirectRouteExists = busRouteService.isDirectRoutePresent(depSid, arrSid);
        return new RouteAnswer(depSid, arrSid, isDirectRouteExists);
    }

}
