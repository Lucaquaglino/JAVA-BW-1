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
	private LocalDate activation;
	private LocalDate expire;
	private boolean isValid;
	@OneToOne(mappedBy = "cardId")
	private User userId;
	
	public Card(LocalDate _activation) {
		this.activation = _activation;
	}

	@Override
	public String toString() {
		return "Card -> [cardId] = " + cardId + ", [activation] = " + activation + ", [expire] = " + expire + ", [isValid] = " + isValid
				+ ", [userId] = " + userId;
	}
	
	
	
	
	
}
