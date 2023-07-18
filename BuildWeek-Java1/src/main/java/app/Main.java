package app;

import java.time.LocalDate;
import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import Enum.State;
import Product.Tiket;
import Punti_vendita.Distributori;
import dao.ProductDao;
import dao.Punti_venditaDao;
import card_User.Card;
import card_User.User;
import utils.JpaUtil;

public class Main {

	public static void main(String[] args) {
		EntityManagerFactory emf = JpaUtil.getEntityManagerFactory();
		EntityManager em = emf.createEntityManager();
		Punti_venditaDao pv = new Punti_venditaDao(em);
		ProductDao pd = new ProductDao(em);
		Scanner sc = new Scanner(System.in);
// instanziare distributori e tiket inizio parte Andrea
		Distributori ds = new Distributori("Venezia", State.OUTOFSERVICE);
		pv.savePunti_vendita(ds);
		Tiket tik1 = new Tiket(LocalDate.now().minusDays(5), LocalDate.now(), true, 20);
		pv.emettiTiket(tik1);
		
//		User u1 = new User("Flavio", "MM", LocalDate.now());
//		Card card1 = new Card(LocalDate.of(2022, 12,1));
		
		
		
		
		

		// instanziare distributori e tiket fine parte Andrea
		try {
			
//			em.getTransaction().begin();
//			em.persist(u1);
//			em.persist(card1);
//			em.getTransaction().commit();
//			System.out.println(u1);
//			System.out.println(card1);

		} finally {
			em.close();
			emf.close();
			sc.close();
		}

	}

}
