package br.com.ponto.aplicacao.helper;

public class Helper {

	private Helper() {}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static boolean isEquals(Object obj1, Object obj2) {
		if(obj1 == null || obj2 == null)
			return false;
		
		if(obj1 instanceof Comparable && obj2 instanceof Comparable)
			return ((Comparable)obj1).compareTo(obj2) == 0;
		
		return obj1.equals(obj2);
	}
}
