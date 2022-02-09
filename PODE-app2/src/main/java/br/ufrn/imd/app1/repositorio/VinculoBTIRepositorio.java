package br.ufrn.imd.app1.repositorio;

import org.springframework.stereotype.Repository;

import br.ufrn.imd.pode.repositorio.VinculoRepositorio;

import br.ufrn.imd.app1.modelo.VinculoBTI;

import java.util.Optional;

@Repository
public interface VinculoBTIRepositorio extends VinculoRepositorio<VinculoBTI> {
}
