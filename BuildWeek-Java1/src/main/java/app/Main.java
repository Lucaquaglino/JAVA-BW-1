package app;

import java.time.LocalDate;
import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import _enum.Periodicy;
import _enum.State;
import card_user.Card;
import card_user.CardUserDAO;
import card_user.User;
import dao.DistributoriDao;
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
		Punti_venditaDao pv = new Punti_venditaDao(em);
		ProductDao pd = new ProductDao(em);
		DistributoriDao ds = new DistributoriDao(em);
		RivenditoriDao rv = new RivenditoriDao(em);
		Scanner sc = new Scanner(System.in);

		Distributori d = new Distributori("Milano", State.ACTIVE);
		ds.creaDistributore(d);
		Rivenditori r = new Rivenditori();
//		rv.creaRivenditore(r);
		Subscription sub = new Subscription(LocalDate.now(), d, Periodicy.MONTHLY, true);
		pd.saveProduct(sub);
		// try {
		
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
		
		
		
		

		
		try {
			
//			userCardOperation.saveUserCard(u1, card1);
//			userCardOperation.saveUserCard(u2, card2);
//			userCardOperation.saveUserCard(u3, card3);
//			userCardOperation.saveUserCard(u4, card4);
//			userCardOperation.saveUserCard(u5, card5);
			
//			userCardOperation.searchUserbyName("Flavio");
//			userCardOperation.searchUserbySurname("Mammoliti");
			
			userCardOperation.searchUserbyName("Luca");
			
//			System.out.println();
//			userCardOperation.cardRenewal(5242719475039L, LocalDate.now());
//			System.out.println();
//			
			userCardOperation.searchCardByCardId(5242719475039L);
			
			
			
		

		} finally {
			em.close();
			emf.close();
			em.close();
		}

	}

}

