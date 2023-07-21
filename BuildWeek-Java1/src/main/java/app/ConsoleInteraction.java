package app;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public abstract class ConsoleInteraction {

	private Scanner sc;

	public ConsoleInteraction(Scanner sc) {
		super();
		this.sc = sc;
	}

	protected int selectNumero(Scanner sc, int max) {
		if (sc.hasNextInt()) {
			int val = sc.nextInt();
			if (val < 1 || val > max) {
				System.out.println("Valore inserito non corretto! Riprovare.");
				return selectNumero(sc, max);
			}
			sc.nextLine();
			return val;
		} else {
			sc.next();
			System.out.println("Inserire un valore numerico.");
			return selectNumero(sc, max);
		}
	}

	protected boolean conferma() {
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

	protected LocalDate selectDate() {
		try {
			return LocalDate.parse(sc.nextLine(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		} catch (DateTimeParseException e) {
			System.out.println("Valore immesso non valido riprovare." + e.getMessage());
			return selectDate();
		}
	}
}
