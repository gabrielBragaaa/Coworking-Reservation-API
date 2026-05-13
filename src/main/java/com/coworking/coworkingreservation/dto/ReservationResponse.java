package com.coworking.coworkingreservation.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Builder
public class ReservationResponse {

    private Long id;
    private String responsibleName;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private String roomName;

}
