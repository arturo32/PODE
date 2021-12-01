package br.ufrn.imd.pode;

import br.ufrn.imd.pode.model.Disciplina;
import br.ufrn.imd.pode.service.DisciplinaService;
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

    DisciplinaService disciplinaService;

    @Autowired
    public void setDisciplinaService(DisciplinaService disciplinaService) {
        this.disciplinaService = disciplinaService;
    }

    @Test
    public void contextLoads() {

    }

    @Test
    public void equivalencia() {
        Set<Disciplina> disciplina = disciplinaService.findDisciplinasByCodigo("IMD1113");
        Set<Disciplina> test = new HashSet<>();
        test.addAll(disciplinaService.findDisciplinasByCodigo("IMD0033"));
        test.addAll(disciplinaService.findDisciplinasByCodigo("DIM0132"));

        for (Disciplina d:disciplina) {
            Assert.assertTrue(disciplinaService.checarEquivalencia(test, d));
        }

        test = new HashSet<>();
        test.addAll(disciplinaService.findDisciplinasByCodigo("BSI1305"));
        test.addAll(disciplinaService.findDisciplinasByCodigo("APS1048"));
        for (Disciplina d:disciplina) {
            Assert.assertFalse(disciplinaService.checarEquivalencia(test, d));
        }
    }

    @Test
    public void prerequisito() {
        Set<Disciplina> disciplina = disciplinaService.findDisciplinasByCodigo("DIM0121");
        Set<Disciplina> test = new HashSet<>(disciplinaService.findDisciplinasByCodigo("IMD0028"));

        for (Disciplina d:disciplina) {
            Assert.assertTrue(disciplinaService.checarPrerequisitos(test, d));
        }

        test = new HashSet<>(disciplinaService.findDisciplinasByCodigo("DIM0115"));
        for (Disciplina d:disciplina) {
            Assert.assertTrue(disciplinaService.checarPrerequisitos(test, d));
        }

        test = new HashSet<>(disciplinaService.findDisciplinasByCodigo("IMD0022"));
        for (Disciplina d:disciplina) {
            Assert.assertFalse(disciplinaService.checarPrerequisitos(test, d));
        }
    }
}
