package br.com.zup.edu.ligaqualidade.desafiobiblioteca.modifique.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import br.com.zup.edu.ligaqualidade.desafiobiblioteca.DadosEmprestimo;
import br.com.zup.edu.ligaqualidade.desafiobiblioteca.EmprestimoConcedido;
import br.com.zup.edu.ligaqualidade.desafiobiblioteca.modifique.repository.EmprestimoConcedidoRepository;
import br.com.zup.edu.ligaqualidade.desafiobiblioteca.modifique.repository.ExemplarRepository;
import br.com.zup.edu.ligaqualidade.desafiobiblioteca.modifique.repository.UsuarioRepository;
import br.com.zup.edu.ligaqualidade.desafiobiblioteca.pronto.DadosUsuario;
import br.com.zup.edu.ligaqualidade.desafiobiblioteca.pronto.TipoExemplar;
import br.com.zup.edu.ligaqualidade.desafiobiblioteca.pronto.TipoUsuario;

public class RegistrarEmprestimoService {

    UsuarioRepository usuarioRepository;
    EmprestimoConcedidoRepository emprestimoConcedidoRepository;
    ExemplarDisponivelService exemplarDisponivelService;
    LocalDate dataParaSerConsideradaNaExpiracao;

    public RegistrarEmprestimoService(UsuarioRepository usuarioRepository,
                                      ExemplarRepository exemplarRepository,
                                      EmprestimoConcedidoRepository emprestimoConcedidoRepository,
                                      LocalDate dataParaSerConsideradaNaExpiracao) {
        this.usuarioRepository = usuarioRepository;
        this.emprestimoConcedidoRepository = emprestimoConcedidoRepository;
        this.exemplarDisponivelService = new ExemplarDisponivelService(exemplarRepository, emprestimoConcedidoRepository);
        this.dataParaSerConsideradaNaExpiracao = dataParaSerConsideradaNaExpiracao;
    }

    public void registrar(Set<DadosEmprestimo> emprestimos) {

    	List<DadosEmprestimo> listaEmprestimos = new ArrayList<>(emprestimos);
    	Collections.sort(listaEmprestimos);
    	
        for (DadosEmprestimo emprestimo : listaEmprestimos) {
            if (emprestimo.tempo > 60) {
                continue;
            }

            DadosUsuario dadosUsuario = usuarioRepository.get(emprestimo.idUsuario);
            if (TipoUsuario.PADRAO.equals(dadosUsuario.padrao) && TipoExemplar.RESTRITO.equals(emprestimo.tipoExemplar)) {
                continue;
            }
            
            if (TipoUsuario.PADRAO.equals(dadosUsuario.padrao)
            		&& emprestimoConcedidoRepository.contemExemplaresExpirados(dataParaSerConsideradaNaExpiracao, LocalDate.now().plusDays(emprestimo.tempo))) {
            	continue;
            }

            Integer idExemplar = exemplarDisponivelService.getId(emprestimo.idLivro, emprestimo.tipoExemplar);

            EmprestimoConcedido emprestimoConcedido = new EmprestimoConcedido(emprestimo.idPedido, emprestimo.idUsuario,
                    idExemplar,
                    LocalDate.now().plusDays(emprestimo.tempo));

            emprestimoConcedidoRepository.registrar(emprestimoConcedido);
        }
    }
}
