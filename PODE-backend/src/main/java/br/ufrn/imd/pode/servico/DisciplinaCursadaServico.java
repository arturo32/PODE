package br.ufrn.imd.pode.servico;

import br.ufrn.imd.pode.modelo.DisciplinaCursada;
import br.ufrn.imd.pode.modelo.dto.DisciplinaCursadaDTO;

public abstract class DisciplinaCursadaServico <T extends DisciplinaCursada, E extends DisciplinaCursadaDTO> extends GenericoServico<T, E, Long> {
}
