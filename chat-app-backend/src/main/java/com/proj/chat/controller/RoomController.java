package com.proj.chat.controller;

import com.proj.chat.dto.CreateRoomDTO;
import com.proj.chat.entity.Message;
import com.proj.chat.entity.Room;
import com.proj.chat.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/rooms")
@CrossOrigin("http://localhost:5173")
public class RoomController {
    @Autowired RoomService roomService;

    @PostMapping
    public ResponseEntity<?> createRoom(@RequestBody CreateRoomDTO room) {
        Room newRoom = new Room();
        newRoom.setOwner(room.getUserName());
        newRoom.setRoomId(room.getRoomId());

        return roomService.createRoom(newRoom).isEmpty() ?
                ResponseEntity.badRequest().body("Room already exists!") : ResponseEntity.status(HttpStatus.CREATED).body(newRoom);
    }

    @GetMapping("/{roomId}")
    public ResponseEntity<?> joinRoom(@PathVariable String roomId) {
        Room foundRoom = roomService.findRoom(roomId).orElse(null);

        return foundRoom == null ?
                ResponseEntity.badRequest().body("Room not found!") : ResponseEntity.ok(foundRoom);
    }

    @GetMapping("/{roomId}/messages")
    public ResponseEntity<List<Message>> getMessages(
            @PathVariable String roomId,
            @RequestParam(value = "page", defaultValue = "0", required = false) int page,
            @RequestParam(value = "size", defaultValue = "20", required = false) int size
    ) {
        Room room = roomService.findRoom(roomId).orElse(null);

        if (room == null) {
            return ResponseEntity.badRequest().build();
        }

        List<Message> messages = room.getMessages();

        int start = Math.max(0, messages.size() - (page + 1) * size);
        int end = Math.min(messages.size(), start + size);
        List<Message> paginatedMessages = messages.subList(start, end);

        return ResponseEntity.ok(paginatedMessages);
    }
}
