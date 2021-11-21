package br.ufrn.imd.pode.config;

import br.ufrn.imd.pode.model.Curso;
import br.ufrn.imd.pode.model.Disciplina;
import br.ufrn.imd.pode.repository.CursoRepository;
import br.ufrn.imd.pode.repository.DisciplinaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

@Component
public class DatabaseLoader implements ApplicationRunner {

	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String dbmode;

	private DisciplinaRepository disciplinaRepository;

	private CursoRepository cursoRepository;

	@Autowired
	public void setDisciplinaRepository(DisciplinaRepository disciplinaRepository) {
		this.disciplinaRepository = disciplinaRepository;
	}

	@Autowired
	public void setCursoRepository(CursoRepository cursoRepository) {
		this.cursoRepository = cursoRepository;
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		if (dbmode.equals("create")) {
			inserirDisciplinas();
			inserir_cursos();
		}
	}

	private void inserirDisciplinas() {
		try (BufferedReader br = new BufferedReader(new FileReader("extracao_dados/dados_extraidos/disciplinas_ti.csv"))) {
			String line = br.readLine();
			while ((line = br.readLine()) != null) {
				String[] values = line.split(",", -1);
				System.out.println(Arrays.toString(values));
				Disciplina disciplina = new Disciplina(Long.parseLong(values[0]), values[1], values[2], Integer.parseInt(values[5]));
				disciplina.setEquivalentes(values[6]);
				disciplina.setPrerequisitos(values[7]);
				disciplina.setCorequisitos(values[8]);
				this.disciplinaRepository.save(disciplina);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void inserir_cursos() {
		try (BufferedReader br = new BufferedReader(new FileReader("extracao_dados/dados_extraidos/cursos_ti.csv"))) {
			String line = br.readLine();
			while ((line = br.readLine()) != null) {
				String[] values = line.split(",", -1);
				Curso curso = new Curso(values[2] + values[1],
						Integer.parseInt(values[6]),
						Integer.parseInt(values[7]),
						Integer.parseInt(values[8]),
						Integer.parseInt(values[9]),
						Integer.parseInt(values[10]),
						Integer.parseInt(values[11]),
						Integer.parseInt(values[12]),
						Integer.parseInt(values[3]),
						Integer.parseInt(values[5]),
						Integer.parseInt(values[4])
				);
				this.cursoRepository.save(curso);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
