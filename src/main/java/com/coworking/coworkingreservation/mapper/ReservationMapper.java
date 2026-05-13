package com.coworking.coworkingreservation.mapper;

import com.coworking.coworkingreservation.dto.ReservationRequest;
import com.coworking.coworkingreservation.dto.ReservationResponse;
import com.coworking.coworkingreservation.entity.Reservation;
import com.coworking.coworkingreservation.entity.Room;

public class ReservationMapper {

    public static Reservation toEntity(ReservationRequest request, Room room){
        return Reservation.builder()
                .responsibleName(request.getResponsibleName())
                .date(request.getDate())
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .room(room)
                .build();
    }

    public static ReservationResponse toResponse(Reservation reservation){
        return ReservationResponse.builder()
                .id(reservation.getId())
                .responsibleName(reservation.getResponsibleName())
                .date(reservation.getDate())
                .startTime(reservation.getStartTime())
                .endTime(reservation.getEndTime())
                .roomName(reservation.getRoom().getName())
                .build();
    }
}
