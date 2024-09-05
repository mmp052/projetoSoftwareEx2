package br.insper.loja.partida.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.anyInt;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import br.insper.loja.partida.dto.RetornarPartidaDTO;
import br.insper.loja.partida.dto.SalvarPartidaDTO;
import br.insper.loja.partida.repository.PartidaRepository;
import br.insper.loja.time.model.Time;
import br.insper.loja.time.service.TimeService;

@ExtendWith(MockitoExtension.class)
public class PartidaServiceTests {


    @InjectMocks
    private PartidaService partidaService;

    @Mock
    private PartidaRepository partidaRepository;

    @Mock
    private TimeService timeService;

    @Test
    public void testCadastrarPartida() {

        SalvarPartidaDTO salvarPartidaDTO = new SalvarPartidaDTO();
        salvarPartidaDTO.setMandante(1);
        salvarPartidaDTO.setVisitante(1);

        Time time = new Time();

        Mockito.when(timeService.getTime(anyInt()))
        .thenReturn(time);

        RetornarPartidaDTO partidaRetorno = partidaService.cadastrarPartida(salvarPartidaDTO);
        Assertions.assertNotNull(partidaRetorno);
    }


}
