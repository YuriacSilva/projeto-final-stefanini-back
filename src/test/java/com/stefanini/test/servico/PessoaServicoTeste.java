package com.stefanini.test.servico;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import javax.persistence.EntityManager;

import org.junit.Before;
import org.junit.Test;

import com.stefanini.dao.PessoaDao;
import com.stefanini.dto.PaginacaoGenericDTO;
import com.stefanini.dto.PessoaDto;
import com.stefanini.exception.NegocioException;
import com.stefanini.model.Pessoa;
import com.stefanini.model.PessoaPerfil;
import com.stefanini.parser.PessoaParser;
import com.stefanini.servico.EnderecoServico;
import com.stefanini.servico.PessoaPerfilServico;
import com.stefanini.servico.PessoaServico;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import mockit.Tested;
import mockit.Verifications;

public class PessoaServicoTeste {

  @Injectable
  EntityManager em;
  
  @Tested
  PessoaServico pessoaServico;
  
  @Injectable
  @Mocked
  PessoaDao pessoaDao;
  
  @Injectable
  PessoaPerfilServico pps;
  
  @Injectable
  EnderecoServico es;
  
  @Injectable
  PessoaParser pp;
  
  @Injectable
  PaginacaoGenericDTO<Pessoa> paginacao;
  
  private Pessoa pessoa;
  
  private PessoaDto pessoaDto;
  
  private List<Pessoa> listaPessoas;
  
  private Stream<PessoaPerfil> streamPessoaPerfil;
  
  private Stream<PessoaPerfil> streamPessoaPerfilPreenchido;
  
  @Before
  public void setUp() {
    pessoa = new Pessoa();
    pessoa.setId(1L);
    pessoa.setNome("yuri");
    pessoa.setSituacao(Boolean.TRUE);
    pessoa.setDataNascimento(LocalDate.now());
    pessoa.setEmail("banana");
    
    listaPessoas = new ArrayList<Pessoa>();
    listaPessoas.add(pessoa);
    
    pessoaDto = new PessoaDto();
    pessoaDto.setId(1L);
    pessoaDto.setNome("yuri");
    pessoaDto.setSituacao(Boolean.TRUE);
    pessoaDto.setDataNascimento(LocalDate.now());
    pessoaDto.setEmail("banana");
    
    streamPessoaPerfil = Stream.empty();
    
    paginacao = new PaginacaoGenericDTO<Pessoa>();
    paginacao.setResultados(listaPessoas);
    
    streamPessoaPerfilPreenchido = Arrays.asList(new PessoaPerfil()).stream();
  }

  @Test
  public void deveObterCompletas() {
    new Expectations() {{
      pessoaDao.buscarPessoas();
      result = Optional.of(listaPessoas);
    }};

    Optional<List<Pessoa>> retorno = pessoaServico.obterCompletas();
    
    assertTrue(retorno.isPresent());
    assertFalse(retorno.get().isEmpty());
    assertEquals(pessoa.getId(), retorno.get().get(0).getId());
    assertEquals("yuri", retorno.get().get(0).getNome());
    assertEquals(Boolean.TRUE, retorno.get().get(0).getSituacao());
    assertEquals(pessoa.getDataNascimento(), retorno.get().get(0).getDataNascimento());
    assertEquals("banana", retorno.get().get(0).getEmail());
    assertEquals(listaPessoas.get(0), retorno.get().get(0));
    
    new Verifications() {{ 
      pessoaDao.buscarPessoas();
      times = 1;
    }};
  }
  
  @Test
  public void deveBuscarPessoasPaginadas() {
    new Expectations() {{
      pessoaDao.buscarPessoasPaginadas(anyInt, anyInt);
      result = paginacao;
    }};

    PaginacaoGenericDTO<Pessoa> retorno = pessoaServico.paginarPessoa(0, 5);
    
    assertFalse(retorno.getResultados().isEmpty());
    assertEquals(pessoa.getId(), retorno.getResultados().get(0).getId());
    assertEquals("yuri", retorno.getResultados().get(0).getNome());
    assertEquals(Boolean.TRUE, retorno.getResultados().get(0).getSituacao());
    assertEquals(pessoa.getDataNascimento(), retorno.getResultados().get(0).getDataNascimento());
    assertEquals("banana", retorno.getResultados().get(0).getEmail());
    assertEquals(listaPessoas.get(0), retorno.getResultados().get(0));
    
    new Verifications() {{ 
      pessoaDao.buscarPessoasPaginadas(anyInt, anyInt);
      times = 1;
    }};
  }
  
