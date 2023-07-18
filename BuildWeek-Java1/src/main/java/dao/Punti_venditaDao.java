package dao;

import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import Product.Product;
import Punti_vendita.Punti_vendita;

public class Punti_venditaDao {
	private final EntityManager em;

	public Punti_venditaDao(EntityManager em) {
		this.em = em;
	}
//metodo save

	public void savePunti_vendita(Punti_vendita ev) {
		EntityTransaction e = em.getTransaction();
		e.begin();
		em.persist(ev);
		e.commit();
		System.out.println("Pubblicazione salvata correttamente");
	}

//metodo find	

	public Punti_vendita findPunti_venditaById(UUID shopId) {

		Punti_vendita trova = em.find(Punti_vendita.class, shopId);
		return trova;
	}

//metodo delete

	public void findPunti_venditaByIdAndDelete(UUID shopId) {
		Punti_vendita trova = em.find(Punti_vendita.class, shopId);
		if (trova != null) {
			EntityTransaction t = em.getTransaction();
			t.begin();
			em.remove(trova);
			t.commit();
			System.out.println("Pubblicazione eliminata con successo");
		} else {
			System.out.println("Pubblicazione non trovato");
		}
	}

	public void emettiTiket(Product tik) {
		EntityTransaction e = em.getTransaction();
		ProductDao pd = new ProductDao(em);
		e.begin();
		try {
			em.persist(tik);
			e.commit();
			System.out.println("Tiket createo correttamente");

		} catch (Exception t) {
			System.out.println("errore");
		}

//		Tiket t = new Tiket(LocalDate.now().minusDays(5), LocalDate.now(), true, 15);
//		pd.saveProduct(t);
//		if (t != null) {
//			System.out.println("operazione conclusa");
//		}

	}

//	public Pubblicazione trovaPerIsbn(UUID isbn) {
//		TypedQuery<Pubblicazione> query = em.createQuery("SELECT e FROM Pubblicazione e WHERE e.isbn = :isbn",
//				Pubblicazione.class);
//		query.setParameter("isbn", isbn);
//		return query.getSingleResult();
//	}
//
//	public Set<Pubblicazione> trovaPerAnnoPubblicazione(LocalDate anno) {
//		TypedQuery<Pubblicazione> query = em
//				.createQuery("SELECT e FROM Pubblicazione e WHERE e.annoPubblicazione = :anno", Pubblicazione.class);
//		query.setParameter("anno", anno);
//		return (Set<Pubblicazione>) query.getResultList();
//	}
//
//	public Set<Libro> trovaPerAutore(String autore) {
//		TypedQuery<Libro> query = em.createQuery("SELECT l FROM Libro l WHERE l.autore = :autore", Libro.class);
//		query.setParameter("autore", autore);
//		return (Set<Libro>) query.getResultList();
//	}
//
//	public Set<Pubblicazione> trovaPerTitolo(String titolo) {
//		TypedQuery<Pubblicazione> query = em.createQuery("SELECT e FROM Pubblicazione e WHERE e.titolo LIKE :titolo",
//				Pubblicazione.class);
//		query.setParameter("titolo", "%" + titolo + "%");
//		return (Set<Pubblicazione>) query.getResultList();
//	}
}
