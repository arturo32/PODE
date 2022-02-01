package br.ufrn.imd.pode.modelo;

import br.ufrn.imd.pode.modelo.dto.DisciplinaDTO;
import org.mvel2.MVEL;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Entity
@Table(name = "disciplina")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Disciplina extends ModeloAbstrato<Long> implements DisciplinaInterface {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_DISCIPLINA")
	@SequenceGenerator(name = "SEQ_DISCIPLINA", sequenceName = "id_seq_disciplina", allocationSize = 1)
	protected Long id;

	@NotNull
	@NotBlank
//	@Column(unique = true)
	protected String codigo;

	//	@NotNull
//	@NotBlank
	@Column(length = 1024)
	protected String nome;

	@NotNull
	protected Integer ch;

	@Column(length = 1024)
	private String prerequisitos;

	public Disciplina() {
	}

	public Disciplina(String codigo, String nome, Integer ch) {
		this.codigo = codigo;
		this.nome = nome;
		this.ch = ch;
	}

	public Disciplina(DisciplinaDTO disciplina) {
		this.id = disciplina.getId();
		this.codigo = disciplina.getCodigo();
		this.nome = disciplina.getNome();
		this.ch = disciplina.getCh();
	}

	@Override
	public Long getId() {
		return this.id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

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

	@Override
	public boolean checarPrerequisitosCodigos(Collection<String> codigos) {
		String expressao = getPrerequisitos().replace(" E ", " && ");
		expressao = expressao.replace(" OU ", " || ");
		Matcher matcher = Pattern.compile("([A-Z]{3}[0-9]{4}|[A-Z]{3}[0-9]{3})").matcher(expressao);
		while (matcher.find()) {
			for (int i = 0; i < matcher.groupCount(); i++) {
				String eval = String.valueOf(codigos.contains(matcher.group(i)));
				expressao = expressao.replace(matcher.group(i), eval);
			}
		}
		return (boolean) MVEL.eval(expressao);
	}

}