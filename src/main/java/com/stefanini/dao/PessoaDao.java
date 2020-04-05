package com.stefanini.dao;

import java.util.List;
import java.util.Optional;

import javax.persistence.TypedQuery;

import com.stefanini.dao.abstracao.GenericDao;
import com.stefanini.dto.PaginacaoGenericDTO;
import com.stefanini.model.Pessoa;

/**
 * O Unico objetivo da Dao 
 * @author joaopedromilhome
 *
 */
public class PessoaDao extends GenericDao<Pessoa, Long> {

	String sql = " SELECT p FROM Pessoa p WHERE p.email=:email";
	String sqlFetch = " SELECT DISTINCT p FROM Pessoa p LEFT JOIN FETCH p.enderecos enderecos LEFT JOIN FETCH p.perfils perfils ";
	
	public PessoaDao() {
		super(Pessoa.class);
	}

	/**
	 * Efetuando busca de Pessoa por email
	 * @param email
	 * @return
	 */
	public Optional<Pessoa> buscarPessoaPorEmail(String email){
		TypedQuery<Pessoa> q = entityManager.createQuery(sql, Pessoa.class);
		q.setParameter("email", email);
		return q.getResultList().stream().findFirst();
	}

	public Optional<List<Pessoa>> buscarPessoas(){
		TypedQuery<Pessoa> q = entityManager.createQuery(sqlFetch + " ORDER BY p.nome", Pessoa.class);
		return Optional.ofNullable(q.getResultList());
	}
	
	public PaginacaoGenericDTO<Pessoa> buscarPessoasPaginadas(Integer indexAtual, Integer qtdPagina){
	  TypedQuery<Pessoa> q = entityManager.createQuery(sqlFetch + " ORDER BY p.nome", Pessoa.class); 
	  PaginacaoGenericDTO<Pessoa> pagina = new PaginacaoGenericDTO<Pessoa>();
	  Integer qtdResultados = q.getResultList().size();
	  
	  pagina.setQtd(qtdResultados);
	  q.setFirstResult(indexAtual).setMaxResults(qtdPagina);
	  
	  pagina.setTotalPaginas((int) Math.ceil( (double) qtdResultados / qtdPagina));
	  
	  pagina.setResultados(q.getResultList());
	  
    return pagina;
  }
	
	@Override
	public Optional<Pessoa> encontrar(Long id) {
		TypedQuery<Pessoa> q = entityManager.createQuery(sqlFetch + " WHERE p.id = :id", Pessoa.class);
		q.setParameter("id", id);
		return q.getResultList().stream().findFirst();
	}
}