  @Test
  public void deveSalvarUmaPessoa() {
    new Expectations() {{
      pp.toDto((Pessoa) any);
      result = pessoaDto;
    }};

    PessoaDto retorno = pessoaServico.salvar(pessoaDto);
    
    assertNotNull(retorno);
    assertEquals("yuri", retorno.getNome());
    assertEquals(Boolean.TRUE, retorno.getSituacao());
    assertEquals(pessoa.getDataNascimento(), retorno.getDataNascimento());
    assertEquals("banana", retorno.getEmail());
    
    new Verifications() {{ 
      pp.toEntity((PessoaDto) any);
      times = 1;
    }};
  }
  
  @Test
  public void deveAtualizarUmaPessoa() {
    new Expectations() {{
      pp.toDto((Pessoa) any);
      result = pessoaDto;
    }};

    PessoaDto retorno = pessoaServico.atualizar(pessoaDto);
    
    assertNotNull(retorno);
    assertEquals("yuri", retorno.getNome());
    assertEquals(Boolean.TRUE, retorno.getSituacao());
    assertEquals(pessoa.getDataNascimento(), retorno.getDataNascimento());
    assertEquals("banana", retorno.getEmail());
    
    new Verifications() {{ 
      pp.toEntity((PessoaDto) any);
      times = 1;
    }};
  }
  
  @Test
  public void deveValidarUmaPessoa() {
    new Expectations() {{
      pessoaDao.encontrar(anyLong);
      result = pessoa;
    }};

    Boolean retorno = pessoaServico.validarPessoa(pessoaDto);
    
    assertNotNull(retorno);
    assertEquals(Boolean.TRUE, retorno);
    
    new Verifications() {{ 
      pessoaDao.encontrar(anyLong);
      times = 1;
    }};
  }
  
  @Test
  public void deveInvalidarUmaPessoa() {
    
    Boolean retorno = pessoaServico.validarPessoa(new PessoaDto());
    
    assertNotNull(retorno);
    assertEquals(Boolean.TRUE, retorno);
    
  }
  
  @Test
  public void deveRemoverUmaPessoa() throws NegocioException {
    new Expectations() {{
      pps.buscarPessoaPerfil(anyLong, anyLong);
      result = streamPessoaPerfil;
    }};
    
    pessoaServico.remover(1L);
    
    new Verifications() {{ 
      pessoaDao.remover(anyLong);
      times = 1;
    }};
  }
  
  @Test(expected = NegocioException.class)
  public void deveFalharAoRemoverUmaPessoa() throws NegocioException {
    new Expectations() {{
      pps.buscarPessoaPerfil(anyLong, anyLong);
      result = streamPessoaPerfilPreenchido;
    }};
    
    pessoaServico.remover(1L);
  }
  
  @Test
  public void deveRetornarUmaPessoa() {
    new Expectations() {{
      pessoaDao.encontrar(anyLong);
      result = Optional.of(pessoa);
    }};

    Optional<Pessoa> retorno = pessoaServico.encontrar(2L);
    
    assertTrue(retorno.isPresent());
    assertEquals(pessoa.getId(), retorno.get().getId());
    assertEquals("yuri", retorno.get().getNome());
    assertEquals(Boolean.TRUE, retorno.get().getSituacao());
    assertEquals(pessoa.getDataNascimento(), retorno.get().getDataNascimento());
    assertEquals("banana", retorno.get().getEmail());
    
    new Verifications() {{ 
      pessoaDao.encontrar(anyLong);
      times = 1;
    }};
  }
  
  @Test
  public void deveRetornarUmaListaDePessoas() {
    new Expectations() {{
      pessoaDao.getList();
      result = Optional.of(listaPessoas);
    }};

    Optional<List<Pessoa>> retorno = pessoaServico.getList();
    
    assertTrue(retorno.isPresent());
    assertFalse(retorno.get().isEmpty());
    assertEquals(pessoa.getId(), retorno.get().get(0).getId());
    assertEquals("yuri", retorno.get().get(0).getNome());
    assertEquals(Boolean.TRUE, retorno.get().get(0).getSituacao());
    assertEquals(pessoa.getDataNascimento(), retorno.get().get(0).getDataNascimento());
    assertEquals("banana", retorno.get().get(0).getEmail());
    assertEquals(listaPessoas.get(0), retorno.get().get(0));
    
    new Verifications() {{ 
      pessoaDao.getList();
      times = 1;
    }};
  }
  
  
  
  
}
