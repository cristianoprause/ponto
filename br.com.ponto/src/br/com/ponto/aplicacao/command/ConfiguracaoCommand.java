package br.com.ponto.aplicacao.command;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

import br.com.ponto.aplicacao.helper.MessageHelper;
import br.com.ponto.aplicacao.helper.UsuarioHelper;
import br.com.ponto.tela.dialog.ConfiguracaoDialog;

public class ConfiguracaoCommand extends AbstractHandler{

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		if(UsuarioHelper.getUsuarioLogado() == null)
			MessageHelper.openError("Cadastre primeiro o usuário para depois registrar as configurações");
		else
			new ConfiguracaoDialog().open();
		return null;
		
	}

}
