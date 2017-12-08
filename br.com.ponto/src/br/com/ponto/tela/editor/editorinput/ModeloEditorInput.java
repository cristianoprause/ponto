package br.com.ponto.tela.editor.editorinput;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

import br.com.ponto.banco.modelo.IModelo;

public class ModeloEditorInput implements IEditorInput{

	private IModelo modelo;

	public ModeloEditorInput(IModelo modelo) {
		this.modelo = modelo;
	}
	
	@Override
	@SuppressWarnings("rawtypes")
	public Object getAdapter(Class adapter) {
		return null;
	}

	@Override
	public boolean exists() {
		return false;
	}

	@Override
	public ImageDescriptor getImageDescriptor() {
		return null;
	}

	@Override
	public String getName() {
		return "";
	}

	@Override
	public IPersistableElement getPersistable() {
		return null;
	}

	@Override
	public String getToolTipText() {
		return "";
	}
	
	public IModelo getModelo() {
		return modelo;
	}

}
