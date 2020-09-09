package com.morssscoding.transit.staticgtfs.api.rest.mapper;

import com.morssscoding.transit.staticgtfs.api.rest.dto.TripDto;
import com.morssscoding.transit.staticgtfs.core.trip.Trip;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = { CommonDtoMapper.class })
public interface TripDtoMapper {

    @Mapping(target = "directionId", source = "directionId", qualifiedByName = "mapToDataType")
    @Mapping(target = "wheelchairAccessible", source = "wheelchairAccessible", qualifiedByName = "mapToDataType")
    @Mapping(target = "bikesAllowed", source = "bikesAllowed", qualifiedByName = "mapToDataType")
    TripDto toDto(Trip trip);
}
