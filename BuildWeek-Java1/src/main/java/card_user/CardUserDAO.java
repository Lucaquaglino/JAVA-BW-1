package card_user;

import java.time.LocalDate;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class CardUserDAO {
	
	EntityManagerFactory emf = Persistence.createEntityManagerFactory("BuildWeek-Java1");
	EntityManager em = emf.createEntityManager();
	
	// CREA e SALVA NEL DATABASE combinazione utente-classe -----------------------------------
	public void saveUserCard(User user, Card card) {
		try {
			if(user != null && card != null) {
				em.getTransaction().begin();
				user.setCard(card);
				card.setUser(user);
				em.persist(user);
				em.persist(card);
				em.getTransaction().commit();
				System.out.println("*----------*");
				System.out.println("[utente]: " + user.getName() + " " + user.getSurname() + "\n[cardID]: " + card.getCardId() + "\nUTENTE SALVATO CON SUCCESSO!");
				System.out.println("*----------*");
			} 
		} catch(Exception e) {
			System.out.println(e.getStackTrace() + " C'è stato un errore in fase di caricamento");
		}
		
	}
	
	// RICERCA per NOME utente ----------------------------------------------------------------
	
	public void searchUserbyName(String str) {
		try {
			List<User> userList = em.createQuery("SELECT u FROM User u WHERE u.name = :name", User.class).setParameter("name", str).getResultList();
			if (userList.isEmpty()) {
	            System.out.println("Nessun utente con nome " + "'" + str + "'" + " è stato trovato, prova con un altro nome");
	        } else {
	            for (User user : userList) {
	                System.out.println(user);
	            }
	        }
		} catch(Exception e) {
			System.out.println(e.getStackTrace() + " Errore in fase di ricerca");
		}
	}
	
	// RICERCA per COGNOME utente ------------------------------------------------------------
	
		public void searchUserbySurname(String str) {
			try {
				List<User> userList = em.createQuery("SELECT u FROM User u WHERE u.surname = :surname", User.class).setParameter("surname", str).getResultList();
				if (userList.isEmpty()) {
		            System.out.println("Nessun utente con nome " + "'" + str + "'" + " è stato trovato, prova con un altro nome");
		        } else {
		            for (User user : userList) {
		                System.out.println(user);
		            }
		        }
			} catch(Exception e) {
				System.out.println(e.getStackTrace() + " Errore in fase di ricerca");
			}
		}
		
		// RICERCA utente per CARD ID  ------------------------------------------------------------
		
			public void searchCardByCardId(Long cardId) {
				try {
					List<Card> cardList = em.createQuery("SELECT c FROM Card c WHERE c.cardId = :cardId", Card.class).setParameter("cardId", cardId).getResultList();
					if (cardList.isEmpty()) {
			            System.out.println("Nessun carta corrisponde a questo ID " + "'" + cardId + "'" + " , prova con un altro ID");
			        } else {
			            for (Card card : cardList) {
			            	User user = card.getUser();
			                System.out.println("La carta ricercata con cardId: - " + card.getCardId() + "\nappartiene all'utente:         - " + user.getName() + " " + user.getSurname() + "\ndata di attivazione:           - " + card.getActivationDate() + "\nvalidità della carta:          - " + (card.isValid() ? "VALIDA" : "*da rinnovare*"));
			            }
			        }
				} catch(Exception e) {
					System.out.println(e.getStackTrace() + " Errore in fase di ricerca");
				}
			}
	
		// RINNOVA la scadenza della CARD  ------------------------------------------------------------
			
			public void cardRenewal(Long cardId, LocalDate date) {
			    try {
			        List<Card> cardList = em.createQuery("SELECT c FROM Card c WHERE c.cardId = :cardId", Card.class).setParameter("cardId", cardId).getResultList();
			        if (cardList.isEmpty()) {
			            System.out.println("Non è possibile effettuare il rinnovo della carta perché nessun elemento corrisponde a questo ID " + "'" + cardId + "'" + ", prova con un altro ID");
			        } else {
			            for (Card card : cardList) {
			                card.setActivationDate(date);
			                card.setExpireDate(date.plusYears(1));
			                card.setValid(true);
			                em.getTransaction().begin();
			                em.getTransaction().commit();
			                System.out.println("La validità della carta con Id: - " + card.getCardId() + "\nè stata estesa fino a:          - " + card.getExpireDate());
			            }
			        }
			    } catch (Exception e) {
			        System.out.println(e.getMessage() + " Errore in fase di ricerca");
			    }
			}

}
