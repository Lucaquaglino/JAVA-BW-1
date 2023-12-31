package mezzo_tratta;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@DiscriminatorValue("Bus")
@Setter
@Getter

@NoArgsConstructor
public class Autobus extends MezzoDiTrasporto {

	@Column(name = "numero_ruote", columnDefinition = "INT2")
	private Short numeroRuote;

	public Autobus(String nome, String marca, Short capienza, Short numeroRuote, Tratta tratta) {
		super(nome, marca, capienza, tratta);
		this.numeroRuote = numeroRuote;
	}

	@Override
	public String toString() {
		return "Autobus [numeroRuote=" + numeroRuote + ", getId()=" + getId() + ", getNome()=" + getNome()
				+ ", getMarca()=" + getMarca() + ", getCapienza()=" + getCapienza() + ", getTratta()=" + getTratta()
				+ "]";
	}

}
