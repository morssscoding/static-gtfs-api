package com.morssscoding.transit.staticgtfs.dataproviders.trip;

import com.morssscoding.transit.staticgtfs.dataproviders.jpa.entity.StopTimeEntity;
import com.morssscoding.transit.staticgtfs.dataproviders.jpa.repository.StopTimeJpaRepository;
import com.morssscoding.transit.staticgtfs.integration.AbstractDatabaseIntegrationTest;
import com.morssscoding.transit.staticgtfs.utils.TestDataProvider;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static com.morssscoding.transit.staticgtfs.utils.TestDataProvider.buildStopTimeEntity;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class StopTimeJpaRepositoryTest extends AbstractDatabaseIntegrationTest {

  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private StopTimeJpaRepository stopTimeJpaRepository;

  @BeforeEach
  void setup(){
    entityManager.clear();
    entityManager.persistAndFlush(TestDataProvider.buildAgencyEntity());
    entityManager.persistAndFlush(TestDataProvider.buildRouteEntity());
    entityManager.persistAndFlush(TestDataProvider.buildCalendarEntity());
    entityManager.persistAndFlush(TestDataProvider.buildTripEntity());
    entityManager.persistAndFlush(TestDataProvider.buildStopEntity());
  }

  @AfterEach
  void cleanup() {
    entityManager.clear();
  }

  @Test
  void testFindById() {
    StopTimeEntity expected = buildStopTimeEntity();
    givenExistingTrip(expected);

    List<StopTimeEntity> stopTimes = stopTimeJpaRepository.findAllByTripId("1");

    assertThat(stopTimes).hasSize(1);
    assertThat(stopTimes).contains(expected);
  }

  private void givenExistingTrip(StopTimeEntity stopTimeEntity) {
    entityManager.persist(stopTimeEntity);
  }

}
