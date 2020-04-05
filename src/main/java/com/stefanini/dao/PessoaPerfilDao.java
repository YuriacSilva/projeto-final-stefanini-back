package com.stefanini.dao;

import java.util.stream.Stream;

import javax.persistence.Query;

import com.stefanini.dao.abstracao.GenericDao;
import com.stefanini.model.PessoaPerfil;

/**
 * O Unico objetivo da Dao 
 * @author joaopedromilhome
 *
 */
public class PessoaPerfilDao extends GenericDao<PessoaPerfil, Long> {

	public PessoaPerfilDao() {
		super(PessoaPerfil.class);
	}

	/**
	 * Efetuando busca de Pessoa por email
	 * @param
	 * @return
	 */
	public Stream<PessoaPerfil> buscarPessoaPerfil(Long idPessoa, Long idPerfil){
		String query = "select * from TB_PESSOA_PERFIL p where 1=1 ";
		if(idPessoa!= null){
			query+= "and p.CO_SEQ_PESSOA=:idPessoa";
		}
		if(idPerfil!= null){
			query+= "and p.CO_SEQ_PERFIL=:idPerfil";
		}
		Query q2 =
				entityManager.createNativeQuery(query, PessoaPerfil.class);
		if(idPerfil != null){
			q2.setParameter("idPerfil", idPerfil);
		}
		if(idPessoa != null){
			q2.setParameter("idPessoa", idPessoa);
		}
		return q2.getResultStream();
	}

}
