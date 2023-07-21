package product;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import _enum.Periodicy;
import card_user.Card;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Subscription")
@Getter
@Setter
@NoArgsConstructor
public class Subscription extends Product {
	@Enumerated(EnumType.STRING)
	private Periodicy period;
	private Boolean isActive;
	private LocalDate expireDate;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(referencedColumnName = "cardId")
	private Card cardId;


	public Subscription(Periodicy period) {
		super();
		this.period = period;
		if (period == Periodicy.WEEKLY) {
			this.expireDate = getEmissionDate().plusWeeks(1);
		} else {
			this.expireDate = getEmissionDate().plusMonths(1);
		}
		this.isActive = (this.expireDate.isAfter(expireDate)) ? false : true; 

	}

}
