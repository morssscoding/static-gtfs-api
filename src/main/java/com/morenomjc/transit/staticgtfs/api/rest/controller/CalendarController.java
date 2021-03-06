package com.morenomjc.transit.staticgtfs.api.rest.controller;

import com.morenomjc.transit.staticgtfs.api.rest.dto.CalendarDto;
import com.morenomjc.transit.staticgtfs.api.rest.mapper.CalendarDtoMapper;
import com.morenomjc.transit.staticgtfs.api.rest.resource.CalendarResource;
import com.morenomjc.transit.staticgtfs.api.spec.ApiDocument;
import com.morenomjc.transit.staticgtfs.api.spec.ApiResource;
import com.morenomjc.transit.staticgtfs.core.calendar.CalendarService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CalendarController implements CalendarResource {

    private final CalendarService calendarService;
    private final CalendarDtoMapper calendarDtoMapper;

    @Override
    public ResponseEntity<ApiDocument> getCalendar(String serviceId) {
        log.info("Action: getCalendar");
        CalendarDto calendarDto = calendarDtoMapper.toDto(calendarService.getCalendar(serviceId));
        ApiResource<?> apiResource = new ApiResource<>(
                getResourceType(),
                calendarDto,
                selfLink(calendarDto.getServiceId(), getClass())
        );
        return ResponseEntity.ok(apiResource);
    }
}
