package br.ufrn.imd.app1.config;

import br.ufrn.imd.pode.modelo.Disciplina;
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

@Transactional
@Component
public class DatabaseLoader implements ApplicationRunner {

	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String dbmode;

	private DisciplinaRepositorio disciplinaRepository;

	@Autowired
	public void setDisciplinaRepository(DisciplinaRepositorio disciplinaRepository) {
		this.disciplinaRepository = disciplinaRepository;
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		if (dbmode.equals("create")) {
			inserirDisciplinas();
			System.out.println("Disciplinas inseridas");
			//inserirCursos();
			//System.out.println("Cursos inseridos");

			System.out.println("Tudo pronto!");
		}
	}

	void inserirDisciplinas() {
		try (BufferedReader br = new BufferedReader(new FileReader("extracao_dados/dados_extraidos/disciplinas.csv"))) {
			CSVParser parser = new CSVParserBuilder().withSeparator(',').withIgnoreQuotations(false).build();
			CSVReader csvReader = new CSVReaderBuilder(br).withSkipLines(1).withCSVParser(parser).build();
			String[] values;
			while ((values = csvReader.readNext()) != null) {
				Disciplina disciplina = new Disciplina(values[0], values[1], Integer.parseInt(values[4]));
				disciplina.setPrerequisitos(values[6]);
				this.disciplinaRepository.save(disciplina);

			}
		} catch (IOException | CsvValidationException e) {
			e.printStackTrace();
		}
	}

	/*void inserirCursos() {
		try (BufferedReader br = new BufferedReader(new FileReader("extracao_dados/dados_extraidos/cursos_ti.csv"))) {
			CSVParser parser = new CSVParserBuilder().withSeparator(',').withIgnoreQuotations(true).build();
			CSVReader csvReader = new CSVReaderBuilder(br).withSkipLines(1).withCSVParser(parser).build();

			String[] values;
			while ((values = csvReader.readNext()) != null) {
				Curso curso = new Curso(values[2] + " - " + values[1], Integer.parseInt(values[6]),
						Integer.parseInt(values[7]), Integer.parseInt(values[8]), Integer.parseInt(values[9]),
						Integer.parseInt(values[10]), Integer.parseInt(values[11]), Integer.parseInt(values[12]),
						Integer.parseInt(values[3]), Integer.parseInt(values[5]), Integer.parseInt(values[4]));
				this.cursoRepository.save(curso);
			}
		} catch (IOException | CsvValidationException e) {
			e.printStackTrace();
		}
	}*/

