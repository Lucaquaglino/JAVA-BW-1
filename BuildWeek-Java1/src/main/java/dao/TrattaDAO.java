package dao;

import java.time.LocalTime;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import mezzo_tratta.Tratta;

public class TrattaDAO {
	private final EntityManager em;

	public TrattaDAO(EntityManager em) {
		this.em = em;
	}

	public void save(Tratta tr) {
		EntityTransaction t = this.em.getTransaction();
		try {
			t.begin();
			em.persist(tr);

			t.commit();

		} catch (Exception e) {
			// log.error(e.getMessage());
			e.printStackTrace();
		}
	}

	public void delete(Tratta tr) {
		EntityTransaction t = this.em.getTransaction();
		try {
			t.begin();
			em.remove(tr);

			t.commit();

		} catch (Exception e) {
			// log.error(e.getMessage());
			e.printStackTrace();
		}
	}

	public Tratta getById(Long id) {
		return em.find(Tratta.class, id);
	}

	public void refresh(Tratta tr) {
		em.refresh(tr);
	}

	public LocalTime getTempoPercorrenza(Tratta tr) {
		return LocalTime.of(tr.getTempoDiPercorrenza().getHours(), tr.getTempoDiPercorrenza().getMinutes());
	}

	public List<Tratta> getTratte() {
		return em.createNamedQuery("all", Tratta.class).getResultList();
	}
}
