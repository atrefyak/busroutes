package com.busroutes.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;

import com.busroutes.dto.RouteAnswer;
import com.busroutes.services.RouteService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class BusRouteControllerTest {

    private static final int DEP_ID = 23;
    private static final int ARR_ID = 44;

    @Mock
    private RouteService routeService;

    @InjectMocks
    private BusRouteController busRouteController;

    @Test
    public void shouldReturnDirectRoute() {
        given(routeService.isDirectRoutePresent(DEP_ID, ARR_ID)).willReturn(Boolean.TRUE);

        final RouteAnswer result = busRouteController.getDirectRoutes(DEP_ID, ARR_ID);

        assertEquals(DEP_ID, result.getDepSid());
        assertEquals(ARR_ID, result.getArrSid());
        assertTrue(result.isDirectBusRoute());
    }

    @Test
    public void shouldReturnFalseWhenNoDirectRoute() {
        given(routeService.isDirectRoutePresent(DEP_ID, ARR_ID)).willReturn(Boolean.FALSE);

        final RouteAnswer result = busRouteController.getDirectRoutes(DEP_ID, ARR_ID);

        assertEquals(DEP_ID, result.getDepSid());
        assertEquals(ARR_ID, result.getArrSid());
        assertFalse(result.isDirectBusRoute());
    }


}