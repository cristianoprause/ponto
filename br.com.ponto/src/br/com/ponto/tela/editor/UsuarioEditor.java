package br.com.ponto.tela.editor;

import org.apache.log4j.Logger;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.beans.PojoProperties;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.widgets.Form;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;

import br.com.ponto.aplicacao.exception.ValidationException;
import br.com.ponto.aplicacao.helper.Helper;
import br.com.ponto.aplicacao.helper.LayoutHelper;
import br.com.ponto.aplicacao.helper.MessageHelper;
import br.com.ponto.aplicacao.helper.UsuarioHelper;
import br.com.ponto.aplicacao.service.ModeloService;
import br.com.ponto.aplicacao.service.UsuarioService;
import br.com.ponto.banco.modelo.Usuario;
import br.com.ponto.tela.editor.editorinput.ModeloEditorInput;

public class UsuarioEditor extends ModeloEditor<Usuario> {

	public static final String ID = "br.com.ponto.tela.editor.UsuarioEditor"; //$NON-NLS-1$
	
	private Logger log = Logger.getLogger(getClass());
	
	private final FormToolkit formToolkit = new FormToolkit(Display.getDefault());
	private Text txtNome;
	private Text txtUsuario;
	private Text txtSenha;

	public UsuarioEditor() {}//NOSONAR

	@Override
	protected void excluirRegistro() {
		if(Helper.isEquals(getService().getModelo(), UsuarioHelper.getUsuarioLogado()))
			MessageHelper.openError("Não é possível excluir o usuário porque ele esta logado. Logue em outro usuário "
					+ "para então excluir este!");
		else
			super.excluirRegistro();
	}
	
	@Override
	protected void validar() throws ValidationException {
		super.validar();
		
		Usuario usuario = ((UsuarioService)getService()).findByUsuario(txtUsuario.getText());
		if(
			usuario != null &&
			(getModelo().getId() == null || getModelo().getId().compareTo(usuario.getId()) != 0)
		  )
			throw new ValidationException("O usuário informado já está em uso.");
	}
	
	@Override
	protected void closeEditor() {
		if(UsuarioHelper.getUsuarioLogado() == null)
			UsuarioHelper.setUsuarioLogado(getService().getModelo());
		
		super.closeEditor();
	}
	
	@Override
	public void dispose() {
		if(UsuarioHelper.getUsuarioLogado() == null) {
			try {
				MessageHelper.openError("Não é possível fechar esta tela sem antes cadastrar o primeiro usuário");
				PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
					.openEditor(new ModeloEditorInput(new Usuario()), ID);
			} catch (PartInitException e) {
				log.error("Erro ao reabrir a edição de usuário", e);
			}
		}
		
		super.dispose();
	}
	
	@Override
	protected void adicionarComponentes(Composite composite) {
		composite.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Form frmUsurio = formToolkit.createForm(composite);
		formToolkit.paintBordersFor(frmUsurio);
		frmUsurio.setText("Usuário");
		frmUsurio.getBody().setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Section sctnDados = formToolkit.createSection(frmUsurio.getBody(), Section.TITLE_BAR);
		formToolkit.paintBordersFor(sctnDados);
		sctnDados.setText("Dados");
		
		Composite compositeDados = new Composite(sctnDados, SWT.NONE);
		formToolkit.adapt(compositeDados);
		formToolkit.paintBordersFor(compositeDados);
		sctnDados.setClient(compositeDados);
		compositeDados.setLayout(new GridLayout(2, false));
		
		Label lblNome = new Label(compositeDados, SWT.NONE);
		lblNome.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		formToolkit.adapt(lblNome, true, true);
		lblNome.setText("Nome");
		
		txtNome = new Text(compositeDados, SWT.BORDER);
		txtNome.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		formToolkit.adapt(txtNome, true, true);
		
		Label lblUsurio = new Label(compositeDados, SWT.NONE);
		lblUsurio.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		formToolkit.adapt(lblUsurio, true, true);
		lblUsurio.setText("Usuário");
		
		txtUsuario = new Text(compositeDados, SWT.BORDER);
		txtUsuario.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		formToolkit.adapt(txtUsuario, true, true);
		
		Label lblSenha = new Label(compositeDados, SWT.NONE);
		lblSenha.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		formToolkit.adapt(lblSenha, true, true);
		lblSenha.setText("Senha");
		
		txtSenha = new Text(compositeDados, SWT.BORDER | SWT.PASSWORD);
		txtSenha.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		formToolkit.adapt(txtSenha, true, true);
		
		initDataBinding();
	}

	@Override
	protected ModeloService<Usuario> newService() {
		return new UsuarioService();
	}
	
	protected DataBindingContext initDataBinding() {
		DataBindingContext bindingContext = new DataBindingContext(LayoutHelper.getDefaultRealm());
		//
		IObservableValue observeTextTxtNomeObserveWidget = WidgetProperties.text(SWT.Modify).observe(txtNome);
		IObservableValue getValueNomeObserveDetailValue = PojoProperties.value(Usuario.class, "nome", String.class).observeDetail(getValue());
		bindingContext.bindValue(observeTextTxtNomeObserveWidget, getValueNomeObserveDetailValue, null, null);
		//
		IObservableValue observeTextTxtUsuarioObserveWidget = WidgetProperties.text(SWT.Modify).observe(txtUsuario);
		IObservableValue getValueUsuarioObserveDetailValue = PojoProperties.value(Usuario.class, "login", String.class).observeDetail(getValue());
		bindingContext.bindValue(observeTextTxtUsuarioObserveWidget, getValueUsuarioObserveDetailValue, null, null);
		//
		IObservableValue observeTextTxtSenhaObserveWidget = WidgetProperties.text(SWT.Modify).observe(txtSenha);
		IObservableValue getValueSenhaObserveDetailValue = PojoProperties.value(Usuario.class, "senha", String.class).observeDetail(getValue());
		bindingContext.bindValue(observeTextTxtSenhaObserveWidget, getValueSenhaObserveDetailValue, null, null);
		//
		return bindingContext;
	}
}
