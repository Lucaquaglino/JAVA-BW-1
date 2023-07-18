package punti_vendita;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Rivenditori")
@DiscriminatorValue("Rivenditori")
@Getter
@Setter
@NoArgsConstructor
public class Rivenditori extends Punti_vendita {
	public Rivenditori(String location) {
		super(location);

	}

	@Override
	public String toString() {
		return "Rivenditori []";
	}

}
