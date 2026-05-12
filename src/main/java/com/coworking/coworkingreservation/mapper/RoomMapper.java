package com.coworking.coworkingreservation.mapper;

import com.coworking.coworkingreservation.dto.RoomRequest;
import com.coworking.coworkingreservation.dto.RoomResponse;
import com.coworking.coworkingreservation.entity.Room;
import com.coworking.coworkingreservation.entity.RoomType;

public class RoomMapper {

    public static Room toEntity(RoomRequest request){
        return Room.builder()
                .name(request.getName())
                .type(request.getType())
                .capacity(request.getCapacity())
                .build();
    }

    public static RoomResponse toResponse(Room room){

        return RoomResponse.builder()
                .id(room.getId())
                .name(room.getName())
                .type(room.getType())
                .capacity(room.getCapacity())
                .build();

    }

}
