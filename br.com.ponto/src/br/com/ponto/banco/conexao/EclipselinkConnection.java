package br.com.ponto.banco.conexao;

import javax.persistence.EntityManager;

import org.eclipse.persistence.internal.jpa.EntityManagerImpl;

import br.com.ponto.banco.modelo.IModelo;

public class EclipselinkConnection<T> {

	private EntityManager entityManager;
	
	public void saveOrUpdate(T modelo){
		if(IModelo.class.cast(modelo).getId() != null)
			getEntityManager().merge(modelo);
		else
			getEntityManager().persist(modelo);
		
		getEntityManager().getTransaction().commit();
	}
	
	public void delete(T modelo){
		getEntityManager().remove(modelo);
		getEntityManager().getTransaction().commit();
	}
	
	public boolean isDirty(){
		return ((EntityManagerImpl)getEntityManager()).getUnitOfWork().hasChanges();
	}
	
	public void refresh(T modelo) {
		getEntityManager().refresh(modelo);
	}
	
	public EntityManager getEntityManager() {
		if(entityManager == null)
			entityManager = ModeloEntityManager.getEntityManager();
		
		if(!entityManager.getTransaction().isActive())
			entityManager.getTransaction().begin();
		
		return entityManager;
	}
	
}
