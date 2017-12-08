package br.com.ponto.banco.repository;

import java.util.List;

public interface ModeloRepository<T> {

	boolean isDirty();
	void saveOrUpdate(T modelo);
	void delete(T modelo);
	T find(Long id);
	List<T> findAll();
	
}
