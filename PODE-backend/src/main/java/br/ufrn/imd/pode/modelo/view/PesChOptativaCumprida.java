package br.ufrn.imd.pode.modelo.view;

public interface PesChOptativaCumprida {

	public Long getId();

	public String getNome();

	public Integer getChm();

	public Integer getCho();

	public Integer getChc();

	public default Integer getChp() {
		return this.getChm() - (this.getCho() + this.getChc());
	}

}
