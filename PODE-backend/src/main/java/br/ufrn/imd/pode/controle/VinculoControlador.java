package br.ufrn.imd.pode.controle;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ufrn.imd.pode.modelo.Vinculo;
import br.ufrn.imd.pode.modelo.dto.VinculoDTO;
import br.ufrn.imd.pode.servico.VinculoServico;

@RestController
@RequestMapping("/vinculos")
public abstract class VinculoControlador<T extends Vinculo, D extends VinculoDTO> extends GenericoControlador<T, D, Long> {

    public abstract VinculoServico<T, D> getVinculoServico();

    @GetMapping("/{id}/percentual-conclusao")
    public Double obterPercentualConclusao(@PathVariable Long idVinculo) {
        return this.getVinculoServico().obterPercentualConclusao(idVinculo);
    }

}
