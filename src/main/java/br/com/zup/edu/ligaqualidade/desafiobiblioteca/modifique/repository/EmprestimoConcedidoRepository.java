package br.com.zup.edu.ligaqualidade.desafiobiblioteca.modifique.repository;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import br.com.zup.edu.ligaqualidade.desafiobiblioteca.EmprestimoConcedido;

public class EmprestimoConcedidoRepository {

    Set<EmprestimoConcedido> emprestimosConcedidos;

    public EmprestimoConcedidoRepository() {
        emprestimosConcedidos = new HashSet<>();
    }

    public void registrar(EmprestimoConcedido emprestimoConcedido) {
        emprestimosConcedidos.add(emprestimoConcedido);
    }

    public void devolver(Integer idEmprestimo) {
        emprestimosConcedidos.stream().filter(it -> it.idEmprestimo == idEmprestimo).findFirst().get().registraDevolucao();
    }

    public Set<EmprestimoConcedido> get() {
        return emprestimosConcedidos;
    }

    public Set<Integer> getExemplaresComEmprestivoAtivos(Set<Integer> idsExemplares) {
        return emprestimosConcedidos.stream().filter(it ->
            !it.getMomentoDevolucao().isPresent() && idsExemplares.contains(it.idExemplar)
        ).map(it -> it.idExemplar).collect(Collectors.toSet());
    }
    
    public boolean contemExemplaresExpirados(LocalDate dataParaSerConsideradaNaExpiracao, LocalDate momentoDevolucaoNovoEmprestimo) {
    	return emprestimosConcedidos
    			.parallelStream()
    			.anyMatch(emprestimoConcedido ->
    				emprestimoConcedido.dataPrevistaDevolucao.isBefore(dataParaSerConsideradaNaExpiracao) && !momentoDevolucaoNovoEmprestimo.equals(emprestimoConcedido.dataPrevistaDevolucao)
    			);
    }
}
