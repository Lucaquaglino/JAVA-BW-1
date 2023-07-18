package entities;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "stato_mezzi")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Servizio {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "stato_id")
	private Long id;


	@Enumerated(EnumType.STRING)
	@Column(length = 50, nullable = false)
	private StatoMezzo stato;

	@Column(name = "data_inizio", nullable = false)
	private LocalDate dataInizio;

	@Column(name = "data_fine")
	private LocalDate dataFine;

	@ManyToOne
	@JoinColumn(name = "mezzo_id", referencedColumnName = "mezzo_id")
	private MezzoDiTrasporto mezzo;

	public Servizio(StatoMezzo stato, LocalDate dataInizio, MezzoDiTrasporto mezzo) {
		super();
		this.stato = stato;
		this.dataInizio = dataInizio;
		this.mezzo = mezzo;
	}

}
