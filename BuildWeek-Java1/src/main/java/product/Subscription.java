package product;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import _enum.Periodicy;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Subscription")
@Getter
@Setter
@NoArgsConstructor
public class Subscription extends Product {
	@Enumerated
	private Periodicy period;
	private Boolean isActive;

	public Subscription(LocalDate activation, LocalDate expire, Periodicy period, Boolean isActive) {
		super(activation, expire);
		this.period = period;
		this.isActive = isActive;
	}

	@Override
	public String toString() {
		return "Subscription [period=" + period + ", isActive=" + isActive + "]";
	}

}
