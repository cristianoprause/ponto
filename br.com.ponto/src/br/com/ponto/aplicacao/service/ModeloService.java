package br.com.ponto.aplicacao.service;

import java.util.List;

import com.google.inject.Injector;

import br.com.ponto.aplicacao.helper.InjectorHelper;
import br.com.ponto.banco.repository.ModeloRepository;

public abstract class ModeloService<T> {

	private T modelo;
	
	public abstract ModeloRepository<T> getRepository();
	
	public void saveOrUpdate(){
		getRepository().saveOrUpdate(modelo);
	}
	
	public void delete(){
		getRepository().delete(modelo);
	}
	
	public T find(Long id){
		return getRepository().find(id);
	}
	
	public List<T> findAll(){
		return getRepository().findAll();
	}
	
	public Injector getInjector(){
		return InjectorHelper.getInstance();
	}
	
	public boolean isDirty(){
		return getRepository().isDirty();
	}
	
	public T getModelo() {
		return modelo;
	}
	
	public void setModelo(T modelo) {
		this.modelo = modelo;
	}
	
}
