package app;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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
import mezzo_tratta.Servizio;
import mezzo_tratta.StatoMezzo;
import mezzo_tratta.Tappa;
import mezzo_tratta.Tram;
import mezzo_tratta.Tratta;
import mezzo_tratta.Zona;

public class MezzieTratteInteraction extends ConsoleInteraction {

	private EntityManager em;
	private Scanner sc;

	public MezzieTratteInteraction(EntityManager em, Scanner sc) {
		super(sc);
		this.em = em;
		this.sc = sc;
	}

	public void startFunzionalitaTratte() {

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
		List<Tratta> tratte = em.createQuery("SELECT tr FROM Tratta tr", Tratta.class).getResultList();
		String output = tratte.stream().map(tr -> 1 + tratte.indexOf(tr) + " per " + tr.getPartenza().getNome() + " - "
				+ tr.getCapolinea().getNome()).collect(Collectors.joining("\n"));
		System.out.println(output);
		int num = selectNumero(sc, tratte.size());
		sc.nextLine();
		Tratta tr = tratte.get(--num);
		TrattaDAO dao = new TrattaDAO(em);
		Duration d = dao.getTempoPercorrenza(tr);
		System.out.println("Il tempo di percorrenza effettivo per la tratta: \n" + tr + "\n è: " + d.toHoursPart()
				+ ":" + d.toMinutesPart());
	}

	private void aggiungiTratta() {
		System.out.println("Inserire nome tratta.");
		String nome = sc.nextLine();
		List<Zona> zone = em.createQuery("SELECT z FROM Zona z", Zona.class).getResultList();
		System.out.println("Selezionare partenza");
		String output = zone.stream().map(z -> 1 + zone.indexOf(z) + " per " + z.getNome())
				.collect(Collectors.joining("\n"));
		System.out.println(output);
		int num = selectNumero(sc, zone.size());
		sc.nextLine();
		Zona partenza = zone.get(--num);
		System.out.println("Selezionare capolinea");
		System.out.println(output);
		num = selectNumero(sc, zone.size());
		sc.nextLine();
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
		System.out.println("Tratta salvata correttamente." + tr);
	}

