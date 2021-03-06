package com.morenomjc.transit.staticgtfs.dataproviders.shape;

import com.morenomjc.transit.staticgtfs.core.shape.Shape;
import com.morenomjc.transit.staticgtfs.dataproviders.jpa.entity.ShapeEntity;
import com.morenomjc.transit.staticgtfs.dataproviders.jpa.repository.ShapeJpaRepository;
import com.morenomjc.transit.staticgtfs.dataproviders.repository.shape.ShapeEntityMapper;
import com.morenomjc.transit.staticgtfs.dataproviders.repository.shape.ShapeEntityMapperImpl;
import com.morenomjc.transit.staticgtfs.dataproviders.repository.shape.ShapeRepository;
import com.morenomjc.transit.staticgtfs.dataproviders.repository.shape.ShapeRepositoryImpl;
import com.morenomjc.transit.staticgtfs.utils.TestDataProvider;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Import(ShapeEntityMapperImpl.class)
class ShapeRepositoryTest {

  private ShapeRepository shapeRepository;

  @Mock
  private ShapeJpaRepository shapeJpaRepository;

  @Autowired
  private ShapeEntityMapper shapeEntityMapper;


  @BeforeAll
  void setup() {
    shapeRepository = new ShapeRepositoryImpl(shapeJpaRepository, shapeEntityMapper);
  }

  @Test
  void testIfShapeIsConvertedProperly() {
    when(shapeJpaRepository.findByShapeId(anyString()))
        .thenReturn(Collections.singletonList(TestDataProvider.buildShapeEntity()));

    List<Shape> shapes = shapeRepository.getShapesById("1");

    assertThat(shapes).hasSize(1);
    assertThat(shapes.get(0).getId()).isEqualTo("1");
    assertThat(shapes.get(0).getLat()).isEqualTo(12.4);
    assertThat(shapes.get(0).getLon()).isEqualTo(12.5);
    assertThat(shapes.get(0).getSequence()).isEqualTo(1);
    assertThat(shapes.get(0).getDistanceTraveled()).isEqualTo(0.1);
  }

  @Test
  void testIfShapeIsConvertedProperlyToEntity() {
    Shape shape = TestDataProvider.buildShape();
    ShapeEntity shapeEntity = shapeEntityMapper.toEntity(shape);

    assertThat(shapeEntity).isNotNull();
    assertThat(shapeEntity.getShapeId()).isNotNull();
    assertThat(shapeEntity.getLat()).isNotNull();
    assertThat(shapeEntity.getLon()).isNotNull();
    assertThat(shapeEntity.getSequence()).isNotNull();
    assertThat(shapeEntity.getDistanceTraveled()).isNotNull();
  }

}
