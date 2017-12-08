package br.com.ponto.aplicacao.helper;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;

public class SelectionHelper {

	private SelectionHelper() {}
	
	@SuppressWarnings("unchecked")
	public static <T> T getElement(TableViewer view){
		IStructuredSelection selecao = (IStructuredSelection) view.getSelection();
		if(selecao.isEmpty())
			return null;
		
		return (T) selecao.getFirstElement();
	}
	
}
