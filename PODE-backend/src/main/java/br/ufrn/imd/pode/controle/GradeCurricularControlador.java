package br.ufrn.imd.pode.controle;

import br.ufrn.imd.pode.modelo.GradeCurricular;
import br.ufrn.imd.pode.modelo.dto.GradeCurricularDTO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/grades-curriculares")
public abstract class GradeCurricularControlador<T extends GradeCurricular, E extends GradeCurricularDTO> extends GenericoControlador<T, E, Long> {
}
