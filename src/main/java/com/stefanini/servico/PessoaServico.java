package com.stefanini.servico;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.validation.Valid;

import com.stefanini.dao.PessoaDao;
import com.stefanini.dto.PaginacaoGenericDTO;
import com.stefanini.dto.PessoaDto;
import com.stefanini.exception.NegocioException;
import com.stefanini.model.Pessoa;
import com.stefanini.parser.PessoaParser;
import com.stefanini.servico.util.PessoaServicoUtil;

/**
 * 
 * Classe de servico, as regras de negocio devem estar nessa classe
 * 
 * @author joaopedromilhome
 *
 */
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class PessoaServico implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  @Inject
  private PessoaDao dao;

  @Inject
  private PessoaPerfilServico pessoaPerfilServico;
  
  @Inject
  private PessoaParser pessoaParser;

  /**
   * Salvar os dados de uma Pessoa
   */
  @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
  public PessoaDto salvar(@Valid PessoaDto pessoaDto) {
    pessoaDto.setImagem(PessoaServicoUtil.transfereImagem(pessoaDto));
    Pessoa entidade = pessoaParser.toEntity(pessoaDto);
    return pessoaParser.toDto(dao.salvar(entidade));
  }

  /**
   * Validando se existe pessoa com email
   */
  public Boolean validarPessoa(@Valid PessoaDto pessoaDto) {
    if (pessoaDto.getId() != null) {
      Optional<Pessoa> encontrar = dao.encontrar(pessoaDto.getId());
      if (encontrar.get().getEmail().equals(pessoaDto.getEmail())) {
        return Boolean.TRUE;
      }
    }
    Optional<Pessoa> pessoa = dao.buscarPessoaPorEmail(pessoaDto.getEmail());
    return pessoa.isEmpty();
  }

  /**
   * Atualizar o dados de uma pessoa
   */
  @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
  public PessoaDto atualizar(@Valid PessoaDto pessoaDto) {
    pessoaDto.setImagem(PessoaServicoUtil.transfereImagem(pessoaDto));
    Pessoa entidade = pessoaParser.toEntity(pessoaDto);
    return pessoaParser.toDto(dao.atualizar(entidade));
  }

  /**
   * Remover uma pessoa pelo id
   */
  @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
  public void remover(@Valid Long id) throws NegocioException {
    if (pessoaPerfilServico.buscarPessoaPerfil(id, null).count() == 0) {
      dao.remover(id);
      return;
    }
    throw new NegocioException("Não foi possivel remover a pessoa");
  }

  /**
   * Buscar uma lista de Pessoa
   */
  public Optional<List<Pessoa>> getList() {
    return dao.getList();
  }

  /**
   * Buscar uma Pessoa pelo ID
   */
//	@Override
  public Optional<Pessoa> encontrar(Long id) {
    Pessoa retorno = dao.encontrar(id).get();
    if(Objects.nonNull(retorno.getImagem()) && !retorno.getImagem().isEmpty()) {
      retorno.setImagem(PessoaServicoUtil.PREFIXO_IMAGEM + PessoaServicoUtil.recuperarImagem64(retorno.getEmail()));
    }
    return Optional.of(retorno);
  }

  public Optional<List<Pessoa>> obterCompletas() {
    return dao.buscarPessoas();
  }

  public PaginacaoGenericDTO<Pessoa> paginarPessoa(Integer indexAtual, Integer qtdPagina) {
    return dao.buscarPessoasPaginadas(indexAtual, qtdPagina);
  }

}
