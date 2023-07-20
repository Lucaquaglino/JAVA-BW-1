package dao;

import java.time.LocalDate;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import product.Product;
import product.Ticket;
import punti_vendita.Punti_vendita;

public class Punti_venditaDao {
	EntityManagerFactory emf = Persistence.createEntityManagerFactory("BuildWeek-Java1");
	EntityManager em = emf.createEntityManager();

//metodo save

	public void savePunti_vendita(Punti_vendita ev) {
		EntityTransaction e = em.getTransaction();
		e.begin();
		em.persist(ev);
		e.commit();
		System.out.println("Punto vendita creato correttamente");
	}

//metodo find	

	public Punti_vendita findPunti_venditaById(String strId) {
		UUID shopId = UUID.fromString(strId);
		Punti_vendita trova = em.find(Punti_vendita.class, shopId);
		if (trova != null) {
			System.out.println("Shop trovato con id: " + strId + " presso " + trova.getLocation());
		} else {
			System.out.println("Non esiste alcun shop con questo id: " + strId);
		}
		return trova;
	}

//metodo delete

	public void findPunti_venditaByIdAndDelete(String shopStrId) {
		UUID shopId = UUID.fromString(shopStrId);
		Punti_vendita trova = em.find(Punti_vendita.class, shopId);
		if (trova != null) {
			EntityTransaction t = em.getTransaction();
			t.begin();
			em.remove(trova);
			t.commit();
			System.out.println("Punto vendita  eliminato con successo");
		} else {
			System.out.println("Punto vendita  non trovato");
		}
	}

	public void emettiTiket(String shopStrId) {
		UUID shopId = UUID.fromString(shopStrId);
		
		Ticket ticket = new Ticket(LocalDate.now(), shopId);
		ticket.setShopId(shopId);
		System.out.println(ticket.getShopId());
		
		try {
			em.getTransaction().begin();
			em.persist(ticket);
			em.getTransaction().commit();
			System.out.println("Tiket emesso correttamente con codice: " + ticket.getProductId());

		} catch (Exception e) {
			em.getTransaction().rollback();
			System.out.println("Errore avvenuto in fase emissione ticket " + e.getMessage());
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
