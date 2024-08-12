package com.example.restapi;

import com.example.restapi.model.Advertise;
import com.example.restapi.repository.AdvertiseRepository;
import com.example.restapi.repository.UserRepository;
import com.example.restapi.service.AdvertiseService;
import com.example.restapi.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
public class AdvertiseServiceTest {

    @Mock
    AdvertiseRepository advertiseRepository;

    @InjectMocks
    AdvertiseService advertiseService;

    Advertise advertise;


    @BeforeEach
    void setUp() {
        advertise = new Advertise();
        advertise.setId(1);
        advertise.setTitle("Test advertise");
        advertise.setDescription("We are going to test this advertise");
        advertise.setUserId(1);
    }

    @Test
    void checkGetAdvertises(){

        ArrayList<Advertise> advertises = new ArrayList<>();
        advertises.add(advertise);

        when(advertiseRepository.findAll()).thenReturn(advertises);

        ArrayList<Advertise> takingAdvertises = advertiseService.getAdvertises(advertise.getUserId());

        assertThat(takingAdvertises).isEqualTo(advertises);

    }

    @Test
    void checkAddAdvertises(){

        ArrayList<Advertise> advertises = new ArrayList<>();

        when(advertiseRepository.findAll()).thenReturn(advertises);

        String answer = advertiseService.addAdvertise(advertise);

        verify(advertiseRepository).save(advertise);
        assertThat(answer).isEqualTo("Advertise added");

    }

    @Test
    void checkDeleteAdvertises(){

        ArrayList<Advertise> advertises = new ArrayList<>();
        advertises.add(advertise);

        String answer = advertiseService.deleteAdvertise(advertise.getId());

        verify(advertiseRepository).deleteById((long) advertise.getUserId());
        assertThat(answer).isEqualTo("Advertise deleted");

    }

    @Test
    void checkUpdateAdvertises(){

        ArrayList<Advertise> advertises = new ArrayList<>();
        advertises.add(advertise);

        Advertise newAdvertise = new Advertise();

        Advertise updatedAdvertise = new Advertise();
        updatedAdvertise.setId(1);
        updatedAdvertise.setTitle("updated");
        updatedAdvertise.setDescription("updated too");
        updatedAdvertise.setUserId(1);


        when(advertiseRepository.findById((long) advertise.getId())).thenReturn(Optional.of(advertise));

        String answer = advertiseService.updateAdvertise(advertise.getId(),updatedAdvertise);

        verify(advertiseRepository).save(updatedAdvertise);
        assertThat(answer).isEqualTo("Advertise updated");

    }

    @Test
    void checkGetAdvertiseById(){
        ArrayList<Advertise> advertises = new ArrayList<>();
        advertises.add(advertise);

        when(advertiseRepository.findById((long) advertise.getId())).thenReturn(Optional.of(advertise));

        Advertise answer = advertiseService.getAdvertise(advertise.getId());


        assertThat(answer).isEqualTo(advertise);

    }


}
