package punti_vendita;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import _enum.State;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Distributori")
@Getter
@Setter
@NoArgsConstructor
public class Distributori extends Punti_vendita {
	@Enumerated(EnumType.STRING)
	private State state;

	public Distributori(String location, State state) {
		super(location);
		this.state = state;
	}

	@Override
	public String toString() {
		return "Distributori [state=" + state + "]";
	}

}
