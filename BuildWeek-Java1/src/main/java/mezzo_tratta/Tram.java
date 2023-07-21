package mezzo_tratta;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@DiscriminatorValue("Tram")
@Setter
@Getter

@NoArgsConstructor
public class Tram extends MezzoDiTrasporto {

	@Column(name = "numero_cabine", columnDefinition = "INT2")
	private Short numeroCabine;

	public Tram(String nome, String marca, Short capienza, Short numeroCabine, Tratta tratta) {
		super(nome, marca, capienza, tratta);
		this.numeroCabine = numeroCabine;
	}

	@Override
	public String toString() {
		return "Tram [numeroCabine=" + numeroCabine + ", getId()=" + getId() + ", getNome()=" + getNome()
				+ ", getMarca()=" + getMarca() + ", getCapienza()=" + getCapienza() + ", getTratta()=" + getTratta()
				+ "]";
	}

}
