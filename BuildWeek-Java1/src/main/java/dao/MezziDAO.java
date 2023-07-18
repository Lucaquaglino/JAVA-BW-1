package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import mezzo_tratta.MezzoDiTrasporto;
import mezzo_tratta.Servizio;
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

	public MezzoDiTrasporto getById(Long id) {
		return em.find(MezzoDiTrasporto.class, id);
	}

	public void refresh(MezzoDiTrasporto m) {
		em.refresh(m);
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
}
