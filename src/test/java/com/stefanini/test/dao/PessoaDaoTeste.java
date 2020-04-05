package com.stefanini.test.dao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.junit.Before;
import org.junit.Test;

import com.stefanini.dao.PessoaDao;
import com.stefanini.model.Pessoa;

import mockit.Injectable;
import mockit.Tested;
import mockit.Verifications;

public class PessoaDaoTeste {

  @Injectable
  EntityManager em;
  
  @Tested
  PessoaDao pessoaDao;
  
  private Pessoa pessoa;
  
  private List<Pessoa> listaPessoa;
  
  @Before
  public void setUp() {
    pessoa = new Pessoa();
    pessoa.setId(1L);
    pessoa.setNome("yuri");
    pessoa.setSituacao(Boolean.TRUE);
    pessoa.setDataNascimento(LocalDate.now());
    pessoa.setEmail("banana");
    pessoa.setImagem("");
    
    listaPessoa = new ArrayList<Pessoa>();
    listaPessoa.add(pessoa);
  }
  
  @Test
  public void deveBuscarUmaPessoaPorEmail() {
    pessoaDao.buscarPessoaPorEmail("email");
    
    new Verifications() {{ 
      pessoaDao.getEntityManager().createQuery(anyString, Pessoa.class);
      times = 1;
    }};
  }
  
  @Test
  public void deveBuscarPessoas() {
    pessoaDao.buscarPessoas();
    
    new Verifications() {{ 
      pessoaDao.getEntityManager().createQuery(anyString, Pessoa.class);
      times = 1;
    }};
  }
  
  @Test
  public void deveBuscarPessoasPaginadas() {
    pessoaDao.buscarPessoasPaginadas(0, 5);
    
    new Verifications() {{ 
      pessoaDao.getEntityManager().createQuery(anyString, Pessoa.class);
      times = 1;
    }};
  }
  
  @Test
  public void deveEncontrarPessoaPorId() {
    pessoaDao.encontrar(1L);
    
    new Verifications() {{ 
      pessoaDao.getEntityManager().createQuery(anyString, Pessoa.class);
      times = 1;
    }};
  }
  
}
