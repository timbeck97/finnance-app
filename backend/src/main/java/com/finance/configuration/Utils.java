package com.finance.configuration;


import com.finance.autentication.model.User;
import com.finance.autentication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Service
public class Utils {

  @Autowired
  private UserRepository userRepository;

  public String getAnoMesAtual(){
    Date hoje=new Date();
    SimpleDateFormat sdf=new SimpleDateFormat("yyyyMM");
    return sdf.format(hoje);

  }
  public String getAnoMesAnterior(){
    LocalDate data=LocalDate.now();
    data=data.minusMonths(1);
    SimpleDateFormat sdf=new SimpleDateFormat("yyyyMM");
    return sdf.format(Date.from(data.atStartOfDay(ZoneId.systemDefault()).toInstant()));
  }
  public User getUsuarioLogado(){
    String userName=(String)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    return userRepository.findByUsername(userName);
  }
  public static String formatDoubleToBRCurrency(double valor){
    return String.format("R$ %.2f",valor);
  }
}
