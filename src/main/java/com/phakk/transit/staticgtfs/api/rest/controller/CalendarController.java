package com.phakk.transit.staticgtfs.api.rest.controller;

import com.phakk.transit.staticgtfs.api.rest.dto.CalendarDto;
import com.phakk.transit.staticgtfs.api.rest.mapper.CalendarDtoMapper;
import com.phakk.transit.staticgtfs.api.rest.resource.CalendarResource;
import com.phakk.transit.staticgtfs.api.spec.ApiDocument;
import com.phakk.transit.staticgtfs.api.spec.ApiResource;
import com.phakk.transit.staticgtfs.core.calendar.CalendarService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
public class CalendarController implements CalendarResource {

    private CalendarService calendarService;
    private CalendarDtoMapper calendarDtoMapper;

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
