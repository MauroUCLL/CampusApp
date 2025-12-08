package be.ucll.backend.campusapp;

import be.ucll.backend.campusapp.controller.UserController;
import be.ucll.backend.campusapp.model.Reservatie;
import be.ucll.backend.campusapp.model.User;
import be.ucll.backend.campusapp.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    private AutoCloseable mocks;

    @BeforeEach
    void setup() {
        mocks = MockitoAnnotations.openMocks(this);
        UserController userController = new UserController(userService);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @AfterEach
    void tearDown() throws Exception {
        mocks.close(); // close mocks to release resources
    }

    @Test
    void testAddReservatie() throws Exception {
        long userId = 1L;
        long reservatieId = 10L;

        User mockUser = new User();
        mockUser.setId(userId);

        when(userService.addReservatie(userId, reservatieId)).thenReturn(mockUser);

        // Correct path: /user/{userId}/reservaties/{reservatieId}
        mockMvc.perform(put("/user/{userId}/reservaties/{reservatieId}", userId, reservatieId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userId));

        verify(userService).addReservatie(userId, reservatieId);
    }

    @Test
    void testGetReservatiesById() throws Exception {
        long userId = 1L;
        long reservatieId = 10L;

        Reservatie r = new Reservatie();
        r.setId(reservatieId);

        when(userService.getReservatiesForUserById(userId, reservatieId))
                .thenReturn(List.of(r));

        mockMvc.perform(get("/user/{userId}/reservaties/{reservatieId}", userId, reservatieId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(reservatieId));

        verify(userService).getReservatiesForUserById(userId, reservatieId);
    }
}
