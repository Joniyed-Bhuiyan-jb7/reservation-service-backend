package bd.edu.seu.reservationservice.controller;

import bd.edu.seu.reservationservice.exception.ResourceAlreadyExistsException;
import bd.edu.seu.reservationservice.exception.ResourceNotExistsException;
import bd.edu.seu.reservationservice.model.Reservation;
import bd.edu.seu.reservationservice.service.ReservationService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/reservations")
public class ReservationController {
    private ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    // TODO add necessary REST endpoints to support CRUD operations
    
    @GetMapping("")
    public ResponseEntity<List<Reservation>> findAll(){
        List<Reservation> reservationList = reservationService.findAll();
        return ResponseEntity.ok(reservationList);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Reservation> findById(@PathVariable int id){
        try {
            Reservation reservation = reservationService.findById(id);
            return ResponseEntity.ok(reservation);
        } catch (ResourceNotExistsException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("")
    public ResponseEntity<Reservation> saveReservation(@RequestBody Reservation reservation){
        try {
            Reservation saveReservation = reservationService.saveReservation(reservation);
            return ResponseEntity.status(HttpStatus.CREATED).body(saveReservation);
        } catch (ResourceAlreadyExistsException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<Reservation> updateReservation(@RequestBody Reservation reservation,@PathVariable int id){
        try {
            Reservation updateReservation = reservationService.updateReservation(id, reservation);
            return ResponseEntity.ok(updateReservation);
        } catch (ResourceNotExistsException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteReservation(@PathVariable int id){
        try {
            reservationService.deleteById(id);
            return ResponseEntity.ok("deleted");
        } catch (ResourceNotExistsException e) {
            return ResponseEntity.notFound().build();
        }
    }
    

    // TODO add necessary REST endpoint to do reservation queries for a given date ranges

    @GetMapping("/date/{start}/{end}")
    public ResponseEntity<List<Reservation>> findBetweenDate(@PathVariable  @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate start, @PathVariable  @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end){
        try {
            return ResponseEntity.ok().body(reservationService.findBetweenDate(start,end));
        } catch (ResourceNotExistsException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
