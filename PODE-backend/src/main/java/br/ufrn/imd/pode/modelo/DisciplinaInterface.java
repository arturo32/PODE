package br.ufrn.imd.pode.modelo;

import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public interface DisciplinaInterface {

    Long getId();

    void setId(Long id);

    String getCodigo();

    void setCodigo(String codigo);

    String getNome();

    void setNome(String nome);

    Integer getCh();

    void setCh(Integer ch);

    String getPrerequisitos();

    void setPrerequisitos(String prerequisitos);

    boolean checarPrerequisitosCodigos(Collection<String> codigos);

    default boolean checarPrerequisitosDisciplinas(Collection<DisciplinaInterface> disciplinas) {
        Set<String> codigos = disciplinas.stream().map(DisciplinaInterface::getCodigo).collect(Collectors.toSet());
        if (StringUtils.isEmpty(getPrerequisitos())) {
            return true;
        }
        return checarPrerequisitosCodigos(codigos);
    }
}