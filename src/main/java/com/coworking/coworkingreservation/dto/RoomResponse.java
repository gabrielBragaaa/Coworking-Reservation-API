package com.coworking.coworkingreservation.dto;

import com.coworking.coworkingreservation.entity.RoomType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RoomResponse {

    private Long id;
    private String name;
    private RoomType type;
    private Integer capacity;
}
