package app;

import java.util.Scanner;

import javax.persistence.EntityManager;

import dao.MezziDAO;

public class MainConsoleInteraction extends ConsoleInteraction {

	private EntityManager em;
	private Scanner sc;

	public MainConsoleInteraction(EntityManager em, Scanner sc) {
		super(sc);
		this.em = em;
		this.sc = sc;

		MezziDAO.salvaDatiGenerati(em);

		start();
	}

	private void start() {
		String output = """
				Inserisci
				1 per utente
				2 per amministratore
				""";
		System.out.println(output);
		int num = selectNumero(sc, 2);
		switch (num) {
		case 1:
			System.out.println("Selezione per utente");

			break;
		case 2:
			System.out.println("Selezione per amministratore");
			new AdminInteraction(em, sc);
			break;
		default:
			throw new IllegalStateException("Errore nel codice");
		}
	}

}
