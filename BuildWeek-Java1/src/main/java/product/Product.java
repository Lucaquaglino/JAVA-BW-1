package product;

import java.time.LocalDate;
import java.util.Random;
import java.util.UUID;

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
	private long productId = 1_000_000_000_000l + new Random().nextLong(9_000_000_000_000l);
	private LocalDate emissionDate = LocalDate.now();

	@ManyToOne
	@JoinColumn(name = "shop_id", referencedColumnName = "shopId")
	private Punti_vendita shopId;

	@Override
	public String toString() {
		return "Product [productId=" + productId + ", emissionDate=" + emissionDate + ", shopId=" + shopId + "]";
	}
}
