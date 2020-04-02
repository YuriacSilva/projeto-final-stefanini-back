package com.stefanini.parser;

import javax.inject.Inject;

import com.stefanini.dto.PessoaDto;
import com.stefanini.model.Pessoa;

public class PessoaParser {

    @Inject
    Pessoa pessoa;
    
    @Inject
    PessoaDto pessoaDto;
    
    public Pessoa toEntity(PessoaDto pessoaDto) {
      Pessoa entidade = new Pessoa();
      entidade.setDataNascimento(pessoaDto.getDataNascimento());
      entidade.setEmail(pessoaDto.getEmail());
      entidade.setEnderecos(pessoaDto.getEnderecos());
      entidade.setId(pessoaDto.getId());
      entidade.setNome(pessoaDto.getNome());
      entidade.setPerfils(pessoaDto.getPerfils());
      entidade.setSituacao(pessoaDto.getSituacao());
      entidade.setAvatar(pessoaDto.getNomeAnexo());
      return entidade;
    }
    
    public PessoaDto toDto(Pessoa entidade) {
      PessoaDto dto = new PessoaDto();
      dto.setDataNascimento(entidade.getDataNascimento());
      dto.setEmail(entidade.getEmail());
      dto.setEnderecos(entidade.getEnderecos());
      dto.setId(entidade.getId());
      dto.setNome(entidade.getNome());
      dto.setPerfils(entidade.getPerfils());
      dto.setSituacao(entidade.getSituacao());
      dto.setNomeAnexo(entidade.getAvatar());
      return dto;
    }
    
}
