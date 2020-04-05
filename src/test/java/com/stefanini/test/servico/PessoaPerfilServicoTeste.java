package com.stefanini.test.servico;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import org.junit.Before;
import org.junit.Test;

import com.stefanini.dao.PessoaPerfilDao;
import com.stefanini.model.PessoaPerfil;
import com.stefanini.servico.PessoaPerfilServico;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import mockit.Tested;
import mockit.Verifications;

public class PessoaPerfilServicoTeste {

  @Injectable
  EntityManager em;
  
  @Tested
  PessoaPerfilServico pps;
  
  @Injectable
  @Mocked
  PessoaPerfilDao ppDao;
  
  private PessoaPerfil pessoaPerfil;
  
  private List<PessoaPerfil> listaPP;
  
  @Before
  public void setUp() {
    pessoaPerfil = new PessoaPerfil();
    pessoaPerfil.setId(1L);
    pessoaPerfil.setIdPessoa(1L);
    pessoaPerfil.setIdPerfil(1L);;
    
    listaPP = new ArrayList<PessoaPerfil>();
    listaPP.add(pessoaPerfil);
  }

  
  @Test
  public void deveRetornarUmPessoaPerfil() {
    new Expectations() {{
      ppDao.buscarPessoaPerfil(anyLong, anyLong);
      result = listaPP.stream();
    }};

    List<PessoaPerfil> retorno = pps.buscarPessoaPerfil(1L, 1L).collect(Collectors.toList());
    
    assertEquals(pessoaPerfil.getId(), retorno.get(0).getId());
    assertEquals(pessoaPerfil.getIdPessoa(), retorno.get(0).getIdPessoa());
    assertEquals(pessoaPerfil.getIdPerfil(), retorno.get(0).getIdPerfil());
    
    new Verifications() {{ 
      ppDao.buscarPessoaPerfil(anyLong, anyLong);
      times = 1;
    }};
  }
  
}
