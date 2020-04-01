package com.stefanini.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.stefanini.dao.PessoaDao;
import com.stefanini.model.Pessoa;
import com.stefanini.servico.PessoaServico;

import mockit.Mock;
import mockit.MockUp;
import mockit.Mocked;
import mockit.Tested;
import mockit.integration.junit4.JMockit;
@RunWith(JMockit.class)
public class PessoaTest {

  @Mocked
  PessoaDao pessoaDAO;
  @Tested
  PessoaServico pessoaServico;

  private Pessoa pessoa;

  @Before
  public void setUp() {
    pessoa = new Pessoa();
    pessoa.setId(1L);
  }

  @Test
  public void deveTrazerPessoa() {
    new MockUp<Optional<Pessoa>>() {
      @Mock
      Optional<Pessoa> doSomething() {
        return Optional.of(pessoa);
      }
    };

    Optional<Pessoa> result = pessoaServico.encontrar(1L);

    assertTrue(result.isPresent());
    assertEquals(pessoa.getId(), result.get().getId());
  }

}
