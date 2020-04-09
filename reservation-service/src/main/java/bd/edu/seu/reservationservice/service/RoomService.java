package bd.edu.seu.reservationservice.service;

import bd.edu.seu.reservationservice.exception.ResourceAlreadyExistsException;
import bd.edu.seu.reservationservice.exception.ResourceNotExistsException;
import bd.edu.seu.reservationservice.model.Room;
import bd.edu.seu.reservationservice.model.RoomType;
import bd.edu.seu.reservationservice.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoomService {
    private RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    // TODO add necessary methods heres

    public List<Room> findAll(){
        List<Room> roomList = new ArrayList<>();
        roomRepository.findAll().forEach(roomList::add);
        return roomList;
    }

    public Room findById(int id) throws ResourceNotExistsException {
        if(roomRepository.existsById(id)){
            return roomRepository.findById(id).get();
        }else{
            throw new ResourceNotExistsException(id+"");
        }
    }


    public Room saveRoom(Room room) throws ResourceAlreadyExistsException {
        if(!roomRepository.existsById(room.getId())){
            return roomRepository.save(room);
        }else {
            throw new ResourceAlreadyExistsException(room.getId()+"");
        }
    }

    public Room updateRoom(int id, Room room) throws ResourceNotExistsException {
        if(roomRepository.existsById(id)){
            room.setId(id);
            return roomRepository.save(room);
        }else{
            throw new ResourceNotExistsException(id+" ");
        }
    }

    public void deleteRoomById(int id) throws ResourceNotExistsException {
        if(roomRepository.existsById(id)){
            roomRepository.deleteById(id);
        }else{
            throw new ResourceNotExistsException(id+"");
        }
    }

    public void deleteAll(){
        roomRepository.deleteAll();
    }


    public Room findRoomByNumber(int number) throws ResourceNotExistsException {
        if(roomRepository.findRoomByNumber(number).isPresent()){
            return roomRepository.findRoomByNumber(number).get();
        }
        else{
            throw new ResourceNotExistsException(number+"");
        }
    }

    public List<Room> findRoomByType(RoomType roomType) throws ResourceNotExistsException {
        if(!roomRepository.findRoomByRoomType(roomType).isEmpty()){
            return roomRepository.findRoomByRoomType(roomType);
        }else{
            throw new ResourceNotExistsException(roomType+"");
        }
    }

}
