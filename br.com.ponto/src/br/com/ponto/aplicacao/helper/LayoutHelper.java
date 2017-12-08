package br.com.ponto.aplicacao.helper;

import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class LayoutHelper {
	
	private static Shell activeShell;
	private static Realm defaultRealm;
	
	private LayoutHelper() {}
	
	public static Shell getActiveShell() {
		if(activeShell == null)
			activeShell = Display.getDefault().getActiveShell();
		return activeShell;
	}
	
	public static Realm getDefaultRealm() {
		if(defaultRealm == null)
			defaultRealm = new Realm() {
				@Override
				public boolean isCurrent() {
					return true;
				}
			};
			
		return defaultRealm;
	}

}
