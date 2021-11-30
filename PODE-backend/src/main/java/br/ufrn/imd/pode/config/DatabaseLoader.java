package br.ufrn.imd.pode.config;

import br.ufrn.imd.pode.model.*;
import br.ufrn.imd.pode.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Optional;

@Transactional
@Component
public class DatabaseLoader implements ApplicationRunner {

	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String dbmode;

	private DisciplinaRepository disciplinaRepository;

	private CursoRepository cursoRepository;

	private EnfaseRepository enfaseRepository;

	private PesRepository pesRepository;

	private DisciplinaPeriodoRepository disciplinaPeriodoRepository;

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

	@Autowired
	public void setDisciplinaPeriodoRepository(DisciplinaPeriodoRepository disciplinaPeriodoRepository) {
		this.disciplinaPeriodoRepository = disciplinaPeriodoRepository;
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		if (dbmode.equals("create")) {
//			inserirDisciplinas();
//			inserirCursos();
//			inserirEnfases();
//			inserirPes();
//			inserirDisciplasCursos();
//			inserirDisciplinaPes();
			System.out.println("DONE");
		}
	}

	@Transactional
	void inserirDisciplinas() {
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

	@Transactional
	void inserirCursos() {
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

	@Transactional
	void inserirEnfases() {
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

	@Transactional
	void inserirPes() {
		try (BufferedReader br = new BufferedReader(new FileReader("extracao_dados/dados_extraidos/cursos_pes.csv"))) {
			String line = br.readLine();
			while ((line = br.readLine()) != null) {
				String[] values = line.split(",", -1);
				Pes pes = new Pes(values[1], Integer.parseInt(values[2]), Integer.parseInt(values[3]));
				this.pesRepository.save(pes);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Transactional
	void inserirDisciplasCursos() {
		Curso curso = cursoRepository.getOne(2L);
		try (BufferedReader br = new BufferedReader(new FileReader("extracao_dados/dados_extraidos/obrigatorias_diurno.csv"))) {
			String line = br.readLine();
			while ((line = br.readLine()) != null) {
				String[] values = line.split(",", -1);
				Optional<Disciplina> disciplina = disciplinaRepository.findDisciplinaByAtivoIsTrueAndCodigoIs(values[1]);
				if (disciplina.isPresent()) {
					DisciplinaPeriodo disciplinaPeriodo = new DisciplinaPeriodo(disciplina.get(), Integer.parseInt(values[0]));
					disciplinaPeriodo = this.disciplinaPeriodoRepository.save(disciplinaPeriodo);
					curso.adicionarDisciplinaObrigatoria(disciplinaPeriodo);
					this.cursoRepository.save(curso);
				} else {
					System.err.println("Disciplina de código '"+ values[1] +"' não encontrada.");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		try (BufferedReader br = new BufferedReader(new FileReader("extracao_dados/dados_extraidos/optativas_diurno.csv"))) {
			String line = br.readLine();
			while ((line = br.readLine()) != null) {
				String[] values = line.split(",", -1);
				Optional<Disciplina> disciplina = disciplinaRepository.findDisciplinaByAtivoIsTrueAndCodigoIs(values[0]);
				if (disciplina.isPresent()) {
					curso.adicionarDisciplinaOptativa(disciplina.get());
					this.cursoRepository.save(curso);
				} else {
					System.err.println("Disciplina de código '"+ values[1] +"' não encontrada.");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		curso = cursoRepository.getOne(1L);
		try (BufferedReader br = new BufferedReader(new FileReader("extracao_dados/dados_extraidos/obrigatorias_noturno.csv"))) {
			String line = br.readLine();
			while ((line = br.readLine()) != null) {
				String[] values = line.split(",", -1);
				Optional<Disciplina> disciplina = disciplinaRepository.findDisciplinaByAtivoIsTrueAndCodigoIs(values[1]);
				if (disciplina.isPresent()) {
					DisciplinaPeriodo disciplinaPeriodo = new DisciplinaPeriodo(disciplina.get(), Integer.parseInt(values[0]));
					disciplinaPeriodo = this.disciplinaPeriodoRepository.save(disciplinaPeriodo);
					curso.adicionarDisciplinaObrigatoria(disciplinaPeriodo);
					this.cursoRepository.save(curso);
				} else {
					System.err.println("Disciplina de código '"+ values[1] +"' não encontrada.");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		try (BufferedReader br = new BufferedReader(new FileReader("extracao_dados/dados_extraidos/optativas_noturno.csv"))) {
			String line = br.readLine();
			while ((line = br.readLine()) != null) {
				String[] values = line.split(",", -1);
				Optional<Disciplina> disciplina = disciplinaRepository.findDisciplinaByAtivoIsTrueAndCodigoIs(values[0]);
				if (disciplina.isPresent()) {
					curso.adicionarDisciplinaOptativa(disciplina.get());
					this.cursoRepository.save(curso);
				} else {
					System.err.println("Disciplina de código '"+ values[1] +"' não encontrada.");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		Enfase enfase = enfaseRepository.getOne(1L);
		try (BufferedReader br = new BufferedReader(new FileReader("extracao_dados/dados_extraidos/obrigatorias_dev_soft.csv"))) {
			String line = br.readLine();
			while ((line = br.readLine()) != null) {
				String[] values = line.split(",", -1);
				Optional<Disciplina> disciplina = disciplinaRepository.findDisciplinaByAtivoIsTrueAndCodigoIs(values[1]);
				if (disciplina.isPresent()) {
					DisciplinaPeriodo disciplinaPeriodo = new DisciplinaPeriodo(disciplina.get(), Integer.parseInt(values[0]));
					disciplinaPeriodo = this.disciplinaPeriodoRepository.save(disciplinaPeriodo);
					enfase.adicionarDisciplinaObrigatoria(disciplinaPeriodo);
					this.enfaseRepository.save(enfase);
				} else {
					System.err.println("Disciplina de código '"+ values[1] +"' não encontrada.");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		enfase = enfaseRepository.getOne(2L);
		try (BufferedReader br = new BufferedReader(new FileReader("extracao_dados/dados_extraidos/obrigatorias_comput.csv"))) {
			String line = br.readLine();
			while ((line = br.readLine()) != null) {
				String[] values = line.split(",", -1);
				Optional<Disciplina> disciplina = disciplinaRepository.findDisciplinaByAtivoIsTrueAndCodigoIs(values[1]);
				if (disciplina.isPresent()) {
					DisciplinaPeriodo disciplinaPeriodo = new DisciplinaPeriodo(disciplina.get(), Integer.parseInt(values[0]));
					disciplinaPeriodo = this.disciplinaPeriodoRepository.save(disciplinaPeriodo);
					enfase.adicionarDisciplinaObrigatoria(disciplinaPeriodo);
					this.enfaseRepository.save(enfase);
				} else {
					System.err.println("Disciplina de código '"+ values[1] +"' não encontrada.");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Transactional
	void inserirDisciplinaPes() {
		long last_id = 0L;
		long current_id = 0L;
		Pes pes = pesRepository.getOne(current_id+1);
		try (BufferedReader br = new BufferedReader(new FileReader("extracao_dados/dados_extraidos/curriculo_componente_pes.csv"))) {
			String line = br.readLine();
			while ((line = br.readLine()) != null) {
				String[] values = line.split(",", -1);
				current_id = Long.parseLong(values[1]);
				if (last_id != current_id) {
					last_id = current_id;
					pes = pesRepository.getOne(current_id+1);
				}
				Optional<Disciplina> disciplina = disciplinaRepository.findDisciplinaByAtivoIsTrueAndCodigoIs(values[3]);
				if (disciplina.isPresent()) {
					if (values[2].equals("OPTATIVA")) {
						pes.adicionarDisciplinaOptativa(disciplina.get());
					} else {
						pes.adicionarDisciplinaObrigatoria(disciplina.get());
					}
					this.pesRepository.save(pes);
				} else {
					System.err.println("Disciplina de código '"+ values[1] +"' não encontrada.");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
