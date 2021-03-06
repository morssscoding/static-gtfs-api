package com.morenomjc.transit.staticgtfs.api.rest.controller;

import com.morenomjc.transit.staticgtfs.api.rest.resource.RouteResource;
import com.morenomjc.transit.staticgtfs.core.route.RouteService;
import com.morenomjc.transit.staticgtfs.api.rest.dto.RouteDto;
import com.morenomjc.transit.staticgtfs.api.rest.logging.RequestTraceHeaders;
import com.morenomjc.transit.staticgtfs.api.rest.mapper.RouteDtoMapper;
import com.morenomjc.transit.staticgtfs.api.spec.ApiData;
import com.morenomjc.transit.staticgtfs.api.spec.ApiDocument;
import com.morenomjc.transit.staticgtfs.api.spec.ApiError;
import com.morenomjc.transit.staticgtfs.api.spec.ApiResource;
import com.morenomjc.transit.staticgtfs.api.spec.ApiResources;
import com.morenomjc.transit.staticgtfs.api.spec.Error;
import com.morenomjc.transit.staticgtfs.core.route.RouteType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
public class RouteController implements RouteResource {

    private final RouteService routeService;
    private final RouteDtoMapper routeDtoMapper;

    @Override
    public ResponseEntity<ApiDocument> getAvailableRoutes() {
        List<?> data = routeService.getRouteTypes().stream()
                .map(routeType -> new ApiData<>(
                        RouteType.TYPE,
                        routeDtoMapper.mapToDto(routeType)
                )).collect(Collectors.toList());
        return ResponseEntity.ok(new ApiResources<>(data, data.size()));
    }

    @Override
    public ResponseEntity<ApiDocument> getRoute(String id) {
        return ResponseEntity.ok(
                new ApiResource<>(
                        getResourceType(),
                        routeDtoMapper.mapToDto(routeService.getRoute(id)),
                        selfLink(id, getClass())
                )
        );
    }

    @Override
    public ResponseEntity<ApiDocument> getRoutesByParams(String agencyId, String routeType) {
        if (validateAtLeastOneRequestParams(agencyId, routeType)) {
            List<ApiData<RouteDto>> data;
            if (!ObjectUtils.isEmpty(agencyId)) {
                data = toApiResources(routeService.getByAgency(agencyId).stream()
                        .map(routeDtoMapper::mapToDto).collect(Collectors.toList()));
            } else {
                data = toApiResources(routeService.getByRouteType(routeType).stream()
                        .map(routeDtoMapper::mapToDto).collect(Collectors.toList()));
            }
            return ResponseEntity.ok(new ApiResources<>(data, data.size()));
        } else {
            return buildInvalidRequestError();
        }
    }

    ResponseEntity<ApiDocument> buildInvalidRequestError() {
        return ResponseEntity.badRequest().body(new ApiError(
                new Error(
                        MDC.get(RequestTraceHeaders.HEADER_REQUEST_ID),
                        LocalDateTime.now(),
                        HttpStatus.BAD_REQUEST.value(),
                        "400",
                        "Invalid Request",
                        "At least 1 request param is required"
                )
        ));
    }

    boolean validateAtLeastOneRequestParams(String... params) {
        int validParam = (int) Arrays.stream(params).filter(s -> !ObjectUtils.isEmpty(s)).count();
        return validParam > 0;
    }

    private List<ApiData<RouteDto>> toApiResources(List<RouteDto> routes) {
        return routes.stream()
                .map(route ->
                        new ApiData<>(
                                getResourceType(),
                                route,
                                selfLink(route.getId(), getClass())
                        )
                ).collect(Collectors.toList());
    }
}
