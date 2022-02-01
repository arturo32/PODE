package br.ufrn.imd.app1.modelo;

import org.mvel2.MVEL;

import javax.persistence.*;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.ufrn.imd.pode.modelo.Disciplina;
import br.ufrn.imd.pode.modelo.DisciplinaInterface;

import br.ufrn.imd.app1.modelo.dto.DisciplinaBTIDTO;

@Entity
@Table(name = "disciplinabti")
public class DisciplinaBTI extends Disciplina implements DisciplinaInterface {
	@Column(length = 1024)

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_DISCIPLINA")
	@SequenceGenerator(name = "SEQ_DISCIPLINA", sequenceName = "id_seq_disciplina", allocationSize = 1)
	protected Long id;

	@Column(
			length = 1024
	)
	private String equivalentes;

	public DisciplinaBTI() {}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public String getEquivalentes() {
		return equivalentes;
	}

	public void setEquivalentes(String equivalentes) {
		this.equivalentes = equivalentes;
	}

	private boolean checarEquivalentesCodigos(Collection<String> codigos, String expressao) {
		expressao = expressao.replace(" E ", " && ");
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

	@Override
	public boolean checarPrerequisitosCodigos(Collection<String> codigos) {
		String expressao = getPrerequisitos().replace(" E ", " && ");
		expressao = expressao.replace(" OU ", " || ");
		Matcher matcher = Pattern.compile("([A-Z]{3}[0-9]{4}|[A-Z]{3}[0-9]{3})").matcher(expressao);
		while (matcher.find()) {
			for (int i = 0; i < matcher.groupCount(); i++) {
				String eval = String.valueOf(checarEquivalentesCodigos(codigos, matcher.group(i)));
				expressao = expressao.replace(matcher.group(i), eval);
			}
		}
		return (boolean) MVEL.eval(expressao);
	}
}
