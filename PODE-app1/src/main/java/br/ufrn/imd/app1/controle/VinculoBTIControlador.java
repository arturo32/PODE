package br.ufrn.imd.app1.controle;

import br.ufrn.imd.app1.modelo.VinculoBTI;
import br.ufrn.imd.app1.modelo.dto.VinculoBTIDTO;
import br.ufrn.imd.pode.controle.VinculoControlador;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/vinculos")
public class VinculoBTIControlador extends VinculoControlador<VinculoBTI, VinculoBTIDTO> {
}
