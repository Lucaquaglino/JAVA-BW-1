package dao;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import _enum.Periodicy;
import _enum.State;
import card_user.Card;
import product.Product;
import product.Subscription;
import product.Ticket;
import punti_vendita.Distributori;
import punti_vendita.Punti_vendita;

public class Punti_venditaDao {
	EntityManagerFactory emf = Persistence.createEntityManagerFactory("BuildWeek-Java1");
	EntityManager em = emf.createEntityManager();

//SALVA SHOPS ----------------------------------------------

	public void addShops(Punti_vendita ev) {
		EntityTransaction e = em.getTransaction();
		e.begin();
		em.persist(ev);
		e.commit();
		System.out.println("Punto vendita creato correttamente");
	}

//TROVA SHOPS by ID ----------------------------------------------	

	public Punti_vendita findShopsById(String strId) {
		UUID shopId = UUID.fromString(strId);
		Punti_vendita trova = em.find(Punti_vendita.class, shopId);
		if (trova != null) {
			System.out.println("Shop trovato con id: " + strId + " presso " + trova.getLocation());
		} else {
			System.out.println("Non esiste alcun shop con questo id: " + strId);
		}
		return trova;
	}
	
//ELENCO SHOPS ----------------------------------------------
	public void shopList() {
		List<Punti_vendita> list = em.createQuery("SELECT shop FROM Punti_vendita shop", Punti_vendita.class).getResultList();
		for (Punti_vendita punti_vendita : list) {
			System.out.println(punti_vendita);
		}
	}
	
//ELENCO SHOPS by LOCATION ----------------------------------------------
	public void shopListByLocation(String location) {
		List<Punti_vendita> list = em.createQuery("SELECT shop FROM Punti_vendita shop WHERE shop.location = :location", Punti_vendita.class).setParameter("location", location).getResultList();
		for (Punti_vendita shop : list) {
			System.out.println(shop);
		}
	}

//RIMUOVI SHOP ----------------------------------------------

	public void removeShopsById(String shopStrId) {
		UUID shopId = UUID.fromString(shopStrId);
		Punti_vendita trova = em.find(Punti_vendita.class, shopId);
		if (trova != null) {
			EntityTransaction t = em.getTransaction();
			t.begin();
			em.remove(trova);
			t.commit();
			System.out.println("Punto vendita eliminato con successo");
		} else {
			System.out.println("Punto vendita non trovato");
		}
	}
	
//EMETTI TICKET --------------------------------------------
	public void emettiTicket(String shopStrId) {
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
	
//EMETTI ABBONAMENTO ---------------------------------------
	public void emettiSubscription(String shopStrId, long cardId, Periodicy periodicity) {
	    UUID shopId = UUID.fromString(shopStrId);
	    Punti_vendita shop = em.find(Punti_vendita.class, shopId);
	    System.out.println(shop);
	    
	    Card card = em.find(Card.class, cardId);
	    

	    try {
	        if (shop != null && card !=null) {
	            if (shop instanceof Distributori) {
	                Distributori distributor = (Distributori) shop;
	                //----
	                if (distributor.getState() == State.OUTOFSERVICE) {
	                	System.out.println("Impossibile effettuare l'ammonamento. Lo shop non è attivo.");
	                }
	                else {
	                	if(card.isValid()) {
	                		Subscription sub = new Subscription(periodicity);
	                		sub.setShopId(shop);
	                		sub.setCardId(card);
	                		em.getTransaction().begin();
	                		em.persist(sub);
	                		em.getTransaction().commit();
	                		System.out.println("L'abbonamneto è stato emesso correttamente con codice: " + sub.getProductId());
	                	} else {
	                		System.out.println("La tua card non è attiva, devi rinnovarla");
	                	}
	                }
	                //----
	             } else {
	            	 if(card.isValid()) {
	                		Subscription sub = new Subscription(periodicity);
	                		sub.setShopId(shop);
	                		sub.setCardId(card);
	                		em.getTransaction().begin();
	                		em.persist(sub);
	                		em.getTransaction().commit();
	                		System.out.println("L'abbonamneto è stato emesso correttamente con codice: " + sub.getProductId());
	                	} else {
	                		System.out.println("La tua card non è attiva, devi rinnovarla");
	                	}
	             }
	            
	        } else {
	            System.out.println("Nessun punto vendita trovato con questo ID: " + shopStrId);
	        }
	    } catch (Exception e) {
	        em.getTransaction().rollback();
	        System.out.println("Errore avvenuto in fase salvataggio abbonamento " + e.getMessage());
	    }
	}
	
	// CONTROLLA VALIDITA' ABBONAMENTO
    public void checkSubscriptionValidity(Long cardId) {
        List<Subscription> subscriptions = em
                .createQuery("SELECT sub FROM Subscription sub WHERE sub.cardId.cardId = :cardId", Subscription.class)
                .setParameter("cardId", cardId).getResultList();
     

        if (!subscriptions.isEmpty()) {
            Subscription subscription = subscriptions.get(0);
            if (subscription.getIsActive()) {
                System.out.println("L'abbonamento è attivo");
            } else {
                System.out.println("Purtroppo l'abbonamento è scaduto, fanne uno nuovo");
            }
        } else {
            System.out.println("Nessun abbonamento trovato per la carta con ID: " + cardId);
        }
    }
    // CONTA PRODOTTI EMESSI BY SHOP
    public void printTicketsAndPeriodByShopAndDate(String shopId1, LocalDate startDate, LocalDate endDate) {
        try {
            UUID shopId = UUID.fromString(shopId1);
            String queryStr = "SELECT COUNT(t) FROM Product t WHERE t.shopId.shopId = :shopId "
                    + "AND (t.emissionDate BETWEEN :startDate AND :endDate)";

            TypedQuery<Long> query = em.createQuery(queryStr, Long.class);
            query.setParameter("shopId", shopId);
            query.setParameter("startDate", startDate);
            query.setParameter("endDate", endDate);

            Long count = query.getSingleResult();

            System.out.println("Numero di biglietti emessi: " + count);

            System.out.println("Periodo di riferimento: " + startDate + " - " + endDate);

            System.out.println("ID del punto vendita: " + shopId1);

        } catch (IllegalArgumentException e) {

            System.out.println("Errore: Il formato dell'ID del punto vendita non è valido.");
        } catch (NoResultException e) {
            System.out.println("Nessun biglietto trovato per il periodo specificato e l'ID del punto vendita.");
        }
    }
    
}
