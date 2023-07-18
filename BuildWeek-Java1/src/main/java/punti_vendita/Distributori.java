package punti_vendita;

import java.util.Set;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import _enum.State;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import product.Product;

@Entity
@Table(name = "Distributori")
@DiscriminatorValue("Distributori")
@Getter
@Setter
@NoArgsConstructor
public class Distributori extends Punti_vendita {
	@Enumerated
	private State state;

	@OneToMany(mappedBy = "distributori")
	private Set<Product> product;

	public Distributori(String location, State state) {
		super(location);
		this.state = state;
	}

	@Override
	public String toString() {
		return "Distributori [state=" + state + "]";
	}

}
