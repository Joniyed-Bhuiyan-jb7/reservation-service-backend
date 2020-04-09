package bd.edu.seu.reservationservice.controller;

import bd.edu.seu.reservationservice.exception.ResourceAlreadyExistsException;
import bd.edu.seu.reservationservice.exception.ResourceNotExistsException;
import bd.edu.seu.reservationservice.model.Room;
import bd.edu.seu.reservationservice.model.RoomType;
import bd.edu.seu.reservationservice.service.RoomService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/rooms")
public class RoomController {
    private RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    // TODO add necessary REST endpoints to support CRUD operations

    @GetMapping("")
    public ResponseEntity<List<Room>> findAll(){
        List<Room> roomList = roomService.findAll();
        return ResponseEntity.ok(roomList);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Room> findById(@PathVariable int id){
        try {
            Room room = roomService.findById(id);
            return ResponseEntity.ok(room);
        } catch (ResourceNotExistsException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("")
    public ResponseEntity<Room> saveRoom(@RequestBody Room room){
        try {
            Room saveRoom = roomService.saveRoom(room);
            return ResponseEntity.status(HttpStatus.CREATED).body(saveRoom);
        } catch (ResourceAlreadyExistsException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<Room> updateRoom(@RequestBody Room room,@PathVariable int id){
        try {
            Room updateRoom = roomService.updateRoom(id, room);
            return ResponseEntity.ok(updateRoom);
        } catch (ResourceNotExistsException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRoom(@PathVariable int id){
        try {
            roomService.deleteRoomById(id);
            return ResponseEntity.ok("deleted");
        } catch (ResourceNotExistsException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // TODO add necessary REST endpoints for RoomType based query


    @GetMapping("/type/{roomType}")
    public ResponseEntity<List<Room>> findRoomByType(@PathVariable RoomType roomType){
        try {
            List<Room> roomList = roomService.findRoomByType(roomType);
            return ResponseEntity.ok().body(roomList);
        } catch (ResourceNotExistsException e) {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/roomNumber/{roomNumber}")
    public ResponseEntity<Room> findRoomByNumber(@PathVariable int roomNumber){
        try {
            return ResponseEntity.ok().body(roomService.findRoomByNumber(roomNumber));
        } catch (ResourceNotExistsException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
