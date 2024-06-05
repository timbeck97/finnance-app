package com.finance.configuration;



//import com.auth0.jwt.JWT;
//import com.auth0.jwt.interfaces.Claim;
import com.finance.autentication.model.User;
import com.finance.autentication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class Utils {

  public static String getAnoMesAtual(){
    Date hoje=new Date();
    SimpleDateFormat sdf=new SimpleDateFormat("yyyyMM");
    return sdf.format(hoje);
  }
  public static String getAnoMesAnterior(){
    LocalDate data=LocalDate.now();
    data=data.minusMonths(1);
    SimpleDateFormat sdf=new SimpleDateFormat("yyyyMM");
    return sdf.format(Date.from(data.atStartOfDay(ZoneId.systemDefault()).toInstant()));
  }

  public static String formatDoubleToBRCurrency(double valor){
    return String.format("R$ %.2f",valor);
  }
}
