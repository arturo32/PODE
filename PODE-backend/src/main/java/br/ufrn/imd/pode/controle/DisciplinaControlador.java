package br.ufrn.imd.pode.controle;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

import br.ufrn.imd.pode.modelo.Disciplina;
import br.ufrn.imd.pode.modelo.dto.DisciplinaDTO;
import br.ufrn.imd.pode.servico.DisciplinaServico;

@RestController
@RequestMapping("/disciplinas")
public abstract class DisciplinaControlador <T extends Disciplina, D extends DisciplinaDTO> extends GenericoControlador<T, D, Long> {

    private DisciplinaServico<T, D> getDisciplinaServico() {
        return (DisciplinaServico<T, D>) this.servico();
    }

    @GetMapping("/codigos/{codigo}")
    public ResponseEntity<Collection<D>> buscarDisciplinaCodigo(@PathVariable String codigo) {
        return ResponseEntity.ok(this.getDisciplinaServico().converterParaListaDTO(this.getDisciplinaServico().buscarDisciplinasPorCodigo(codigo)));
    }

}
