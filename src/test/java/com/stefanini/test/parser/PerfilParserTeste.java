package com.stefanini.test.parser;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.stefanini.dto.PerfilDto;
import com.stefanini.model.Perfil;
import com.stefanini.parser.PerfilParser;

import mockit.Injectable;
import mockit.Tested;
import mockit.Verifications;

public class PerfilParserTeste {

  @Tested
  PerfilParser perfilParser;
  
  @Injectable
  Perfil perfilEntity;
  
  @Injectable
  PerfilDto perfilDto;
  
  private Set<Perfil> listaPerfil;
  private Set<PerfilDto> listaPerfilDto;
  
  @Before
  public void setUp() {
    perfilEntity = new Perfil();
    perfilEntity.setId(1L);
    perfilEntity.setNome("perfil1");
    perfilEntity.setDescricao("descricao1");
    
    listaPerfil = new HashSet<Perfil>();
    listaPerfil.add(perfilEntity);
    
    perfilDto = new PerfilDto();
    perfilDto.setId(2L);
    perfilDto.setNome("perfil2");
    perfilDto.setDescricao("descricao2");
    
    listaPerfilDto = new HashSet<PerfilDto>();
    listaPerfilDto.add(perfilDto);
  }

  @Test
  public void deveToEntity() {
    
    perfilParser.toEntitySet(listaPerfilDto);
    
    new Verifications() {{ 
      perfilParser.toEntity(perfilDto);
      times = 1;
    }};
  }
  
  @Test
  public void deveToDto() {
    
    perfilParser.toDtoSet(listaPerfil);
    
    new Verifications() {{ 
      perfilParser.toDto(perfilEntity);
      times = 1;
    }};
  }
  
}
