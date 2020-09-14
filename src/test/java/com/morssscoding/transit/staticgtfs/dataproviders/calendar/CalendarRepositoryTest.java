package com.morssscoding.transit.staticgtfs.dataproviders.calendar;

import com.morssscoding.transit.staticgtfs.core.calendar.Calendar;
import com.morssscoding.transit.staticgtfs.core.exception.DataNotFoundException;
import com.morssscoding.transit.staticgtfs.dataproviders.jpa.repository.CalendarJpaRepository;
import com.morssscoding.transit.staticgtfs.dataproviders.repository.calendar.CalendarEntityMapper;
import com.morssscoding.transit.staticgtfs.dataproviders.repository.calendar.CalendarRepository;
import com.morssscoding.transit.staticgtfs.dataproviders.repository.calendar.CalendarRepositoryImpl;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import static com.morssscoding.transit.staticgtfs.utils.TestDataProvider.buildCalendarEntity;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@Import(CalendarRepositoryTest.TestConfig.class)
@RunWith(SpringRunner.class)
public class CalendarRepositoryTest {

    private CalendarRepository calendarRepository;

    @Mock
    private CalendarJpaRepository calendarJpaRepository;

    @Autowired
    private CalendarEntityMapper calendarEntityMapper;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setup(){
        calendarRepository = new CalendarRepositoryImpl(calendarJpaRepository, calendarEntityMapper);
    }

    @TestConfiguration
    static class TestConfig{
        @Bean
        public CalendarEntityMapper calendarEntityMapper(){
            return Mappers.getMapper(CalendarEntityMapper.class);
        }
    }

    @Test
    public void testIfCalendarEntityIsMappedProperly(){
        givenCalendar();

        Calendar calendar = calendarRepository.getCalendar("1");

        assertThat(calendar).isNotNull();
        assertThat(calendar.getServiceId()).isEqualTo("1");
        assertThat(calendar.getMonday()).isTrue();
        assertThat(calendar.getTuesday()).isTrue();
        assertThat(calendar.getWednesday()).isTrue();
        assertThat(calendar.getThursday()).isTrue();
        assertThat(calendar.getFriday()).isTrue();
        assertThat(calendar.getSaturday()).isFalse();
        assertThat(calendar.getSunday()).isFalse();
    }

    @Test
    public void testIfCalendarNotFound(){
        expectedException.expect(DataNotFoundException.class);
        expectedException.expectMessage(equalTo("Route not found"));

        whenCalendarNotFound();

        calendarRepository.getCalendar("1");
    }

    public void givenCalendar(){
        when(calendarJpaRepository.findByServiceId(anyString()))
                .thenReturn(buildCalendarEntity());
    }

    public void whenCalendarNotFound(){
        when(calendarJpaRepository.findByServiceId(anyString()))
                .thenThrow(new DataNotFoundException("Route not found"));
    }
}