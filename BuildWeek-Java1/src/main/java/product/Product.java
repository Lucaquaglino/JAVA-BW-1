package product;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import punti_vendita.Punti_vendita;

@Entity
@Table(name = "Product")
@Getter
@Setter
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Product {
	@Id
	@GeneratedValue
	private long productId;

	@ManyToOne
	@JoinColumn(name = "PV_Id", referencedColumnName = "shopId")
	private Punti_vendita pv;

	public Product(Punti_vendita pv) {
		this.pv = pv;
	}

	@Override
	public String toString() {
		return "Product [productId=" + productId + ", pv=" + pv + "]";
	}

}
