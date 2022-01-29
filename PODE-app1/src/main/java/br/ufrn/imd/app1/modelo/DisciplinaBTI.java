package br.ufrn.imd.app1.modelo;

import br.ufrn.imd.app1.modelo.dto.DisciplinaBTIDTO;
import br.ufrn.imd.pode.modelo.Disciplina;
import br.ufrn.imd.pode.modelo.DisciplinaInterface;
import org.mvel2.MVEL;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Entity
@Table(
		name = "disciplinabti"
)
public class DisciplinaBTI extends Disciplina implements DisciplinaInterface {
	@Column(
			length = 1024
	)
	private String equivalentes;

	public DisciplinaBTI() {}

	public DisciplinaBTI(String codigo, String nome, Integer ch) {
		super(codigo, nome, ch);
	}

	public DisciplinaBTI(DisciplinaBTIDTO disciplina) {
		super(disciplina);
		this.setEquivalentes(disciplina.getEquivalentes());
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