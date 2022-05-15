package com.example.demo;

import com.example.demo.model.Area;
import com.example.demo.model.TennisCourt;
import com.example.demo.model.TennisCourtManager;
import com.example.demo.repository.AreaRepository;
import com.example.demo.repository.ManagerRepository;
import com.example.demo.repository.TennisCourtRepository;
import com.example.demo.service.TennisCourtService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mindrot.jbcrypt.BCrypt;
import org.mockito.Incubating;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(MockitoJUnitRunner.Silent.class)
public class TennisCourtServiceTest {
    @Mock
    private AreaRepository areaRepository;
    @Mock
    private  ManagerRepository managerRepository;
    @Mock
    private TennisCourtRepository tennisCourtRepository;

    @InjectMocks
    private TennisCourtService tennisCourtService;

    private List<TennisCourt> tennisCourts= new ArrayList<>();
    private TennisCourt t1 = new TennisCourt();
    private TennisCourt t2 = new TennisCourt();
    private TennisCourt t3 = new TennisCourt();
    private Area area1 = new Area();
    private Area area2 = new Area();
    private List<Area> areas = new ArrayList<>();


    private TennisCourtManager m1 = new TennisCourtManager();
    private TennisCourtManager m2 = new TennisCourtManager();
    @Before
    public void setup() {
        area1.setId(1);
        area1.setName("GHEORGHENI");

        area2.setId(2);
        area2.setName("ZORILOR");
        areas.add(area1);
        areas.add(area2);

        m1.setIdManager(1);
        m1.setFirstName("Oana");
        m1.setLastName("Moisa");
        m1.setUsername("oanamanager");

        t1.setIdTennisCourt(1);
        t1.setName("TennisCourt1");
        t1.setLocation("Cluj");
        t1.setDescription("grass");
        t1.setPricePerHour(23);
        t1.setArea(area1);
        t1.setManager(m1);

        m2.setIdManager(2);
        m2.setFirstName("Ileana");
        m2.setLastName("Pop");
        m2.setUsername("ileanamanager");

        t2.setIdTennisCourt(2);
        t2.setName("TennisCourt2");
        t2.setLocation("Cluj");
        t2.setDescription("grass");
        t2.setPricePerHour(25);
        t2.setArea(area2);
        t2.setManager(m2);

        tennisCourts.add(t1);
        tennisCourts.add(t2);


    }

    @Test
    public void getListOfTennisCourtsTest() {
        Mockito.when(tennisCourtRepository.findAll()).thenReturn(tennisCourts);
        List<TennisCourt> foundTennisCourts = tennisCourtService.getListOfTennisCourts();
        assertNotNull(foundTennisCourts);
        assertEquals(2, foundTennisCourts.size());

    }

    @Test
    public void getListOfAreasTest() {
        Mockito.when(areaRepository.findAll()).thenReturn(areas);
        List<Area> foundAreas = tennisCourtService.getListOfAreas();
        assertNotNull(foundAreas);
        assertEquals(2, foundAreas.size());

    }

    @Test
    public void getTennisCourtForManagerTest(){
        Mockito.when(tennisCourtRepository.findTennisCourtByManager_Username("oanamanager")).thenReturn(Optional.of(t1));
        Optional<TennisCourt> found = tennisCourtService.getTennisCourtForManager("oanamanager");

        assertNotNull(found);
        assertEquals(t1.getName(), found.get().getName());
        assertEquals(t1.getIdTennisCourt(), found.get().getIdTennisCourt());
    }

}
