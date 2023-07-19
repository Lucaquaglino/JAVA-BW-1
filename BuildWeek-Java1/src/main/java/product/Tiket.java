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
public class Tiket extends Product {
	private Boolean isUsed;
	private long veichleId;
	private LocalDate emission;

	public Tiket(LocalDate emissionDate, Punti_vendita pv, Boolean isUsed, long veichleId) {
		super(pv);
		this.emission = emissionDate;
		this.isUsed = isUsed = false;
		this.veichleId = veichleId;
		if (isUsed) {
			isUsed = true;
		}
	}

	@Override
	public String toString() {
		return "Tiket [isUsed=" + isUsed + ", veichleId=" + veichleId + "]";
	}

}
