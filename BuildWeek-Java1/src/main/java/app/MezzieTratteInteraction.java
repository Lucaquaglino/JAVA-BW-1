package app;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import dao.MezziDAO;
import dao.TrattaDAO;
import mezzo_tratta.Autobus;
import mezzo_tratta.MezzoDiTrasporto;
import mezzo_tratta.Tappa;
import mezzo_tratta.Tram;
import mezzo_tratta.Tratta;
import mezzo_tratta.Zona;

public class MezzieTratteInteraction {

	private EntityManager em;
	private Scanner sc;

	public MezzieTratteInteraction(EntityManager em, Scanner sc) {
		super();
		this.em = em;
		this.sc = sc;
	}

	public void startFunzionalitàTratte() {

		String elenco = """
				Inserisci:
				1 per gestione tratte
				2 per info tratte
				""";
		System.out.println(elenco);
		int num = selectNumero(sc, 2);
		switch (num) {
		case 1:
			gestioneTratte();
			break;
		case 2:
			infoTratte();
			break;
		default:
			System.out.println("Errore riprovare.");
		}
	}

	private void gestioneTratte() {
		String elenco = """
				Inserisci:
				1 per aggiungere nuova tratta
				2 per rimuovere tratta esistente
				""";
		System.out.println(elenco);
		int num = selectNumero(sc, 2);
		switch (num) {
		case 1:
			aggiungiTratta();
			break;
		case 2:
			rimuoviTratta();
			break;
		default:
			System.out.println("Errore riprovare.");
		}
	}

	private void infoTratte() {
		System.out.println("Seleziona tratta");
		List<Tratta> tratte = em.createNamedQuery("SELECT tr FROM Tratta tr", Tratta.class).getResultList();
		String output = tratte.stream().map(
				tr -> 1 + tratte.indexOf(tr) + " per " + tr.getPartenza().getNome() + " - "
						+ tr.getCapolinea().getNome())
				.collect(Collectors.joining("\n"));
		System.out.println(output);
		int num = selectNumero(sc, tratte.size());
		Tratta tr = tratte.get(--num);
		TrattaDAO dao = new TrattaDAO(em);
		Duration d = dao.getTempoPercorrenza(tr);
		System.out.println("Il tempo di percorrenza effettivo per la tratta: \n" + tr + "\n è: " + d.toHoursPart() + ":"
				+ d.toMinutesPart());
	}

