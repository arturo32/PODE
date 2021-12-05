package br.ufrn.imd.pode.model.others;

public abstract class PesResult {

	private Long id;
	private String nome;
	private Integer chm;
	private Integer cho;
	private Integer chc;

	public PesResult() {
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

	public Integer getChc() {
		return chc;
	}

	public void setChc(Integer chc) {
		this.chc = chc;
	}
	
	public abstract Integer getChp();

}
