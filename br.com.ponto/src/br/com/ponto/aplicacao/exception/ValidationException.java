package br.com.ponto.aplicacao.exception;

import static com.google.common.collect.Lists.newArrayList;

import java.util.List;

public class ValidationException extends Exception {

	private static final long serialVersionUID = -6792343688787102258L;
	private final String mensagem;
	private final List<String> mensagens;

	public ValidationException(String mensagem, List<String> mensagens) {
		this.mensagem = mensagem;
		this.mensagens = mensagens;
	}
	
	public ValidationException(String mensagem) {
		this.mensagem = mensagem;
		this.mensagens = newArrayList(mensagem);
	}

	@Override
	public String getMessage() {
		return mensagem;
	}

	public List<String> getMensagens() {
		return mensagens;
	}
}