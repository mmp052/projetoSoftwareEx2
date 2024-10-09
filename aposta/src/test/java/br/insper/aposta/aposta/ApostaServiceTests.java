// package br.insper.aposta.aposta;

// import java.util.ArrayList;
// import java.util.List;
// import java.util.Optional;

// import org.junit.jupiter.api.Assertions;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith;
// import static org.mockito.ArgumentMatchers.anyInt;
// import static org.mockito.ArgumentMatchers.anyString;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.Mockito;
// import org.mockito.junit.jupiter.MockitoExtension;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;

// import br.insper.aposta.partida.PartidaNaoEncontradaException;
// import br.insper.aposta.partida.PartidaNaoRealizadaException;
// import br.insper.aposta.partida.PartidaService;
// import br.insper.aposta.partida.RetornarPartidaDTO;

// @ExtendWith(MockitoExtension.class)
// public class ApostaServiceTests {

//     @InjectMocks
//     ApostaService apostaService;

//     @Mock
//     PartidaService partidaService;

//     @Mock
//     ApostaRepository apostaRepository;

//     @Test
//     public void testGetApostaWhenApostaIsNull() {
//         Mockito.when(apostaRepository.findById("1"))
//                 .thenReturn(Optional.empty());

//         Assertions.assertThrows(ApostaNaoEncontradaException.class,
//                 () -> apostaService.getAposta("1"));
//     }

//     @Test
//     public void testGetApostaWhenApostaIsNotNullStatusRealizada() {
//         Aposta aposta = new Aposta();
//         aposta.setStatus("GANHOU");

//         Mockito.when(apostaRepository.findById("1"))
//                 .thenReturn(Optional.of(aposta));

//         Aposta apostaRetorno = apostaService.getAposta("1");
//         Assertions.assertNotNull(apostaRetorno);
//         Assertions.assertEquals("GANHOU", apostaRetorno.getStatus());
//     }

//     @Test
//     public void testGetApostaWhenPartidaIsNotFind() {
//         ResponseEntity<RetornarPartidaDTO> partida = new ResponseEntity<>(HttpStatus.NOT_FOUND);

//         Aposta aposta = new Aposta();
//         aposta.setStatus("REALIZADA");
//         aposta.setIdPartida(1);

//         Mockito.when(apostaRepository.findById("1"))
//                 .thenReturn(Optional.of(aposta));

//         Mockito.when(partidaService.getPartida(anyInt()))
//                .thenReturn(partida);

//         Assertions.assertThrows(PartidaNaoEncontradaException.class,
//             () -> apostaService.getAposta("1"));
//     }

//     // @Test
//     // public void testGetApostaWhenPartidaIsFind() {
//     //     RetornarPartidaDTO partidaData = Mockito.mock(RetornarPartidaDTO.class);
//     //     Mockito.when(partidaData.getStatus()).thenReturn("REALIZADA");
//     //     ResponseEntity<RetornarPartidaDTO> partida = new ResponseEntity<>(partidaData, HttpStatus.ACCEPTED);

//     //     Aposta aposta = new Aposta();
//     //     aposta.setStatus("REALIZADA");
//     //     aposta.setIdPartida(1);
//     //     aposta.setResultado("VITORIA_MANDANTE");

//     //     Mockito.when(apostaRepository.findById("1"))
//     //             .thenReturn(Optional.of(aposta));

//     //     Mockito.when(partidaService.getPartida(anyInt()))
//     //            .thenReturn(partida);

//     //     Aposta apostaRetorno = apostaService.getAposta("1");
//     //     Assertions.assertNotNull(apostaRetorno);
//     //     Assertions.assertEquals("GANHOU", apostaRetorno.getStatus());
//     // }

//     @Test
//     public void testSalvarWhenPartidaNotFind() {
//         ResponseEntity<RetornarPartidaDTO> partida = new ResponseEntity<>(HttpStatus.NOT_FOUND);

//         Aposta aposta = new Aposta();
//         aposta.setIdPartida(1);

