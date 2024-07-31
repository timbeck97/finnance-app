package com.finance.application.service;

import com.finance.application.builder.PagamentoBuilder;
import com.finance.application.dto.PagamentoDTO;
import com.finance.application.model.Pagamento;
import com.finance.application.repository.PagamentoRepository;
import com.finance.autentication.model.User;
import com.finance.autentication.service.UserService;
import com.finance.configuration.exceptions.DataNotFoundException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PagamentoServiceTest {

  @Mock
  private PagamentoRepository pagamentoRepository;
  @Mock
  private UserService userService;
  @InjectMocks
  private PagamentoService pagamentoService;
  @Captor
  private ArgumentCaptor<Pagamento> pagamentoCaptor;

  @Test
  void findByData() {
    PagamentoDTO pagamento = new PagamentoDTO();
    Mockito.doReturn(pagamento).when(pagamentoRepository).findByDataAndUsuario(Mockito.anyString(), Mockito.any());
    assertEquals(pagamento, pagamentoService.findByData("data"));
  }

  @Test
  void save() {
    User usuarioLogado = new User();
    PagamentoDTO pagamento = new PagamentoDTO();
    pagamento.setData("202401");
    pagamento.setValor(1500);
    Mockito.doReturn(usuarioLogado).when(userService).getUsuarioLogado();
    Mockito.doReturn(new Pagamento()).when(pagamentoRepository).save(pagamentoCaptor.capture());
    Pagamento result = pagamentoService.save(pagamento);
    assertNotNull(result);
    assertEquals(pagamento.getData(), pagamentoCaptor.getValue().getData());
    assertEquals(pagamento.getValor(), pagamentoCaptor.getValue().getValor());
    assertEquals(usuarioLogado, pagamentoCaptor.getValue().getUsuario());
  }

  @Nested
  class Update{
      @Test
      void shouldUpdatePagamento() {
        User mockUsuarioLogado = mockUsuarioLogado();
        PagamentoDTO pagamento = new PagamentoDTO();
        pagamento.setData("202401");
        pagamento.setValor(1500);
        Mockito.doReturn(new Pagamento()).when(pagamentoRepository).save(pagamentoCaptor.capture());
        Mockito.doReturn(Optional.ofNullable(PagamentoBuilder.create().withUsuario(mockUsuarioLogado).build())).when(pagamentoRepository).findById(Mockito.any());
        Pagamento result = pagamentoService.updatePagamento(pagamento, 1L);
        assertNotNull(result);
        assertEquals(pagamento.getData(), pagamentoCaptor.getValue().getData());
        assertEquals(pagamento.getValor(), pagamentoCaptor.getValue().getValor());
      }

      @Test
      void shouldNotFoundPagamento() {
        PagamentoDTO pagamento = new PagamentoDTO();
        Mockito.doReturn(Optional.ofNullable(null)).when(pagamentoRepository).findById(Mockito.any());
        assertThrows(DataNotFoundException.class, () -> pagamentoService.updatePagamento(pagamento, 1L));
      }
      @Test
      void shouldNotPermitUpdate() {
        mockUsuarioLogado();
        Mockito.doReturn(Optional.ofNullable(PagamentoBuilder.create().withUsuario(new User("","","")).build())).when(pagamentoRepository).findById(Mockito.any());
        assertThrows(IllegalArgumentException.class, () -> pagamentoService.updatePagamento(new PagamentoDTO(), 1L));
      }
  }

  private User mockUsuarioLogado(){
    User usuarioLogado=getUser();
    Mockito.doReturn(usuarioLogado).when(userService).getUsuarioLogado();
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
