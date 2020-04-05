package com.stefanini.test.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.junit.Before;
import org.junit.Test;

import com.stefanini.dao.PerfilDao;
import com.stefanini.model.Perfil;

import mockit.Injectable;
import mockit.Tested;
import mockit.Verifications;

public class PerfilDaoTeste {

  @Injectable
  EntityManager em;
  
  @Tested
  PerfilDao perfilDao;
  
  private Perfil perfil;
  
  private List<Perfil> listaPerfil;
  
  @Before
  public void setUp() {
    perfil = new Perfil();
    perfil.setId(1L);
    perfil.setNome("perfil1");
    perfil.setDescricao("descricao1");
    
    listaPerfil = new ArrayList<Perfil>();
    listaPerfil.add(perfil);
  }
  
  @Test
  public void deveBuscarUmPerfilPorNome() {
    perfilDao.buscarPerfilPorNome("perfil1");
    
    new Verifications() {{ 
      perfilDao.getEntityManager().createQuery(anyString, Perfil.class);
      times = 1;
    }};
  }
  
  @Test
  public void deveBuscarPessoasPaginadas() {
    perfilDao.buscarPerfisPaginados(0, 5);
    
    new Verifications() {{ 
      perfilDao.getEntityManager().createQuery(anyString, Perfil.class);
      times = 1;
    }};
  }
  
  // -----------------------------------------------------------------------------------------
  // Testes da GenericDao
  @Test
  public void deveEncontrar() {
    perfilDao.encontrar(1L);
    
    new Verifications() {{ 
      perfilDao.getEntityManager().find(Perfil.class, anyLong);
      times = 1;
    }};
  }
  
  @Test
  public void deveGetList() {
    perfilDao.getList();
    
    new Verifications() {{ 
      perfilDao.getEntityManager().getCriteriaBuilder();
      times = 1;
    }};
  }
  
  @Test
  public void deveRemover() {
    perfilDao.remover(1L);
    
    new Verifications() {{ 
      perfilDao.getEntityManager().remove(any);
      times = 1;
    }};
  }
  
  @Test
  public void deveAtualizar() {
    perfilDao.atualizar(perfil);
    
    new Verifications() {{ 
      perfilDao.getEntityManager().merge(any);
      times = 1;
    }};
  }
  
  @Test
  public void deveSalvar() {
    perfilDao.salvar(perfil);
    
    new Verifications() {{ 
      perfilDao.getEntityManager().persist(any);
      times = 1;
    }};
  }
  
}
