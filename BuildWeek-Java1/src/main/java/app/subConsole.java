package app;

import java.util.Scanner;

import javax.persistence.EntityManager;

public class subConsole extends ConsoleInteraction {
	private EntityManager em;

	public subConsole(Scanner sc, EntityManager em) {
		super(sc);
		this.em = em;
	}

}
