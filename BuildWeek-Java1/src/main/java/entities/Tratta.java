package entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.postgresql.util.PGInterval;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@NoArgsConstructor
@Setter
@Getter
@ToString
@Table(name = "tratte")
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

	@Column(name = "arrival_time", columnDefinition = "TIME")
	private LocalDateTime arrivalTime;

	@Column(name = "start_time", columnDefinition = "TIME")
	private LocalDateTime startTime;

	@Column(name = "t_percorrenza", columnDefinition = "INTERVAL")
	private PGInterval tempoDiPercorrenza;

	public Tratta(String nome, Zona partenza, Zona capolinea, LocalDateTime arrivalTime, LocalDateTime startTime,
			PGInterval tempoDiPercorrenza) {
		super();
		this.nome = nome;
		this.partenza = partenza;
		this.capolinea = capolinea;
		this.arrivalTime = arrivalTime;
		this.startTime = startTime;
		this.tempoDiPercorrenza = tempoDiPercorrenza;
	}

}
