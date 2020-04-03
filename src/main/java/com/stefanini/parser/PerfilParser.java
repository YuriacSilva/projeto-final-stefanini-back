package com.stefanini.parser;

import java.util.Set;
import java.util.stream.Collectors;

import com.stefanini.dto.PerfilDto;
import com.stefanini.model.Perfil;

public class PerfilParser {

  public Perfil toEntity(PerfilDto dto) {
    Perfil entidade = new Perfil();
    entidade.setId(dto.getId());
    entidade.setDataHoraAlteracao(dto.getDataHoraAlteracao());
    entidade.setDataHoraInclusao(dto.getDataHoraInclusao());
    entidade.setDescricao(dto.getDescricao());
    entidade.setNome(dto.getNome());
    return entidade;
  }

  public PerfilDto toDto(Perfil entidade) {
    PerfilDto dto = new PerfilDto();
    dto.setId(entidade.getId());
    dto.setDataHoraAlteracao(entidade.getDataHoraAlteracao());
    dto.setDataHoraInclusao(entidade.getDataHoraInclusao());
    dto.setDescricao(entidade.getDescricao());
    dto.setNome(entidade.getNome());
    return dto;
  }

  public Set<Perfil> toEntitySet(Set<PerfilDto> dto) {
    return dto.stream().map(this::toEntity).collect(Collectors.toSet());
  }

  public Set<PerfilDto> toDtoSet(Set<Perfil> entidade) {
    return entidade.stream().map(this::toDto).collect(Collectors.toSet());
  }

}
