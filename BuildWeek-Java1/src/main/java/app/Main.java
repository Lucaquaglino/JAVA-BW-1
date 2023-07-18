package app;

import java.time.LocalDate;
import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import card_User.Card;
import card_User.User;
import utils.JpaUtil;

public class Main {

	public static void main(String[] args) {
		EntityManagerFactory emf = JpaUtil.getEntityManagerFactory();
		EntityManager em = emf.createEntityManager();
		Scanner sc = new Scanner(System.in);
		
//		User u1 = new User("Flavio", "MM", LocalDate.now());
//		Card card1 = new Card(LocalDate.of(2022, 12,1));
		
		
		
		
		

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
