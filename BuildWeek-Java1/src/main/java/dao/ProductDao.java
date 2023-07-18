package dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import product.Product;

public class ProductDao {
	private final EntityManager em;

	public ProductDao(EntityManager em) {
		this.em = em;
	}
//metodo save

	public void saveProduct(Product ev) {
		EntityTransaction e = em.getTransaction();
		e.begin();
		em.persist(ev);
		e.commit();
		System.out.println("Product salvato correttamente");
	}

//metodo find	

	public Product findProductById(long prodId) {

		Product trova = em.find(Product.class, prodId);
		return trova;
	}

//metodo delete

	public void findProductByIdAndDelete(long prodId) {
		Product trova = em.find(Product.class, prodId);
		if (trova != null) {
			EntityTransaction t = em.getTransaction();
			t.begin();
			em.remove(trova);
			t.commit();
			System.out.println("Product eliminato con successo");
		} else {
			System.out.println("Product non trovato");
		}
	}
}
