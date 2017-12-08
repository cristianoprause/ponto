package br.com.ponto.tela.dialog;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import br.com.ponto.aplicacao.exception.ValidationException;
import br.com.ponto.aplicacao.helper.LayoutHelper;
import br.com.ponto.aplicacao.service.UsuarioService;
import br.com.ponto.banco.modelo.Usuario;

public class LoginDialog extends TitleAreaDialog {
	
	private UsuarioService service = new UsuarioService();
	private Usuario usuario;
	
	private Text txtUsuario;
	private Text txtSenha;

	public LoginDialog() {
		super(LayoutHelper.getActiveShell());
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		setMessage("Informe os dados do login");
		setTitle("Login");
		Composite area = (Composite) super.createDialogArea(parent);
		Composite container = new Composite(area, SWT.NONE);
		container.setLayout(new GridLayout(2, false));
		container.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		Label lblUsurio = new Label(container, SWT.NONE);
		lblUsurio.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblUsurio.setText("Usuário");
		
		txtUsuario = new Text(container, SWT.BORDER);
		txtUsuario.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblSenha = new Label(container, SWT.NONE);
		lblSenha.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblSenha.setText("Senha");
		
		txtSenha = new Text(container, SWT.BORDER | SWT.PASSWORD);
		txtSenha.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		return area;
	}
	
	@Override
	protected void okPressed() {
		try{
			if(txtUsuario.getText().isEmpty()){
				txtUsuario.setFocus();
				throw new ValidationException("Informe o usuário");
			}
			
			if(txtSenha.getText().isEmpty()){
				txtSenha.setFocus();
				throw new ValidationException("Informe a senha");
			}
			
			usuario = service.findByUsuarioSenha(txtUsuario.getText(), txtSenha.getText());
			if(usuario == null){
				txtUsuario.setFocus();
				throw new ValidationException("Usuário não encontrado ou a senha esta incorreta. Verifique");
			}
			
			super.okPressed();
		}catch(ValidationException e) {
			setErrorMessage(e.getMessage());
		}
	}

	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
		createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
	}

	@Override
	protected Point getInitialSize() {
		return new Point(319, 217);
	}
	
	public Usuario getUsuario() {
		return usuario;
	}

}
