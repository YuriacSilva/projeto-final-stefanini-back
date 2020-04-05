package com.stefanini.test.parser;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.stefanini.dto.PessoaDto;
import com.stefanini.model.Pessoa;
import com.stefanini.parser.PessoaParser;

import mockit.Injectable;
import mockit.Tested;
import mockit.Verifications;

public class PessoaParserTeste {

  @Tested
  PessoaParser pessoaParser;
  
  @Injectable
  Pessoa pessoaEntity;
  
  @Injectable
  PessoaDto pessoaDto;
  
  private List<Pessoa> listaPessoa;
  private List<PessoaDto> listaPessoaDto;
  
  @Before
  public void setUp() {
    pessoaEntity = new Pessoa();
    pessoaEntity.setId(1L);
    pessoaEntity.setNome("yuri");
    pessoaEntity.setSituacao(Boolean.TRUE);
    pessoaEntity.setDataNascimento(LocalDate.now());
    pessoaEntity.setEmail("banana");
    pessoaEntity.setImagem("");
    
    listaPessoa = new ArrayList<Pessoa>();
    listaPessoa.add(pessoaEntity);
    
    pessoaDto = new PessoaDto();
    pessoaDto.setId(2L);
    pessoaDto.setNome("yuri2");
    pessoaDto.setSituacao(Boolean.TRUE);
    pessoaDto.setDataNascimento(LocalDate.now());
    pessoaDto.setEmail("banana2");
    pessoaDto.setImagem("");
    
    listaPessoaDto = new ArrayList<PessoaDto>();
    listaPessoaDto.add(pessoaDto);
  }

  @Test
  public void deveToEntity() {
    
    pessoaParser.toEntityList(listaPessoaDto);
    
    new Verifications() {{ 
      pessoaParser.toEntity(pessoaDto);
      times = 1;
    }};
  }
  
  @Test
  public void deveToDto() {
    
    pessoaParser.toDtoList(listaPessoa);
    
    new Verifications() {{ 
      pessoaParser.toDto(pessoaEntity);
      times = 1;
    }};
  }
  
}
