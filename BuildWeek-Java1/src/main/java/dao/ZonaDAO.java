package dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import mezzo_tratta.Zona;

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

	public static List<Zona> getDatiGenerati() {
		List<Zona> zone = new ArrayList<Zona>();
		zone.add(new Zona("Trastevere"));
		zone.add(new Zona("Colosseo"));
		zone.add(new Zona("Rebibbia"));
		zone.add(new Zona("Conca d'oro"));
		zone.add(new Zona("Piazza Bologna"));
		zone.add(new Zona("Verano"));
		zone.add(new Zona("Largo Argentino"));
		zone.add(new Zona("Tuscolana"));
		zone.add(new Zona("Termini"));
		zone.add(new Zona("Tiburtina"));

		return zone;
	}
}
