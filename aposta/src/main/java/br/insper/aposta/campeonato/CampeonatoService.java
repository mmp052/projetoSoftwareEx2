package br.insper.aposta.campeonato;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;

import br.insper.aposta.aposta.ApostaRepository;
import br.insper.aposta.partida.RetornarPartidaDTO;

public class CampeonatoService {
    @Autowired
    private ApostaRepository apostaRepository;

    @KafkaListener(topics = "partidas")
    public void getPartidas(RetornarPartidaDTO partidaDTO) {
        apostaRepository.findAll().forEach(aposta -> {
            if(partidaDTO.getId().equals(aposta.getIdPartida())) {
                if (aposta.getResultado().equals("EMPATE") && partidaDTO.isEmpate()) {
                    aposta.setStatus("GANHOU");
                }

                if (aposta.getResultado().equals("VITORIA_MANDANTE") && partidaDTO.isVitoriaMandante()) {
                    aposta.setStatus("GANHOU");
                }

                if (aposta.getResultado().equals("EMPATE") && partidaDTO.isVitoriaVisitante()) {
                    aposta.setStatus("GANHOU");
                }

                if (aposta.getStatus().equals("REALIZADA")) {
                    aposta.setStatus("PERDEU");
                }

                apostaRepository.save(aposta);
            }
        });
        
    }
}
