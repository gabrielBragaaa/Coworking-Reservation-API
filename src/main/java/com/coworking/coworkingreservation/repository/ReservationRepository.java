package com.coworking.coworkingreservation.repository;

import com.coworking.coworkingreservation.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Query("""
            SELECT r FROM Reservation r
            WHERE r.room.id = :roomId
            AND r.date = :date
            AND r.canceled = false
            AND (r.startTime < :endTime
            AND r.endTime > :startTime)
            """)
    List<Reservation> findConflictingReservations(
            Long roomId,
            LocalDate date,
            LocalTime startTime,
            LocalTime endTime
    );

    List<Reservation> findByDateAndCanceledFalse(LocalDate date);

}
