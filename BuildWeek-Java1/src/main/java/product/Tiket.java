package product;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Tiket")
@Getter
@Setter
@NoArgsConstructor
public class Tiket extends Product {
	private Boolean isUsed;
	private long veichleId;

	public Tiket(LocalDate activation, LocalDate expire, Boolean isUsed, long veichleId) {
		super(activation, expire);
		this.isUsed = isUsed;
		this.veichleId = veichleId;
	}

	@Override
	public String toString() {
		return "Tiket [isUsed=" + isUsed + ", veichleId=" + veichleId + "]";
	}

}