	private void aggiungiTratta() {
		System.out.println("Inserire nome tratta.");
		String nome = sc.nextLine();
		List<Zona> zone = em.createNamedQuery("SELECT z FROM Zona z", Zona.class).getResultList();
		System.out.println("Selezionare partenza");
		String output = zone.stream().map(z -> 1 + zone.indexOf(z) + " per " + z.getNome())
				.collect(Collectors.joining("\n"));
		System.out.println(output);
		int num = selectNumero(sc, zone.size());
		Zona partenza = zone.get(--num);
		System.out.println("Selezionare capolinea");
		System.out.println(output);
		num = selectNumero(sc, zone.size());
		Zona capolinea = zone.get(--num);

		System.out.println("Inserisci orario partenza.");
		LocalTime op = selectOrario();
		System.out.println("Inserisci orario arrivo.");
		LocalTime oa = selectOrario();

		System.out.println("Aggiungere tappe alla tratta");
		int count = 0;
		boolean val = false;
		List<Tappa> tappe = new LinkedList<Tappa>();
		do {
			tappe.add(inserisciTappa(++count));
			System.out.println("Aggiungere altre tappe? Y/N");
			val = conferma();

		} while (!val);

		Tratta tr = new Tratta(nome, partenza, capolinea, op, oa, Set.of(tappe.toArray(new Tappa[0])));
		TrattaDAO dao = new TrattaDAO(em);
		dao.save(tr);
		System.out.println("Tratta salvata correttamente.");
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

	private LocalTime selectOrario() {
		try {
			LocalTime op = LocalTime.parse(sc.nextLine());
			return op;
		} catch (DateTimeParseException e) {
			System.out.println(e.getMessage());
			System.out.println("Valore inserito non valido riprovare...");
			return selectOrario();
		}
	}

	private void rimuoviTratta() {
		System.out.println("Scegli tratta");
		List<Tratta> tratte = em.createQuery("SELECT tr FROM Tratta tr", Tratta.class).getResultList();
		String output = tratte.stream().map(
				tr -> 1 + tratte.indexOf(tr) + " per " + tr.getPartenza().getNome() + " - "
						+ tr.getCapolinea().getNome())
				.collect(Collectors.joining("\n"));
		System.out.println(output);
		int num = selectNumero(sc, tratte.size());
		Tratta tr = tratte.get(--num);
		MezzoDiTrasporto m;
		if (tr.getMezzo() != null) {
			m = em.find(MezzoDiTrasporto.class, tr.getMezzo().getId());
		} else {
			m = null;
		}
		EntityTransaction t = em.getTransaction();
		try {
			t.begin();
			m.setTratta(null);
			em.persist(m);
			em.remove(tr);

			t.commit();
			System.out.println("Tratta rimossa correttamente.");
		} catch (Exception e) {
			System.out.println("Errore durante la procedura: " + e.getMessage());
		}
	}

	private int selezioneMinuti() {
		if (sc.hasNextInt()) {
			int val = sc.nextInt();
			if (val < 0 || val > 60) {
				System.out.println("Valore inserito non valido.");
				return selezioneMinuti();
			}
			return val;
		} else {
			System.out.println("Valore inserito non valido.");
			return selezioneMinuti();
		}
	}

	private Tappa inserisciTappa(int order) {
		List<Zona> zone = em.createNamedQuery("SELECT z FROM Zona z", Zona.class).getResultList();
		System.out.println("Selezionare zona");
		String output = zone.stream().map(z -> 1 + zone.indexOf(z) + " per " + z.getNome())
				.collect(Collectors.joining("\n"));
		System.out.println(output);
		int num = selectNumero(sc, zone.size());
		Zona z = zone.get(--num);
		System.out.println("Inserisci minuti");
		int minuti = selezioneMinuti();
		System.out.println("Inserisci ore");
		int ore = selectNumero(sc, 10);
		Duration d = Duration.ofMinutes(minuti + (60 * ore));
		Tappa t = new Tappa(z, null, order, d);
		return t;
	}

	public void startFunzionalitàMezzi() {

		String elenco = """
				Inserisci:
				1 per gestione mezzi
				2 per info mezzo di trasporto
				""";
		System.out.println(elenco);
		int num = selectNumero(sc, 2);
		switch (num) {
		case 1:
			gestioneMezzi();
			break;
		case 2:
			infoMezzi();
			break;
		default:
			System.out.println("Errore riprovare.");
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

	public void gestioneMezzi() {
		String elenco = """
				Inserisci:
				1 per aggiungere nuovo mezzo
				2 per rimuovere mezzo esistente
				""";
		System.out.println(elenco);
		int num = selectNumero(sc, 2);
		switch (num) {
		case 1:
			aggiungiMezzo();
			break;
		case 2:
			rimuoviMezzo();
			break;
		default:
			System.out.println("Errore riprovare.");
		}
	}

	public void infoMezzi() {
		TypedQuery<MezzoDiTrasporto> q = em.createQuery("SELECT m FROM MezzoDiTrasporto m", MezzoDiTrasporto.class);
		List<MezzoDiTrasporto> lista = q.getResultList();
		String s = lista.stream().map(m -> 1 + lista.indexOf(m) + " per " + m.getNome())
				.collect(Collectors.joining("\n"));
		System.out.println("Inserire: \n" + s);
		int num = selectNumero(sc, lista.size());
		try {
			MezzoDiTrasporto m = lista.get(--num);
			TypedQuery<Zona> qt = em.createQuery(
					"SELECT DISTINCT(z) FROM MezziDiTrasporto m INNER JOIN m.tratta tr INNER JOIN tr.tappe t INNER JOIN t.zona z WHERE m.id = :param",
					Zona.class);
			qt.setParameter("param", m.getId());
			List<Zona> tappe = qt.getResultList();
			s = tappe.stream().map(t -> 1 + tappe.indexOf(t) + " per " + t.getNome()).collect(Collectors.joining("\n"));
			System.out.println("Inserire: \n" + s);
			num = selectNumero(sc, tappe.size());
			Zona tappa = tappe.get(--num);
			TypedQuery<Integer> qn = em.createNamedQuery("ripetizioniTappa", Integer.class);
			qn.setParameter("paramMezzo", m.getId());
			qn.setParameter("paramTappa", tappa.getId());
			int ripetizioni = qn.getSingleResult();
			System.out.println("La tappa " + tappa.getNome() + " viene percorsa "
					+ (ripetizioni > 1 ? ripetizioni + "volte" : "una volta") + "dal mezzo di trasporto: " + m);

		} catch (IndexOutOfBoundsException e) {
			System.out.println(e.getMessage());
			System.out.println("Si è verificato un errore riprovare!");
			return;
		}
	}

	private void aggiungiMezzo() {
		List<Tratta> tratte = em.createQuery("SELECT COUNT(tr) FROM Tratta tr WHERE tr.mezzo IS NULL", Tratta.class)
				.getResultList();
		try {
			System.out.println("Inserire nome mezzo:");
			String nome = sc.nextLine();
			System.out.println("Inserire marca mezzo:");
			String marca = sc.nextLine();
			System.out.println("Inserire capienza:");
			short capienza = (short) selectNumero(sc, 200);
			String elenco = """
					Inserisci
					1 per Tram
					2 per Autobus
					""";
			System.out.println(elenco);
			Tratta tratta = null;
			if (tratte.size() > 0) {
				System.out.println("Selezionare tratta");
				String output = tratte.stream().map(tr -> 1 + tratte.indexOf(tr) + " per " + tr.getPartenza().getNome()
						+ " - " + tr.getCapolinea().getNome()).collect(Collectors.joining("\n"));
				System.out.println(output);
				int nt = selectNumero(sc, tratte.size());
				tratta = tratte.get(--nt);
			}

			int num = selectNumero(sc, 2);
			MezzoDiTrasporto m;
			switch (num) {
			case 1:
				System.out.println("Inserire numero cabine, massimo 4");
				int val = selectNumero(sc, 4);
				m = new Tram(nome, marca, capienza, (short) val, tratta);
				break;
			case 2:
				System.out.println("Inserire numero ruote, massimo 10");
				val = selectNumero(sc, 10);
				m = new Autobus(nome, marca, capienza, (short) val, tratta);
				break;
			default:
				m = null;
			}
			MezziDAO dao = new MezziDAO(em);
			dao.save(m);
			System.out.println("Mezzo salvato correttamente.");
		} catch (ClassCastException e) {
			System.out.println("Errore durante la procedura! riprovare. " + e.getMessage());
			return;
		}
	}

	private void rimuoviMezzo() {
		List<MezzoDiTrasporto> lista = em.createQuery("SELECT m FROM MezzoDiTrasporto m", MezzoDiTrasporto.class)
				.getResultList();
		System.out.println("Selezionare lemento da rimuovere.");
		String output = lista.stream().map(m -> 1 + lista.indexOf(m) + " per " + m.getNome())
				.collect(Collectors.joining("\n"));
		System.out.println(output);
		int input = selectNumero(sc, lista.size());
		MezzoDiTrasporto m = lista.get(--input);
		MezziDAO dao = new MezziDAO(em);
		dao.delete(m);
		System.out.println("Eliminato mezzo di trasporto: " + m);
	}

}
