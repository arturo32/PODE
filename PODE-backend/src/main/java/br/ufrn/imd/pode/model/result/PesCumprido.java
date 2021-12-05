package br.ufrn.imd.pode.model.result;

public class PesCumprido {

	private Long id;
	private String nome;
	private Integer chm;
	private Integer cho;
	private Integer chCumprida;

	public PesCumprido() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getChm() {
		return chm;
	}

	public void setChm(Integer chm) {
		this.chm = chm;
	}

	public Integer getCho() {
		return cho;
	}

	public void setCho(Integer cho) {
		this.cho = cho;
	}

	public Integer getChCumprida() {
		return chCumprida;
	}

	public void setChCumprida(Integer chCumprida) {
		this.chCumprida = chCumprida;
	}

}
