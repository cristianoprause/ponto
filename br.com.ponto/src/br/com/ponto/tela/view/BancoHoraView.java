package br.com.ponto.tela.view;

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
import org.eclipse.ui.forms.widgets.Form;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.wb.swt.ResourceManager;

import br.com.ponto.aplicacao.helper.FormatterHelper;
import br.com.ponto.aplicacao.helper.SelectionHelper;
import br.com.ponto.aplicacao.service.BancoHoraService;
import br.com.ponto.banco.modelo.BancoHora;
import br.com.ponto.tela.dialog.BancoHoraDialog;
import br.com.ponto.tela.filter.BancoHoraFilter;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

public class BancoHoraView extends ViewPart {
	public BancoHoraView() {
	}

	public static final String ID = "br.com.ponto.tela.view.BancoHoraView"; //$NON-NLS-1$
	
	private BancoHoraService service = new BancoHoraService();
	private BancoHoraFilter filter = new BancoHoraFilter();
	
	private final FormToolkit formToolkit = new FormToolkit(Display.getDefault());
	private Text txtFiltro;
	private TableViewer tvBancoHora;

	@Override
	public void createPartControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Form frmBancoDeHoras = formToolkit.createForm(container);
		formToolkit.paintBordersFor(frmBancoDeHoras);
		frmBancoDeHoras.setText("Banco de horas");
		frmBancoDeHoras.getBody().setLayout(new GridLayout(1, false));
		
		Section sctnFiltro = formToolkit.createSection(frmBancoDeHoras.getBody(), Section.TITLE_BAR);
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
				tvBancoHora.refresh();
			}
		});
		txtFiltro.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		formToolkit.adapt(txtFiltro, true, true);
		txtFiltro.setMessage("Buscar...");
		
		Section sctnBancoDeHoras = formToolkit.createSection(frmBancoDeHoras.getBody(), Section.TITLE_BAR);
		sctnBancoDeHoras.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		formToolkit.paintBordersFor(sctnBancoDeHoras);
		sctnBancoDeHoras.setText("Banco de horas");
		
		Composite compositeBancoHora = new Composite(sctnBancoDeHoras, SWT.NONE);
		formToolkit.adapt(compositeBancoHora);
		formToolkit.paintBordersFor(compositeBancoHora);
		sctnBancoDeHoras.setClient(compositeBancoHora);
		compositeBancoHora.setLayout(new GridLayout(1, false));
		
		Button btnAdicionar = new Button(compositeBancoHora, SWT.NONE);
		btnAdicionar.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				new BancoHoraDialog().open();
				setFocus();
			}
		});
		formToolkit.adapt(btnAdicionar, true, true);
		btnAdicionar.setImage(ResourceManager.getPluginImage("br.com.ponto", "assents/funcoes/addView16.png"));
		btnAdicionar.setText("Adicionar");
		
		tvBancoHora = new TableViewer(compositeBancoHora, SWT.BORDER | SWT.FULL_SELECTION);
		tvBancoHora.addDoubleClickListener(new IDoubleClickListener() {
			@Override
			public void doubleClick(DoubleClickEvent event) {
				BancoHora banco = SelectionHelper.getElement(tvBancoHora);
				if(banco == null)
					return;
				
				new BancoHoraDialog(banco).open();
				tvBancoHora.refresh();
			}
		});
		Table tbBancoHora = tvBancoHora.getTable();
		tbBancoHora.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		tbBancoHora.setLinesVisible(true);
		tbBancoHora.setHeaderVisible(true);
		formToolkit.paintBordersFor(tbBancoHora);
		tvBancoHora.setContentProvider(ArrayContentProvider.getInstance());
		
		TableViewerColumn tvcDataInicial = new TableViewerColumn(tvBancoHora, SWT.NONE);
		tvcDataInicial.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				return FormatterHelper.formatDate(((BancoHora)element).getDataInicial());
			}
		});
		TableColumn tblclmnDataInicial = tvcDataInicial.getColumn();
		tblclmnDataInicial.setWidth(247);
		tblclmnDataInicial.setText("Data inicial");
		
		TableViewerColumn tvcDataFinal = new TableViewerColumn(tvBancoHora, SWT.NONE);
		tvcDataFinal.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				return FormatterHelper.formatDate(((BancoHora)element).getDataFinal());
			}
		});
		TableColumn tblclmnDataFinal = tvcDataFinal.getColumn();
		tblclmnDataFinal.setWidth(299);
		tblclmnDataFinal.setText("Data final");
		
		Menu menu = new Menu(tbBancoHora);
		tbBancoHora.setMenu(menu);
		
		MenuItem mntmExcluir = new MenuItem(menu, SWT.NONE);
		mntmExcluir.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				BancoHora banco = SelectionHelper.getElement(tvBancoHora);
				if(banco == null)
					return;
				
				service.delete(banco);
				setFocus();
			}
		});
		mntmExcluir.setImage(ResourceManager.getPluginImage("br.com.ponto", "assents/funcoes/remove16.png"));
		mntmExcluir.setText("Excluir");

	}

	@Override
	public void setFocus() {
		tvBancoHora.setInput(service.findAll());
		tvBancoHora.refresh();
	}
}
