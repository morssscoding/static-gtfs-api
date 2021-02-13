package com.morssscoding.transit.staticgtfs.dataproviders.stop;

import com.morssscoding.transit.staticgtfs.dataproviders.jpa.entity.StopEntity;
import com.morssscoding.transit.staticgtfs.dataproviders.jpa.repository.StopJpaRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static com.morssscoding.transit.staticgtfs.utils.TestDataProvider.buildStopEntity;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class StopJpaRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private StopJpaRepository stopJpaRepository;

    @After
    public void cleanup(){
        entityManager.clear();
    }

    @Test
    public void testFindById(){
        StopEntity expected = buildStopEntity();
        givenExistingStop(expected);

        StopEntity stopEntity = stopJpaRepository.findByStopId("1");

        assertThat(stopEntity).isEqualTo(expected);
    }

    private void givenExistingStop(StopEntity stopEntity){
        entityManager.persist(stopEntity);
    }

}
