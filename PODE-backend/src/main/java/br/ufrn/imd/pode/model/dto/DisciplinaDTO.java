package br.ufrn.imd.pode.model.dto;

public class DisciplinaDTO extends AbstractDTO {

	private String codigo;

	private String nome;

	private Integer ch;

	private String prerequisitos;

	private String corequisitos;

	private String equivalentes;

	public DisciplinaDTO() {
	}

	/*public DisciplinaDTO(Disciplina novaDisciplina) {
		this.id = novaDisciplina.getId();
		this.codigo = novaDisciplina.getCodigo();
		this.nome = novaDisciplina.getNome();
		this.ch = novaDisciplina.getCh();
	}*/

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getCh() {
		return ch;
	}

	public void setCh(Integer ch) {
		this.ch = ch;
	}

	public String getPrerequisitos() {
		return prerequisitos;
	}

	public void setPrerequisitos(String prerequisitos) {
		this.prerequisitos = prerequisitos;
	}

	public String getCorequisitos() {
		return corequisitos;
	}

	public void setCorequisitos(String corequisitos) {
		this.corequisitos = corequisitos;
	}

	public String getEquivalentes() {
		return equivalentes;
	}

	public void setEquivalentes(String equivalentes) {
		this.equivalentes = equivalentes;
	}
}
