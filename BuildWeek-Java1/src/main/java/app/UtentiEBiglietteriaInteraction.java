package app;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import _enum.State;
import card_user.Card;
import card_user.CardUserDAO;
import card_user.User;
import dao.Punti_venditaDao;
import mezzo_tratta.MezzoDiTrasporto;
import product.Subscription;
import product.Ticket;
import punti_vendita.Distributori;
import punti_vendita.Punti_vendita;
import punti_vendita.Rivenditori;

public class UtentiEBiglietteriaInteraction extends ConsoleInteraction {

	private EntityManager em;
	private Scanner sc;

	public UtentiEBiglietteriaInteraction(EntityManager em, Scanner sc) {
		super(sc);
		this.em = em;
		this.sc = sc;
	}

	public void startFunzionalitaUtenti() {

		String output = """
				Inserisci
				1 per controllo validità abbonamenti
				2 per gestione utenti
				""";
		System.out.println(output);
		int num = selectNumero(sc, 2);

		switch (num) {
		case 1:
			validitaAbbonamenti();
			break;
		case 2:
			gestioneUtenti();
			break;
		default:
			throw new IllegalStateException("Errori nel codice.");
		}
	}

	public void startFunzionalitaBiglietti() {
		String output = """
				Inserisci
				1 per traccia biglietti
				2 per gestione punti emissione
				""";
		System.out.println(output);
		int input = selectNumero(sc, 2);

		switch (input) {
		case 1:
			tracciaBiglietti();
			break;

		case 2:
			gestionePuntiVendita();
			break;
		default:
			throw new IllegalStateException("Errore nel codice");
		}
	}

	private void tracciaBiglietti() {
		String output = """
				Inserisci
				1 per traccia per punti di emissione
				2 per traccia per mezzo di trasporto
				""";
		System.out.println(output);
		int input = selectNumero(sc, 2);

		switch (input) {
		case 1:
			tracciaBigliettiPerPV();
			break;

		case 2:
			tracciaBigliettiPerMT();
			break;
		default:
			throw new IllegalStateException("Errore nel codice");
		}
	}

