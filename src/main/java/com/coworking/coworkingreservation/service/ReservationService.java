package com.coworking.coworkingreservation.service;

import com.coworking.coworkingreservation.dto.ReservationRequest;
import com.coworking.coworkingreservation.dto.ReservationResponse;
import com.coworking.coworkingreservation.entity.Reservation;
import com.coworking.coworkingreservation.entity.Room;
import com.coworking.coworkingreservation.exception.BusinessException;
import com.coworking.coworkingreservation.mapper.ReservationMapper;
import com.coworking.coworkingreservation.repository.ReservationRepository;
import com.coworking.coworkingreservation.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final RoomRepository roomRepository;

    public ReservationResponse createReservation(ReservationRequest request) {
        Room room = roomRepository.findById(request.getRoomId()).orElseThrow();
        List<Reservation> conflicts = reservationRepository.findConflictingReservations(
                room.getId(),
                request.getDate(),
                request.getStartTime(),
                request.getEndTime()
        );
         if (!conflicts.isEmpty()){
             throw new BusinessException("There is already a reservation for this time");
         }

        Reservation reservation = ReservationMapper.toEntity(request, room);
        Reservation savedReservation = reservationRepository.save(reservation);
        return ReservationMapper.toResponse(savedReservation);
    }

    public void cancelReservation(Long reservationId){
        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow();
        reservation.setCanceled(true);
        reservationRepository.save(reservation);
    }

    public List<ReservationResponse> findDailyReservations(LocalDate date){
        List<Reservation> reservations = reservationRepository.findByDateAndCanceledFalse(date);
        return reservations.stream().map(ReservationMapper::toResponse).toList();
    }
}
