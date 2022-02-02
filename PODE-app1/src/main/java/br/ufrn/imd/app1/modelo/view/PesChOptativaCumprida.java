package br.ufrn.imd.app1.modelo.view;

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
