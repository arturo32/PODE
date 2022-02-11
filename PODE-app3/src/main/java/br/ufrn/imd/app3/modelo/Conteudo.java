package br.ufrn.imd.app3.modelo;

import org.mvel2.MVEL;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.ufrn.imd.pode.modelo.Disciplina;
import br.ufrn.imd.pode.modelo.DisciplinaInterface;

@Entity
@Table(name = "conteudo")
public class Conteudo extends Disciplina implements DisciplinaInterface {

	@ManyToOne
	@NotNull
	private Tema tema;

	@ManyToOne
	@NotNull
	private Topico topico;

	@NotNull
	@NotBlank
	private String nivel;

	public Conteudo() {}

	public Conteudo(String codigo, String nome, int ch) {
		this.codigo = codigo;
		this.nome = nome;
		this.ch = ch;
	}

	public Tema getTema() {
		return tema;
	}

	public void setTema(Tema tema) {
		this.tema = tema;
	}

	public Topico getTopico() {
		return topico;
	}

	public void setTopico(Topico topico) {
		this.topico = topico;
	}

	public String getNivel() {
		return nivel;
	}

	public void setNivel(String nivel) {
		this.nivel = nivel;
	}

	@Override
	public boolean checarPrerequisitosCodigos(Collection<String> codigos) {
		String expressao = getPrerequisitos().replace(" E ", " && ");
		expressao = expressao.replace(" OU ", " || ");
		Matcher matcher = Pattern.compile("([A-Z]{3}[0-9]{4}|[A-Z]{3}[0-9]{3})").matcher(expressao);
		while (matcher.find()) {
			for (int i = 0; i < matcher.groupCount(); i++) {
				String eval = String.valueOf(codigos.contains(matcher.group(i)));
				expressao = expressao.replace(matcher.group(i), eval);
			}
		}
		return (boolean) MVEL.eval(expressao);
	}
}
