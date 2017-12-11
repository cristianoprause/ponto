package br.com.ponto.tela.dialog;

import java.math.BigDecimal;
import java.util.Date;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.beans.PojoProperties;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.observable.value.WritableValue;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;

import br.com.ponto.aplicacao.exception.ValidationException;
import br.com.ponto.aplicacao.helper.LayoutHelper;
import br.com.ponto.aplicacao.helper.MessageHelper;
import br.com.ponto.aplicacao.helper.UsuarioHelper;
import br.com.ponto.aplicacao.helper.ValidatorHelper;
import br.com.ponto.aplicacao.service.ConfiguracaoService;
import br.com.ponto.banco.modelo.Configuracao;
import br.com.ponto.tela.converters.BigDecimalToStringConvert;
import br.com.ponto.tela.converters.DateToStringConvert;
import br.com.ponto.tela.converters.StringToBigDecimalConvert;
import br.com.ponto.tela.converters.StringToDateConvert;
import br.com.ponto.tela.text.DateText;
import br.com.ponto.tela.text.NumberText;

public class ConfiguracaoDialog extends TitleAreaDialog {

	private ConfiguracaoService service = new ConfiguracaoService();
	private WritableValue configuracaoValue;

	private final FormToolkit formToolkit = new FormToolkit(Display.getDefault());
	private Text txtTempoDiaSemana;
	private Text txtTempoSabado;
	private Text txtMargemErro;
	private Text txtSalario;

