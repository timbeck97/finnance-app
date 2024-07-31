package com.finance.application.service;

import com.finance.application.builder.DepositoBuilder;
import com.finance.application.dto.DepositoDTO;
import com.finance.application.model.Deposito;
import com.finance.application.model.Gasto;
import com.finance.application.repository.DepositoRepository;
import com.finance.application.repository.GastoRepository;
import com.finance.autentication.model.User;
import com.finance.autentication.service.UserService;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class DepositoServiceTest {


  @Mock
  private UserService userService;
  @Mock
  private SaldoService saldoService;
  @InjectMocks
  private DepositoService depositoService;
  @Mock
  private DepositoRepository depositoRepository;
  @Mock
  private GastoRepository gastoRepository;
  @Captor
  private ArgumentCaptor<Deposito> depositoCaptor;
  @Test
  void findAll() {
    mockUsuarioLogado();
    doReturn(new ArrayList<>()).when(depositoRepository).findByUsuarioAndData(Mockito.any(User.class),Mockito.anyString());
    depositoService.findAll(null);
    verify(depositoRepository,Mockito.times(1)).findByUsuarioAndData(Mockito.any(User.class),Mockito.anyString());
  }

  @Test
  void shoulSaveSuccesfully() {
    DepositoDTO dto = new DepositoDTO();
    dto.setData("2021-01-01");
    dto.setDescricao("Teste");
    dto.setValor(100.0);
    mockUsuarioLogado();
    doReturn(DepositoBuilder.init().withData("2021-01-01").withDescricao("Teste").withValor(100.0).build())
      .when(depositoRepository).save(depositoCaptor.capture());
    depositoService.save(dto);
    doNothing().when(saldoService).atualizarSaldo(depositoCaptor.capture());
    doReturn(null).when(gastoRepository).findById(Mockito.anyLong());
    verify(saldoService).atualizarSaldo(depositoCaptor.capture());

    List<Deposito> depositos = depositoCaptor.getAllValues();
    assertEquals("2021-01-01",depositos.get(0).getData());
    assertEquals("Teste",depositos.get(0).getDescricao());
    assertEquals(100.0,depositos.get(0).getValor());
    assertEquals("2021-01-01",depositos.get(1).getData());
    assertEquals("Teste",depositos.get(1).getDescricao());
    assertEquals(100.0,depositos.get(1).getValor());

  }
  @Nested
  class Update{
    @Test
    void shouldUpdateSuccessfuly() {
      DepositoDTO dto = new DepositoDTO();
      dto.setData("2021-01-01");
      dto.setDescricao("Teste");
      dto.setValor(100.0);
      mockUsuarioLogado();
      doReturn(DepositoBuilder.init().withData("2021-01-01").withDescricao("Teste").withValor(100.0).build())
        .when(depositoRepository).save(depositoCaptor.capture());

      doNothing().when(saldoService).atualizarSaldo(depositoCaptor.capture());
      doReturn(Optional.of(new Deposito())).when(depositoRepository).findById(Mockito.anyLong());
      doReturn(Optional.ofNullable(new Gasto())).when(gastoRepository).findById(Mockito.anyLong());
      depositoService.update(dto, 1);

      verify(saldoService).atualizarSaldo(depositoCaptor.capture());


      List<Deposito> depositos = depositoCaptor.getAllValues();
      assertEquals("2021-01-01",depositos.get(0).getData());
      assertEquals("Teste",depositos.get(0).getDescricao());
      assertEquals(100.0,depositos.get(0).getValor());
      assertEquals("2021-01-01",depositos.get(1).getData());
      assertEquals("Teste",depositos.get(1).getDescricao());
      assertEquals(100.0,depositos.get(1).getValor());
    }
    @Test
    void shouldNotFound() {
      doReturn(Optional.empty()).when(depositoRepository).findById(Mockito.any());
      doThrow(new IllegalArgumentException("Recurso não encontrado")).when(depositoRepository).findById(Mockito.any());
    }
  }

  private User mockUsuarioLogado(){
    User usuarioLogado=getUser();
    doReturn(usuarioLogado).when(userService).getUsuarioLogado();
    return usuarioLogado;
  }
  private User getUser(){
    User user = new User();
    user.setUsername("teste");
    user.setEmail("teste@email.com");
    user.setName("Teste");
    user.setPassword("teste");
    return user;
  }
}
