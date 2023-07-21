package mezzo_tratta;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.TypeDef;

import io.hypersistence.utils.hibernate.type.interval.PostgreSQLIntervalType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import product.Ticket;

@Entity
@NoArgsConstructor
@Setter
@Getter

@Table(name = "tratte")
@NamedQuery(name = "all", query = "SELECT tr FROM Tratta tr")
@TypeDef(typeClass = PostgreSQLIntervalType.class, defaultForType = Duration.class)
@NamedQuery(name = "tempoPerTratta", query = "SELECT SUM(t.tempoDiPercorrenza) FROM Tratta tr INNER JOIN tr.tappe t WHERE tr.id = :paramId GROUP BY tr")
public class Tratta {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "tratta_id")
	private Long id;

	private String nome;

	@ManyToOne
	@JoinColumn(name = "id_partenza", referencedColumnName = "zona_id")
	private Zona partenza;

	@ManyToOne
	@JoinColumn(name = "id_capolinea", referencedColumnName = "zona_id")
	private Zona capolinea;

	@OneToMany(mappedBy = "tratta", cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
	@OrderBy("ordine")
	private Set<Tappa> tappe;

	@Column(name = "arrival_time", columnDefinition = "TIME")
	private LocalTime arrivalTime;

	@Column(name = "start_time", columnDefinition = "TIME")
	private LocalTime startTime;

	@OneToOne(mappedBy = "tratta")
	private MezzoDiTrasporto mezzo;
	

//	@Column(name = "t_percorrenza", columnDefinition = "INTERVAL")
//	private Duration tempoDiPercorrenza;

	public Tratta(String nome, Zona partenza, Zona capolinea, LocalTime arrivalTime, LocalTime startTime,
			// Duration tempoDiPercorrenza,
			Set<Tappa> tappe) {
		super();
		this.nome = nome;
		this.partenza = partenza;
		this.capolinea = capolinea;
		this.arrivalTime = arrivalTime;
		this.startTime = startTime;
		// this.tempoDiPercorrenza = tempoDiPercorrenza;
		tappe.forEach(t -> t.setTratta(this));
		this.tappe = tappe;

	}

	@Override
	public String toString() {
		return "Tratta [id=" + id + ", nome=" + nome + ", partenza=" + partenza + ", capolinea=" + capolinea
				+ ", tappe=" + tappe + ", arrivalTime=" + arrivalTime + ", startTime=" + startTime + "]";
	}

}
