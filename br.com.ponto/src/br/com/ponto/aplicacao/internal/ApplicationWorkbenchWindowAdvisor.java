package br.com.ponto.aplicacao.internal;

import org.apache.log4j.Logger;
import org.eclipse.swt.graphics.Point;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;

import br.com.ponto.aplicacao.helper.MessageHelper;
import br.com.ponto.aplicacao.helper.UsuarioHelper;
import br.com.ponto.banco.modelo.Usuario;
import br.com.ponto.tela.editor.UsuarioEditor;
import br.com.ponto.tela.editor.editorinput.ModeloEditorInput;

public class ApplicationWorkbenchWindowAdvisor extends WorkbenchWindowAdvisor {

	private Logger log = Logger.getLogger(getClass());
	
	public ApplicationWorkbenchWindowAdvisor(IWorkbenchWindowConfigurer configurer) {
		super(configurer);
	}

	@Override
	public ActionBarAdvisor createActionBarAdvisor(
			IActionBarConfigurer configurer) {
		return new ApplicationActionBarAdvisor(configurer);
	}

	@Override
	public void preWindowOpen() {
		IWorkbenchWindowConfigurer configurer = getWindowConfigurer();
		configurer.setInitialSize(new Point(800, 600));
		configurer.setShowCoolBar(false);
		configurer.setShowProgressIndicator(true);
		configurer.setShowStatusLine(true);
		configurer.setTitle("Ponto");
	}
	
	@Override
	public void postWindowOpen() {
		verificaUsuarioLogado();
	}
	
	/**
	 * Verifica se o usuário foi logado e caso não (na situação de iniciar o sistema pela primeira vez) ele obriga a
	 * cadastrar o usuário
	 */
	private void verificaUsuarioLogado() {
		if(UsuarioHelper.getUsuarioLogado() != null)
			return;
		
		try {
			MessageHelper.openInformation("Cadastre um usuário antes de utilizar o sistema");
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().
				openEditor(new ModeloEditorInput(new Usuario()), UsuarioEditor.ID);
		} catch (PartInitException e) {
			log.error("Erro ao cadastrar o primeiro usuário", e);
		}
	}
}
