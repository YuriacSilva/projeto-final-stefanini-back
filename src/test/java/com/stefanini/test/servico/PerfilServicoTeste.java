package com.stefanini.test.servico;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import javax.persistence.EntityManager;

import org.junit.Before;
import org.junit.Test;

import com.stefanini.dao.PerfilDao;
import com.stefanini.dto.PaginacaoGenericDTO;
import com.stefanini.exception.NegocioException;
import com.stefanini.model.Perfil;
import com.stefanini.model.PessoaPerfil;
import com.stefanini.servico.PerfilServico;
import com.stefanini.servico.PessoaPerfilServico;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import mockit.Tested;
import mockit.Verifications;

public class PerfilServicoTeste {

  @Injectable
  EntityManager em;
  
  @Tested
  PerfilServico perfilServico;
  
  @Injectable
  @Mocked
  PerfilDao perfilDao;
  
  @Injectable
  PessoaPerfilServico pps;
  
  private Perfil perfil;
  
  private List<Perfil> listaPerfis;
  
  private Stream<PessoaPerfil> streamPessoaPerfil;
  
  private Stream<PessoaPerfil> streamPessoaPerfilPreenchido;
  
  private PaginacaoGenericDTO<Perfil> pgp;
  
  @Before
  public void setUp() {
    perfil = new Perfil();
    perfil.setId(1L);
    perfil.setNome("perfil1");
    perfil.setDescricao("descricao1");
    
    listaPerfis = new ArrayList<Perfil>();
    listaPerfis.add(perfil);
    
    streamPessoaPerfil = Stream.empty();
    
    pgp = new PaginacaoGenericDTO<Perfil>();
    
    streamPessoaPerfilPreenchido = Arrays.asList(new PessoaPerfil()).stream();
  }

  @Test
  public void deveRemoverUmPerfil() throws NegocioException{
    new Expectations() {{
      pps.buscarPessoaPerfil(anyLong, anyLong);
      result = streamPessoaPerfil;
    }};
    
    perfilServico.remover(1L);
    
    new Verifications() {{ 
      perfilDao.remover(anyLong);
      times = 1;
    }};
  }
  
  @Test(expected = NegocioException.class)
  public void deveFalharAoRemoverUmPerfil() throws NegocioException{
    new Expectations() {{
      pps.buscarPessoaPerfil(anyLong, anyLong);
      result = streamPessoaPerfilPreenchido;
    }};
    
    perfilServico.remover(1L);
  }
  
  @Test
  public void deveRetornarUmPerfil() {
    new Expectations() {{
      perfilDao.encontrar(anyLong);
      result = Optional.of(perfil);
    }};

    Optional<Perfil> retorno = perfilServico.encontrar(2L);
    
    assertTrue(retorno.isPresent());
    assertEquals(perfil.getId(), retorno.get().getId());
    assertEquals("perfil1", retorno.get().getNome());
    assertEquals("descricao1", retorno.get().getDescricao());
    
    new Verifications() {{ 
      perfilDao.encontrar(anyLong);
      times = 1;
    }};
  }
  
  @Test
  public void deveRetornarUmaListaDePerfis() {
    new Expectations() {{
      perfilDao.getList();
      result = Optional.of(listaPerfis);
    }};

    Optional<List<Perfil>> retorno = perfilServico.getList();
    
    assertTrue(retorno.isPresent());
    assertFalse(retorno.get().isEmpty());
    assertEquals(perfil.getId(), retorno.get().get(0).getId());
    assertEquals("perfil1", retorno.get().get(0).getNome());
    assertEquals("descricao1", retorno.get().get(0).getDescricao());
    assertEquals(listaPerfis.get(0), retorno.get().get(0));
    
    new Verifications() {{ 
      perfilDao.getList();
      times = 1;
    }};
  }
  
  @Test
  public void deveSalvarUmPerfil() {
    new Expectations() {
      {
        perfilDao.salvar(perfil);
        result = perfil;
      }
    };

    Perfil retorno = perfilServico.salvar(perfil);

    assertEquals(perfil.getId(), retorno.getId());
    assertEquals("perfil1", retorno.getNome());
    assertEquals("descricao1", retorno.getDescricao());

    new Verifications() {
      {
        perfilDao.salvar(perfil);
        times = 1;
      }
    };
  }
  
  @Test
  public void deveAtualizarUmPerfil() {
    new Expectations() {
      {
        perfilDao.atualizar(perfil);
        result = perfil;
      }
    };

    Perfil retorno = perfilServico.atualizar(perfil);

    assertEquals(perfil.getId(), retorno.getId());
    assertEquals("perfil1", retorno.getNome());
    assertEquals("descricao1", retorno.getDescricao());

    new Verifications() {
      {
        perfilDao.atualizar(perfil);
        times = 1;
      }
    };
  }
  
  @Test
  public void deveValidarUmPerfil() {
    new Expectations() {
      {
        perfilDao.buscarPerfilPorNome(perfil.getNome());
        result = Optional.of(perfil);
      }
    };

    Boolean retorno = perfilServico.validarPerfil(perfil);

    assertEquals(Boolean.FALSE, retorno);

    new Verifications() {
      {
        perfilDao.buscarPerfilPorNome(perfil.getNome());
        times = 1;
      }
    };
  }
  
  @Test
  public void devePaginarPerfis() {
    new Expectations() {
      {
        perfilDao.buscarPerfisPaginados(anyInt, anyInt);
        result = pgp;
      }
    };

    PaginacaoGenericDTO<Perfil> retorno = perfilServico.paginarPerfil(0, 5);

    assertEquals(pgp, retorno);

    new Verifications() {
      {
        perfilServico.paginarPerfil(anyInt, anyInt);
        times = 1;
      }
    };
  }
  
}
