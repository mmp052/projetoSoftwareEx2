package br.insper.loja.time.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import br.insper.loja.time.exception.TimeNaoEncontradoException;
import br.insper.loja.time.model.Time;
import br.insper.loja.time.repository.TimeRepository;

@ExtendWith(MockitoExtension.class)
public class TimeServiceTests {


    @InjectMocks
    private TimeService timeService;

    @Mock
    private TimeRepository timeRepository;

    @Test
    public void testListarTimesWhenEstadoIsNull() {

        // preparacao
        Mockito.when(timeRepository.findAll()).thenReturn(new ArrayList<>());

        // chamada do codigo testado
        List<Time> times = timeService.listarTimes(null);

        // verificacao dos resultados
        Assertions.assertTrue(times.isEmpty());
    }

    @Test
    public void testListarTimesWhenEstadoIsNotNull() {

        // preparacao
        List<Time> lista = new ArrayList<>();

        Time time = new Time();
        time.setEstado("SP");
        time.setIdentificador("time-1");
        lista.add(time);

        Mockito.when(timeRepository.findByEstado(Mockito.anyString())).thenReturn(lista);

        // chamada do codigo testado
        List<Time> times = timeService.listarTimes("SP");

        // verificacao dos resultados
        Assertions.assertTrue(times.size() == 1);
        Assertions.assertEquals("SP", times.getFirst().getEstado());
        Assertions.assertEquals("time-1", times.getFirst().getIdentificador());
    }

    @Test
    public void testGetTimeWhenTimeIsNotNull() {

        Time time = new Time();
        time.setEstado("SP");
        time.setIdentificador("time-1");

        Mockito.when(timeRepository.findById(1)).thenReturn(Optional.of(time));

        Time timeRetorno = timeService.getTime(1);

        Assertions.assertNotNull(timeRetorno);
        Assertions.assertEquals("SP", timeRetorno.getEstado());
        Assertions.assertEquals("time-1", timeRetorno.getIdentificador());

    }

    @Test
    public void testGetTimeWhenTimeIsNull() {

        Mockito.when(timeRepository.findById(1)).thenReturn(Optional.empty());

        Assertions.assertThrows(TimeNaoEncontradoException.class,
                () -> timeService.getTime(1));

    }

    @Test
    public void testCadastrarTimeWhenNomeIsEmpty() {
        Time time = new Time();
        time.setNome("");
        time.setIdentificador("123");

        RuntimeException exception = Assertions.assertThrows(RuntimeException.class,
                () -> timeService.cadastrarTime(time));

        Assertions.assertEquals("Dados invalidos", exception.getMessage());
    }

    @Test
    public void testCadastrarTimeWhenNomeAndIdentificadorIsNotEmpty() {
        Time time = new Time();
        time.setNome("teste");
        time.setIdentificador("123");

        Mockito.when(timeRepository.save(time)).thenReturn(time);

        Time timeRetorno = timeService.cadastrarTime(time);

        Assertions.assertNotNull(timeRetorno);
        Assertions.assertEquals("teste", timeRetorno.getNome());
        Assertions.assertEquals("123", timeRetorno.getIdentificador());
    }

    @Test
    public void testCadastrarTimeWhenIdentificadorIsEmpty() {
        Time time = new Time();
        time.setNome("teste");
        time.setIdentificador("");

        RuntimeException exception = Assertions.assertThrows(RuntimeException.class,
                () -> timeService.cadastrarTime(time));

        Assertions.assertEquals("Dados invalidos", exception.getMessage());
    }


}