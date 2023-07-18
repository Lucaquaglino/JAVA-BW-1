package Product;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import Punti_vendita.Distributori;
import Punti_vendita.Rivenditori;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
	private LocalDate activation;
	private LocalDate expire;

	@ManyToOne
	@JoinColumn(name = "rivenditori_id", referencedColumnName = "shopId")
	private Rivenditori rivenditori;

	@ManyToOne
	@JoinColumn(name = "distributori_id", referencedColumnName = "shopId")
	private Distributori distributori;

	public Product(LocalDate activation, LocalDate expire) {
		super();
		this.activation = activation;
		this.expire = expire;
	}

	@Override
	public String toString() {
		return "Product [productId=" + productId + ", activation=" + activation + ", expire=" + expire + "]";
	}

}
