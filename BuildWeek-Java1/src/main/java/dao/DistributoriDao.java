package dao;

import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import punti_vendita.Distributori;

public class DistributoriDao {
	private final EntityManager em;

	public DistributoriDao(EntityManager em) {
		this.em = em;
	}
//metodo save

	public void creaDistributore(Distributori ev) {
		EntityTransaction e = em.getTransaction();
		e.begin();
		em.persist(ev);
		e.commit();
		System.out.println("Distributore creato correttamente");
	}

//metodo find	

	public Distributori findDistributoreById(UUID Id) {

		Distributori trova = em.find(Distributori.class, Id);
		return trova;
	}

//metodo delete

	public void findDistributoreByIdAndDelete(UUID Id) {
		Distributori trova = em.find(Distributori.class, Id);
		if (trova != null) {
			EntityTransaction t = em.getTransaction();
			t.begin();
			em.remove(trova);
			t.commit();
			System.out.println("Distributore eliminato con successo");
		} else {
			System.out.println("Distributore non trovato");
		}
	}
}
