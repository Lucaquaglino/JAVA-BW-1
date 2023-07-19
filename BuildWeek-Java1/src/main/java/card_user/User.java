package card_user;

import java.time.LocalDate;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
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
public class User {
	@Id
	@GeneratedValue
	private UUID userId;
	private String name;
	private String surname;
	private LocalDate birth;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(referencedColumnName = "cardId")
	private Card card;
	
	public User(String _name, String _surname, LocalDate _birth) {
		this.name = _name;
		this.surname = _surname;
		this.birth = _birth;
	}

	@Override
	public String toString() {
		return "User --> \n[userId] = " + userId + "\n[name] = " + name + "\n[surname] = " + surname + "\n[birth] = " + birth + "\n[cardId] = "
				+ card.getCardId();
	}
}
