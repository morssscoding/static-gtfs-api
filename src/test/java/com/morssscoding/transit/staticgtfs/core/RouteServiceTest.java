package com.morssscoding.transit.staticgtfs.core;

import com.morssscoding.transit.staticgtfs.utils.TestDataProvider;
import com.morssscoding.transit.staticgtfs.core.route.Route;
import com.morssscoding.transit.staticgtfs.core.route.RouteService;
import com.morssscoding.transit.staticgtfs.core.route.RouteServiceImpl;
import com.morssscoding.transit.staticgtfs.dataproviders.repository.route.RouteRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class RouteServiceTest {

    private RouteService routeService;

    @Mock
    private RouteRepository routeRepository;

    @Before
    public void setup(){
        routeService = new RouteServiceImpl(routeRepository);
    }

    @Test
    public void testGetRouteById(){
        Route expected = TestDataProvider.buildRoute();
        givenARoute(expected);

        Route result = routeService.getRoute("1");

        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void testGetRoutesByAgency(){
        Route expected = TestDataProvider.buildRoute();
        when(routeRepository.getRoutesByAgency(anyString())).thenReturn(Collections.singletonList(expected));

        List<Route> actual = routeService.getRoutesByAgency("test");

        assertThat(actual).isNotEmpty();
        assertThat(actual.get(0)).isEqualTo(expected);
    }

    private void givenARoute(Route route){
        when(routeRepository.getRouteById(anyString())).thenReturn(route);
    }

}