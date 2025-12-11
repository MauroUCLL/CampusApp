package be.ucll.backend.campusapp;

import be.ucll.backend.campusapp.controller.CampusController;
import be.ucll.backend.campusapp.service.CampusService;
import be.ucll.backend.campusapp.exception.CampusException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CampusControllerTest {

    @Mock
    private CampusService campusService;

    @InjectMocks
    private CampusController campusController;

    @Test
    void getReservatiesByRoomId_shouldThrowCampusException_whenServiceThrowsCampusException() {

        String campusId = "campus-001";
        long roomId = 10L;

        String expectedMessage =
                "Lokaal with id 10 not found in campus campus-001";

        when(campusService.findReservatiesByRoomId(campusId, roomId))
                .thenThrow(new CampusException(expectedMessage));

        CampusException exception = assertThrows(
                CampusException.class,
                () -> campusController.getReservatiesByRoomId(campusId, roomId)
        );

        assertEquals(expectedMessage, exception.getMessage());

        verify(campusService, times(1)).findReservatiesByRoomId(campusId, roomId);
    }
}
