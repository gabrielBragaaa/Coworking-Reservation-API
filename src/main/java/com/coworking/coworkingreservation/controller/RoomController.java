package com.coworking.coworkingreservation.controller;

import com.coworking.coworkingreservation.dto.RoomRequest;
import com.coworking.coworkingreservation.dto.RoomResponse;
import com.coworking.coworkingreservation.service.RoomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rooms")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RoomResponse createRoom(@RequestBody @Valid RoomRequest request) {
        return roomService.createRoom(request);
    }

    @GetMapping
    public List<RoomResponse> findAllRooms() {
        return roomService.findAllRooms();
    }

    @GetMapping("/{id}")
    public RoomResponse findByRoomId(@PathVariable Long id){
        return roomService.findRoomById(id);

    }
}
