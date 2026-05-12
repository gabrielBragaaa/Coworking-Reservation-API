package com.coworking.coworkingreservation.repository;

import com.coworking.coworkingreservation.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
    
}
