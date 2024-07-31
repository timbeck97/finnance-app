package com.finance.application.service;

import com.finance.application.builder.GastoBuilder;
import com.finance.application.dto.GastoDTO;
import com.finance.application.model.Gasto;
import com.finance.application.repository.GastoRepository;
import com.finance.autentication.model.User;
import com.finance.autentication.service.UserService;
import com.finance.configuration.enums.ECategoriaGasto;
import com.finance.configuration.enums.EFormaPagamento;
import com.finance.configuration.enums.ETipoGasto;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class GastoServiceTest {

  @Mock
  private GastoRepository gastoRepository;
  @Mock
  private UserService userService;
  @Mock
  private SaldoService saldoService;
  @InjectMocks
  private GastoService gastoService;
  @Captor
  private ArgumentCaptor<Gasto> gastoArgumentCaptor;

  @Nested
  class FindValues {
    @Test
    void shouldReturnAllExpanses() {
      when(gastoRepository.findByData(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(Page.empty());
      assertEquals(0, gastoService.findAll("202101", 1, 10, null, "filtro", null).size());
    }
    @Test
    void shouldReturnSummedValues() {
      mockUsuarioLogado();
      Object[] objects1 = new Object[]{BigDecimal.valueOf(100.00), BigDecimal.valueOf(200.00)};
      List<Object[]> mockResult = new ArrayList<>();
      mockResult.add(objects1);
      when(gastoRepository.findGastosMensais(any(), any())).thenReturn(mockResult);
      Map<String, Double> result = gastoService.findAllMensal("2024-07");

      Map<String, Double> expected = new HashMap<>();
      expected.put("totalPix", 100.00);
      expected.put("totalCartao", 200.00);

      assertEquals(expected, result);
    }
  }

  @Nested
  class Save{
    @Test
    void shouldSave() {
      User user = mockUsuarioLogado();
      GastoDTO gastoDTO = new GastoDTO();
      gastoDTO.setData("202401");
      gastoDTO.setCategoria(ECategoriaGasto.OUTROS);
      gastoDTO.setFormaPagamento(EFormaPagamento.CARTAO);
      gastoDTO.setTipoGasto(ETipoGasto.VARIAVEL);
      gastoDTO.setValor(100.0);
      gastoDTO.setDescricao("Teste");
      Mockito.doReturn(GastoBuilder.create().withDto(gastoDTO, user).build()).when(gastoRepository).save(gastoArgumentCaptor.capture());
      Mockito.doNothing().when(saldoService).atualizarSaldo(any(Gasto.class));
      Gasto result = gastoService.save(gastoDTO);
      assertEquals(result.getData(), gastoArgumentCaptor.getValue().getData());
      assertEquals(result.getCategoria(), gastoArgumentCaptor.getValue().getCategoria());
      assertEquals(result.getFormaPagamento(), gastoArgumentCaptor.getValue().getFormaPagamento());
      assertEquals(result.getTipoGasto(), gastoArgumentCaptor.getValue().getTipoGasto());
      assertEquals(result.getValor(), gastoArgumentCaptor.getValue().getValor());
      assertEquals(result.getDescricao(), gastoArgumentCaptor.getValue().getDescricao());
      verify(saldoService, never()).atualizarSaldo(any(Gasto.class));

    }
    @Test
    void shouldSaveAndUpdateBalance() {
      User user = mockUsuarioLogado();
      GastoDTO gastoDTO = new GastoDTO();
      gastoDTO.setData("202401");
      gastoDTO.setCategoria(ECategoriaGasto.OUTROS);
      gastoDTO.setFormaPagamento(EFormaPagamento.PIX);
      gastoDTO.setTipoGasto(ETipoGasto.VARIAVEL);
      gastoDTO.setValor(100.0);
      gastoDTO.setDescricao("Teste");
      Mockito.doReturn(GastoBuilder.create().withDto(gastoDTO, user).build()).when(gastoRepository).save(gastoArgumentCaptor.capture());
      Mockito.doNothing().when(saldoService).atualizarSaldo(any(Gasto.class));
      Gasto result = gastoService.save(gastoDTO);
      assertEquals(result.getData(), gastoArgumentCaptor.getValue().getData());
      assertEquals(result.getCategoria(), gastoArgumentCaptor.getValue().getCategoria());
      assertEquals(result.getFormaPagamento(), gastoArgumentCaptor.getValue().getFormaPagamento());
      assertEquals(result.getTipoGasto(), gastoArgumentCaptor.getValue().getTipoGasto());
      assertEquals(result.getValor(), gastoArgumentCaptor.getValue().getValor());
      assertEquals(result.getDescricao(), gastoArgumentCaptor.getValue().getDescricao());
      verify(saldoService).atualizarSaldo(any(Gasto.class));

    }

  }

  @Nested
  class Update{
    @Test
    void shouldUpdateAndUpdateBalancePixToPix() {
      User user = mockUsuarioLogado();
      GastoDTO gastoDTO = new GastoDTO();
      gastoDTO.setData("202401");
      gastoDTO.setCategoria(ECategoriaGasto.OUTROS);
      gastoDTO.setFormaPagamento(EFormaPagamento.PIX);
      gastoDTO.setTipoGasto(ETipoGasto.VARIAVEL);
      gastoDTO.setValor(100.0);
      gastoDTO.setDescricao("Teste");
      Mockito.doReturn(Optional.of(GastoBuilder.create().withFormaPagamento(EFormaPagamento.PIX).build())).when(gastoRepository).findById(anyLong());
      Mockito.doReturn(GastoBuilder.create().withDto(gastoDTO, user).build()).when(gastoRepository).save(gastoArgumentCaptor.capture());
      Mockito.doNothing().when(saldoService).atualizarSaldo(any(Gasto.class));
      Gasto result = gastoService.updateGasto(gastoDTO, 1);

      verify(saldoService, never()).reverterGasto(any(Gasto.class));
      verify(saldoService).atualizarSaldo(gastoArgumentCaptor.capture());

      List<Gasto> allValues = gastoArgumentCaptor.getAllValues();
      assertAll(result, allValues);
    }
    @Test
    void shouldUpdateAndUpdateBalanceCrediCardToPix() {
      User user = mockUsuarioLogado();
      GastoDTO gastoDTO = new GastoDTO();
      gastoDTO.setData("202401");
      gastoDTO.setCategoria(ECategoriaGasto.OUTROS);
      gastoDTO.setFormaPagamento(EFormaPagamento.PIX);
      gastoDTO.setTipoGasto(ETipoGasto.VARIAVEL);
      gastoDTO.setValor(100.0);
      gastoDTO.setDescricao("Teste");
      Mockito.doReturn(Optional.of(GastoBuilder.create().withFormaPagamento(EFormaPagamento.CARTAO).build())).when(gastoRepository).findById(anyLong());
      Mockito.doReturn(GastoBuilder.create().withDto(gastoDTO, user).build()).when(gastoRepository).save(gastoArgumentCaptor.capture());
      Mockito.doNothing().when(saldoService).atualizarSaldo(any(Gasto.class));
      Gasto result = gastoService.updateGasto(gastoDTO, 1);

      verify(saldoService, never()).reverterGasto(any(Gasto.class));
      verify(saldoService).atualizarSaldo(gastoArgumentCaptor.capture());

      List<Gasto> allValues = gastoArgumentCaptor.getAllValues();
      assertAll(result, allValues);
    }
    @Test
    void shouldUpdateAndRevertBalancePixToCrediCard() {
      User user = mockUsuarioLogado();
      GastoDTO gastoDTO = new GastoDTO();
      gastoDTO.setData("202401");
      gastoDTO.setCategoria(ECategoriaGasto.OUTROS);
      gastoDTO.setFormaPagamento(EFormaPagamento.CARTAO);
      gastoDTO.setTipoGasto(ETipoGasto.VARIAVEL);
      gastoDTO.setValor(100.0);
      gastoDTO.setDescricao("Teste");
      Mockito.doReturn(Optional.of(GastoBuilder.create().withFormaPagamento(EFormaPagamento.PIX).build())).when(gastoRepository).findById(anyLong());
      Mockito.doReturn(GastoBuilder.create().withDto(gastoDTO, user).build()).when(gastoRepository).save(gastoArgumentCaptor.capture());
      Mockito.doNothing().when(saldoService).atualizarSaldo(any(Gasto.class));
      Gasto result = gastoService.updateGasto(gastoDTO, 1);

      verify(saldoService, never()).atualizarSaldo(any(Gasto.class));
      verify(saldoService).reverterGasto(gastoArgumentCaptor.capture());


      List<Gasto> allValues = gastoArgumentCaptor.getAllValues();
      assertAll(result, allValues);
    }
    @Test
    void shouldUpdateAndRevertBalanceCrediCard() {
      User user = mockUsuarioLogado();
      GastoDTO gastoDTO = new GastoDTO();
      gastoDTO.setData("202401");
      gastoDTO.setCategoria(ECategoriaGasto.OUTROS);
      gastoDTO.setFormaPagamento(EFormaPagamento.CARTAO);
      gastoDTO.setTipoGasto(ETipoGasto.VARIAVEL);
      gastoDTO.setValor(100.0);
      gastoDTO.setDescricao("Teste");
      Mockito.doReturn(Optional.of(GastoBuilder.create().withFormaPagamento(EFormaPagamento.CARTAO).build())).when(gastoRepository).findById(anyLong());
      Mockito.doReturn(GastoBuilder.create().withDto(gastoDTO, user).build()).when(gastoRepository).save(gastoArgumentCaptor.capture());
      Mockito.doNothing().when(saldoService).atualizarSaldo(any(Gasto.class));
      Gasto result = gastoService.updateGasto(gastoDTO, 1);

      verify(saldoService, never()).atualizarSaldo(any(Gasto.class));
      verify(saldoService).reverterGasto(gastoArgumentCaptor.capture());
      List<Gasto> allValues = gastoArgumentCaptor.getAllValues();

      assertAll(result, allValues);

    }


    @Test
    void shouldNotFindExpansive(){
      when(gastoRepository.findById(anyLong())).thenReturn(Optional.empty());
      assertThrows(IllegalArgumentException.class, () -> gastoService.updateGasto(new GastoDTO(), 1));
    }
  }

  @Test
  void copiarGastosFixos() {
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
  private void assertAll(Gasto result,  List<Gasto> allValues){
    assertEquals(result.getData(), allValues.get(0).getData());
    assertEquals(result.getCategoria(), allValues.get(0).getCategoria());
    assertEquals(result.getFormaPagamento(), allValues.get(0).getFormaPagamento());
    assertEquals(result.getTipoGasto(), allValues.get(0).getTipoGasto());
    assertEquals(result.getValor(), allValues.get(0).getValor());
    assertEquals(result.getDescricao(), allValues.get(0).getDescricao());

    assertEquals(result.getData(), allValues.get(1).getData());
    assertEquals(result.getCategoria(), allValues.get(1).getCategoria());
    assertEquals(result.getFormaPagamento(), allValues.get(1).getFormaPagamento());
    assertEquals(result.getTipoGasto(), allValues.get(1).getTipoGasto());
    assertEquals(result.getValor(), allValues.get(1).getValor());
    assertEquals(result.getDescricao(), allValues.get(1).getDescricao());
  }
}
