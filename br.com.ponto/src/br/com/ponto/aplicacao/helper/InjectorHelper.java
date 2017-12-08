package br.com.ponto.aplicacao.helper;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class InjectorHelper {
	
	private InjectorHelper() {}

	private static Injector injector;
	
	public static Injector getInstance(){
		if(injector == null){
			injector = Guice.createInjector(new AbstractModule() {
				@Override
				protected void configure() {}//NOSONAR
			});
		}
		
		return injector;
	}
	
}
