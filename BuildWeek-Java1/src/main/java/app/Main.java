package app;

import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import _enum.State;
import dao.DistributoriDao;
import dao.ProductDao;
import dao.Punti_venditaDao;
import dao.RivenditoriDao;
import punti_vendita.Distributori;
import punti_vendita.Punti_vendita;
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
// instanziare distributori e tiket inizio parte Andrea
		Punti_vendita puntoVendita = new Punti_vendita("MIlano");
		pv.savePunti_vendita(puntoVendita);
		Distributori dis = new Distributori(State.ACTIVE, "Bergamo");
		ds.creaDistributore(dis);
		Rivenditori riv = new Rivenditori("Bergamo sud");
		rv.creaRivenditore(riv);
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
