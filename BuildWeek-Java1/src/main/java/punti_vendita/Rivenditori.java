package punti_vendita;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Rivenditori")
@Getter
@Setter
@NoArgsConstructor
public class Rivenditori {
	@Id
	@GeneratedValue
	private UUID rivenditori_Id;
	private String location;

	@ManyToOne
	@JoinColumn(name = "fk_riv_ID", referencedColumnName = "shopId")
	private Punti_vendita punti_vendita;

	public Rivenditori(String location) {
		this.location = location;
	}

	@Override
	public String toString() {
		return "Rivenditori [rivenditori_Id=" + rivenditori_Id + ", location=" + location + ", puntiVendita="
				+ punti_vendita + "]";
	}

}
