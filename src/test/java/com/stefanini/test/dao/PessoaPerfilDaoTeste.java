package com.stefanini.test.dao;

import javax.persistence.EntityManager;

import org.junit.Before;
import org.junit.Test;

import com.stefanini.dao.PessoaPerfilDao;
import com.stefanini.model.PessoaPerfil;

import mockit.Injectable;
import mockit.Tested;
import mockit.Verifications;

public class PessoaPerfilDaoTeste {

  @Injectable
  EntityManager em;
  
  @Tested
  PessoaPerfilDao ppd;
  
  private PessoaPerfil pessoaPerfil;
  
  @Before
  public void setUp() {
    pessoaPerfil = new PessoaPerfil();
    pessoaPerfil.setId(1L);
    pessoaPerfil.setIdPessoa(1L);
    pessoaPerfil.setIdPerfil(1L);
  }
  
  
  @Test
  public void deveBuscarPessoaPerfil() {
    ppd.buscarPessoaPerfil(1L, 1L);
    
    new Verifications() {{ 
      ppd.getEntityManager().createNativeQuery(anyString, PessoaPerfil.class);
      times = 1;
    }};
  }
  
}
