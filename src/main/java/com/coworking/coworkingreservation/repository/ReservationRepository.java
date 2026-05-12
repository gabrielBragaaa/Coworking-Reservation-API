package com.coworking.coworkingreservation.repository;

import com.coworking.coworkingreservation.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
