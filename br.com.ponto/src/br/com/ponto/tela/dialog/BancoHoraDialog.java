package br.com.ponto.tela.dialog;

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
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import br.com.ponto.aplicacao.exception.ValidationException;
import br.com.ponto.aplicacao.helper.LayoutHelper;
import br.com.ponto.aplicacao.helper.MessageHelper;
import br.com.ponto.aplicacao.helper.ValidatorHelper;
import br.com.ponto.aplicacao.service.BancoHoraService;
import br.com.ponto.banco.modelo.BancoHora;
import br.com.ponto.tela.converters.DateToStringConvert;
import br.com.ponto.tela.converters.StringToDateConvert;
import br.com.ponto.tela.text.DateText;

public class BancoHoraDialog extends TitleAreaDialog {
	
	private BancoHoraService service = new BancoHoraService();
	private WritableValue bancoValue;
	
	private Text txtDataInicial;
	private Text txtDataFinal;

	public BancoHoraDialog() {
		this(new BancoHora());
	}
	
	public BancoHoraDialog(BancoHora banco) {
		super(LayoutHelper.getActiveShell());
		service.setModelo(banco);
		bancoValue = new WritableValue(LayoutHelper.getDefaultRealm());
		bancoValue.setValue(service.getModelo());
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		setMessage("Informe o período do banco de horas");
		setTitle("Banco de horas");
		
		Composite area = (Composite) super.createDialogArea(parent);
		Composite container = new Composite(area, SWT.NONE);
		container.setLayout(new GridLayout(4, false));
		container.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		Label lblDataInicial = new Label(container, SWT.NONE);
		lblDataInicial.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblDataInicial.setText("Data inicial");
		
		txtDataInicial = new DateText(container).getControl();
		GridData gdTxtDataInicial = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gdTxtDataInicial.widthHint = 79;
		txtDataInicial.setLayoutData(gdTxtDataInicial);
		
		Label lblDataFinal = new Label(container, SWT.NONE);
		lblDataFinal.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblDataFinal.setText("Data final");
		
		txtDataFinal = new DateText(container).getControl();
		GridData gdTxtDataFinal = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gdTxtDataFinal.widthHint = 79;
		txtDataFinal.setLayoutData(gdTxtDataFinal);

		initDataBinding();
		
		return area;
	}
	
	@Override
	protected void okPressed() {
		try {
			ValidatorHelper.validar(service.getModelo());
			
			if(!service.isBancoHoraLivre(service.getModelo()))
				throw new ValidationException("Ja existe um banco de horas dentro do período informado");
			
			service.saveOrUpdate();
			MessageHelper.openInformation("Banco de horas registrado com sucesso!");
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
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
		createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
	}

	@Override
	protected Point getInitialSize() {
		return new Point(329, 184);
	}
	
	protected DataBindingContext initDataBinding() {
		DataBindingContext bindingContext = new DataBindingContext(LayoutHelper.getDefaultRealm());
		//
		UpdateValueStrategy stringToDate = new UpdateValueStrategy().setConverter(new StringToDateConvert());
		UpdateValueStrategy dateToString = new UpdateValueStrategy().setConverter(new DateToStringConvert());
		//
		IObservableValue observeTextTxtDataInicialObserveWidget = WidgetProperties.text(SWT.Modify).observe(txtDataInicial);
		IObservableValue bancoValueDataInicialObserveDetailValue = PojoProperties.value(BancoHora.class, "dataInicial", Date.class).observeDetail(bancoValue);
		bindingContext.bindValue(observeTextTxtDataInicialObserveWidget, bancoValueDataInicialObserveDetailValue, stringToDate, dateToString);
		//
		IObservableValue observeTextTxtDataFinalObserveWidget = WidgetProperties.text(SWT.Modify).observe(txtDataFinal);
		IObservableValue bancoValueDataFinalObserveDetailValue = PojoProperties.value(BancoHora.class, "dataFinal", Date.class).observeDetail(bancoValue);
		bindingContext.bindValue(observeTextTxtDataFinalObserveWidget, bancoValueDataFinalObserveDetailValue, stringToDate, dateToString);
		//
		return bindingContext;
	}
}