	private void tracciaBigliettiPerPV() {
		List<Punti_vendita> lista = em.createQuery("SELECT pv FROM Punti_vendita pv", Punti_vendita.class)
				.getResultList();
		if (lista.isEmpty()) {
			System.out.println("Non ci sono punti di emissione biglietti.");
			return;
		}
		System.out.println("Seleziona un punto emissione");
		String output = lista.stream().map(pv -> (1 + lista.indexOf(pv)) + " per " + pv.getLocation())
				.collect(Collectors.joining("\n"));
		System.out.println(output);
		int num = selectNumero(sc, lista.size());
		Punti_vendita pv = lista.get(--num);
		System.out.println("Inserisci data inizio periodo");
		LocalDate inizio = selectDate();
		System.out.println("Inserisci data fine periodo");
		LocalDate fine = selectDate();

		TypedQuery<Ticket> q = em.createQuery(
				"SELECT t FROM Ticket t WHERE t.shopId.shopId = :paramId AND t.emissionDate BETWEEN :paramInizio AND :paramFine",
				Ticket.class);
		q.setParameter("paramId", pv.getShopId());
		q.setParameter("paramInizio", inizio);
		q.setParameter("paramFine", fine);
		int result = q.getResultList().size();
		DateTimeFormatter fm = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM);
		System.out.println("I biglietti venduti presso: " + pv + "\n a partire da " + inizio.format(fm) + " e fino a "
				+ fine.format(fm) + "\n risultano: " + result);
	}

	private void tracciaBigliettiPerMT() {
		List<MezzoDiTrasporto> lista = em.createQuery("SELECT m FROM MezzoDiTrasporto m", MezzoDiTrasporto.class)
				.getResultList();
		System.out.println("Selezionare lemento da rimuovere.");
		String output2 = lista.stream().map(m -> 1 + lista.indexOf(m) + " per " + m.getNome())
				.collect(Collectors.joining("\n"));
		System.out.println(output2);
		int input = selectNumero(sc, lista.size());

		MezzoDiTrasporto m = lista.get(--input);

		System.out.println("Inserisci data inizio periodo");
		LocalDate inizio = selectDate();
		System.out.println("Inserisci data fine periodo");
		LocalDate fine = selectDate();

		TypedQuery<Ticket> q = em.createQuery(
				"SELECT t FROM Ticket t WHERE t.veichleId = :paramId AND t.emissionDate BETWEEN :paramInizio AND :paramFine",
				Ticket.class);
		q.setParameter("paramId", m.getId());
		q.setParameter("paramInizio", inizio);
		q.setParameter("paramFine", fine);
		int result = q.getResultList().size();

		DateTimeFormatter fm = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM);
		System.out.println("I biglietti vidimati sulmezzo: " + m + "\n a partire da " + inizio.format(fm) + " e fino a "
				+ fine.format(fm) + "\n risultano: " + result);

	}
	private void gestionePuntiVendita() {
		String output = """
				Inserisci
				1 per aggiungi nuovo punto emissione
				2 per rimuovi punto emissione esistente
				""";
		System.out.println(output);
		int num = selectNumero(sc, 2);
		switch (num) {
		case 1:
			aggiungiPE();
			break;
		case 2:
			rimuoviPE();
			break;
		default:
			throw new IllegalStateException("Errore nel codice");
		}
	}

	private void aggiungiPE() {
		System.out.println("Inserire nome location");
		String location = sc.nextLine();
		String output = """
				Inserisci
				1 per distributore automatico
				2 per rivenditore autorizzato
				""";
		System.out.println(output);
		Punti_vendita pv;
		int num = selectNumero(sc, 2);
		switch (num) {
		case 1:
			System.out.println("""
					Inserisci
					1 per attivo
					2 per fuori servizio
					""");
			int val = selectNumero(sc, 2);
			switch (val) {
			case 1:
				pv = new Distributori(location, State.ACTIVE);
				break;

			case 2:
				pv = new Distributori(location, State.OUTOFSERVICE);
				break;
			default:
				throw new IllegalStateException("Errore nel codice");
			}
			break;
		case 2:
			pv = new Rivenditori(location);
			break;
		default:
			throw new IllegalStateException("Errore nel codice");
		}
		Punti_venditaDao dao = new Punti_venditaDao();
		dao.addShops(pv);
		System.out.println("Punto emissione salvato con successo." + pv);
	}

	private void rimuoviPE() {
		System.out.println("Seleziona punto emissione");
		List<Punti_vendita> lista = em.createQuery("SELECT pv FROM Punti_vendita pv", Punti_vendita.class)
				.getResultList();
		String output = lista.stream().map(pv -> 1 + lista.indexOf(pv) + " per " + pv.getLocation())
				.collect(Collectors.joining("\n"));
		System.out.println(output);
		int num = selectNumero(sc, lista.size());
		Punti_vendita pv = lista.get(--num);
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();
			em.remove(pv);
			tx.commit();
			System.out.println("Punto emissione rimosso con successo." + pv);
		} catch (Exception e) {
			System.out.println("Errore durante l'operazione di rimozione dati: " + e.getMessage());
		}
	}



	private void validitaAbbonamenti() {
		List<User> utenti = em.createQuery("SELECT u FROM User u WHERE u.card IS NOT NULL", User.class).getResultList();
		System.out.println("Seleziona utente.");
		String output = utenti.stream().map(u -> (1 + utenti.indexOf(u)) + " per " + u.getName() + " " + u.getSurname())
				.collect(Collectors.joining("\n"));
		System.out.println(output);
		int input = selectNumero(sc, utenti.size());
		sc.nextLine();
		User u = utenti.get(--input);
		Card c = u.getCard();
		TypedQuery<Subscription> q = em.createQuery(
				"SELECT s FROM User u INNER JOIN u.card c INNER JOIN c.subscription s WHERE u.id = :param",
				Subscription.class);
		q.setParameter("param", u.getUserId());
		try {
		Subscription s = q.getSingleResult();
		if (s.getExpireDate() != null && s.getExpireDate().isAfter(LocalDate.now())) {
			System.out.println("L'abbonamento per l'utente: " + u + "\n è ancora valido.");
		} else if (s.getExpireDate() != null && s.getExpireDate().isBefore(LocalDate.now())) {
			System.out.println("L'abbonamento per l'utente: " + u + "\n è scaduto.");
		} else {
			throw new IllegalStateException("Errore dati salvati.");
		}
	} catch (NoResultException e) {
		System.out.println("L'utente: " + u + "\n non ha un abbonamento");
	}
	}

	private void gestioneUtenti() {
		System.out.println("Scegliere operazione.");
		String output = """
				Inserisci
				1 per aggiunta nuovo utente
				2 per rimozione utente esistente
				""";
		System.out.println(output);
		int num = selectNumero(sc, 2);

		switch (num) {
		case 1:
			aggiungiUtente();
			break;
		case 2:
			cancellaUtente();
			break;
		default:
			throw new IllegalStateException("Errore nel codice.");
		}
	}

	private void cancellaUtente() {
		List<User> utenti = em.createQuery("SELECT u FROM User u", User.class).getResultList();
		System.out.println("Seleziona utente.");
		String output = utenti.stream().map(u -> (1 + utenti.indexOf(u)) + " per " + u.getName() + " " + u.getSurname())
				.collect(Collectors.joining("\n"));
		System.out.println(output);
		int num = selectNumero(sc, utenti.size());
		User u = utenti.get(--num);

		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();
			em.remove(u);
			tx.commit();
			System.out.println("Utente rimosso correttamente.");
		} catch (Exception e) {
			System.out.println("Errore durante loperazione di cancellazione utente.");
		}

	}

	private void aggiungiUtente() {

		System.out.println("Inserisci nome utente");
		String nome = sc.nextLine();
		System.out.println("Inserisci cognome utente");
		String cognome = sc.nextLine();
		System.out.println("Inserisci data di nascita in formato aaaa-mm-gg");
		LocalDate dn = selectDate();

		User u = new User(nome, cognome, dn);
		Card c = new Card(LocalDate.now());
		CardUserDAO dao = new CardUserDAO();
		dao.saveUserCard(u, c);

	}


}
