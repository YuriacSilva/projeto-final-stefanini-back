package com.stefanini.dao;

import java.util.Optional;

import javax.persistence.TypedQuery;

import com.stefanini.dao.abstracao.GenericDao;
import com.stefanini.dto.PaginacaoGenericDTO;
import com.stefanini.model.Perfil;

/**
 * O Unico objetivo da Dao 
 * @author joaopedromilhome
 *
 */
public class PerfilDao extends GenericDao<Perfil, Long> {

  String sql = " SELECT p FROM Perfil p "; 
  
	public PerfilDao() {
		super(Perfil.class);
	}

	/**
	 * Efetuando busca de Pessoa por email
	 * @param nome
	 * @return
	 */
	public Optional<Perfil> buscarPessoaPorNome(String nome){
		TypedQuery<Perfil> q2 =
				entityManager.createQuery(sql + " where p.nome=:nome", Perfil.class);
		q2.setParameter("nome", nome);
		return q2.getResultStream().findFirst();
	}

  public PaginacaoGenericDTO<Perfil> buscarPerfisPaginados(Integer indexAtual, Integer qtdPagina){
    TypedQuery<Perfil> q = entityManager.createQuery(sql + " ORDER BY p.nome", Perfil.class);
    PaginacaoGenericDTO<Perfil> pagina = new PaginacaoGenericDTO<Perfil>();
    Integer qtdResultados = q.getResultList().size();
    pagina.setQtd(qtdResultados);
    q.setFirstResult(indexAtual).setMaxResults(qtdPagina);
    
    if(qtdResultados % qtdPagina == 0) {
      pagina.setTotalPaginas(qtdResultados / qtdPagina);
    } else {
      pagina.setTotalPaginas((qtdResultados / qtdPagina) + 1);
    }
    
    pagina.setResultados(q.getResultList());
    
    return pagina;
  }
  
}
