package app;

import java.time.LocalDate;
import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import _enum.Periodicy;
import _enum.State;
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
		try {

		} finally {
			em.close();
			emf.close();
			sc.close();
		}

	}

}
