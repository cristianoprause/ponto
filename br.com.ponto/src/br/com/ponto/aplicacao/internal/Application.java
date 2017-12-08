package br.com.ponto.aplicacao.internal;

import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;

import br.com.ponto.aplicacao.helper.UsuarioHelper;
import br.com.ponto.aplicacao.service.UsuarioService;
import br.com.ponto.banco.conexao.ModeloEntityManager;
import br.com.ponto.tela.dialog.LoginDialog;

/**
 * This class controls all aspects of the application's execution
 */
public class Application implements IApplication {

	/* (non-Javadoc)
	 * @see org.eclipse.equinox.app.IApplication#start(org.eclipse.equinox.app.IApplicationContext)
	 */
	public Object start(IApplicationContext context) {
		Display display = PlatformUI.createDisplay();
		try {
			ModeloEntityManager.init();
			UsuarioService usuarioService = new UsuarioService();
			if(usuarioService.isUsuarioCadastrado()) {
				LoginDialog ld = new LoginDialog();
				if(ld.open() != IDialogConstants.OK_ID)
					return IApplication.EXIT_OK;
				
				UsuarioHelper.setUsuarioLogado(ld.getUsuario());
			}
			int returnCode = PlatformUI.createAndRunWorkbench(display, new ApplicationWorkbenchAdvisor());
			if (returnCode == PlatformUI.RETURN_RESTART) {
				return IApplication.EXIT_RESTART;
			}
			return IApplication.EXIT_OK;
		} finally {
			display.dispose();
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.equinox.app.IApplication#stop()
	 */
	public void stop() {
		if (!PlatformUI.isWorkbenchRunning())
			return;
		final IWorkbench workbench = PlatformUI.getWorkbench();
		final Display display = workbench.getDisplay();
		display.syncExec(new Runnable() {
			public void run() {
				if (!display.isDisposed())
					workbench.close();
			}
		});
	}
	
}
