package com.coworking.coworkingreservation.controller;

import com.coworking.coworkingreservation.dto.ReservationRequest;
import com.coworking.coworkingreservation.dto.ReservationResponse;
import com.coworking.coworkingreservation.service.ReservationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReservationResponse createReservation(@RequestBody @Valid ReservationRequest request){
        return reservationService.createReservation(request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelReservation(@PathVariable Long id){
        reservationService.cancelReservation(id);
    }

}