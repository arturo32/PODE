package br.ufrn.imd.app3;

import br.ufrn.imd.app3.modelo.CursoBTI;
import br.ufrn.imd.app3.modelo.DisciplinaBTI;
import br.ufrn.imd.app3.modelo.DisciplinaPeriodo;
import br.ufrn.imd.app3.modelo.Enfase;
import br.ufrn.imd.app3.modelo.dto.DisciplinaPeriodoDTO;
import br.ufrn.imd.app3.repositorio.CursoBTIRepositorio;
import br.ufrn.imd.app3.repositorio.DisciplinaBTIRepositorio;
import br.ufrn.imd.app3.repositorio.EnfaseRepositorio;
import br.ufrn.imd.app3.servico.DisciplinaPeriodoServico;
import br.ufrn.imd.pode.modelo.Disciplina;
import br.ufrn.imd.pode.modelo.DisciplinaCursada;
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

	private DisciplinaBTIRepositorio disciplinaRepository;

	private CursoBTIRepositorio cursoRepository;

	private EnfaseRepositorio enfaseRepositorio;

	private DisciplinaPeriodoServico disciplinaPeriodoService;

	@Autowired
	public void setDisciplinaRepository(DisciplinaBTIRepositorio disciplinaRepository) {
		this.disciplinaRepository = disciplinaRepository;
	}
	@Autowired
	public void setCursoRepository(CursoBTIRepositorio cursoRepository) {
		this.cursoRepository = cursoRepository;
	}
	@Autowired
	public void setEnfaseRepositorio(EnfaseRepositorio enfaseRepositorio) {
		this.enfaseRepositorio = enfaseRepositorio;
	}
	@Autowired
	public void setDisciplinaPeriodoService(DisciplinaPeriodoServico disciplinaPeriodoService) {
		this.disciplinaPeriodoService = disciplinaPeriodoService;
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		if (dbmode.equals("create")) {
			inserirDisciplinas();
			System.out.println("Disciplinas inseridas");
			inserirCursos();
			System.out.println("Cursos inseridos");
			inserirEnfases();
			System.out.println("PES inseridos");
			inserirDisciplasCursos();
			System.out.println("Disciplinas obrigatorias e optativas dos cursos inseridas");
			inserirDisciplinaEnfases();
			System.out.println("Disciplinas obrigatorias e optativas das enfases inseridas");

			System.out.println("Tudo pronto!");
		}
	}

	void inserirDisciplinas() {
		try (BufferedReader br = new BufferedReader(new FileReader("../extracao_dados/dados_extraidos/teste.csv"))) {
			CSVParser parser = new CSVParserBuilder().withSeparator(',').withIgnoreQuotations(false).build();
			CSVReader csvReader = new CSVReaderBuilder(br).withSkipLines(1).withCSVParser(parser).build();
			String[] values;
			while ((values = csvReader.readNext()) != null) {
				DisciplinaBTI disciplina = new DisciplinaBTI(values[0], values[1], Integer.parseInt(values[4]));
				disciplina.setEquivalentes(values[5]);
				disciplina.setPrerequisitos(values[6]);
				this.disciplinaRepository.save(disciplina);
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
				CursoBTI curso = new CursoBTI(values[2] + " - " + values[1], Integer.parseInt(values[6]),
						Integer.parseInt(values[7]), Integer.parseInt(values[8]), Integer.parseInt(values[9]),
						Integer.parseInt(values[10]), Integer.parseInt(values[11]), Integer.parseInt(values[12]),
						Integer.parseInt(values[3]), Integer.parseInt(values[5]), Integer.parseInt(values[4]));
				this.cursoRepository.save(curso);
			}
		} catch (IOException | CsvValidationException e) {
			e.printStackTrace();
		}
	}

	void inserirEnfases() {
		CursoBTI curso = cursoRepository.getOne(2L);
		try (BufferedReader br = new BufferedReader(new FileReader("../extracao_dados/dados_extraidos/enfases_ti.csv"))) {
			CSVParser parser = new CSVParserBuilder().withSeparator(',').withIgnoreQuotations(true).build();
			CSVReader csvReader = new CSVReaderBuilder(br).withSkipLines(1).withCSVParser(parser).build();

			String[] values;
			while ((values = csvReader.readNext()) != null) {
				Enfase enfase = new Enfase(values[2] + " - " + values[1], curso);
				this.enfaseRepositorio.save(enfase);
			}
		} catch (IOException | CsvValidationException e) {
			e.printStackTrace();
		}
	}

	void inserirDisciplasCursos() {
		CursoBTI curso = cursoRepository.getOne(2L);
		curso.setDisciplinasObrigatorias(new HashSet<>());
		curso.setDisciplinasOptativas(new HashSet<>());
		try (BufferedReader br = new BufferedReader(
				new FileReader("../extracao_dados/dados_extraidos/obrigatorias_diurno.csv"))) {
			CSVParser parser = new CSVParserBuilder().withSeparator(',').withIgnoreQuotations(true).build();
			CSVReader csvReader = new CSVReaderBuilder(br).withSkipLines(1).withCSVParser(parser).build();

			String[] values;
			while ((values = csvReader.readNext()) != null) {
				Set<DisciplinaBTI> disciplinas = disciplinaRepository.findDisciplinasByAtivoIsTrueAndCodigoIs(values[1]);
				for (DisciplinaBTI d : disciplinas) {
					DisciplinaPeriodo disciplinaPeriodo = disciplinaPeriodoService
							.obterDisciplinaCursada(new DisciplinaPeriodoDTO(d.getId(), Integer.parseInt(values[0])));
					curso.getDisciplinasObrigatorias().add(disciplinaPeriodo);
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
				new FileReader("../extracao_dados/dados_extraidos/optativas_diurno.csv"))) {
			CSVParser parser = new CSVParserBuilder().withSeparator(',').withIgnoreQuotations(true).build();
			CSVReader csvReader = new CSVReaderBuilder(br).withSkipLines(1).withCSVParser(parser).build();

			String[] values;
			while ((values = csvReader.readNext()) != null) {
				Set<DisciplinaBTI> disciplinas = disciplinaRepository.findDisciplinasByAtivoIsTrueAndCodigoIs(values[0]);
				for (DisciplinaBTI d : disciplinas) {
					curso.getDisciplinasOptativas().add(d);
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
		curso.setDisciplinasObrigatorias(new HashSet<>());
		curso.setDisciplinasOptativas(new HashSet<>());
		try (BufferedReader br = new BufferedReader(
				new FileReader("../extracao_dados/dados_extraidos/obrigatorias_noturno.csv"))) {
			CSVParser parser = new CSVParserBuilder().withSeparator(',').withIgnoreQuotations(true).build();
			CSVReader csvReader = new CSVReaderBuilder(br).withSkipLines(1).withCSVParser(parser).build();

			String[] values;
			while ((values = csvReader.readNext()) != null) {
				Set<DisciplinaBTI> disciplinas = disciplinaRepository.findDisciplinasByAtivoIsTrueAndCodigoIs(values[1]);
				for (DisciplinaBTI d : disciplinas) {
					DisciplinaPeriodo disciplinaPeriodo = disciplinaPeriodoService
							.obterDisciplinaCursada(new DisciplinaPeriodoDTO(d.getId(), Integer.parseInt(values[0])));
					curso.getDisciplinasObrigatorias().add(disciplinaPeriodo);
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
				new FileReader("../extracao_dados/dados_extraidos/optativas_noturno.csv"))) {
			CSVParser parser = new CSVParserBuilder().withSeparator(',').withIgnoreQuotations(true).build();
			CSVReader csvReader = new CSVReaderBuilder(br).withSkipLines(1).withCSVParser(parser).build();

			String[] values;
			while ((values = csvReader.readNext()) != null) {
				Set<DisciplinaBTI> disciplinas = disciplinaRepository.findDisciplinasByAtivoIsTrueAndCodigoIs(values[0]);
				for (DisciplinaBTI d : disciplinas) {
					curso.getDisciplinasOptativas().add(d);
					this.cursoRepository.save(curso);
				}
				if (disciplinas.isEmpty()) {
					System.err.println("Disciplina de código '" + values[1] + "' não encontrada.");
				}
			}
		} catch (IOException | CsvValidationException e) {
			e.printStackTrace();
		}
	}

	void inserirDisciplinaEnfases() {
		Enfase enfase = enfaseRepositorio.getOne(3L);
		Set<DisciplinaCursada> disciplinaPeriodoSet = new HashSet<>();
		try (BufferedReader br = new BufferedReader(
				new FileReader("../extracao_dados/dados_extraidos/obrigatorias_dev_soft.csv"))) {
			CSVParser parser = new CSVParserBuilder().withSeparator(',').withIgnoreQuotations(true).build();
			CSVReader csvReader = new CSVReaderBuilder(br).withSkipLines(1).withCSVParser(parser).build();

			String[] values;
			while ((values = csvReader.readNext()) != null) {
				Set<DisciplinaBTI> disciplinas = disciplinaRepository.findDisciplinasByAtivoIsTrueAndCodigoIs(values[1]);
				for (DisciplinaBTI d : disciplinas) {
					DisciplinaPeriodo disciplinaPeriodo = disciplinaPeriodoService
							.obterDisciplinaCursada(new DisciplinaPeriodoDTO(d.getId(), Integer.parseInt(values[0])));
					disciplinaPeriodoSet.add(disciplinaPeriodo);
				}
				if (disciplinas.isEmpty()) {
					System.err.println("Disciplina de código '" + values[1] + "' não encontrada.");
				}
			}
		} catch (IOException | CsvValidationException e) {
			e.printStackTrace();
		}
		enfase.setDisciplinasObrigatorias(disciplinaPeriodoSet);
		this.enfaseRepositorio.save(enfase);

		enfase = enfaseRepositorio.getOne(4L);
		disciplinaPeriodoSet = new HashSet<>();
		try (BufferedReader br = new BufferedReader(
				new FileReader("../extracao_dados/dados_extraidos/obrigatorias_comput.csv"))) {
			CSVParser parser = new CSVParserBuilder().withSeparator(',').withIgnoreQuotations(true).build();
			CSVReader csvReader = new CSVReaderBuilder(br).withSkipLines(1).withCSVParser(parser).build();

			String[] values;
			while ((values = csvReader.readNext()) != null) {
				Set<DisciplinaBTI> disciplinas = disciplinaRepository.findDisciplinasByAtivoIsTrueAndCodigoIs(values[1]);
				for (Disciplina d : disciplinas) {
					DisciplinaPeriodo disciplinaPeriodo = disciplinaPeriodoService
							.obterDisciplinaCursada(new DisciplinaPeriodoDTO(d.getId(), Integer.parseInt(values[0])));
					disciplinaPeriodoSet.add(disciplinaPeriodo);
				}
				if (disciplinas.isEmpty()) {
					System.err.println("Disciplina de código '" + values[1] + "' não encontrada.");
				}
			}
		} catch (IOException | CsvValidationException e) {
			e.printStackTrace();
		}
		enfase.setDisciplinasObrigatorias(disciplinaPeriodoSet);
		this.enfaseRepositorio.save(enfase);
	}
}

