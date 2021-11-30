package br.ufrn.imd.pode;

import br.ufrn.imd.pode.model.Disciplina;
import br.ufrn.imd.pode.service.DisciplinaService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
        Disciplina disciplina = disciplinaService.findById(1L);
    }
}
