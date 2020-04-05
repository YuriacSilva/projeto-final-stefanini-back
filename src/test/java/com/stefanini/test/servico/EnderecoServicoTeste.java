package com.stefanini.test.servico;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.junit.Before;
import org.junit.Test;

import com.stefanini.dao.EnderecoDao;
import com.stefanini.exception.NegocioException;
import com.stefanini.model.Endereco;
import com.stefanini.servico.EnderecoServico;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import mockit.Tested;
import mockit.Verifications;

public class EnderecoServicoTeste {

  @Injectable
  EntityManager em;

  @Tested
  EnderecoServico enderecoServico;

  @Injectable
  @Mocked
  EnderecoDao enderecoDao;

  private Endereco endereco;

  private List<Endereco> listaEnderecos;

  @Before
  public void setUp() {
    endereco = new Endereco();
    endereco.setId(1L);
    endereco.setBairro("bairro1");
    endereco.setCep("70073901");
    endereco.setComplemento("complemento1");
    endereco.setIdPessoa(1L);
    endereco.setLocalidade("localidade1");
    endereco.setLogradouro("logradouro1");
    endereco.setUf("UF");

    listaEnderecos = new ArrayList<Endereco>();
    listaEnderecos.add(endereco);
  }

  @Test
  public void deveRemoverUmEndereco() throws NegocioException {
    enderecoServico.remover(1L);

    new Verifications() {
      {
        enderecoDao.remover(anyLong);
        times = 1;
      }
    };
  }

  @Test
  public void deveRetornarUmEndereco() {
    new Expectations() {
      {
        enderecoDao.encontrar(anyLong);
        result = Optional.of(endereco);
      }
    };

    Optional<Endereco> retorno = enderecoServico.encontrar(2L);

    assertTrue(retorno.isPresent());
    assertEquals(endereco.getId(), retorno.get().getId());
    assertEquals("bairro1", retorno.get().getBairro());
    assertEquals("70073901", retorno.get().getCep());
    assertEquals("complemento1", retorno.get().getComplemento());
    assertEquals(endereco.getIdPessoa(), retorno.get().getIdPessoa());
    assertEquals("localidade1", retorno.get().getLocalidade());
    assertEquals("logradouro1", retorno.get().getLogradouro());
    assertEquals("UF", retorno.get().getUf());

    new Verifications() {
      {
        enderecoDao.encontrar(anyLong);
        times = 1;
      }
    };
  }

  @Test
  public void deveRetornarUmaListaDeEnderecos() {
    new Expectations() {
      {
        enderecoDao.getList();
        result = Optional.of(listaEnderecos);
      }
    };

    Optional<List<Endereco>> retorno = enderecoServico.getList();

    assertTrue(retorno.isPresent());
    assertEquals(endereco.getId(), retorno.get().get(0).getId());
    assertEquals("bairro1", retorno.get().get(0).getBairro());
    assertEquals("70073901", retorno.get().get(0).getCep());
    assertEquals("complemento1", retorno.get().get(0).getComplemento());
    assertEquals(endereco.getIdPessoa(), retorno.get().get(0).getIdPessoa());
    assertEquals("localidade1", retorno.get().get(0).getLocalidade());
    assertEquals("logradouro1", retorno.get().get(0).getLogradouro());
    assertEquals("UF", retorno.get().get(0).getUf());
    assertEquals(listaEnderecos.get(0), retorno.get().get(0));

    new Verifications() {
      {
        enderecoDao.getList();
        times = 1;
      }
    };
  }

  @Test
  public void deveSalvarUmEndereco() {
    new Expectations() {
      {
        enderecoDao.salvar(endereco);
        result = endereco;
      }
    };

    Endereco retorno = enderecoServico.salvar(endereco);

    assertEquals(endereco.getId(), retorno.getId());
    assertEquals("bairro1", retorno.getBairro());
    assertEquals("70073901", retorno.getCep());
    assertEquals("complemento1", retorno.getComplemento());
    assertEquals(endereco.getIdPessoa(), retorno.getIdPessoa());
    assertEquals("localidade1", retorno.getLocalidade());
    assertEquals("logradouro1", retorno.getLogradouro());
    assertEquals("UF", retorno.getUf());

    new Verifications() {
      {
        enderecoDao.salvar(endereco);
        times = 1;
      }
    };
  }

  @Test
  public void deveAtualizarUmEndereco() {
    new Expectations() {
      {
        enderecoDao.atualizar(endereco);
        result = endereco;
      }
    };

    Endereco retorno = enderecoServico.atualizar(endereco);

    assertEquals(endereco.getId(), retorno.getId());
    assertEquals("bairro1", retorno.getBairro());
    assertEquals("70073901", retorno.getCep());
    assertEquals("complemento1", retorno.getComplemento());
    assertEquals(endereco.getIdPessoa(), retorno.getIdPessoa());
    assertEquals("localidade1", retorno.getLocalidade());
    assertEquals("logradouro1", retorno.getLogradouro());
    assertEquals("UF", retorno.getUf());

    new Verifications() {
      {
        enderecoDao.atualizar(endereco);
        times = 1;
      }
    };
  }

  @Test
  public void deveBuscarUmEnderecoPeloCEP() {
    Optional<String> retorno = enderecoServico.buscarCep("70073901");

    assertTrue(retorno.isPresent());
  }

  @Test
  public void deveFalharAoBuscarUmEnderecoPeloCEP() {
    Optional<String> retorno = enderecoServico.buscarCep("1234567");

    assertTrue(retorno.isEmpty());
  }
  
}
