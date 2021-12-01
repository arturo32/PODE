package br.ufrn.imd.pode.service;

import br.ufrn.imd.pode.exception.ValidationException;
import br.ufrn.imd.pode.helper.ExceptionHelper;
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
	public DisciplinaDTO convertToDto(Disciplina disciplina) {
		return new DisciplinaDTO(disciplina);
	}

	@Override
	public Disciplina convertToEntity(DisciplinaDTO disciplinaDTO) {
		Disciplina disciplina = new Disciplina();
		disciplina.setId(disciplinaDTO.getId());
		disciplina.setCodigo(disciplinaDTO.getCodigo());
		disciplina.setNome(disciplinaDTO.getNome());
		disciplina.setCh(disciplinaDTO.getCh());
		// TODO validar expressões de equivalência
		disciplina.setPrerequisitos(disciplinaDTO.getPrerequisitos());
		disciplina.setCorequisitos(disciplinaDTO.getCorequisitos());
		disciplina.setEquivalentes(disciplinaDTO.getEquivalentes());
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
	public void setRepository(DisciplinaRepository disciplinaRepository) {
		this.repository = disciplinaRepository;
	}

	public Set<Disciplina> findDisciplinasByCodigo(String codigo) {
		return this.repository.findDisciplinasByAtivoIsTrueAndCodigoIs(codigo);
	}

	public boolean checarEquivalencia(Set<String> codigos, String expressao) {
		expressao = expressao.replace(" E ", " && ");
		expressao = expressao.replace(" OU ", " || ");
		Matcher matcher = Pattern.compile("([A-Z]{3}[0-9]{4})").matcher(expressao);
		while (matcher.find()) {
			for (int i = 0; i < matcher.groupCount(); i++) {
				String eval = String.valueOf(codigos.contains(matcher.group(i)));
				expressao = expressao.replace(matcher.group(i), eval);
			}
		}
		return (boolean) MVEL.eval(expressao);
	}

	// Checa se um conjunto de disciplinas é equivalente a disciplina alvo (se atendem a expressão de equivalencia)
	public boolean checarEquivalencia(Set<Disciplina> disciplinas, Disciplina disciplina_alvo) {
		Set<String> codigos = disciplinas.stream().map(Disciplina::getCodigo).collect(Collectors.toSet());
		String expressao = disciplina_alvo.getEquivalentes();
		if (expressao == null || expressao.isEmpty()) {
			return false;
		}
		return checarEquivalencia(codigos, expressao);
	}

	public boolean checarPrerequisitos(Set<String> codigos, String expressao) {
		expressao = expressao.replace(" E ", " && ");
		expressao = expressao.replace(" OU ", " || ");
		Matcher matcher = Pattern.compile("([A-Z]{3}[0-9]{4})").matcher(expressao);
		while (matcher.find()) {
			for (int i = 0; i < matcher.groupCount(); i++) {
				String eval = String.valueOf(checarEquivalencia(codigos, matcher.group(i)));
				expressao = expressao.replace(matcher.group(i), eval);
			}
		}
		return (boolean) MVEL.eval(expressao);
	}

	// Checa se um conjunto de disciplinas atende os prerequisitos da disciplina alvo (se atendem a expressão de prerequisito)
	public boolean checarPrerequisitos(Set<Disciplina> disciplinas, Disciplina disciplina_alvo) {
		Set<String> codigos = disciplinas.stream().map(Disciplina::getCodigo).collect(Collectors.toSet());
		String expressao = disciplina_alvo.getPrerequisitos();
		if (expressao == null || expressao.isEmpty()) {
			return false;
		}
		return checarPrerequisitos(codigos, expressao);
	}

	@Override
	public DisciplinaDTO validate(DisciplinaDTO disciplina) {
		ExceptionHelper exceptionHelper = new ExceptionHelper();
		/* verifica codigo */
		if (disciplina.getCodigo() == null || disciplina.getCodigo().isEmpty()) {
			exceptionHelper.add("codigo inválido");
		} else {
			Matcher matcher = Pattern.compile("[A-Z]{3}[0-9]{4}").matcher(disciplina.getCodigo());
			if (!matcher.find()) {
				exceptionHelper.add("formato de codigo inválido(exemplo: ABC1234)");
			}
		}
		/* verifica nome */
		if (disciplina.getNome() == null || disciplina.getNome().isEmpty()) {
			exceptionHelper.add("nome inválido");
		}
		/* verifica ch */
		if (disciplina.getCh() == null || disciplina.getCh() <= 0) {
			exceptionHelper.add("ch inválido");
		}
		// TODO verificar prequisitos, equivalencias e corequisitos
		/* verifica se existe exceçao */
		if (exceptionHelper.getMessage().isEmpty()) {
			return disciplina;
		} else {
			throw new ValidationException(exceptionHelper.getMessage());
		}
	}

}
