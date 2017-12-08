package br.com.ponto.aplicacao.internal;

import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IWorkbenchPart;

public abstract class PartListenerAdapter implements IPartListener {

@Override
public void partActivated(IWorkbenchPart part) {}

@Override
public void partBroughtToTop(IWorkbenchPart part) {}

@Override
public void partDeactivated(IWorkbenchPart part) {}

@Override
public void partOpened(IWorkbenchPart part) {}

}