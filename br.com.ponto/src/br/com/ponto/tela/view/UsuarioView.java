package br.com.ponto.tela.view;

import org.apache.log4j.Logger;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.forms.widgets.Form;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.wb.swt.ResourceManager;

import br.com.ponto.aplicacao.helper.SelectionHelper;
import br.com.ponto.aplicacao.service.UsuarioService;
import br.com.ponto.banco.modelo.Usuario;
import br.com.ponto.tela.editor.UsuarioEditor;
import br.com.ponto.tela.editor.editorinput.ModeloEditorInput;
import br.com.ponto.tela.filter.UsuarioFilter;

public class UsuarioView extends ViewPart {

	public static final String ID = "br.com.ponto.tela.view.UsuarioView"; //$NON-NLS-1$
	
	private UsuarioService service = new UsuarioService();
	private UsuarioFilter filter = new UsuarioFilter();
	private Logger log = Logger.getLogger(getClass());
	
	private final FormToolkit formToolkit = new FormToolkit(Display.getDefault());
	private Text txtFiltro;

	private TableViewer tvUsuario;

	public UsuarioView() {}//NOSONAR

	@Override
	public void createPartControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Form frmUsurio = formToolkit.createForm(container);
		formToolkit.paintBordersFor(frmUsurio);
		frmUsurio.setText("Usuário");
		frmUsurio.getBody().setLayout(new GridLayout(1, false));
		
		Section sctnFiltro = formToolkit.createSection(frmUsurio.getBody(), Section.TITLE_BAR);
		sctnFiltro.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		formToolkit.paintBordersFor(sctnFiltro);
		sctnFiltro.setText("Filtro");
		
		Composite compositeFiltro = new Composite(sctnFiltro, SWT.NONE);
		formToolkit.adapt(compositeFiltro);
		formToolkit.paintBordersFor(compositeFiltro);
		sctnFiltro.setClient(compositeFiltro);
		compositeFiltro.setLayout(new GridLayout(1, false));
		
		txtFiltro = new Text(compositeFiltro, SWT.BORDER);
		txtFiltro.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				filter.setSearch(txtFiltro.getText());
				tvUsuario.refresh();
			}
		});
		txtFiltro.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		formToolkit.adapt(txtFiltro, true, true);
		txtFiltro.setMessage("Buscar...");
		
		Section sctnUsurios = formToolkit.createSection(frmUsurio.getBody(), Section.TITLE_BAR);
		sctnUsurios.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true, 1, 1));
		formToolkit.paintBordersFor(sctnUsurios);
		sctnUsurios.setText("Usuários");
		
		Composite compositeUsuario = new Composite(sctnUsurios, SWT.NONE);
		formToolkit.adapt(compositeUsuario);
		formToolkit.paintBordersFor(compositeUsuario);
		sctnUsurios.setClient(compositeUsuario);
		compositeUsuario.setLayout(new GridLayout(1, false));
		
		Button btnAdicionar = new Button(compositeUsuario, SWT.NONE);
		btnAdicionar.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					getSite().getPage().openEditor(new ModeloEditorInput(new Usuario()), UsuarioEditor.ID);
				} catch (PartInitException e1) {
					log.error("Erro ao cadastrar um novo usuário", e1);
				}
			}
		});
		btnAdicionar.setImage(ResourceManager.getPluginImage("br.com.ponto", "assents/funcoes/addView16.png"));
		formToolkit.adapt(btnAdicionar, true, true);
		btnAdicionar.setText("Adicionar");
		
		tvUsuario = new TableViewer(compositeUsuario, SWT.BORDER | SWT.FULL_SELECTION);
		tvUsuario.addDoubleClickListener(new IDoubleClickListener() {
			@Override
			public void doubleClick(DoubleClickEvent event) {
				try {
					Usuario usuario = SelectionHelper.getElement(tvUsuario);
					if(usuario == null)
						return;
					
					getSite().getPage().openEditor(new ModeloEditorInput(usuario), UsuarioEditor.ID);
				} catch (PartInitException e) {
					log.error("Erro ao editar um usuário", e);
				}
			}
		});
		Table tbUsuario = tvUsuario.getTable();
		tbUsuario.setLinesVisible(true);
		tbUsuario.setHeaderVisible(true);
		tbUsuario.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		formToolkit.paintBordersFor(tbUsuario);
		tvUsuario.setContentProvider(ArrayContentProvider.getInstance());
		tvUsuario.addFilter(filter);
		
		TableViewerColumn tvcNome = new TableViewerColumn(tvUsuario, SWT.NONE);
		tvcNome.setLabelProvider(new ColumnLabelProvider(){
			@Override
			public String getText(Object element) {
				return ((Usuario)element).getNome();
			}
		});
		TableColumn tblclmnNome = tvcNome.getColumn();
		tblclmnNome.setWidth(286);
		tblclmnNome.setText("Nome");
		
		TableViewerColumn tvcUsuario = new TableViewerColumn(tvUsuario, SWT.NONE);
		tvcUsuario.setLabelProvider(new ColumnLabelProvider(){
			@Override
			public String getText(Object element) {
				return ((Usuario)element).getLogin();
			}
		});
		TableColumn tblclmnUsurio = tvcUsuario.getColumn();
		tblclmnUsurio.setWidth(138);
		tblclmnUsurio.setText("Usuário");
	}

	@Override
	public void setFocus() {
		tvUsuario.setInput(service.findAll());
	}

}
