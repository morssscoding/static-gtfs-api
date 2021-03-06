package com.morenomjc.transit.staticgtfs.dataproviders.repository.frequency;

import com.morenomjc.transit.staticgtfs.batch.model.GtfsFrequency;
import com.morenomjc.transit.staticgtfs.core.mapper.CommonCoreDataMapper;
import com.morenomjc.transit.staticgtfs.dataproviders.jpa.entity.FrequencyEntity;
import com.morenomjc.transit.staticgtfs.dataproviders.repository.enumvalue.EnumValueEntityMapper;
import com.morenomjc.transit.staticgtfs.core.frequency.Frequency;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", uses = { EnumValueEntityMapper.class, CommonCoreDataMapper.class }, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FrequencyEntityMapper {

    @Mapping(target = "headwaySecs", source = "headwaySecs", qualifiedByName = "mapToDuration")
    @Mapping(target = "exactTimes", ignore = true)
    Frequency fromEntity(FrequencyEntity frequencyEntity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "headwaySecs", source = "headwaySecs", qualifiedByName = "mapFromDuration")
    FrequencyEntity toEntity(Frequency frequency);

    @Mapping(target = "tripId", source = "trip_id")
    @Mapping(target = "startTime", source = "start_time", qualifiedByName = "mapToLocalTime")
    @Mapping(target = "endTime", source = "end_time", qualifiedByName = "mapToLocalTime")
    @Mapping(target = "headwaySecs", source = "headway_secs", qualifiedByName = "mapToDuration")
    @Mapping(target = "exactTimes", ignore = true)
    Frequency convert(GtfsFrequency gtfsFrequency);
}
