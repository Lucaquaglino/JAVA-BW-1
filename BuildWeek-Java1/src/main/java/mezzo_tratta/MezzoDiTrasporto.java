package mezzo_tratta;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import product.Ticket;

@Entity
@Table(name = "mezzi")
@NoArgsConstructor
@Getter
@Setter
@ToString
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_mezzo")

@NamedQuery(name = "tracciaPeriodiServizio", query = "SELECT p  FROM Servizio p INNER JOIN p.mezzo m WHERE m.id = :paramId")
@NamedQuery(name = "ripetizioniTappa", query = "SELECT COUNT(t) FROM MezzoDiTrasporto m INNER JOIN m.tratta tr INNER JOIN tr.tappe t WHERE m.id = :paramMezzo AND t.zona.id = :paramTappa")
public abstract class MezzoDiTrasporto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "mezzo_id")
	private Long id;

	@Column(length = 50)
	private String nome;

	@Column(length = 50)
	private String marca;

	@Column(columnDefinition = "INT2")
	private Short capienza;

	@OneToMany(mappedBy = "mezzo", cascade = CascadeType.ALL)
	@OrderBy("dataInizio")
	private Set<Servizio> servizi;

	@OneToOne
	@JoinColumn(name = "tratta_id")
	private Tratta tratta;
	
	@OneToOne
	@JoinColumn(name = "ticket_id")
	private Ticket ticket;

	public MezzoDiTrasporto(String nome, String marca, Short capienza, Tratta tratta) {
		super();
		this.nome = nome;
		this.marca = marca;
		this.capienza = capienza;
		this.tratta = tratta;
	}


}
