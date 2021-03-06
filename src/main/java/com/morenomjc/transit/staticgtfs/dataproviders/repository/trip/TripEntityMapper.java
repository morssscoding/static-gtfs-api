package com.morenomjc.transit.staticgtfs.dataproviders.repository.trip;

import com.morenomjc.transit.staticgtfs.batch.model.GtfsTrip;
import com.morenomjc.transit.staticgtfs.core.trip.Trip;
import com.morenomjc.transit.staticgtfs.dataproviders.jpa.entity.TripEntity;
import com.morenomjc.transit.staticgtfs.dataproviders.repository.enumvalue.EnumValueEntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", uses = {EnumValueEntityMapper.class}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TripEntityMapper {

    @Mapping(target = "directionId", ignore = true)
    @Mapping(target = "wheelchairAccessible", ignore = true)
    @Mapping(target = "bikesAllowed", ignore = true)
    Trip fromEntity(TripEntity tripEntity);

    @Mapping(target = "id", ignore = true)
    TripEntity toEntity(Trip trip);

    @Mapping(target = "routeId", source = "route_id")
    @Mapping(target = "serviceId", source = "service_id")
    @Mapping(target = "tripId", source = "trip_id")
    @Mapping(target = "headsign", source = "trip_headsign")
    @Mapping(target = "shortName", source = "trip_short_name")
    @Mapping(target = "directionId", ignore = true)
    @Mapping(target = "blockId", source = "block_id")
    @Mapping(target = "shapeId", source = "shape_id")
    @Mapping(target = "wheelchairAccessible", ignore = true)
    @Mapping(target = "bikesAllowed", ignore = true)
    Trip convert(GtfsTrip gtfsTrip);
}
