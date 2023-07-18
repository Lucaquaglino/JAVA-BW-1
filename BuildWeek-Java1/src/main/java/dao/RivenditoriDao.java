package dao;

import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import punti_vendita.Rivenditori;

public class RivenditoriDao {
	private final EntityManager em;

	public RivenditoriDao(EntityManager em) {
		this.em = em;
	}
//metodo save

	public void creaRivenditore(Rivenditori ev) {
		EntityTransaction e = em.getTransaction();
		e.begin();
		em.persist(ev);
		e.commit();
		System.out.println("Rivenditore creato correttamente");
	}

//metodo find	

	public Rivenditori findRivenditoreById(UUID Id) {

		Rivenditori trova = em.find(Rivenditori.class, Id);
		return trova;
	}

//metodo delete

	public void findRivenditoreByIdAndDelete(UUID Id) {
		Rivenditori trova = em.find(Rivenditori.class, Id);
		if (trova != null) {
			EntityTransaction t = em.getTransaction();
			t.begin();
			em.remove(trova);
			t.commit();
			System.out.println("Rivenditore eliminato con successo");
		} else {
			System.out.println("Rivenditore non trovato");
		}
	}
}
