package com.morssscoding.transit.staticgtfs.api.rest;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.ResultMatcher.matchAll;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.morssscoding.transit.staticgtfs.api.rest.controller.ShapeController;
import com.morssscoding.transit.staticgtfs.api.rest.dto.ShapeDto;
import com.morssscoding.transit.staticgtfs.api.rest.mapper.ShapePointDtoMapper;
import com.morssscoding.transit.staticgtfs.core.shape.ShapeService;
import com.morssscoding.transit.staticgtfs.utils.JsonAssertionUtil;
import com.morssscoding.transit.staticgtfs.utils.TestDataProvider;
import java.util.Collections;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WithMockUser
@WebMvcTest(controllers = ShapeController.class)
@Import(ShapeControllerTest.TestConfig.class)
class ShapeControllerTest extends JsonAssertionUtil {

  @Autowired
  MockMvc mockMvc;

  @MockBean
  ShapeService shapeService;

  @Test
  void testGetShapeEndpoint() throws Exception {
    when(shapeService.getShapePoints(anyString()))
        .thenReturn(Collections.singletonList(TestDataProvider.buildShape()));

    this.mockMvc.perform(get("/shapes/1")
        .contentType(MediaType.APPLICATION_JSON)
    ).andExpect(
        matchAll(
            status().isOk(),
            jsonPath("$.data.attributes.shape_id").isString(),
            jsonPath("$.data.attributes.points").isArray(),
            jsonPath("$.data.type", is("shapes")),
            assertHasJsonFields("$.data.attributes", ShapeDto.class),
            assertHasJsonFields("$.data.attributes.points[0]", ShapeDto.PointDto.class),
            jsonPath("$.data.links[0].rel").value("self")
        )
    );
  }

  @TestConfiguration
  static class TestConfig {

    @Bean
    ShapePointDtoMapper shapeDtoMapper() {
      return Mappers.getMapper(ShapePointDtoMapper.class);
    }
  }
}
