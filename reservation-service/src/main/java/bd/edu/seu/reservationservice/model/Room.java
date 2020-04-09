package bd.edu.seu.reservationservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Room {
    @Id
    private int id;
    private int number;
    private RoomType roomType;
}
