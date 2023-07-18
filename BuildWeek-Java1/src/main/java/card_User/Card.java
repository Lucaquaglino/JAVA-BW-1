package card_User;

import java.time.LocalDate;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class Card {
	@Id
	@GeneratedValue
	private UUID cardId;
	private LocalDate activationDate;
	private LocalDate expireDate;
	private boolean isValid;
	@OneToOne(mappedBy = "card")
	private User user;
	
	public Card(LocalDate _activationDate) {
		this.activationDate = _activationDate;
		this.expireDate = activationDate.plusYears(1);
		this.isValid = LocalDate.now().isAfter(this.expireDate) ? false : true;
	}

	@Override
	public String toString() {
		return "Card -> [cardId] = " + cardId + ", [activationDate] = " + activationDate + ", [expireDate] = " + expireDate + ", [isValid] = " + isValid
				+ ", [userId] = " + user;
	}
}
