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

		// instanziare distributori e tiket fine parte Andrea
		try {

		} finally {
			em.close();
			emf.close();
			sc.close();
		}

	}

}
