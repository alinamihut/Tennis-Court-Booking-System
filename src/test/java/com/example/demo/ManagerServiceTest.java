package com.example.demo;

import com.example.demo.model.Customer;
import com.example.demo.model.TennisCourt;
import com.example.demo.model.TennisCourtManager;
import com.example.demo.model.dto.CustomerDTO;
import com.example.demo.model.dto.TennisCourtManagerDTO;
import com.example.demo.model.mapper.ManagerMapper;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.ManagerRepository;
import com.example.demo.service.ManagerService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mindrot.jbcrypt.BCrypt;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class ManagerServiceTest {

    @InjectMocks
    private ManagerService managerService;

    @Mock
    private ManagerRepository managerRepository;

    private final List<TennisCourtManager> managers= new ArrayList<>();
    private TennisCourtManager m1 = new TennisCourtManager();
    private TennisCourtManager m2 = new TennisCourtManager();
    private TennisCourtManager m3 = new TennisCourtManager();

    @Before
    public void setup() {
        m1.setIdManager(1);
        m1.setFirstName("Oana");
        m1.setLastName("Moisa");
        m1.setUsername("oanamanager");

        String hashedPassword = BCrypt.hashpw("1234", BCrypt.gensalt());
        m1.setPassword(hashedPassword);


        m2.setIdManager(2);
        m2.setFirstName("Ileana");
        m2.setLastName("Pop");
        m2.setUsername("ileanamanager");

        String hashedPassword2 = BCrypt.hashpw("1234", BCrypt.gensalt());
        m2.setPassword(hashedPassword2);

        m3.setIdManager(2);
        m3.setFirstName("Alina");
        m3.setLastName("M");
        m3.setUsername("alinamanager");

        String hashedPassword3 = BCrypt.hashpw("1234", BCrypt.gensalt());
        m3.setPassword(hashedPassword3);

        managers.add(m1);
        managers.add(m2);
        managers.add(m3);


    }


    @Test
    public void ggetListOfTennisCourtManagersTest() {

        Mockito.when(managerRepository.findAll()).thenReturn(managers);
        List<TennisCourtManager> foundManagers= managerService.getListOfTennisCourtManagers();
        assertNotNull(foundManagers);
        assertEquals(3, foundManagers.size());
    }



    @Test
    public void findByUsernameTest() {
        Mockito.when(managerRepository.findManagerByUsername(m1.getUsername())).thenReturn(Optional.ofNullable(m1));
        Optional<TennisCourtManager> found = managerService.findByUsername(m1.getUsername());

        assertNotNull(found);
        assertEquals(m1.getUsername(), found.get().getUsername());
    }

    @Test
    public void findByIdTest() {
        Mockito.when(managerRepository.findManagerByIdManager(m1.getIdManager())).thenReturn(Optional.ofNullable(m1));
        Optional<TennisCourtManager> found = managerService.findById(m1.getIdManager());


        assertNotNull(found);
        assertEquals(m1.getIdManager(), found.get().getIdManager());
    }



    @Test
    public void loginCustomerFoundAndCorrectPasswordTest() {
        Mockito.when(managerRepository.findManagerByUsername(m1.getUsername())).thenReturn(Optional.ofNullable(m1));
        Optional<TennisCourtManager> found = managerService.findByUsername(m1.getUsername());


        assertEquals(m1.getUsername(), found.get().getUsername());
        assertEquals(m1.getPassword(), found.get().getPassword());
        assertNotNull(found);

    }

    @Test
    public void checkIfUsernameExistsTest(){
        Mockito.when(managerRepository.findAll()).thenReturn(managers);
        List<TennisCourtManager> allManagers = managerService.getListOfTennisCourtManagers();
        boolean found = false;
        for (TennisCourtManager manager: allManagers){
            if (manager.getUsername().equals("oanamanager")){
                found = true;
                assertTrue(found);
                break;
            }
        }

    }

    @Test
    public void insertManagerTest() {
        TennisCourtManagerDTO dto = new TennisCourtManagerDTO();
        dto.setFirstName("New");
        dto.setLastName("Manager");
        dto.setUsername("manager1");
        dto.setPassword("1234");
        ManagerMapper managerMapper = new ManagerMapper();
        TennisCourtManager manager = managerMapper.convertFromDTO(dto);

        String hashedPassword = BCrypt.hashpw(manager.getPassword(), BCrypt.gensalt());
        manager.setPassword(hashedPassword);

        assertTrue(managerService.insertManager(dto));

    }

}
