package mezzo_tratta;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@NoArgsConstructor
@Setter
@Getter
@ToString
@Table(name = "zone")
public class Zona {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "zona_id")
	private Long id;

	@Column(length = 100, unique = true)
	private String nome;

	@OneToMany(mappedBy = "capolinea")
	private Set<Tratta> capolinea;

	@OneToMany(mappedBy = "partenza")
	private Set<Tratta> partenze;

	public Zona(String nome) {
		super();
		this.nome = nome;
	}

}
