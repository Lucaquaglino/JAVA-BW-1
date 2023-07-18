package mezzo_tratta;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="tappe", uniqueConstraints = { @UniqueConstraint(columnNames = { "zona_id", "tratta_id", "order" }) })
@NoArgsConstructor
@Getter
@Setter
@ToString

public class Tappa {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "tappa_id")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="zona_id")
	private Zona zona;
	
	@ManyToOne
	@JoinColumn(name="tratta_id")
	private Tratta tratta;
	
	
	private int order;

	public Tappa(Zona zona, Tratta tratta, int order) {
		super();
		this.zona = zona;
		this.tratta = tratta;
		this.order = order;
	}
	

}
