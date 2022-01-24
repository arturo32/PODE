package br.ufrn.imd.pode;

import br.ufrn.imd.pode.modelo.Disciplina;
import br.ufrn.imd.pode.servico.DisciplinaServico;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {

    DisciplinaServico disciplinaService;

    @Autowired
    public void setDisciplinaService(DisciplinaServico disciplinaService) {
        this.disciplinaService = disciplinaService;
    }

    @Test
    public void contextLoads() {

    }

    @Test
    public void equivalencia() {
        Set<Disciplina> disciplina = disciplinaService.buscarDisciplinasPorCodigo("IMD1113");
        Set<Disciplina> test = new HashSet<>();
        test.addAll(disciplinaService.buscarDisciplinasPorCodigo("IMD0033"));
        test.addAll(disciplinaService.buscarDisciplinasPorCodigo("DIM0132"));

        for (Disciplina d:disciplina) {
            Assert.assertTrue(disciplinaService.checarEquivalencia(test, d));
        }

        test = new HashSet<>();
        test.addAll(disciplinaService.buscarDisciplinasPorCodigo("BSI1305"));
        test.addAll(disciplinaService.buscarDisciplinasPorCodigo("APS1048"));
        for (Disciplina d:disciplina) {
            Assert.assertFalse(disciplinaService.checarEquivalencia(test, d));
        }
    }

    @Test
    public void prerequisito() {
        Set<Disciplina> disciplina = disciplinaService.buscarDisciplinasPorCodigo("DIM0121");
        Set<Disciplina> test = new HashSet<>(disciplinaService.buscarDisciplinasPorCodigo("IMD0028"));

        for (Disciplina d:disciplina) {
            Assert.assertTrue(disciplinaService.checarPrerequisitos(test, d));
        }

        test = new HashSet<>(disciplinaService.buscarDisciplinasPorCodigo("DIM0115"));
        for (Disciplina d:disciplina) {
            Assert.assertTrue(disciplinaService.checarPrerequisitos(test, d));
        }

        test = new HashSet<>(disciplinaService.buscarDisciplinasPorCodigo("IMD0022"));
        for (Disciplina d:disciplina) {
            Assert.assertFalse(disciplinaService.checarPrerequisitos(test, d));
        }
    }
}
