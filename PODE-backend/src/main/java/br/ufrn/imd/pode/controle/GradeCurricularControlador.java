package br.ufrn.imd.pode.controle;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ufrn.imd.pode.modelo.GradeCurricular;
import br.ufrn.imd.pode.modelo.dto.GradeCurricularDTO;

@RestController
@RequestMapping("/grades-curriculares")
public abstract class GradeCurricularControlador <T extends GradeCurricular, D extends GradeCurricularDTO> extends GenericoControlador<T, D, Long> {

}
