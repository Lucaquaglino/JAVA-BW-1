package punti_vendita;

import java.util.Set;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import product.Product;

@Entity
@Table(name = "Punto_Vendita")
@Getter
@Setter
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Punti_vendita {
	@Id
	@GeneratedValue
	private UUID shopId;
	private String location;

	@OneToMany(mappedBy = "shopId")
	private Set<Product> products;

	@Override
	public String toString() {
		return "Punti_vendita [shopId=" + shopId + ", location=" + location + "]";
	}

	public Punti_vendita(String location) {
		super();
		this.location = location;
	}
	
}
