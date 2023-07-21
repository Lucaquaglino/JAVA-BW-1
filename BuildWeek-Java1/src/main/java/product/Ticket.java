package product;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Ticket")
@Getter
@Setter
@NoArgsConstructor
public class Ticket extends Product {
	private Boolean isUsed = false;

	// @JoinColumn
	long veichleId;

	@Override
	public String toString() {
		return "Ticket [" + getProductId() + "] isUsed = " + isUsed + "]";
	}
}
