package com.morenomjc.transit.staticgtfs.core.route;

import com.morenomjc.transit.staticgtfs.dataproviders.repository.route.RouteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RouteServiceImpl implements RouteService {

    private final RouteRepository routeRepository;

    @Override
    public Route getRoute(String id) {
        Route route = routeRepository.getRouteById(id);
        log.info("Found route with id: [{}]", route.getId());
        return route;
    }

    @Override
    public List<Route> getByAgency(String agency) {
        List<Route> routes = routeRepository.getRoutesByAgency(agency);
        log.info("Found [{}] routes with agency: [{}]", routes.size(), agency);
        return routes;
    }

    @Override
    public List<Route> getByRouteType(String routeType) {
        List<Route> routes = routeRepository.getRoutesByType(routeType);
        log.info("Found [{}] routes with route_type: [{}]", routes.size(), routeType);
        return routes;
    }

    @Override
    public List<RouteType> getRouteTypes() {
        return routeRepository.getRouteTypes();
    }
}
