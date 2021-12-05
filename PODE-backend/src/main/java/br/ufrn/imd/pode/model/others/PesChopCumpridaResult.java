package br.ufrn.imd.pode.model.others;

public class PesChopCumpridaResult extends PesResult {

	@Override
	public Integer getChp() {
		return this.getChm() - (this.getCho() + this.getChc());
	}

}