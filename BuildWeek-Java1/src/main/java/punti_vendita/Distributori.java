package punti_vendita;

import java.util.Set;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import _enum.State;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import product.Product;

@Entity
@Table(name = "Distributori")
@Getter
@Setter
@NoArgsConstructor
public class Distributori {
	@Id
	@GeneratedValue
	private UUID distributori_Id;
	@Enumerated
	private State state;
	private String location;

	@ManyToOne
	@JoinColumn(name = "fk_dis_ID", referencedColumnName = "shopId")
	private Punti_vendita punti_vendita;

	@OneToMany(mappedBy = "distributori")
	private Set<Product> product;

	public Distributori(State state, String location) {
		this.state = state;
		this.location = location;
	}

	@Override
	public String toString() {
		return "Distributori [distributori_Id=" + distributori_Id + ", state=" + state + ", location=" + location
				+ ", punti_vendita=" + punti_vendita + ", product=" + product + "]";
	}

}