	/*void inserirDisciplasCursos() {
		Curso curso = cursoRepository.getOne(2L);
		try (BufferedReader br = new BufferedReader(
				new FileReader("extracao_dados/dados_extraidos/obrigatorias_diurno.csv"))) {
			CSVParser parser = new CSVParserBuilder().withSeparator(',').withIgnoreQuotations(true).build();
			CSVReader csvReader = new CSVReaderBuilder(br).withSkipLines(1).withCSVParser(parser).build();

			String[] values;
			while ((values = csvReader.readNext()) != null) {
				Set<Disciplina> disciplinas = disciplinaRepository.findDisciplinasByAtivoIsTrueAndCodigoIs(values[1]);
				for (Disciplina d : disciplinas) {
					DisciplinaPeriodo disciplinaPeriodo = disciplinaPeriodoService
							.getDisciplinaPeriodoPorPeriodoDisciplinaId(Integer.parseInt(values[0]), d.getId());
					curso.adicionarDisciplinaObrigatoria(disciplinaPeriodo);
					this.cursoRepository.save(curso);
				}
				if (disciplinas.isEmpty()) {
					System.err.println("Disciplina de código '" + values[1] + "' não encontrada.");
				}
			}
		} catch (IOException | CsvValidationException e) {
			e.printStackTrace();
		}
		try (BufferedReader br = new BufferedReader(
				new FileReader("extracao_dados/dados_extraidos/optativas_diurno.csv"))) {
			CSVParser parser = new CSVParserBuilder().withSeparator(',').withIgnoreQuotations(true).build();
			CSVReader csvReader = new CSVReaderBuilder(br).withSkipLines(1).withCSVParser(parser).build();

			String[] values;
			while ((values = csvReader.readNext()) != null) {
				Set<Disciplina> disciplinas = disciplinaRepository.findDisciplinasByAtivoIsTrueAndCodigoIs(values[0]);
				for (Disciplina d : disciplinas) {
					curso.adicionarDisciplinaOptativa(d);
					this.cursoRepository.save(curso);
				}
				if (disciplinas.isEmpty()) {
					System.err.println("Disciplina de código '" + values[1] + "' não encontrada.");
				}
			}
		} catch (IOException | CsvValidationException e) {
			e.printStackTrace();
		}

		curso = cursoRepository.getOne(1L);
		try (BufferedReader br = new BufferedReader(
				new FileReader("extracao_dados/dados_extraidos/obrigatorias_noturno.csv"))) {
			CSVParser parser = new CSVParserBuilder().withSeparator(',').withIgnoreQuotations(true).build();
			CSVReader csvReader = new CSVReaderBuilder(br).withSkipLines(1).withCSVParser(parser).build();

			String[] values;
			while ((values = csvReader.readNext()) != null) {
				Set<Disciplina> disciplinas = disciplinaRepository.findDisciplinasByAtivoIsTrueAndCodigoIs(values[1]);
				for (Disciplina d : disciplinas) {
					DisciplinaPeriodo disciplinaPeriodo = disciplinaPeriodoService
							.getDisciplinaPeriodoPorPeriodoDisciplinaId(Integer.parseInt(values[0]), d.getId());
					curso.adicionarDisciplinaObrigatoria(disciplinaPeriodo);
					this.cursoRepository.save(curso);
				}
				if (disciplinas.isEmpty()) {
					System.err.println("Disciplina de código '" + values[1] + "' não encontrada.");
				}
			}
		} catch (IOException | CsvValidationException e) {
			e.printStackTrace();
		}
		try (BufferedReader br = new BufferedReader(
				new FileReader("extracao_dados/dados_extraidos/optativas_noturno.csv"))) {
			CSVParser parser = new CSVParserBuilder().withSeparator(',').withIgnoreQuotations(true).build();
			CSVReader csvReader = new CSVReaderBuilder(br).withSkipLines(1).withCSVParser(parser).build();

			String[] values;
			while ((values = csvReader.readNext()) != null) {
				Set<Disciplina> disciplinas = disciplinaRepository.findDisciplinasByAtivoIsTrueAndCodigoIs(values[0]);
				for (Disciplina d : disciplinas) {
					curso.adicionarDisciplinaOptativa(d);
					this.cursoRepository.save(curso);
				}
				if (disciplinas.isEmpty()) {
					System.err.println("Disciplina de código '" + values[1] + "' não encontrada.");
				}
			}
		} catch (IOException | CsvValidationException e) {
			e.printStackTrace();
		}

		Enfase enfase = enfaseRepository.getOne(1L);
		try (BufferedReader br = new BufferedReader(
				new FileReader("extracao_dados/dados_extraidos/obrigatorias_dev_soft.csv"))) {
			CSVParser parser = new CSVParserBuilder().withSeparator(',').withIgnoreQuotations(true).build();
			CSVReader csvReader = new CSVReaderBuilder(br).withSkipLines(1).withCSVParser(parser).build();

			String[] values;
			while ((values = csvReader.readNext()) != null) {
				Set<Disciplina> disciplinas = disciplinaRepository.findDisciplinasByAtivoIsTrueAndCodigoIs(values[1]);
				for (Disciplina d : disciplinas) {
					DisciplinaPeriodo disciplinaPeriodo = disciplinaPeriodoService
							.getDisciplinaPeriodoPorPeriodoDisciplinaId(Integer.parseInt(values[0]), d.getId());
					enfase.adicionarDisciplinaObrigatoria(disciplinaPeriodo);
					this.enfaseRepository.save(enfase);
				}
				if (disciplinas.isEmpty()) {
					System.err.println("Disciplina de código '" + values[1] + "' não encontrada.");
				}
			}
		} catch (IOException | CsvValidationException e) {
			e.printStackTrace();
		}
		enfase = enfaseRepository.getOne(2L);
		try (BufferedReader br = new BufferedReader(
				new FileReader("extracao_dados/dados_extraidos/obrigatorias_comput.csv"))) {
			CSVParser parser = new CSVParserBuilder().withSeparator(',').withIgnoreQuotations(true).build();
			CSVReader csvReader = new CSVReaderBuilder(br).withSkipLines(1).withCSVParser(parser).build();

			String[] values;
			while ((values = csvReader.readNext()) != null) {
				Set<Disciplina> disciplinas = disciplinaRepository.findDisciplinasByAtivoIsTrueAndCodigoIs(values[1]);
				for (Disciplina d : disciplinas) {
					DisciplinaPeriodo disciplinaPeriodo = disciplinaPeriodoService
							.getDisciplinaPeriodoPorPeriodoDisciplinaId(Integer.parseInt(values[0]), d.getId());
					enfase.adicionarDisciplinaObrigatoria(disciplinaPeriodo);
					this.enfaseRepository.save(enfase);
				}
				if (disciplinas.isEmpty()) {
					System.err.println("Disciplina de código '" + values[1] + "' não encontrada.");
				}
			}
		} catch (IOException | CsvValidationException e) {
			e.printStackTrace();
		}
	}*/
}
