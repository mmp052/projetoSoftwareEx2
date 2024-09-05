package br.insper.loja.partida.service;

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

import br.insper.loja.partida.dto.EditarPartidaDTO;
import br.insper.loja.partida.dto.RetornarPartidaDTO;
import br.insper.loja.partida.dto.SalvarPartidaDTO;
import br.insper.loja.partida.model.Partida;
import br.insper.loja.partida.repository.PartidaRepository;
import br.insper.loja.time.model.Time;
import br.insper.loja.time.service.TimeService;

@ExtendWith(MockitoExtension.class)
public class PartidaServiceTests {
    @InjectMocks
    private PartidaService partidaService;

    @Mock
    private TimeService timeService;

    @Mock
    private PartidaRepository partidaRepository;

    @Test
    public void testGetPartidaWhenIdIsNull() {

        Mockito.when(partidaRepository.findById(1)).thenReturn(Optional.empty());

        Assertions.assertThrows(RuntimeException.class,
                () -> partidaService.getPartida(1));

    }

    @Test
    public void testGetPartidaWhenIdIsNotNull() {
        Time time = new Time();

        time.setNome("Palmeiras");
        time.setIdentificador("PAL");
        time.setEstado("SP");
        time.setEstadio("AllP");

        Partida partida = new Partida();
        partida.setStatus("REALIZADA");
        partida.setPlacarMandante(1);
        partida.setPlacarVisitante(0);
        partida.setIdentificador("123");
        partida.setMandante(time);
        partida.setVisitante(time);
        partida.setId(1);

        Mockito.when(partidaRepository.findById(1)).thenReturn(Optional.of(partida));

        RetornarPartidaDTO partidaRetorno = partidaService.getPartida(1);

        Assertions.assertNotNull(partidaRetorno);
        Assertions.assertEquals("REALIZADA", partidaRetorno.getStatus());
        Assertions.assertEquals(0, partidaRetorno.getPlacarVisitante());
        Assertions.assertEquals(1, partidaRetorno.getPlacarMandante());
    }

    @Test
    public void testListarPartidasWhenMandanteIsNull() {

        ArrayList<Partida> lista = new ArrayList<>();

        Time time = new Time();

        time.setNome("Palmeiras");
        time.setIdentificador("PAL");
        time.setEstado("SP");
        time.setEstadio("AllP");

        Partida partida = new Partida();
        partida.setStatus("REALIZADA");
        partida.setPlacarMandante(1);
        partida.setPlacarVisitante(0);
        partida.setIdentificador("123");
        partida.setMandante(time);
        partida.setVisitante(time);
        partida.setId(1);

        lista.add(partida);

        // preparacao
        Mockito.when(partidaRepository.findAll()).thenReturn(lista);

        // chamada do codigo testado
        List<RetornarPartidaDTO> partidas = partidaService.listarPartidas(null);

        // verificacao dos resultados
        Assertions.assertNotNull(partidas);
        Assertions.assertEquals(1, partidas.size());
        Assertions.assertEquals("Palmeiras", partidas.get(0).getNomeMandante());

    }

    @Test
    public void testListarPartidasWhenMandanteIsNotNull() {

        ArrayList<Partida> lista = new ArrayList<>();

        Time time = new Time();

        time.setNome("Palmeiras");
        time.setIdentificador("PAL");
        time.setEstado("SP");
        time.setEstadio("AllP");

        Partida partida = new Partida();
        partida.setStatus("REALIZADA");
        partida.setPlacarMandante(1);
        partida.setPlacarVisitante(0);
        partida.setIdentificador("123");
        partida.setMandante(time);
        partida.setVisitante(time);
        partida.setId(1);

        lista.add(partida);

        // preparacao
        Mockito.when(partidaRepository.findAll()).thenReturn(lista);

        // chamada do codigo testado
        List<RetornarPartidaDTO> partidas = partidaService.listarPartidas("PAL");

        // verificacao dos resultados
        Assertions.assertNotNull(partidas);
        Assertions.assertEquals(1, partidas.size());
        Assertions.assertEquals("Palmeiras", partidas.get(0).getNomeMandante());

    }

    @Test
    public void testCadastrarPartida() {
        Time time = new Time();
        time.setNome("Palmeiras");
        time.setIdentificador("PAL");
        time.setEstado("SP");
        time.setEstadio("AllP");
        time.setId(1);

        Partida partida = new Partida();
        partida.setStatus("REALIZADA");
        partida.setPlacarMandante(1);
        partida.setPlacarVisitante(0);
        partida.setIdentificador("123");
        partida.setMandante(time);
        partida.setVisitante(time);
        partida.setId(1);

        SalvarPartidaDTO salvarPartidaDTO = new SalvarPartidaDTO();
        salvarPartidaDTO.setMandante(1);
        salvarPartidaDTO.setVisitante(1);


        Mockito.when(timeService.getTime(1)).thenReturn(time);
        Mockito.when(partidaRepository.save(Mockito.any(Partida.class))).thenReturn(partida);



        RetornarPartidaDTO partidaRetorno = partidaService.cadastrarPartida(salvarPartidaDTO);

        Assertions.assertNotNull(partidaRetorno);
        Assertions.assertEquals("Palmeiras", partidaRetorno.getNomeMandante());
    }

    @Test
    public void testEditarPartida() {
        Time time = new Time();
        time.setNome("Palmeiras");
        time.setIdentificador("PAL");
        time.setEstado("SP");
        time.setEstadio("AllP");
        time.setId(1);

        Partida partida = new Partida();
        partida.setStatus("AGENDADA");
        partida.setPlacarMandante(1);
        partida.setPlacarVisitante(0);
        partida.setIdentificador("123");
        partida.setMandante(time);
        partida.setVisitante(time);
        partida.setId(1);

        SalvarPartidaDTO salvarPartidaDTO = new SalvarPartidaDTO();
        salvarPartidaDTO.setMandante(1);
        salvarPartidaDTO.setVisitante(1);

        Mockito.when(partidaRepository.findById(1)).thenReturn(Optional.of(partida));  // Simula a busca pela partida no repositório
        Mockito.when(partidaRepository.save(Mockito.any(Partida.class))).thenReturn(partida);

        EditarPartidaDTO editarPartidaDTO = new EditarPartidaDTO();
        editarPartidaDTO.setPlacarMandante(1);
        editarPartidaDTO.setPlacarVisitante(0);


        RetornarPartidaDTO partidaRetorno = partidaService.editarPartida(editarPartidaDTO, 1);

        Assertions.assertNotNull(partidaRetorno);
        Assertions.assertEquals("Palmeiras", partidaRetorno.getNomeMandante());
        Assertions.assertEquals(1, partidaRetorno.getPlacarMandante());
        Assertions.assertEquals(0, partidaRetorno.getPlacarVisitante());
        Assertions.assertEquals("REALIZADA", partidaRetorno.getStatus());

    }






}