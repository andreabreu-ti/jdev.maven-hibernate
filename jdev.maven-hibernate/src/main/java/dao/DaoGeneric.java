package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import jdevmavenhibernate.HibernateUtil;

public class DaoGeneric<E> {

	private EntityManager entityManager = HibernateUtil.getEntityManager();

	// Metodo para Salvar
	public void salvar(E entidade) {

		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		entityManager.persist(entidade);
		transaction.commit();
	}

	// Metodo para Consultar

	public E pesquisar(E entidade) {

		Object id = HibernateUtil.getPrimaryKey(entidade);
		E e = (E) entityManager.find(entidade.getClass(), id);
		return e;
	}

	// Outra forma de Consultar
	public E pesquisar(Long id, Class<E> entidade) {

		E e = (E) entityManager.find(entidade, id);
		return e;
	}

	// Metodo de update - Salva ou Atualiza
	public E updateMerge(E entidade) {

		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		E entidadeSalva = entityManager.merge(entidade);
		transaction.commit();
		return entidadeSalva;
	}

	// Metodo para Excluir
	public void deletarPorId(E entidade) {

		Object id = HibernateUtil.getPrimaryKey(entidade);
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		entityManager
				.createNativeQuery(
						"delete from " + entidade.getClass().getSimpleName().toLowerCase() + " where id =" + id)
				.executeUpdate(); //Faz o delete
		transaction.commit();
	}
	
	//Metodo para Consultar Todos
	
	public List<E> listar(Class<E> entidade){
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		List<E> lista = entityManager.createQuery("from "+ entidade.getName()).getResultList();
		transaction.commit();
		return lista;
	}
	
	//Get do entitymanager de forma publica, para acessar de outras parte do projeto, teste
	public EntityManager getEntityManager() {
		return entityManager;
	}
	
	
	
	
	
	
	
	
	
	

}
