package com.phakk.transit.staticgtfs.api.rest.controller;

import com.phakk.transit.staticgtfs.api.rest.mapper.StopDtoMapper;
import com.phakk.transit.staticgtfs.api.rest.resource.StopResource;
import com.phakk.transit.staticgtfs.api.spec.ApiDocument;
import com.phakk.transit.staticgtfs.api.spec.ApiResource;
import com.phakk.transit.staticgtfs.core.stop.StopService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
public class StopController implements StopResource {

    private StopService stopService;
    private StopDtoMapper stopDtoMapper;

    @Override
    public ResponseEntity<ApiDocument> getStop(String id) {
        log.info("Action: getStop [{}]", id);
        return ResponseEntity.ok(
                new ApiResource<>(
                        getResourceType(),
                        stopDtoMapper.toDto(stopService.getStop(id)),
                        selfLink(id, getClass())
                )
        );
    }
}