//         Mockito.when(partidaService.getPartida(anyInt()))
//                .thenReturn(partida);

//         Assertions.assertThrows(PartidaNaoEncontradaException.class,
//             () -> apostaService.salvar(aposta));
//     }

//     @Test
//     public void testSalvarWhenPartidaFind() {
//         RetornarPartidaDTO partidaData = Mockito.mock(RetornarPartidaDTO.class);
//         ResponseEntity<RetornarPartidaDTO> partida = new ResponseEntity<>(partidaData, HttpStatus.ACCEPTED);

//         Aposta aposta = new Aposta();
//         aposta.setIdPartida(1);

//         Mockito.when(partidaService.getPartida(anyInt()))
//         .thenReturn(partida);

//         Mockito.when(apostaRepository.save(aposta))
//         .thenReturn(aposta);

//         Aposta apostaRetorno = apostaService.salvar(aposta);
//         Assertions.assertNotNull(apostaRetorno);
//     }

//     @Test
//     public void testListar() {
//         Mockito.when(apostaRepository.findAll())
//         .thenReturn(new ArrayList<>());

//         List<Aposta> listaRetorno = apostaService.listar();
//         Assertions.assertNotNull(listaRetorno);
//     }

//     @Test
//     public void testGetApostaWhenPartidaIsNotRealizada() {
//         Aposta aposta = new Aposta();
//         aposta.setStatus("REALIZADA");
//         aposta.setIdPartida(1);

//         Mockito.when(apostaRepository.findById("1"))
//                 .thenReturn(Optional.of(aposta));

//         RetornarPartidaDTO partidaData = Mockito.mock(RetornarPartidaDTO.class);
//         Mockito.when(partidaData.getStatus()).thenReturn("PENDENTE");
//         ResponseEntity<RetornarPartidaDTO> partida = new ResponseEntity<>(partidaData, HttpStatus.ACCEPTED);
        
//         Mockito.when(partidaService.getPartida(anyInt()))
//                 .thenReturn(partida);

//         Assertions.assertThrows(PartidaNaoRealizadaException.class,
//                 () -> apostaService.getAposta("1"));
//     }

//     @Test
//     public void testGetApostaWhenPartidaDTOIsEmpate() {
//         Aposta aposta = new Aposta();
//         aposta.setStatus("REALIZADA");
//         aposta.setResultado("EMPATE");
//         aposta.setIdPartida(1);

//         Mockito.when(apostaRepository.findById("1"))
//                 .thenReturn(Optional.of(aposta));

//         RetornarPartidaDTO partidaData = new RetornarPartidaDTO();
//         partidaData.setStatus("REALIZADA");
//         partidaData.setPlacarMandante(0);
//         partidaData.setPlacarVisitante(0);
        
//         Mockito.when(partidaService.getPartida(anyInt()))
//                 .thenReturn(ResponseEntity.ok(partidaData));

//         Aposta apostaRetorno = apostaService.getAposta("1");
//         Assertions.assertNotNull(apostaRetorno);
//         Assertions.assertEquals("GANHOU", apostaRetorno.getStatus());
//     }

//     @Test
//     public void testGetApostaWhenVitoriaMandante() {
//         Aposta aposta = new Aposta();
//         aposta.setStatus("REALIZADA");
//         aposta.setResultado("VITORIA_MANDANTE");
//         aposta.setIdPartida(1);

        
//         Mockito.when(apostaRepository.findById(anyString()))
//         .thenReturn(Optional.of(aposta));
        
//         RetornarPartidaDTO partidaData = new RetornarPartidaDTO();
//         partidaData.setStatus("REALIZADA");
//         partidaData.setPlacarMandante(1);
//         partidaData.setPlacarVisitante(0);

//         Mockito.when(partidaService.getPartida(anyInt()))
//                 .thenReturn(ResponseEntity.ok(partidaData));

//         Aposta apostaRetorno = apostaService.getAposta("1");
//         Assertions.assertNotNull(apostaRetorno);
//         Assertions.assertEquals("GANHOU", apostaRetorno.getStatus());
//     }
// }