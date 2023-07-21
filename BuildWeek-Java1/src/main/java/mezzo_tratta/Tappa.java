package mezzo_tratta;

import java.time.Duration;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.TypeDef;

import io.hypersistence.utils.hibernate.type.interval.PostgreSQLIntervalType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tappe", uniqueConstraints = { @UniqueConstraint(columnNames = { "zona_id", "tratta_id", "ordine" }) })
@NoArgsConstructor
@Getter
@Setter

@TypeDef(typeClass = PostgreSQLIntervalType.class, defaultForType = Duration.class)
public class Tappa {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "tappa_id")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "zona_id", referencedColumnName = "zona_id")
	private Zona zona;
	
	@ManyToOne
	@JoinColumn(name = "tratta_id", referencedColumnName = "tratta_id")
	private Tratta tratta;
	
	@Column(name = "t_percorrenza", columnDefinition = "INTERVAL")
	private Duration tempoDiPercorrenza;
	
	private int ordine;

	public Tappa(Zona zona, Tratta tratta, int ordine, Duration tempoDiPercorrenza) {
		super();
		this.zona = zona;
		this.tratta = tratta;
		this.ordine = ordine;
		this.tempoDiPercorrenza = tempoDiPercorrenza;
	}

	@Override
	public String toString() {
		return "Tappa [id=" + id + ", zona=" + zona + ", tempoDiPercorrenza="
				+ (tempoDiPercorrenza.toHoursPart() + ":" + tempoDiPercorrenza.toMinutesPart()) + ", ordine="
				+ ordine + "]";
	}
	

}
