package br.ufrn.imd.pode.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufrn.imd.pode.model.dto.RecomendacaoDTO;
import br.ufrn.imd.pode.model.result.PesCumprido;

@Service
@Transactional
public class RecomendacaoService {

	private PesService pesService;

	public PesService getPesService() {
		return pesService;
	}

	@Autowired
	public void setPesService(PesService pesService) {
		this.pesService = pesService;
	}

	public List<RecomendacaoDTO> recomendarDisciplinasPorProximidadeConclusaoPes(long id_vinculo) {
		/* Etapa 1 */
		List<PesCumprido> chobCumpridaDosPes = this.pesService.getRepository().buscarChobCumpridaDosPes(id_vinculo);
		for (PesCumprido pesCumprido : chobCumpridaDosPes) {
			System.out.println(pesCumprido.getChCumprida());
		}
		/* Etapa 2 */
		List<PesCumprido> chopCumpridaDosPes = this.pesService.getRepository().buscarChopCumpridaDosPes(id_vinculo);
		for (PesCumprido pesCumprido : chopCumpridaDosPes) {
			System.out.println(pesCumprido.getChCumprida());
		}
		return null;
	}

}
