package br.ufrn.imd.pode.config;

import br.ufrn.imd.pode.model.Curso;
import br.ufrn.imd.pode.model.Disciplina;
import br.ufrn.imd.pode.model.Enfase;
import br.ufrn.imd.pode.model.Pes;
import br.ufrn.imd.pode.repository.CursoRepository;
import br.ufrn.imd.pode.repository.DisciplinaRepository;
import br.ufrn.imd.pode.repository.EnfaseRepository;
import br.ufrn.imd.pode.repository.PesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Optional;

@Component
public class DatabaseLoader implements ApplicationRunner {

	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String dbmode;

	private DisciplinaRepository disciplinaRepository;

	private CursoRepository cursoRepository;

	private EnfaseRepository enfaseRepository;

	private PesRepository pesRepository;

	@Autowired
	public void setDisciplinaRepository(DisciplinaRepository disciplinaRepository) {
		this.disciplinaRepository = disciplinaRepository;
	}

	@Autowired
	public void setCursoRepository(CursoRepository cursoRepository) {
		this.cursoRepository = cursoRepository;
	}

	@Autowired
	public void setEnfaseRepository(EnfaseRepository enfaseRepository) {
		this.enfaseRepository = enfaseRepository;
	}

	@Autowired
	public void setPesRepository(PesRepository pesRepository) {
		this.pesRepository = pesRepository;
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		if (dbmode.equals("create")) {
			inserirDisciplinas();
			inserirCursos();
			inserirEnfases();
			inserirPes();
			System.out.println("DONE");
		}
	}

	private void inserirDisciplinas() {
		try (BufferedReader br = new BufferedReader(new FileReader("extracao_dados/dados_extraidos/disciplinas.csv"))) {
			String line = br.readLine();
			while ((line = br.readLine()) != null) {
				String[] values = line.split(";", -1);
				Disciplina disciplina = new Disciplina(values[0], values[1], Integer.parseInt(values[4]));
				disciplina.setEquivalentes(values[5]);
				disciplina.setPrerequisitos(values[6]);
				disciplina.setCorequisitos(values[7]);
				this.disciplinaRepository.save(disciplina);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void inserirCursos() {
		try (BufferedReader br = new BufferedReader(new FileReader("extracao_dados/dados_extraidos/cursos_ti.csv"))) {
			String line = br.readLine();
			while ((line = br.readLine()) != null) {
				String[] values = line.split(",", -1);
				Curso curso = new Curso(values[2] + " - " + values[1],
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

	private void inserirEnfases() {
		Curso curso = cursoRepository.getOne(2L);
		try (BufferedReader br = new BufferedReader(new FileReader("extracao_dados/dados_extraidos/enfases_ti.csv"))) {
			String line = br.readLine();
			while ((line = br.readLine()) != null) {
				String[] values = line.split(",", -1);
				Enfase enfase = new Enfase(values[2] + " - " + values[1], curso);
				this.enfaseRepository.save(enfase);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void inserirPes() {
		try (BufferedReader br = new BufferedReader(new FileReader("extracao_dados/dados_extraidos/cursos_pes.csv"))) {
			String line = br.readLine();
			while ((line = br.readLine()) != null) {
				String[] values = line.split(",", -1);
				Pes pes = new Pes(values[1], Integer.parseInt(values[2]), Integer.parseInt(values[2]));
				this.pesRepository.save(pes);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
