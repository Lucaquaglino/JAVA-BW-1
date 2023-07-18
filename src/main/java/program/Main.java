package program;

import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import util.JpaUtil;

public class Main {

	public static void main(String[] args) {

		EntityManagerFactory emf = JpaUtil.getEntityManagerFactory();
		EntityManager em = emf.createEntityManager();
		Scanner sc = new Scanner(System.in);

		try {

		} finally {
			em.close();

			emf.close();
			sc.close();
		}

	}

}
