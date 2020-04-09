package bd.edu.seu.reservationservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Reservation {
    @Id
    private int id;
    private String name;
    private LocalDate fromDate;
    private LocalDate toDate;
    @OneToOne
    private Room room;
}
