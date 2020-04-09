package bd.edu.seu.reservationservice.service;

import bd.edu.seu.reservationservice.exception.ResourceAlreadyExistsException;
import bd.edu.seu.reservationservice.exception.ResourceNotExistsException;
import bd.edu.seu.reservationservice.model.Reservation;
import bd.edu.seu.reservationservice.model.Room;
import bd.edu.seu.reservationservice.repository.ReservationRepository;
import bd.edu.seu.reservationservice.repository.RoomRepository;
import org.apache.tomcat.jni.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReservationService {
    private ReservationRepository reservationRepository;
    private RoomRepository roomRepository;

    public ReservationService(ReservationRepository reservationRepository,RoomRepository roomRepository) {
        this.reservationRepository = reservationRepository;
        this.roomRepository = roomRepository;
    }

    // TODO add necessary methods here

    public List<Reservation> findAll(){
        List<Reservation> reservationList = new ArrayList<>();
        reservationRepository.findAll().forEach(reservationList::add);
        return reservationList;
    }


    public Reservation findById(int id) throws ResourceNotExistsException {
        if(reservationRepository.existsById(id)){
            Reservation reservation = reservationRepository.findById(id).get();
            return reservation;
        }else{
            throw new ResourceNotExistsException(id+"");
        }
    }

    public Reservation saveReservation(Reservation reservation) throws ResourceAlreadyExistsException {
        if(!reservationRepository.existsById(reservation.getId()) && roomRepository.existsById(reservation.getRoom().getId())){
            return reservationRepository.save(reservation);
        }else{
            throw new ResourceAlreadyExistsException(reservation.getId()+"");
        }
    }

    public void deleteAll(){
        reservationRepository.deleteAll();
    }

    public void deleteById(int id) throws ResourceNotExistsException {
        if(reservationRepository.existsById(id)){
            reservationRepository.deleteById(id);
        }else{
            throw new ResourceNotExistsException(id+"");
        }
    }

    public Reservation updateReservation(int id, Reservation reservation) throws ResourceNotExistsException {
        if(reservationRepository.existsById(id)){
            reservation.setId(id);
            Reservation savedReservation = reservationRepository.save(reservation);
            return savedReservation;
        }else{
            throw new ResourceNotExistsException(id+"");
        }
    }

    public List<Reservation> findBetweenDate(LocalDate start,LocalDate end) throws ResourceNotExistsException {
         List<Reservation> reservationList = reservationRepository.findReservationByFromDateBetweenOrToDateBetween(start,end,start,end);
         List<Reservation> reservationList1 = reservationRepository.findReservationByFromDateBeforeAndToDateAfter(start,end);
         List<Room> roomList = new ArrayList<>();
         if(!reservationList.isEmpty() || !reservationList1.isEmpty()){
              reservationList.addAll(reservationList1);
              return reservationList;
         }else{
             throw new ResourceNotExistsException("no resevation available between this range...");
         }
    }

}
