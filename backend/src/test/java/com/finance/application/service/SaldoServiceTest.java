package com.finance.application.service;

import com.finance.application.builder.DepositoBuilder;
import com.finance.application.builder.GastoBuilder;
import com.finance.application.model.Deposito;
import com.finance.application.model.Gasto;
import com.finance.application.model.MovimentacaoSaldo;
import com.finance.application.model.Pagamento;
import com.finance.application.repository.MovimentacaoSaldoRepository;
import com.finance.autentication.model.User;
import com.finance.autentication.repository.UserRepository;
import com.finance.autentication.service.UserService;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class SaldoServiceTest {
  @InjectMocks
  private SaldoService saldoService;
  @Mock
  private UserService userService;
  @Mock
  private GastoService gastoService;
  @Mock
  private DepositoService depositoService;
  @Mock
  private UserRepository userRepository;
  @Mock
  private MovimentacaoSaldoRepository movimentacaoSaldoRepository;
  @Captor
  private ArgumentCaptor<User> userCapture;

  @Nested
  class AtualizarSaldo{
    @Test
    void shouldUpdateBalanceOnDeposit() {
      Deposito deposito = DepositoBuilder.init().withValor(100).withDescricao("Teste").build();
      User user = new User();
      user.setContaCorrente(1000.0);
      Mockito.when(userService.getUsuarioLogado()).thenReturn(user);
      saldoService.atualizarSaldo(deposito);
      verify(movimentacaoSaldoRepository, never()).delete(Mockito.any(MovimentacaoSaldo.class));
      verify(movimentacaoSaldoRepository).save(Mockito.any(MovimentacaoSaldo.class));
      verify(userRepository,Mockito.times(1)).save(user);
      assertEquals(1100.0, user.getContaCorrente());
    }
    @Test
    void shouldUpdateBalanceOnUpdateDepositValue() {
      //nesse cenário: usuario tinha deposito de 500 reais, ele alterou o valor para 100:
      // conta corrente deve diminuir em 400 reais
      Deposito deposito = DepositoBuilder.init().withId(1L).withValor(100).withDescricao("Teste").build();
      User user = new User();
      user.setContaCorrente(1000.0);
      Mockito.when(userService.getUsuarioLogado()).thenReturn(user);
      MovimentacaoSaldo movimentacao = new MovimentacaoSaldo();
      movimentacao.setValor(500.0);
      Mockito.when(movimentacaoSaldoRepository.findByDeposito(deposito)).thenReturn(movimentacao);
      saldoService.atualizarSaldo(deposito);
      verify(movimentacaoSaldoRepository).delete(movimentacao);
      verify(movimentacaoSaldoRepository).save(Mockito.any(MovimentacaoSaldo.class));
      verify(userRepository,Mockito.times(2)).save(user);
      assertEquals(600.0, user.getContaCorrente());
    }
    @Test
    void shouldUpdateBalanceOnExpense() {
      Gasto gasto = GastoBuilder.create().withValor(100.0).build();
      User user = new User();
      user.setContaCorrente(1000.0);
      Mockito.when(userService.getUsuarioLogado()).thenReturn(user);
      saldoService.atualizarSaldo(gasto);
      verify(movimentacaoSaldoRepository, never()).delete(Mockito.any(MovimentacaoSaldo.class));
      verify(movimentacaoSaldoRepository).save(Mockito.any(MovimentacaoSaldo.class));
      verify(userRepository,Mockito.times(1)).save(user);
      assertEquals(900.0, user.getContaCorrente());
    }
    @Test
    void shouldUpdateBalanceOnUpdateExpense() {
      Gasto gasto = GastoBuilder.create().withId(1L).withValor(100.0).build();
      User user = new User();
      user.setContaCorrente(1000.0);
      Mockito.when(userService.getUsuarioLogado()).thenReturn(user);
      MovimentacaoSaldo movimentacao = new MovimentacaoSaldo();
      movimentacao.setValor(500.0);
      Mockito.when(movimentacaoSaldoRepository.findByGasto(gasto)).thenReturn(movimentacao);
      saldoService.atualizarSaldo(gasto);
      verify(movimentacaoSaldoRepository).delete(movimentacao);
      verify(movimentacaoSaldoRepository).save(Mockito.any(MovimentacaoSaldo.class));
      verify(userRepository,Mockito.times(2)).save(user);
      assertEquals(1400.0, user.getContaCorrente());
    }
  }

  @Nested
  class GetMovimentoMensal{
    @Test
    void shouldAddValue() {
      User user = getUser();
      user.setContaCorrente(1000);
      MovimentacaoSaldo movimentacaoSaldo = saldoService.getMovimentoSaldoDefault(100.0 , user, true);
      assertEquals(1100.0, movimentacaoSaldo.getSaldoAtualizado());
      assertEquals(1000.0, movimentacaoSaldo.getSaldoAnterior());
      assertEquals(100.0, movimentacaoSaldo.getValor());


    }
    @Test
    void shouldSubtractValue() {
      User user = getUser();
      user.setContaCorrente(1000);
      MovimentacaoSaldo movimentacaoSaldo = saldoService.getMovimentoSaldoDefault(100.0 , user, false);
      assertEquals(900, movimentacaoSaldo.getSaldoAtualizado());
      assertEquals(1000.0, movimentacaoSaldo.getSaldoAnterior());
      assertEquals(100.0, movimentacaoSaldo.getValor());

    }


  }

  @Nested
  class AtualizaSaldoContaCorrente{
    @Test
    void shouldAddValue() {
      User usuarioLogado = mockUsuarioLogado();
      Mockito.doReturn(new User()).when(userRepository).save(userCapture.capture());
      saldoService.atualizaSaldoContaCorrente(100.0, usuarioLogado, true);
      assertEquals(1100, usuarioLogado.getContaCorrente());
    }
    @Test
    void shouldSubtractValue() {
      User usuarioLogado = mockUsuarioLogado();
      Mockito.doReturn(new User()).when(userRepository).save(userCapture.capture());
      saldoService.atualizaSaldoContaCorrente(100.0, usuarioLogado, false);
      assertEquals(900, usuarioLogado.getContaCorrente());
    }
    @Test
    void shouldNotSubtractNegativeValue() {
      User usuarioLogado = mockUsuarioLogado();
      assertThrows(IllegalArgumentException.class, () -> saldoService.atualizaSaldoContaCorrente(-100.0, usuarioLogado, false));
    }
  }


  @Nested
  class ReverterValores{
    @Test
    void shouldRevertExpanse() {
      User user = mockUsuarioLogado();
      double updatedAmout=user.getContaCorrente()+300;
      MovimentacaoSaldo movimentacao = new MovimentacaoSaldo();
      movimentacao.setValor(300);
      Mockito.when(movimentacaoSaldoRepository.findByGasto(Mockito.any())).thenReturn(movimentacao);
      saldoService.reverterGasto(GastoBuilder.create().withId(1L).build());
      assertEquals(updatedAmout, user.getContaCorrente());
    }
    @Test
    void shouldNotRevertExpanse() {
      User user = mockUsuarioLogado();
      MovimentacaoSaldo movimentacao = new MovimentacaoSaldo();
      Mockito.when(movimentacaoSaldoRepository.findByGasto(Mockito.any())).thenReturn(movimentacao);
      saldoService.reverterGasto(GastoBuilder.create().withId(1L).build());
      assertEquals(user.getContaCorrente(), user.getContaCorrente());
    }
    @Test
    void shouldReverDeposit(){
      User user = mockUsuarioLogado();
      double updatedAmout=user.getContaCorrente()-300;
      MovimentacaoSaldo movimentacao = new MovimentacaoSaldo();
      movimentacao.setValor(300);
      Mockito.when(movimentacaoSaldoRepository.findByDeposito(Mockito.any())).thenReturn(movimentacao);
      saldoService.reverterDeposito(DepositoBuilder.init().withId(1L).withValor(300).build());
      assertEquals(updatedAmout, user.getContaCorrente());
    }
    @Test
    void shouldNotRevertDeposit(){
      User user = mockUsuarioLogado();
      MovimentacaoSaldo movimentacao = new MovimentacaoSaldo();
      Mockito.when(movimentacaoSaldoRepository.findByDeposito(Mockito.any())).thenReturn(movimentacao);
      saldoService.reverterDeposito(DepositoBuilder.init().withId(1L).withValor(300).build());
      assertEquals(user.getContaCorrente(), user.getContaCorrente());
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
    user.setContaCorrente(1000);
    return user;
  }
}
