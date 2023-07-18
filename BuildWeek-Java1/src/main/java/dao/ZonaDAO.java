package dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import entities.Zona;

public class ZonaDAO {
	private final EntityManager em;

	public ZonaDAO(EntityManager em) {
		this.em = em;
	}

	public void save(Zona z) {
		EntityTransaction t = this.em.getTransaction();
		try {
			t.begin();
			em.persist(z);

			t.commit();

		} catch (Exception e) {
			// log.error(e.getMessage());
			e.printStackTrace();
		}
	}

	public void delete(Zona z) {
		EntityTransaction t = this.em.getTransaction();
		try {
			t.begin();
			em.remove(z);

			t.commit();

		} catch (Exception e) {
			// log.error(e.getMessage());
			e.printStackTrace();
		}
	}

	public Zona getById(Long id) {
		return em.find(Zona.class, id);
	}

	public void refresh(Zona z) {
		em.refresh(z);
	}
}
