package card_user;

import java.time.LocalDate;
import java.util.Random;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Card {
	@Id
	private long cardId = 1_000_000_000_000l + new Random().nextLong(9_000_000_000_000l);
	private LocalDate activationDate;
	private LocalDate expireDate;
	private boolean isValid;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(referencedColumnName = "userId")
	private User user;
	
	public Card(LocalDate _activationDate) {
		this.activationDate = _activationDate;
		this.expireDate = activationDate.plusYears(1);
		this.isValid = LocalDate.now().isAfter(this.expireDate) ? false : true;
	}

	@Override
	public String toString() {
		return "Card -> [cardId] = " + cardId + ", [activationDate] = " + activationDate + ", [expireDate] = " + expireDate + ", [isValid] = " + isValid
				+ ", [user] = " + user.getUserId();
	}
	
	
}
