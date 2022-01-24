package br.ufrn.imd.pode.modelo.view;

public interface PesChObrigatoriaCumprida {

	Long getId();

	String getNome();

	Integer getChm();

	Integer getCho();

	Integer getChc();

	default Integer getChp() {
		return this.getCho() - this.getChc();
	}

}
