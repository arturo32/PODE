package br.ufrn.imd.pode.service;

import br.ufrn.imd.pode.model.Disciplina;
import br.ufrn.imd.pode.model.dto.DisciplinaDTO;
import br.ufrn.imd.pode.repository.DisciplinaRepository;
import br.ufrn.imd.pode.repository.GenericRepository;
import org.mvel2.MVEL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@Transactional
public class DisciplinaService extends GenericService<Disciplina, DisciplinaDTO, Long> {

	private DisciplinaRepository repository;


	@Override
	public DisciplinaDTO convertToDto(Disciplina entity) {
		return null;
	}

	@Override
	public Disciplina convertToEntity(DisciplinaDTO dto) {
		if (dto.getId() != null) {
			return this.findById(dto.getId());
		}
		Disciplina disciplina = new Disciplina();
		disciplina.setId(dto.getId());
		disciplina.setCodigo(dto.getCodigo());
		disciplina.setNome(dto.getNome());
		disciplina.setCh(dto.getCh());

		//todo Validar expressões de equivalência
		disciplina.setPrerequisitos(dto.getPrerequisitos());
		disciplina.setCorequisitos(dto.getCorequisitos());
		disciplina.setEquivalentes(dto.getEquivalentes());

		return disciplina;
	}

	@Override
	protected GenericRepository<Disciplina, Long> repository() {
		return this.repository;
	}

	public DisciplinaRepository getRepository() {
		return this.repository;
	}

	@Autowired
	public void setRepository(DisciplinaRepository repository) {
		this.repository = repository;
	}

	public void salvar(Disciplina disciplina) {
		this.repository.save(disciplina);
	}

	// Checa se um conjunto de disciplinas é equivalente a disciplina alvo (se atendem a expressão de equivalencia)
	public boolean checarEquivalencia(Set<Disciplina> disciplinas, Disciplina disciplina_alvo) {
		Set<String> codigos = disciplinas.stream().map(Disciplina::getCodigo).collect(Collectors.toSet());
		String eq = disciplina_alvo.getEquivalentes();
		if (eq == null || eq.isEmpty()) {
			return false;
		}
		eq = eq.replace(" E ", " && ");
		eq = eq.replace(" OU ", " || ");
		Matcher matcher = Pattern.compile("([A-Z][A-Z][A-Z]\\d\\d\\d\\d)").matcher(eq);
		while(matcher.find()){
			for (int i = 0; i < matcher.groupCount(); i++) {
				String eval = String.valueOf(codigos.contains(matcher.group(i)));
				eq = eq.replace(matcher.group(i), eval);
			}
		}
		return (boolean) MVEL.eval(eq);
	}
}
