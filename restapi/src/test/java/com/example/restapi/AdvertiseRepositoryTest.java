package com.example.restapi;

import com.example.restapi.model.Advertise;
import com.example.restapi.repository.AdvertiseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
public class AdvertiseRepositoryTest {

    @Autowired
    AdvertiseRepository advertiseRepository;

    Advertise advertise;

    @BeforeEach
    void setUp() {
        advertise = new Advertise();
        advertise.setId(1);
        advertise.setTitle("Test advertise");
        advertise.setDescription("We are going to test this advertise");
        advertise.setUserId(1);
        advertiseRepository.save(advertise);
    }

    @Test
    void checkUpdateQuery(){
        Advertise updatedAdvertise = new Advertise();
        updatedAdvertise.setId(1);
        updatedAdvertise.setTitle("updated advertise");
        updatedAdvertise.setDescription("updated advertise");
        updatedAdvertise.setUserId(1);

        advertiseRepository.save(updatedAdvertise);

        Advertise finalAdvertise = advertiseRepository.findById((long) advertise.getId()).get();

        assertThat(finalAdvertise).isEqualTo(updatedAdvertise);

    }

}
