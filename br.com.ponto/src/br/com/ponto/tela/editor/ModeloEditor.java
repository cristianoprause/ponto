package br.com.ponto.tela.editor;

import static br.com.ponto.aplicacao.helper.MessageHelper.openInformation;
import static br.com.ponto.aplicacao.helper.MessageHelper.openQuestion;

import org.eclipse.core.databinding.observable.value.WritableValue;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.ISaveablePart2;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.part.EditorPart;
import org.eclipse.wb.swt.ResourceManager;

import br.com.ponto.aplicacao.exception.ValidationException;
import br.com.ponto.aplicacao.helper.LayoutHelper;
import br.com.ponto.aplicacao.helper.ValidatorHelper;
import br.com.ponto.aplicacao.service.ModeloService;
import br.com.ponto.banco.modelo.IModelo;
import br.com.ponto.tela.dialog.ErroDialog;
import br.com.ponto.tela.dialog.SimNaoCancelarDialog;
import br.com.ponto.tela.editor.editorinput.ModeloEditorInput;

public abstract class ModeloEditor<T> extends EditorPart implements ISaveablePart2{
	private final FormToolkit formToolkit = new FormToolkit(Display.getDefault());

	private WritableValue value;
	private ModeloService<T> service;
	protected abstract ModeloService<T> newService();
	private boolean showExcluir = true;

	protected Composite compositeConteudo;

	public ModeloEditor() {}//NOSONAR
	
	protected abstract void adicionarComponentes(Composite composite);
	
	@Override
	public void createPartControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new GridLayout(1, false));
		
		compositeConteudo = new Composite(container, SWT.NONE);
		compositeConteudo.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		adicionarComponentes(compositeConteudo);
		
		Section sctnEdicao = formToolkit.createSection(container, Section.TITLE_BAR);
		sctnEdicao.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		formToolkit.paintBordersFor(sctnEdicao);
		sctnEdicao.setText("Edição");
		sctnEdicao.setExpanded(true);
		
		Composite compositeBotoes = new Composite(sctnEdicao, SWT.NONE);
		formToolkit.adapt(compositeBotoes);
		formToolkit.paintBordersFor(compositeBotoes);
		sctnEdicao.setClient(compositeBotoes);
		compositeBotoes.setLayout(new GridLayout(2, false));
		
		Button btnSalvar = new Button(compositeBotoes, SWT.NONE);
		btnSalvar.setImage(ResourceManager.getPluginImage("br.com.ponto", "assents/funcoes/save32.png"));
		btnSalvar.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					salvarRegistro();
				} catch (ValidationException e1) {
					new ErroDialog(e1.getMessage()).open();
				}
			}
		});
		formToolkit.adapt(btnSalvar, true, true);
		btnSalvar.setText("Salvar");
		
		if(showExcluir){
			Button btnExcluir = new Button(compositeBotoes, SWT.NONE);
			btnExcluir.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					excluirRegistro();
				}
			});
			btnExcluir.setImage(ResourceManager.getPluginImage("br.com.pontos", "assents/funcoes/delete32.png"));
			formToolkit.adapt(btnExcluir, true, true);
			btnExcluir.setText("Excluir");
		}

	}
	
	protected void validar() throws ValidationException{
		ValidatorHelper.validar(getService().getModelo());
	}
	
	protected void closeEditor(){
		getEditorSite().getPart().getSite().getWorkbenchWindow().getActivePage().closeEditor(this, false);
	}

	@Override
	public void setFocus() {}

	@Override
	public void doSave(IProgressMonitor monitor) {}

	@Override
	public void doSaveAs() {}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}
	
	private void salvarRegistro() throws ValidationException{
		validar();
		getService().saveOrUpdate();
		openInformation("Registro lançado com sucesso!");
		closeEditor();
	}
	
	protected void excluirRegistro() {
		if(!openQuestion("Deseja realmente apagar este registro?"))
			return;
		getService().delete();
		openInformation("Registro apagado com sucesso!");
		closeEditor();
	}

	@Override
	public int promptToSaveOnClose() {
		SimNaoCancelarDialog sncd = new SimNaoCancelarDialog("Os dados foram alterados, deseja salvar antes de sair?");
		try {
			sncd.open();
			
			if(sncd.getId() == IDialogConstants.OK_ID){
				salvarRegistro();
				return YES;
			}
			
			if(sncd.getId() == IDialogConstants.CANCEL_ID)
				return NO;
		
		} catch (ValidationException e) {
			new ErroDialog(e.getMessage()).open();
		}
		
		return CANCEL;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		IModelo modelo = ((ModeloEditorInput)input).getModelo();
		
		getService().setModelo((T)modelo);
		value = new WritableValue(LayoutHelper.getDefaultRealm());
		value.setValue((T) modelo);
		
		setShowExcluir(modelo.getId() != null);
		
		setSite(site);
		setInput(input);
	}
	
	public void setShowExcluir(boolean showExcluir) {
		this.showExcluir = showExcluir;
	}
	
	@Override
	public boolean isDirty() {
		return getService().isDirty();
	}
	
	protected ModeloService<T> getService() {
		if(service == null)
			service = newService();
		return service;
	}
	
	public WritableValue getValue() {
		return value;
	}
	
	@SuppressWarnings("unchecked")
	public T getModelo(){
		return (T) value.getValue();
	}
}