	public ConfiguracaoDialog() {
		super(LayoutHelper.getActiveShell());
		
		Configuracao configuracao = service.findByUsuario(UsuarioHelper.getUsuarioLogado());
		if(configuracao == null)
			configuracao = new Configuracao();
		
		service.setModelo(configuracao);
		configuracaoValue = new WritableValue(LayoutHelper.getDefaultRealm());
		configuracaoValue.setValue(service.getModelo());
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		setMessage("Informe os dados da configuração");
		setTitle("Configurações do usuário");
		Composite area = (Composite) super.createDialogArea(parent);
		Composite container = new Composite(area, SWT.NONE);
		container.setLayout(new FillLayout(SWT.HORIZONTAL));
		container.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		Section sctnDados = formToolkit.createSection(container, Section.TITLE_BAR);
		formToolkit.paintBordersFor(sctnDados);
		sctnDados.setText("Dados");
		
		Composite compositeDados = new Composite(sctnDados, SWT.NONE);
		formToolkit.adapt(compositeDados);
		formToolkit.paintBordersFor(compositeDados);
		sctnDados.setClient(compositeDados);
		compositeDados.setLayout(new GridLayout(2, false));
		
		Label lblTempoNosDias = new Label(compositeDados, SWT.NONE);
		lblTempoNosDias.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		formToolkit.adapt(lblTempoNosDias, true, true);
		lblTempoNosDias.setText("Tempo nos dias de semana");
		
		txtTempoDiaSemana = new DateText(compositeDados, "H:m:s").getControl();//NOSONAR
		GridData gdTxtTempoDiaSemana = new GridData(SWT.LEFT, SWT.FILL, false, false, 1, 1);
		gdTxtTempoDiaSemana.widthHint = 79;
		txtTempoDiaSemana.setLayoutData(gdTxtTempoDiaSemana);
		formToolkit.adapt(txtTempoDiaSemana, true, true);
		
		Label lblTempoAosSbados = new Label(compositeDados, SWT.NONE);
		lblTempoAosSbados.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		formToolkit.adapt(lblTempoAosSbados, true, true);
		lblTempoAosSbados.setText("Tempo aos sábados");
		
		txtTempoSabado = new DateText(compositeDados, "H:m:s").getControl();
		GridData gdTxtTempoSabado = new GridData(SWT.LEFT, SWT.FILL, false, false, 1, 1);
		gdTxtTempoSabado.widthHint = 79;
		txtTempoSabado.setLayoutData(gdTxtTempoSabado);
		formToolkit.adapt(txtTempoSabado, true, true);
		
		Label lblMargemDeErro = new Label(compositeDados, SWT.NONE);
		lblMargemDeErro.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		formToolkit.adapt(lblMargemDeErro, true, true);
		lblMargemDeErro.setText("Margem de erro");
		
		txtMargemErro = new DateText(compositeDados, "H:m:s").getControl();
		GridData gdTxtMargemErro = new GridData(SWT.LEFT, SWT.FILL, false, false, 1, 1);
		gdTxtMargemErro.widthHint = 79;
		txtMargemErro.setLayoutData(gdTxtMargemErro);
		formToolkit.adapt(txtMargemErro, true, true);
		
		Label lblSalrio = new Label(compositeDados, SWT.NONE);
		lblSalrio.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		formToolkit.adapt(lblSalrio, true, true);
		lblSalrio.setText("Salário");
		
		txtSalario = new NumberText(compositeDados).getControl();
		GridData gdTxtSalario = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		gdTxtSalario.widthHint = 79;
		txtSalario.setLayoutData(gdTxtSalario);
		formToolkit.adapt(txtSalario, true, true);
		
		initDataBinding();
		
		return area;
	}

	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
		createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
	}
	
	@Override
	protected void okPressed() {
		try {
			ValidatorHelper.validar(service.getModelo());
			service.saveOrUpdate();
			MessageHelper.openInformation("Configurações registradas com sucesso!");
			super.okPressed();
		} catch (ValidationException e) {
			setErrorMessage(e.getMessage());
		}
	}
	
	@Override
	protected void cancelPressed() {
		resetModel();
		super.cancelPressed();
	}
	
	@Override
	protected void handleShellCloseEvent() {
		resetModel();
		super.handleShellCloseEvent();
	}
	
	private void resetModel() {
		if(service.getModelo().getId() != null)
			service.refresh();
	}

	@Override
	protected Point getInitialSize() {
		return new Point(300, 283);
	}
	
	protected DataBindingContext initDataBinding() {
		DataBindingContext bindingContext = new DataBindingContext(LayoutHelper.getDefaultRealm());
		//
		UpdateValueStrategy stringToTime = new UpdateValueStrategy().setConverter(new StringToDateConvert("HH:mm:ss"));
		UpdateValueStrategy timeToString = new UpdateValueStrategy().setConverter(new DateToStringConvert("HH:mm:ss"));
		UpdateValueStrategy stringToBigDecimal = new UpdateValueStrategy().setConverter(new StringToBigDecimalConvert());
		UpdateValueStrategy bigDecimalToString = new UpdateValueStrategy().setConverter(new BigDecimalToStringConvert());
		//
		IObservableValue observeTextTxtTempoDiaSemanaObserveWidget = WidgetProperties.text(SWT.Modify).observe(txtTempoDiaSemana);
		IObservableValue configuracaoValueTempoDiaSemanaObserveDetailValue = PojoProperties.value(Configuracao.class, "tempoDiaSemana", Date.class).observeDetail(configuracaoValue);
		bindingContext.bindValue(observeTextTxtTempoDiaSemanaObserveWidget, configuracaoValueTempoDiaSemanaObserveDetailValue, stringToTime, timeToString);
		//
		IObservableValue observeTextTxtTempoSabadoObserveWidget = WidgetProperties.text(SWT.Modify).observe(txtTempoSabado);
		IObservableValue configuracaoValueTempoSabadoObserveDetailValue = PojoProperties.value(Configuracao.class, "tempoSabado", Date.class).observeDetail(configuracaoValue);
		bindingContext.bindValue(observeTextTxtTempoSabadoObserveWidget, configuracaoValueTempoSabadoObserveDetailValue, stringToTime, timeToString);
		//
		IObservableValue observeTextTxtMargemErroObserveWidget = WidgetProperties.text(SWT.Modify).observe(txtMargemErro);
		IObservableValue configuracaoValueMargemErroObserveDetailValue = PojoProperties.value(Configuracao.class, "margemErro", Date.class).observeDetail(configuracaoValue);
		bindingContext.bindValue(observeTextTxtMargemErroObserveWidget, configuracaoValueMargemErroObserveDetailValue, stringToTime, timeToString);
		//
		IObservableValue observeTextTxtSalarioObserveWidget = WidgetProperties.text(SWT.Modify).observe(txtSalario);
		IObservableValue configuracaoValueSalarioObserveDetailValue = PojoProperties.value(Configuracao.class, "salario", BigDecimal.class).observeDetail(configuracaoValue);
		bindingContext.bindValue(observeTextTxtSalarioObserveWidget, configuracaoValueSalarioObserveDetailValue, stringToBigDecimal, bigDecimalToString);
		//
		return bindingContext;
	}
}
