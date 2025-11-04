package com.proj.chat.controller;

import com.proj.chat.entity.Message;
import com.proj.chat.entity.Room;
import com.proj.chat.payload.MessageRequest;
import com.proj.chat.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;

@Controller
@CrossOrigin("http://localhost:5173")
public class ChatController {
    @Autowired RoomService roomService;

    @MessageMapping("/sendMessage/{roomId}") // send message to /app/sendMessage/roomId
    @SendTo("/topic/room/{roomId}") // subscribe
    public Message sendMessage(
            @DestinationVariable String roomId,
            @RequestBody MessageRequest request
    ) {
        Room room = roomService.findRoom(request.getRoomId()).orElse(null);

        Message message = new Message();
        message.setContent(request.getContent());
        message.setSender(request.getSender());
        message.setTimeStamp(LocalDateTime.now());

        if (room != null) {
            room.getMessages().add(message);
            roomService.addMessage(room);
        } else {
            throw new RuntimeException("Room not found!");
        }

        return message;
    }

}
