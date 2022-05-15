package com.example.demo;

import com.example.demo.model.Administrator;
import com.example.demo.repository.AdminRepository;
import com.example.demo.service.AdminService;
import org.junit.Assert;
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

@RunWith(MockitoJUnitRunner.class)
public class AdministratorServiceTest {
    @InjectMocks
    private AdminService adminService;

    @Mock
    private AdminRepository adminRepository;

    private final List<Administrator> admins= new ArrayList<>();
    private Administrator a1 = new Administrator();
    private Administrator a2 = new Administrator();
    private Administrator a3 = new Administrator();

    @Before
    public void setup() {

        a1.setIdAdmin(1);
        a1.setUsername("admin1");

        String hashedPassword = BCrypt.hashpw("1234", BCrypt.gensalt());
        a1.setPassword(hashedPassword);


        a2.setIdAdmin(2);
        a2.setUsername("admin2");

        String hashedPassword2 = BCrypt.hashpw("1234", BCrypt.gensalt());
        a2.setPassword(hashedPassword2);

        a3.setIdAdmin(3);
        a3.setUsername("admin3");

        String hashedPassword3 = BCrypt.hashpw("1234", BCrypt.gensalt());
        a3.setPassword(hashedPassword3);

        admins.add(a1);
        admins.add(a2);
        admins.add(a3);


    }

    @Test
    public void getListOfAdminsTest() {

        Mockito.when(adminRepository.findAll()).thenReturn(admins);
        List<Administrator> foundAdmins= adminService.getListOfAdmins();
        assertNotNull(foundAdmins);
        assertEquals(3, foundAdmins.size());
    }

    @Test
    public void insertAdmin() {

        Administrator admin = new Administrator( "admin", "1234");

        String hashedPassword = BCrypt.hashpw(admin.getPassword(), BCrypt.gensalt());
        admin.setPassword(hashedPassword);

        assertTrue(adminService.insertAdmin(admin));
    }


    @Test
    public void checkIfUsernameExists(){
        Mockito.when(adminRepository.findAll()).thenReturn(admins);
        List<Administrator> allAdmins = adminService.getListOfAdmins();
        boolean found = false;
        for (Administrator admin:allAdmins){
            if (admin.getUsername().equals("admin1")){
                found = true;
                assertTrue(found);
                break;
            }
        }
    }

    @Test
    public void loginAdminTest() {
        Mockito.when(adminRepository.findByUsername(a1.getUsername())).thenReturn(Optional.ofNullable(a1));
        Optional<Administrator> found = adminService.findByUsername(a1.getUsername());


        assertEquals(a1.getUsername(), found.get().getUsername());
        assertEquals(a1.getPassword(), found.get().getPassword());
        assertNotNull(found);
    }


    @Test
    public void findByUsernameTest() {
        Mockito.when(adminRepository.findByUsername(a1.getUsername())).thenReturn(Optional.ofNullable(a1));
        Optional<Administrator> found = adminService.findByUsername(a1.getUsername());

        assertNotNull(found);
        assertEquals(a1.getUsername(), found.get().getUsername());
    }
    @Test
    public void findByIdTest() {
        Mockito.when(adminRepository.findByIdAdmin(a1.getIdAdmin())).thenReturn(Optional.ofNullable(a1));
        Optional<Administrator> found = adminService.findById(a1.getIdAdmin());

        assertNotNull(found);
        assertEquals(a1.getIdAdmin(), found.get().getIdAdmin());
    }
}

