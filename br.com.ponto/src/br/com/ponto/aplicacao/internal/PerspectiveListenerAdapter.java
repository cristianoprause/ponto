package br.com.ponto.aplicacao.internal;

import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;

public class PerspectiveListenerAdapter extends PartListenerAdapter {

	@Override
	public void partClosed(IWorkbenchPart part) {
		IWorkbenchPage activePage = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage();
		if (activePage == null)
			return; // Se n�o existe p�gina ativa, n�o continua. Ou seja, o
					// usu�rio est� fechando o programa

		IEditorReference[] refs = activePage.getEditorReferences();

		if (refs.length == 0)
			activePage.setEditorAreaVisible(false);
	}
}