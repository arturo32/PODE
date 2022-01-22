package br.ufrn.imd.pode.modelo.view;

public interface PesChObrigatoriaCumprida {

	public Long getId();

	public String getNome();

	public Integer getChm();

	public Integer getCho();

	public Integer getChc();

	public default Integer getChp() {
		return this.getCho() - this.getChc();
	}

}
