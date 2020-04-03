package com.stefanini.parser;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import com.stefanini.dto.PessoaDto;
import com.stefanini.model.Pessoa;

public class PessoaParser {

    public Pessoa toEntity(PessoaDto dto) {
      Pessoa entidade = new Pessoa();
      entidade.setDataNascimento(dto.getDataNascimento());
      entidade.setEmail(dto.getEmail());
      entidade.setEnderecos(new HashSet<>());
      entidade.setEnderecos(dto.getEnderecos());
      entidade.setId(dto.getId());
      entidade.setNome(dto.getNome());
      entidade.setPerfils(new HashSet<>());
      entidade.setPerfils(dto.getPerfils());
      entidade.setSituacao(dto.getSituacao());
      entidade.setImagem(dto.getImagem());
      return entidade;
    }
    
    public PessoaDto toDto(Pessoa entidade) {
      PessoaDto dto = new PessoaDto();
      dto.setDataNascimento(entidade.getDataNascimento());
      dto.setEmail(entidade.getEmail());
      dto.setEnderecos(new HashSet<>());
      dto.setEnderecos(entidade.getEnderecos());
      dto.setId(entidade.getId());
      dto.setNome(entidade.getNome());
      dto.setPerfils(new HashSet<>());
      dto.setPerfils(entidade.getPerfils());
      dto.setSituacao(entidade.getSituacao());
      dto.setImagem(entidade.getImagem());
      return dto;
    }
    
    public List<Pessoa> toEntityList(List<PessoaDto> dto){
      return dto.stream().map(this::toEntity).collect(Collectors.toList());
    }
    
    public List<PessoaDto> toDtoList(List<Pessoa> entidade){
      return entidade.stream().map(this::toDto).collect(Collectors.toList());
    }
    
}
