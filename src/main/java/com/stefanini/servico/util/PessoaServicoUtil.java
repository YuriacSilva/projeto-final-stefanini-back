package com.stefanini.servico.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;

import javax.imageio.ImageIO;

import com.stefanini.dto.PessoaDto;

public class PessoaServicoUtil {
  
  private static final String PASTA_PADRAO = 
      "C:\\Users\\Yuri\\Desktop\\Refs-Aula\\versao-sala\\treinamento\\src\\main\\resources\\Avatares\\";
  
  private static final String NOME_PADRAO = 
      "\\portrait.jpg";
  
  public static String transfereImagem (PessoaDto pessoaDto) {
    String caminhoDestino = PASTA_PADRAO + pessoaDto.getEmail() + NOME_PADRAO;
    String dadosAnexo = pessoaDto.getDadosAnexo().split(",")[1];
    BufferedImage image = null;
    byte[] bytes;
    if(!new File(PASTA_PADRAO + pessoaDto.getEmail()).exists()) {
      new File(PASTA_PADRAO + pessoaDto.getEmail()).mkdir();
    }
    try {
      bytes = Base64.getDecoder().decode(dadosAnexo.getBytes());

      ByteArrayInputStream byteStream = new ByteArrayInputStream(bytes);
      image = ImageIO.read(byteStream);
      byteStream.close();
      ImageIO.write(image, "JPG", new File(caminhoDestino));
    } catch (IOException e) {
      e.printStackTrace();
    }
    return caminhoDestino;
  }
  
}
