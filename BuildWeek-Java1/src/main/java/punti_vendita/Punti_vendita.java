package punti_vendita;

import java.util.Set;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Punto_Vendita")
@Getter
@Setter
@NoArgsConstructor
public class Punti_vendita {
	@Id
	@GeneratedValue
	private UUID shopId;
	private String location;

	@OneToMany(mappedBy = "punti_vendita")
	private Set<Distributori> distributori;

	@OneToMany(mappedBy = "punti_vendita")
	private Set<Rivenditori> rivenditori;

	@Override
	public String toString() {
		return "Punti_vendita [shopId=" + shopId + ", distributori=" + distributori + ", rivenditori=" + rivenditori
				+ "]";
	}

	public Punti_vendita(String location) {
		this.location = location;
	}

}
