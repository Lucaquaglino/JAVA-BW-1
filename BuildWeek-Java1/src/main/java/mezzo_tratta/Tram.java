package mezzo_tratta;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@DiscriminatorValue("Tram")
@Setter
@Getter
@ToString
@NoArgsConstructor
public class Tram extends MezzoDiTrasporto {

	@Column(name = "numero_cabine", columnDefinition = "INT2")
	private Short numeroCabine;

	public Tram(String nome, String marca, Short capienza, Short numeroCabine, Tratta tratta) {
		super(nome, marca, capienza, tratta);
		this.numeroCabine = numeroCabine;
	}

}
