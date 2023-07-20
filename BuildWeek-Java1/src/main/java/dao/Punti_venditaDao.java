package dao;

import java.time.LocalDate;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import _enum.State;
import product.Product;
import product.Ticket;
import punti_vendita.Distributori;
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
	    Punti_vendita shop = em.find(Punti_vendita.class, shopId);

	    try {
	        if (shop != null) {
	            if (shop instanceof Distributori) {
	                Distributori distributor = (Distributori) shop;
	                if (distributor.getState() == State.OUTOFSERVICE) {
	                	System.out.println("Impossibile emettere il ticket. Lo shop non è attivo.");
	                }
	                else {
		            	 Ticket ticket = new Ticket();
		            	 ticket.setShopId(shop);
		            	 em.getTransaction().begin();
		            	 em.persist(ticket);
		            	 em.getTransaction().commit();
		            	 System.out.println("Ticket emesso correttamente con codice: " + ticket.getProductId());
	                }
	             } else {
	            	 Ticket ticket = new Ticket();
	            	 ticket.setShopId(shop);
	            	 em.getTransaction().begin();
	            	 em.persist(ticket);
	            	 em.getTransaction().commit();
	            	 System.out.println("Ticket emesso correttamente con codice: " + ticket.getProductId());
	             }
	            
	        } else {
	            System.out.println("Nessun punto vendita trovato con questo ID: " + shopStrId);
	        }
	    } catch (Exception e) {
	        em.getTransaction().rollback();
	        System.out.println("Errore avvenuto in fase emissione ticket " + e.getMessage());
	    }
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
