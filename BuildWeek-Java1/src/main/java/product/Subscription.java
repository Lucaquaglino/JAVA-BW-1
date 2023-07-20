package product;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import _enum.Periodicy;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import punti_vendita.Punti_vendita;

@Entity
@Table(name = "Subscription")
@Getter
@Setter
@NoArgsConstructor
public class Subscription extends Product {
	@Enumerated
	private Periodicy period;
	private Boolean isActive;
	private LocalDate expireDate;
	private LocalDate activationDate;

	public Subscription(LocalDate _activationDate, Punti_vendita pv, Periodicy period, Boolean isActive) {
		super();
		this.activationDate = _activationDate;
		this.period = period;
		this.isActive = isActive;
		if (period == Periodicy.WEEKLY) {
			this.expireDate = _activationDate.plusWeeks(1);
		} else {
			this.expireDate = _activationDate.plusMonths(1);
		}

	}

}
