package br.ufrn.imd.pode.controle;

import br.ufrn.imd.pode.modelo.DisciplinaCursada;
import br.ufrn.imd.pode.modelo.dto.DisciplinaCursadaDTO;

public abstract class DisciplinaCursadaControlador <T extends DisciplinaCursada, D extends DisciplinaCursadaDTO> extends GenericoControlador<T, D, Long> {
}
