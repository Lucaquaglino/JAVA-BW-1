package app;

import java.util.Scanner;

import javax.persistence.EntityManager;

public class AdminInteraction {

	private EntityManager em;
	private Scanner sc;

	public AdminInteraction(EntityManager em, Scanner sc) {
		super();
		this.em = em;
		this.sc = sc;

		boolean exit = false;
		do {
			start();
			System.out.println("Vuoi effettuare altre operazioni? Y/N");
			exit = conferma();
		} while (!exit);

		System.out.println("Fine");
	}

	public void start() {
		String output = """
				Inserisci:
				1 per utenti
				2 per punti emissione
				3 per mezzi di trasporto
				4 per tratte
				""";
		System.out.println(output);
		int num = selectNumero(sc, 4);
		switch (num) {
		case 1:
			new UtentiEBiglietteriaInteraction(em, sc).startFunzionalitaUtenti();
			break;
		case 2:
			new UtentiEBiglietteriaInteraction(em, sc).startFunzionalitaBiglietti();
			break;
		case 3:
			new MezzieTratteInteraction(em, sc).startFunzionalitaMezzi();
			break;
		case 4:
			new MezzieTratteInteraction(em, sc).startFunzionalitaTratte();
			break;
		default:
			throw new IllegalStateException("Errori nel codice.");
		}

	}

	private boolean conferma() {
		String input = sc.nextLine().toLowerCase();
		if (input.equals("y")) {
			return false;
		} else if (input.equals("n")) {
			return true;
		} else {
			System.out.println("Valore inserito non valido.");
			return conferma();
		}
	}

	public int selectNumero(Scanner sc, int max) {
		if (sc.hasNextInt()) {
			int val = sc.nextInt();
			if (val < 1 || val > max) {
				System.out.println("Valore inserito non corretto! Riprovare.");
				return selectNumero(sc, max);
			}
			return val;
		} else {
			System.out.println("Inserire un valore numerico.");
			return selectNumero(sc, max);
		}
	}
}
