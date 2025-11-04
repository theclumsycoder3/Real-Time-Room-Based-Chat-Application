package com.proj.chat.service;

import com.proj.chat.entity.Room;
import com.proj.chat.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoomService {
    @Autowired private RoomRepository roomRepository;

    public Optional<Room> createRoom(Room room) {
        if (roomRepository.findByRoomId(room.getRoomId()).isPresent()) {
            return Optional.empty();
        }

        return Optional.of(roomRepository.save(room));
    }

    public Optional<Room> findRoom(String roomId) {
        return roomRepository.findByRoomId(roomId);
    }

    public Optional<Room> addMessage(Room room) {
        return Optional.of(roomRepository.save(room));
    }
}
