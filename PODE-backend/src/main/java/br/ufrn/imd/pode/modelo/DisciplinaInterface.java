package br.ufrn.imd.pode.modelo;

import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public interface DisciplinaInterface {

    Long getId();

    String getCodigo();

    String getNome();

    Integer getCh();

    String getPrerequisitos();

    boolean checarPrerequisitosCodigos(Collection<String> codigos);

    default boolean checarPrerequisitosDisciplinas(Collection<DisciplinaInterface> disciplinas) {
        Set<String> codigos = disciplinas.stream().map(DisciplinaInterface::getCodigo).collect(Collectors.toSet());
        if (StringUtils.isEmpty(getPrerequisitos())) {
            return true;
        }
        return checarPrerequisitosCodigos(codigos);
    }
}