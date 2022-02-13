package br.ufrn.imd.app3;

import br.ufrn.imd.app3.modelo.*;
import br.ufrn.imd.app3.repositorio.CursoRepositorio;
import br.ufrn.imd.app3.repositorio.ConteudoRepositorio;
import br.ufrn.imd.app3.repositorio.TemaRepositorio;
import br.ufrn.imd.app3.repositorio.TopicoRepositorio;
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
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Transactional
@Component
public class DatabaseLoader implements ApplicationRunner {

	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String dbmode;

	private ConteudoRepositorio conteudoRepositorio;

	private CursoRepositorio cursoRepository;

	private ConteudoCursadoServico conteudoCursadoServico;

	private TemaRepositorio temaRepositorio;

	private TopicoRepositorio topicoRepositorio;

	@Autowired
	public void setConteudoRepositorio(ConteudoRepositorio conteudoRepositorio) {
		this.conteudoRepositorio = conteudoRepositorio;
	}
	@Autowired
	public void setCursoRepository(CursoRepositorio cursoRepository) {
		this.cursoRepository = cursoRepository;
	}

	@Autowired
	public void setConteudoCursadoServico(ConteudoCursadoServico conteudoCursadoServico) {
		this.conteudoCursadoServico = conteudoCursadoServico;
	}

	@Autowired
	public void setTemaRepositorio(TemaRepositorio temaRepositorio) {
		this.temaRepositorio = temaRepositorio;
	}

	@Autowired
	public void setTopicoRepositorio(TopicoRepositorio topicoRepositorio) {
		this.topicoRepositorio = topicoRepositorio;
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		if (dbmode.equals("create")) {
			inserirTemas();
			System.out.println("Temas inseridos");
			inserirTopicos();
			System.out.println("Topicos inseridos");
			inserirConteudos();
			System.out.println("Conteudos inseridos");
			inserirCursos();
			System.out.println("Cursos inseridos");
			inserirDisciplasCursos();
			System.out.println("Disciplinas obrigatorias e optativas dos cursos inseridas");

			System.out.println("Tudo pronto!");
		}
	}

	void inserirTemas() {
		try (BufferedReader br = new BufferedReader(new FileReader("../extracao_dados/dados_app3/temas.csv"))) {
			CSVParser parser = new CSVParserBuilder().withSeparator(',').withIgnoreQuotations(false).build();
			CSVReader csvReader = new CSVReaderBuilder(br).withSkipLines(1).withCSVParser(parser).build();
			String[] values;
			while ((values = csvReader.readNext()) != null) {
				Tema tema = new Tema(values[1]);
				this.temaRepositorio.save(tema);
			}
		} catch (IOException | CsvValidationException e) {
			e.printStackTrace();
		}
	}

	void inserirTopicos() {
		try (BufferedReader br = new BufferedReader(new FileReader("../extracao_dados/dados_app3/topicos.csv"))) {
			CSVParser parser = new CSVParserBuilder().withSeparator(',').withIgnoreQuotations(false).build();
			CSVReader csvReader = new CSVReaderBuilder(br).withSkipLines(1).withCSVParser(parser).build();
			String[] values;
			while ((values = csvReader.readNext()) != null) {
				Topico topico = new Topico(values[1], this.temaRepositorio.getOne(Long.parseLong(values[2])));
				this.topicoRepositorio.save(topico);
			}
		} catch (IOException | CsvValidationException e) {
			e.printStackTrace();
		}
	}

	void inserirConteudos() {
		try (BufferedReader br = new BufferedReader(new FileReader("../extracao_dados/dados_app3/conteudos.csv"))) {
			CSVParser parser = new CSVParserBuilder().withSeparator(',').withIgnoreQuotations(false).build();
			CSVReader csvReader = new CSVReaderBuilder(br).withSkipLines(1).withCSVParser(parser).build();
			String[] values;
			while ((values = csvReader.readNext()) != null) {
				Conteudo conteudo = new Conteudo(values[1], values[2], Integer.parseInt(values[3]));
				conteudo.setPrerequisitos(values[4]);
				conteudo.setTema(this.temaRepositorio.getOne(Long.parseLong(values[5])));
				conteudo.setTopico(this.topicoRepositorio.getOne(Long.parseLong(values[6])));
				this.conteudoRepositorio.save(conteudo);
			}
		} catch (IOException | CsvValidationException e) {
			e.printStackTrace();
		}
	}

	void inserirCursos() {
		try (BufferedReader br = new BufferedReader(new FileReader("../extracao_dados/dados_app3/cursos.csv"))) {
			CSVParser parser = new CSVParserBuilder().withSeparator(',').withIgnoreQuotations(true).build();
			CSVReader csvReader = new CSVReaderBuilder(br).withSkipLines(1).withCSVParser(parser).build();

			String[] values;
			while ((values = csvReader.readNext()) != null) {
				Curso curso = new Curso(values[1], Integer.parseInt(values[2]), Integer.parseInt(values[3]), Integer.parseInt(values[4]));
				this.cursoRepository.save(curso);
			}
		} catch (IOException | CsvValidationException e) {
			e.printStackTrace();
		}
	}

	void inserirDisciplasCursos() {
		try (BufferedReader br = new BufferedReader(
				new FileReader("../extracao_dados/dados_app3/conteudos_cursos_obg.csv"))) {
			CSVParser parser = new CSVParserBuilder().withSeparator(',').withIgnoreQuotations(true).build();
			CSVReader csvReader = new CSVReaderBuilder(br).withSkipLines(1).withCSVParser(parser).build();

			String[] values;
			while ((values = csvReader.readNext()) != null) {
				Curso curso = cursoRepository.getOne(Long.parseLong(values[0]));
				if (curso.getDisciplinasObrigatorias() == null) {
					curso.setDisciplinasObrigatorias(new HashSet<>());
				}
				curso.getDisciplinasObrigatorias().add(new ConteudoCursado(conteudoRepositorio.getOne(Long.parseLong(values[1])), LocalDate.now()));
				this.cursoRepository.save(curso);
			}
		} catch (IOException | CsvValidationException e) {
			e.printStackTrace();
		}

		try (BufferedReader br = new BufferedReader(
				new FileReader("../extracao_dados/dados_app3/conteudos_cursos_opt.csv"))) {
			CSVParser parser = new CSVParserBuilder().withSeparator(',').withIgnoreQuotations(true).build();
			CSVReader csvReader = new CSVReaderBuilder(br).withSkipLines(1).withCSVParser(parser).build();

			String[] values;
			while ((values = csvReader.readNext()) != null) {
				Curso curso = cursoRepository.getOne(Long.parseLong(values[0]));
				if (curso.getDisciplinasOptativas() == null) {
					curso.setDisciplinasOptativas(new HashSet<>());
				}
				curso.getDisciplinasOptativas().add(conteudoRepositorio.getOne(Long.parseLong(values[1])));
				this.cursoRepository.save(curso);
			}
		} catch (IOException | CsvValidationException e) {
			e.printStackTrace();
		}
	}
}

