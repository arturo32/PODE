package br.ufrn.imd.pode.modelo.view;

public interface PesChOptativaCumprida {

	Long getId();

	String getNome();

	Integer getChm();

	Integer getCho();

	Integer getChc();

	default Integer getChp() {
		return this.getChm() - (this.getCho() + this.getChc());
	}

}