	private LocalTime selectOrario() {
		try {
			LocalTime op = LocalTime.parse(sc.nextLine(), DateTimeFormatter.ofPattern("HH:mm"));
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
		String output = tratte.stream().map(tr -> 1 + tratte.indexOf(tr) + " per " + tr.getPartenza().getNome() + " - "
				+ tr.getCapolinea().getNome()).collect(Collectors.joining("\n"));
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

	private int selezioneOre() {
		if (sc.hasNextInt()) {
			int val = sc.nextInt();
			if (val < 0 || val > 10) {
				System.out.println("Valore inserito non valido.");
				return selezioneOre();
			}
			return val;
		} else {
			System.out.println("Valore inserito non valido.");
			return selezioneOre();
		}
	}

	private Tappa inserisciTappa(int order) {
		List<Zona> zone = em.createQuery("SELECT z FROM Zona z", Zona.class).getResultList();
		System.out.println("Selezionare zona");
		String output = zone.stream().map(z -> 1 + zone.indexOf(z) + " per " + z.getNome())
				.collect(Collectors.joining("\n"));
		System.out.println(output);
		int num = selectNumero(sc, zone.size());
		sc.nextLine();
		Zona z = zone.get(--num);
		System.out.println("Inserisci minuti");
		int minuti = selezioneMinuti();
		System.out.println("Inserisci ore");
		int ore = selezioneOre();
		sc.nextLine();
		Duration d = Duration.ofMinutes(minuti + (60 * ore));
		Tappa t = new Tappa(z, null, order, d);
		return t;
	}

	public void startFunzionalitaMezzi() {

		String elenco = """
				Inserisci:
				1 per gestione mezzi
				2 per info mezzo di trasporto
				3 per gestione servizio
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
		case 3:
			gestioneServizio();
			break;
		default:
			System.out.println("Errore riprovare.");
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
		sc.nextLine();
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
		sc.nextLine();
		try {
			MezzoDiTrasporto m = lista.get(--num);
			TypedQuery<Zona> qt = em.createQuery(
					"SELECT DISTINCT(z) FROM MezzoDiTrasporto m INNER JOIN m.tratta tr INNER JOIN tr.tappe t INNER JOIN t.zona z WHERE m.id = :param",
					Zona.class);
			qt.setParameter("param", m.getId());
			List<Zona> tappe = qt.getResultList();
			s = tappe.stream().map(t -> 1 + tappe.indexOf(t) + " per " + t.getNome()).collect(Collectors.joining("\n"));
			System.out.println("Inserire: \n" + s);
			num = selectNumero(sc, tappe.size());
			sc.nextLine();
			Zona tappa = tappe.get(--num);
			TypedQuery<Long> qn = em.createNamedQuery("ripetizioniTappa", Long.class);
			qn.setParameter("paramMezzo", m.getId());
			qn.setParameter("paramTappa", tappa.getId());
			long ripetizioni = qn.getSingleResult();
			System.out.println("La tappa " + tappa.getNome() + " viene percorsa "
					+ (ripetizioni > 1 ? ripetizioni + "volte" : "una volta") + " dal mezzo di trasporto: " + m);

		} catch (IndexOutOfBoundsException e) {
			System.out.println(e.getMessage());
			System.out.println("Si è verificato un errore riprovare!");
			return;
		}
	}

	private void aggiungiMezzo() {
		List<Tratta> tratte = em.createQuery("SELECT tr FROM Tratta tr WHERE tr.mezzo IS EMPTY", Tratta.class)
				.getResultList();
		try {
			System.out.println("Inserire nome mezzo:");
			String nome = sc.nextLine();
			System.out.println("Inserire marca mezzo:");
			String marca = sc.nextLine();
			System.out.println("Inserire capienza:");
			short capienza = (short) selectNumero(sc, 200);

			Tratta tratta = null;
			if (tratte.size() > 0) {
				System.out.println("Selezionare tratta");
				String output = tratte.stream().map(tr -> 1 + tratte.indexOf(tr) + " per " + tr.getPartenza().getNome()
						+ " - " + tr.getCapolinea().getNome()).collect(Collectors.joining("\n"));
				System.out.println(output);
				int nt = selectNumero(sc, tratte.size());
				sc.nextLine();
				tratta = tratte.get(--nt);
			}
			String elenco = """
					Inserisci
					1 per Tram
					2 per Autobus
					""";
			System.out.println(elenco);
			int num = selectNumero(sc, 2);
			sc.nextLine();
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
			sc.nextLine();
			MezziDAO dao = new MezziDAO(em);
			dao.save(m);
			System.out.println("Mezzo salvato correttamente." + m);
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
		sc.nextLine();
		MezzoDiTrasporto m = lista.get(--input);
		MezziDAO dao = new MezziDAO(em);
		dao.delete(m);
		System.out.println("Eliminato mezzo di trasporto: " + m);
	}

	private void gestioneServizio() {
		String output = """
				Inserisci
				1 per aggiungi servizio
				2 per rimuovi servizio
				""";
		System.out.println(output);
		int num = selectNumero(sc, 2);
		sc.nextLine();
		switch (num) {
		case 1:
			aggiungiServ();
			break;
		case 2:
			rimuoviServ();
			break;

		default:
			System.out.println("Errore riprovare.");
		}
	}

	private MezzoDiTrasporto selectMezzo() {
		List<MezzoDiTrasporto> lista = em.createQuery("SELECT m FROM MezzoDiTrasporto m", MezzoDiTrasporto.class)
				.getResultList();
		System.out.println("Selezionare lemento da rimuovere.");
		String output2 = lista.stream().map(m -> 1 + lista.indexOf(m) + " per " + m.getNome())
				.collect(Collectors.joining("\n"));
		System.out.println(output2);
		int input = selectNumero(sc, lista.size());
		sc.nextLine();
		MezzoDiTrasporto m = lista.get(--input);
		return m;
	}

	private void rimuoviServ() {
		MezzoDiTrasporto m = selectMezzo();
		TypedQuery<Servizio> q = em.createQuery("SELECT s FROM Servizio s WHERE s.mezzo.id = :param", Servizio.class);
		q.setParameter("param", m.getId());
		List<Servizio> lista = q.getResultList();
		String output = lista.stream().map(s -> 1 + lista.indexOf(s) + " per " + s).collect(Collectors.joining("\n"));
		System.out.println("Seleziona servizio");
		System.out.println(output);
		int num = selectNumero(sc, lista.size());
		sc.nextLine();
		Servizio s = lista.get(--num);
		MezziDAO dao = new MezziDAO(em);
		dao.delete(s);
		System.out.println("Servizio rimosso con successo.");
	}

	private void aggiungiServ() {

		MezzoDiTrasporto m = selectMezzo();
		System.out.println("Inserire data inizio in formato aaaa-mm-gg");
		LocalDate inizio = selectDate();

		System.out.println("Inserire data fine in formato aaaa-mm-gg");
		LocalDate fine = selectDate();

		String output = """
				Inserisci
				1 per manutenzione
				2 per in servizio
				""";
		System.out.println(output);
		int num = selectNumero(sc, 2);
		sc.nextLine();
		Servizio s;
		switch (num) {
		case 1:
			s = new Servizio(StatoMezzo.IN_MANUTENZIONE, inizio, m);

			break;
		case 2:
			s = new Servizio(StatoMezzo.IN_SERVIZIO, inizio, m);
			break;
		default:
			throw new IllegalStateException("Errore nel codice");
		}
		s.setDataFine(fine);

		MezziDAO dao = new MezziDAO(em);
		dao.save(s);
		System.out.println("Servizio salvato correttamente");
	}
}
