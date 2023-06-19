package com.finance.service;


import com.finance.model.User;
import com.finance.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

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
}
