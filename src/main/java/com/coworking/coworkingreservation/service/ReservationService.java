package com.coworking.coworkingreservation.service;

import com.coworking.coworkingreservation.dto.ReservationRequest;
import com.coworking.coworkingreservation.dto.ReservationResponse;
import com.coworking.coworkingreservation.entity.Reservation;
import com.coworking.coworkingreservation.entity.Room;
import com.coworking.coworkingreservation.mapper.ReservationMapper;
import com.coworking.coworkingreservation.repository.ReservationRepository;
import com.coworking.coworkingreservation.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final RoomRepository roomRepository;

    public ReservationResponse createReservation(ReservationRequest request) {
        Room room = roomRepository.findById(request.getRoomId()).orElseThrow();
        Reservation reservation = ReservationMapper.toEntity(request, room);
        Reservation savedReservation = reservationRepository.save(reservation);
        return ReservationMapper.toResponse(savedReservation);
    }
}
