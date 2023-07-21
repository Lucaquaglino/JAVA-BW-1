package dao;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

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
			em.getTransaction().commit();

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

	public Duration getTempoPercorrenza(Tratta tr) {
		TypedQuery<Duration> q = em.createNamedQuery("tempoPerTratta", Duration.class);
		q.setParameter("paramId", tr.getId());

		return q.getSingleResult();
	}

	public List<Tratta> getTratte() {
		return em.createNamedQuery("all", Tratta.class).getResultList();
	}

	public static List<Tratta> getDatiGenerati() {
		List<Zona> zone = ZonaDAO.getDatiGenerati();
		List<Tratta> tratte = new ArrayList<Tratta>();
		tratte.add(new Tratta("tratta-1", zone.get(0), zone.get(1), LocalTime.of(9, 30), LocalTime.of(12, 30),

				Set.of(new Tappa(zone.get(0), null, 0, Duration.ofMinutes(40l)),
						new Tappa(zone.get(2), null, 1, Duration.ofMinutes(20l)),
						new Tappa(zone.get(3), null, 2, Duration.ofMinutes(30l)),
						new Tappa(zone.get(1), null, 3, Duration.ofMinutes(50l)))));
		tratte.add(new Tratta("tratta-2", zone.get(1), zone.get(2), LocalTime.of(8, 30), LocalTime.of(13, 30),
				// Duration.ofHours(4l),
				Set.of(new Tappa(zone.get(1), null, 0, Duration.ofMinutes(30l)),
						new Tappa(zone.get(3), null, 1, Duration.ofMinutes(10l)),
						new Tappa(zone.get(5), null, 2, Duration.ofMinutes(130l)),
						new Tappa(zone.get(3), null, 4, Duration.ofMinutes(80l)),
						new Tappa(zone.get(2), null, 3, Duration.ofMinutes(30l)))));
		tratte.add(new Tratta("tratta-3", zone.get(4), zone.get(3), LocalTime.of(7, 30), LocalTime.of(18, 30),
				// Duration.ofHours(8),
				Set.of(new Tappa(zone.get(5), null, 0, Duration.ofMinutes(30l)),
						new Tappa(zone.get(6), null, 1, Duration.ofMinutes(50l)),
						new Tappa(zone.get(5), null, 2, Duration.ofMinutes(35l)),
						new Tappa(zone.get(3), null, 4, Duration.ofMinutes(45l)),
						new Tappa(zone.get(1), null, 3, Duration.ofMinutes(15l)))));
		return tratte;
	}

	public boolean presente(Tratta t) {
		TypedQuery<Tratta> qt = em.createQuery(
				"SELECT tr FROM  Tratta tr WHERE tr.partenza.nome = :param1 AND tr.capolinea.nome = :param2",
				Tratta.class);
		qt.setParameter("param1", t.getPartenza().getNome());
		qt.setParameter("param2", t.getCapolinea().getNome());
		return qt.getResultList().size() > 0;

	}
}
