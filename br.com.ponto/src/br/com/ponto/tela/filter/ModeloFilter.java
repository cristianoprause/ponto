package br.com.ponto.tela.filter;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

public abstract class ModeloFilter<T> extends ViewerFilter{

	protected String search;

	protected abstract boolean verificar(T element);
	
	public void setSearch(String search){
		this.search = ".*"+search.toLowerCase()+".*";
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		return search == null || 
				search.isEmpty() || 
				verificar((T) element);
	}
}
