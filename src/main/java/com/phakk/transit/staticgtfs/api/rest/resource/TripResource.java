package com.phakk.transit.staticgtfs.api.rest.resource;

import com.phakk.transit.staticgtfs.api.spec.ApiDocument;
import com.phakk.transit.staticgtfs.core.constants.DataTypes;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/trips")
public interface TripResource extends TypedResource {

    @GetMapping(value = "/{tripId}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ApiDocument> getTrip(@PathVariable(name = "tripId") String tripId);

    @GetMapping(value = "/{tripId}/stops", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ApiDocument> getStopTimes(@PathVariable(name = "tripId") String tripId);

    @Override
    default String getResourceType() {
        return DataTypes.TRIP.getValue();
    }
}
