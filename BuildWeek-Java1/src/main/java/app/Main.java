package app;

import java.time.LocalDate;
import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import _enum.Periodicy;
import _enum.State;
import dao.DistributoriDao;
import card_user.Card;
import card_user.CardUserDAO;
import card_user.User;
import dao.ProductDao;
import dao.Punti_venditaDao;
import dao.RivenditoriDao;
import product.Subscription;
import punti_vendita.Distributori;
import punti_vendita.Rivenditori;
import utils.JpaUtil;

public class Main {

	public static void main(String[] args) {
		EntityManagerFactory emf = JpaUtil.getEntityManagerFactory();
		EntityManager em = emf.createEntityManager();
		
		
		
		CardUserDAO userCardOperation = new CardUserDAO(); 
		User u1 = new User("Flavio", "Mammoliti", LocalDate.of(1989,05,19));
		Card card1 = new Card(LocalDate.of(2022, 12,1));
		
		User u2 = new User("Andrea", "DepasQ", LocalDate.of(1994,03,22));
		Card card2 = new Card(LocalDate.now());
		
		User u3 = new User("Luca", "Quaglino", LocalDate.of(1991,07,11));
		Card card3 = new Card(LocalDate.of(2021, 11, 7));
		
		User u4 = new User("Marco", "DeNic", LocalDate.of(1988, 12, 25));
		Card card4 = new Card(LocalDate.now()); 
		
		User u5 = new User("Flavio", "Gimmy", LocalDate.of(2001, 9, 17));
		Card card5 = new Card(LocalDate.now());
		
		
		Punti_venditaDao shopsOperations = new Punti_venditaDao();
		Distributori dist1 = new Distributori("Milano", State.ACTIVE);
		Distributori dist2 = new Distributori("Torino", State.OUTOFSERVICE);
		
		Rivenditori shop1 = new Rivenditori("Roma");
		Rivenditori shop2 = new Rivenditori("Forte dei Marmi");
		
		
		
		
		
		

		
		try {
			
//			shopsOperations.savePunti_vendita(shop1);
//			shopsOperations.savePunti_vendita(shop2);
//			shopsOperations.savePunti_vendita(dist1);
//			shopsOperations.savePunti_vendita(dist2);
			
			
			System.out.println();
			
			shopsOperations.emettiTiket("743a8275-6228-4fab-a150-10eff6a70f8b");
//			userCardOperation.saveUserCard(u1, card1);
//			userCardOperation.saveUserCard(u2, card2);
//			userCardOperation.saveUserCard(u3, card3);
//			userCardOperation.saveUserCard(u4, card4);
//			userCardOperation.saveUserCard(u5, card5);
			
//			userCardOperation.searchUserbyName("Flavio");
//			userCardOperation.searchUserbySurname("Mammoliti");
			
//			userCardOperation.searchUserbyName("Luca");
//			System.out.println();
//			userCardOperation.searchCardByCardId(8371018202084L);
//			
//			System.out.println();
//			userCardOperation.cardRenewal(8371018202084L, LocalDate.now());
//			System.out.println();
////			
//			userCardOperation.searchCardByCardId(8371018202084L);
//			userCardOperation.removeUserById("ba5ca93c-9c97-43cd-89e9-193724032d94");
			
			
			
		

		} finally {
			em.close();
			emf.close();
			em.close();
		}

	} 
}


