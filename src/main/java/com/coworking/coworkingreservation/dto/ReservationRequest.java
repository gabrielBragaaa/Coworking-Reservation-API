package com.coworking.coworkingreservation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class ReservationRequest {
    
    @NotBlank(message = "Responsible name is required")
    private String responsibleName;
    
    @NotNull(message = "Reservation date is required")
    private LocalDate date;
    
    @NotNull(message = "Start time is required")
    private LocalTime startTime;
    
    @NotNull(message = "End time is required")
    private LocalTime endTime;
    
    @NotNull(message = "Room id is required")
    private Long roomId;
}
