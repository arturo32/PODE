package br.ufrn.imd.app3;

import br.ufrn.imd.app3.modelo.Curso;
import br.ufrn.imd.app3.modelo.Conteudo;
import br.ufrn.imd.app3.repositorio.CursoRepositorio;
import br.ufrn.imd.app3.repositorio.ConteudoRepositorio;
import br.ufrn.imd.app3.servico.ConteudoCursadoServico;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Transactional
@Component
public class DatabaseLoader implements ApplicationRunner {

	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String dbmode;

	private ConteudoRepositorio disciplinaRepository;

	private CursoRepositorio cursoRepository;

	private ConteudoCursadoServico conteudoCursadoServico;

	@Autowired
	public void setDisciplinaRepository(ConteudoRepositorio disciplinaRepository) {
		this.disciplinaRepository = disciplinaRepository;
	}
	@Autowired
	public void setCursoRepository(CursoRepositorio cursoRepository) {
		this.cursoRepository = cursoRepository;
	}

	@Autowired
	public void setConteudoCursadoServico(ConteudoCursadoServico conteudoCursadoServico) {
		this.conteudoCursadoServico = conteudoCursadoServico;
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		if (dbmode.equals("create")) {
//			inserirDisciplinas();
			System.out.println("Disciplinas inseridas");
//			inserirCursos();
			System.out.println("Cursos inseridos");
//			inserirDisciplasCursos();
			System.out.println("Disciplinas obrigatorias e optativas dos cursos inseridas");

			System.out.println("Tudo pronto!");
		}
	}

	void inserirDisciplinas() {
		try (BufferedReader br = new BufferedReader(new FileReader("../extracao_dados/dados_extraidos/teste.csv"))) {
			CSVParser parser = new CSVParserBuilder().withSeparator(',').withIgnoreQuotations(false).build();
			CSVReader csvReader = new CSVReaderBuilder(br).withSkipLines(1).withCSVParser(parser).build();
			String[] values;
			while ((values = csvReader.readNext()) != null) {
//				Conteudo disciplina = new Conteudo(values[0], values[1], Integer.parseInt(values[4]));
//				disciplina.setPrerequisitos(values[6]);
//				this.disciplinaRepository.save(disciplina);
			}
		} catch (IOException | CsvValidationException e) {
			e.printStackTrace();
		}
	}

	void inserirCursos() {
		try (BufferedReader br = new BufferedReader(new FileReader("../extracao_dados/dados_extraidos/cursos_ti.csv"))) {
			CSVParser parser = new CSVParserBuilder().withSeparator(',').withIgnoreQuotations(true).build();
			CSVReader csvReader = new CSVReaderBuilder(br).withSkipLines(1).withCSVParser(parser).build();

			String[] values;
			while ((values = csvReader.readNext()) != null) {
//				Curso curso = new Curso(values[2] + " - " + values[1], Integer.parseInt(values[6]),
//						Integer.parseInt(values[7]), Integer.parseInt(values[8]), Integer.parseInt(values[9]),
//						Integer.parseInt(values[10]), Integer.parseInt(values[11]), Integer.parseInt(values[12]),
//						Integer.parseInt(values[3]), Integer.parseInt(values[5]), Integer.parseInt(values[4]));
//				this.cursoRepository.save(curso);
			}
		} catch (IOException | CsvValidationException e) {
			e.printStackTrace();
		}
	}
		void inserirDisciplasCursos() {
		Curso curso = cursoRepository.getOne(2L);
		curso.setDisciplinasObrigatorias(new HashSet<>());
		curso.setDisciplinasOptativas(new HashSet<>());
		try (BufferedReader br = new BufferedReader(
				new FileReader("../extracao_dados/dados_extraidos/obrigatorias_diurno.csv"))) {
			CSVParser parser = new CSVParserBuilder().withSeparator(',').withIgnoreQuotations(true).build();
			CSVReader csvReader = new CSVReaderBuilder(br).withSkipLines(1).withCSVParser(parser).build();

			String[] values;
			while ((values = csvReader.readNext()) != null) {
				Set<Conteudo> disciplinas = disciplinaRepository.findDisciplinasByAtivoIsTrueAndCodigoIs(values[1]);
				for (Conteudo d : disciplinas) {
//					DisciplinaPeriodo disciplinaPeriodo = disciplinaPeriodoService
//							.obterDisciplinaCursada(new DisciplinaPeriodoDTO(d.getId(), Integer.parseInt(values[0])));
//					curso.getDisciplinasObrigatorias().add(disciplinaPeriodo);
//					this.cursoRepository.save(curso);
				}
				if (disciplinas.isEmpty()) {
					System.err.println("Disciplina de código '" + values[1] + "' não encontrada.");
				}
			}
		} catch (IOException | CsvValidationException e) {
			e.printStackTrace();
		}
	}
}

