package com.stefanini.dto;

import java.util.List;

public class PaginacaoGenericDTO<T> {

  private Integer totalPaginas;
	private Integer qtd;
	private List<T> resultados;
	
  public Integer getQtd() {
    return qtd;
  }
  public void setQtd(Integer qtd) {
    this.qtd = qtd;
  }
  public List<T> getResultados() {
    return resultados;
  }
  public void setResultados(List<T> resultados) {
    this.resultados = resultados;
  }
  public Integer getTotalPaginas() {
    return totalPaginas;
  }
  public void setTotalPaginas(Integer totalPaginas) {
    this.totalPaginas = totalPaginas;
  }
	
}
