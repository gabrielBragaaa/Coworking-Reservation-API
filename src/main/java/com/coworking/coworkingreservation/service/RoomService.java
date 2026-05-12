package com.coworking.coworkingreservation.service;

import com.coworking.coworkingreservation.dto.RoomRequest;
import com.coworking.coworkingreservation.dto.RoomResponse;
import com.coworking.coworkingreservation.entity.Room;
import com.coworking.coworkingreservation.mapper.RoomMapper;
import com.coworking.coworkingreservation.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;

    public RoomResponse createRoom(RoomRequest request) {

        Room room = RoomMapper.toEntity(request);
        Room savedRoom = roomRepository.save(room);

        return RoomMapper.toResponse(savedRoom);

    }
}
