package br.ufrn.imd.pode.model.others;

public class PesChobCumpridaResult extends PesResult {

	public PesChobCumpridaResult() {
		super();
	}

	@Override
	public Integer getChp() {
		return this.getCho() - this.getChc();
	}

}