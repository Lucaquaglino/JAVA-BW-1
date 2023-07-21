package app;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import _enum.Periodicy;
import card_user.User;
import dao.ProductDao;
import mezzo_tratta.MezzoDiTrasporto;
import mezzo_tratta.Tratta;
import product.Subscription;
import product.Ticket;
import punti_vendita.Punti_vendita;

public class UserInteraction extends ConsoleInteraction {

	private EntityManager em;
	private Scanner sc;

	public UserInteraction(Scanner sc, EntityManager em) {
		super(sc);
		this.sc = sc;
		this.em = em;

		boolean exit = false;
		do {
			start();
			System.out.println("Effettuare altre operazioni? Y/N");
			exit = conferma();
		} while (!exit);
	}

	private void start() {

		List<User> lista = em.createQuery("SELECT u FROM User u", User.class).getResultList();
		String output = lista.stream().map(u -> 1 + lista.indexOf(u) + " per " + u.getName() + " " + u.getSurname())
				.collect(Collectors.joining("\n"));
		System.out.println(output);
		int num = selectNumero(sc, lista.size());
		User u = lista.get(--num);
		TypedQuery<Subscription> tq = em.createQuery(
				"SELECT s FROM User u INNER JOIN u.card c INNER JOIN c.subscription s WHERE u.id = :param",
				Subscription.class);
		tq.setParameter("param", u.getUserId());
		boolean val = tq.getResultList().size() > 0;
		if (val) {
			selectTratta(null);
		} else {
			acquisto(u);
		}

	}

	private void acquisto(User u) {
		String output = """
				Inserisci
				1 per biglietto
				2 per abbonamento
				""";
		System.out.println(output);
		int num = selectNumero(sc, 2);
		switch (num) {
		case 1:
			acquistaBiglietto();
			break;

		case 2:
			abbonamento(u);
			break;
		default:
			throw new IllegalStateException();
		}
	}

	private void abbonamento(User u) {
		List<Punti_vendita> lista = em.createQuery("SELECT p FROM Punti_vendita p", Punti_vendita.class)
				.getResultList();
		String output = lista.stream().map(p -> 1 + lista.indexOf(p) + " per " + p).collect(Collectors.joining("\n"));
		System.out.println(output);
		int num = selectNumero(sc, lista.size());
		Punti_vendita pv = lista.get(--num);
		ProductDao dao = new ProductDao(em);
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		Subscription s = new Subscription(Periodicy.MONTHLY);
		s.setShopId(pv);
		s.setCardId(u.getCard());
		em.persist(s);
		u.getCard().setSubscription(s);
		em.persist(u.getCard());
		tx.commit();
		System.out.println("Abbonamento acquistato: " + s);
		selectTratta(null);

	}

	private void selectTratta(Ticket ticket) {
		System.out.println("Scegli tratta");
		List<Tratta> tratte = em.createQuery("SELECT tr FROM Tratta tr WHERE tr.mezzo IS NOT EMPTY", Tratta.class)
				.getResultList();
		String output = tratte.stream().map(tr -> 1 + tratte.indexOf(tr) + " per " + tr.getPartenza().getNome() + " - "
				+ tr.getCapolinea().getNome()).collect(Collectors.joining("\n"));
		System.out.println(output);
		int num = selectNumero(sc, tratte.size());
		Tratta tr = tratte.get(--num);
		MezzoDiTrasporto m = tr.getMezzo();

		System.out.println("Selezionato mezzo: " + m);

		if (ticket != null) {
			ticket.setVeichleId(m.getId());
			ProductDao dao = new ProductDao(em);
			dao.saveProduct(ticket);
		}
	}

	private void acquistaBiglietto() {
		List<Punti_vendita> lista = em.createQuery("SELECT p FROM Punti_vendita p", Punti_vendita.class)
				.getResultList();
		String output = lista.stream().map(p -> 1 + lista.indexOf(p) + " per " + p).collect(Collectors.joining("\n"));
		System.out.println(output);
		int num = selectNumero(sc, lista.size());
		Punti_vendita pv = lista.get(--num);
		Ticket ticket = new Ticket();
		ticket.setEmissionDate(LocalDate.now());
		ticket.setShopId(pv);
		ProductDao dao = new ProductDao(em);
		dao.saveProduct(ticket);
		System.out.println("acquistato biglietto: " + ticket);
		selectTratta(ticket);
	}

}
