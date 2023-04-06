package domainModel;

import java.io.Serializable;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "bia")
public class Bia implements Serializable{

    @Id
    @GeneratedValue
    @Column(name="id")
    private UUID id;
    
    @Column(name = "ma")
    private String ma;

    @Column(name = "ten")
    private String ten;
    
    @Override
    public String toString() {
        return ten;
    }
}
