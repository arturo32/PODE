package br.ufrn.imd.app1.modelo.view;

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
