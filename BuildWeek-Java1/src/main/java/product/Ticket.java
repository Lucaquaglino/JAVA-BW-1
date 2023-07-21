package product;

import java.time.LocalDate;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mezzo_tratta.MezzoDiTrasporto;
import punti_vendita.Punti_vendita;

@Entity
@Table(name = "Tiket")
@Getter
@Setter
@NoArgsConstructor
public class Ticket extends Product {
	private Boolean isUsed = false;
	
	@OneToOne
	@JoinColumn(name = "mezzo_id")
	MezzoDiTrasporto veichle;


	@Override
	public String toString() {
		return "Tiket [" + getProductId() + "] isUsed = " + isUsed + "]";
	}
}
