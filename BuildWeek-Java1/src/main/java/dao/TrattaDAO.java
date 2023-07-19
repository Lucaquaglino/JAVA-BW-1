package dao;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import mezzo_tratta.Tappa;
import mezzo_tratta.Tratta;
import mezzo_tratta.Zona;

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
		return LocalTime.of(tr.getTempoDiPercorrenza().toHoursPart(), tr.getTempoDiPercorrenza().toMinutesPart());
	}

	public List<Tratta> getTratte() {
		return em.createNamedQuery("all", Tratta.class).getResultList();
	}

	public static List<Tratta> getDatiGenerati() {
		List<Zona> zone = ZonaDAO.getDatiGenerati();
		List<Tratta> tratte = new ArrayList<Tratta>();
		tratte.add(new Tratta("tratta-1", zone.get(0), zone.get(1), LocalTime.of(9, 30), LocalTime.of(12, 30),
				Duration.ofHours(5),
				Set.of(new Tappa(zone.get(0), null, 0), new Tappa(zone.get(2), null, 1),
						new Tappa(zone.get(3), null, 2), new Tappa(zone.get(1), null, 3))));
		tratte.add(new Tratta("tratta-2", zone.get(1), zone.get(2), LocalTime.of(8, 30), LocalTime.of(13, 30),
				Duration.ofHours(4l),
				Set.of(new Tappa(zone.get(1), null, 0), new Tappa(zone.get(3), null, 1),
						new Tappa(zone.get(5), null, 2), new Tappa(zone.get(3), null, 4),
						new Tappa(zone.get(2), null, 3))));
		tratte.add(new Tratta("tratta-3", zone.get(4), zone.get(3), LocalTime.of(7, 30), LocalTime.of(18, 30),
				Duration.ofHours(8),
				Set.of(new Tappa(zone.get(5), null, 0), new Tappa(zone.get(6), null, 1),
						new Tappa(zone.get(5), null, 2), new Tappa(zone.get(3), null, 4),
						new Tappa(zone.get(1), null, 3))));
		return tratte;
	}
}
