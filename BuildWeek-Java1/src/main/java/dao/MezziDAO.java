package dao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import mezzo_tratta.Autobus;
import mezzo_tratta.MezzoDiTrasporto;
import mezzo_tratta.Servizio;
import mezzo_tratta.StatoMezzo;
import mezzo_tratta.Tram;
import mezzo_tratta.Tratta;
import mezzo_tratta.Zona;

public class MezziDAO {
	private final EntityManager em;

	public MezziDAO(EntityManager em) {
		this.em = em;
	}

	public void save(MezzoDiTrasporto m) {
		EntityTransaction t = this.em.getTransaction();
		try {
			t.begin();
			em.persist(m);

			t.commit();

		} catch (Exception e) {
			// log.error(e.getMessage());
			e.printStackTrace();
		}
	}

	public void save(Servizio s) {
		EntityTransaction t = this.em.getTransaction();
		try {
			t.begin();
			em.persist(s);

			t.commit();

		} catch (Exception e) {
			// log.error(e.getMessage());
			e.printStackTrace();
		}
	}

	public void delete(MezzoDiTrasporto m) {
		EntityTransaction t = this.em.getTransaction();
		try {
			t.begin();
			em.remove(m);

			t.commit();

		} catch (Exception e) {
			// log.error(e.getMessage());
			e.printStackTrace();
		}
	}

	public void delete(Servizio s) {
		EntityTransaction t = this.em.getTransaction();
		try {
			t.begin();
			em.remove(s);

			t.commit();

		} catch (Exception e) {
			// log.error(e.getMessage());
			e.printStackTrace();
		}
	}

	public MezzoDiTrasporto getById(Long id) {
		return em.find(MezzoDiTrasporto.class, id);
	}

	public Servizio getServizioById(Long id) {
		return em.find(Servizio.class, id);
	}

	public void refresh(MezzoDiTrasporto m) {
		em.refresh(m);
	}

	public void refresh(Servizio s) {
		em.refresh(s);
	}

	public List<Servizio> getStatoServizioPerMezzo(MezzoDiTrasporto m) {
		TypedQuery<Servizio> tq = em.createNamedQuery("tracciaPeriodiServizio", Servizio.class);
		tq.setParameter("paramId", m.getId());
		return tq.getResultList();
	}

	public int getPercorrenzaTappa(Zona z, MezzoDiTrasporto m) {
		TypedQuery<Integer> q = em.createNamedQuery("ripetizioniTappa", Integer.class);
		q.setParameter("paramMezzo", m.getId());
		q.setParameter("paramTappa", z.getId());
		return q.getSingleResult();
	}

	public static List<MezzoDiTrasporto> getDatiGenerati() {
		List<Tratta> tratte = TrattaDAO.getDatiGenerati();
		List<MezzoDiTrasporto> mezzi = new ArrayList<MezzoDiTrasporto>();
		mezzi.add(new Tram("Tram-1", "Fiat", (short) 80, (short) 3, tratte.get(0)));
		mezzi.add(new Autobus("Autobus-1", "Volswaghen", (short) 120, (short) 8, tratte.get(2)));
		mezzi.add(new Autobus("Autobus-2", "Volswaghen", (short) 120, (short) 8, tratte.get(1)));

		Servizio s1 = new Servizio(StatoMezzo.IN_MANUTENZIONE, LocalDate.of(2020, 10, 10), mezzi.get(0));
		s1.setDataFine(LocalDate.of(2021, 1, 2));
		Servizio s2 = new Servizio(StatoMezzo.IN_SERVIZIO, LocalDate.of(2021, 1, 2), mezzi.get(0));
		mezzi.get(0).setServizi(Set.of(s1, s2));

		Servizio s3 = new Servizio(StatoMezzo.IN_MANUTENZIONE, LocalDate.of(2020, 10, 10), mezzi.get(1));
		s3.setDataFine(LocalDate.of(2021, 1, 2));
		Servizio s4 = new Servizio(StatoMezzo.IN_SERVIZIO, LocalDate.of(2021, 1, 2), mezzi.get(1));
		mezzi.get(1).setServizi(Set.of(s3, s4));

		Servizio s5 = new Servizio(StatoMezzo.IN_MANUTENZIONE, LocalDate.of(2020, 10, 10), mezzi.get(2));
		s3.setDataFine(LocalDate.of(2021, 1, 2));
		Servizio s6 = new Servizio(StatoMezzo.IN_SERVIZIO, LocalDate.of(2021, 1, 2), mezzi.get(2));
		mezzi.get(2).setServizi(Set.of(s5, s6));

		return mezzi;
	}

	public static void salvaDatiGenerati(EntityManager em) {
		List<MezzoDiTrasporto> mezzi = getDatiGenerati();
		mezzi.stream().map(m -> m.getTratta()).flatMap(t -> Stream.of(t.getCapolinea(), t.getPartenza())).toList()
				.forEach(new ZonaDAO(em)::save);
		mezzi.stream().flatMap(m -> m.getTratta().getTappe().stream()).map(t -> t.getZona()).toList()
				.forEach(new ZonaDAO(em)::save);
		mezzi.stream().map(m -> m.getTratta()).toList().forEach(new TrattaDAO(em)::save);
		mezzi.forEach(new MezziDAO(em)::save);
	}

}
