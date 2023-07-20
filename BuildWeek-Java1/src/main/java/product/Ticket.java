package product;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import punti_vendita.Punti_vendita;

@Entity
@Table(name = "Tiket")
@Getter
@Setter
@NoArgsConstructor
public class Ticket extends Product {
	private Boolean isUsed = false;
	private LocalDate date;
	private Punti_vendita shop;
	// @JoinColumn
	long veichleId;

	public Ticket(LocalDate date, Punti_vendita shop) {
		super();
		this.date = date;
		this.shop = shop;
	}

	@Override
	public String toString() {
		return "Tiket [" + getProductId() + "] isUsed = " + isUsed + "]";
	}

}
