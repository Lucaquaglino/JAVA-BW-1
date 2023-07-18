package punti_vendita;

import java.util.UUID;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "punti_vendita")
@DiscriminatorColumn(name = "punti_vendita_type")
@Getter
@Setter
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Punti_vendita {
	@Id
	@GeneratedValue
	private UUID shopId;
	private String location;

	public Punti_vendita(String location) {
		super();
		this.location = location;
	}

	@Override
	public String toString() {
		return "Punti_vendita [shopId=" + shopId + ", location=" + location + "]";
	}

}
