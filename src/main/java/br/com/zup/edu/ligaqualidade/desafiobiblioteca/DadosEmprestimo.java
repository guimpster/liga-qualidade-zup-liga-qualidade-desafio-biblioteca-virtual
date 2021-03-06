package br.com.zup.edu.ligaqualidade.desafiobiblioteca;

import br.com.zup.edu.ligaqualidade.desafiobiblioteca.pronto.TipoExemplar;

public class DadosEmprestimo implements Comparable<Object> {

	public final int idLivro;
	public final int idUsuario;
	//vai ser usado para posterior consulta em cima da solução
	public final int idPedido;
	public final int tempo;
	public final TipoExemplar tipoExemplar;

	public DadosEmprestimo(int idLivro, int idUsuario,int tempo,TipoExemplar tipoExemplar, int idPedido) {
		this.idLivro = idLivro;
		this.idUsuario = idUsuario;
		this.tempo = tempo;
		this.tipoExemplar = tipoExemplar;
		this.idPedido = idPedido;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idLivro;
		result = prime * result + idPedido;
		result = prime * result + idUsuario;
		result = prime * result + tempo;
		result = prime * result + ((tipoExemplar == null) ? 0 : tipoExemplar.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DadosEmprestimo other = (DadosEmprestimo) obj;
		if (idLivro != other.idLivro)
			return false;
		if (idPedido != other.idPedido)
			return false;
		if (idUsuario != other.idUsuario)
			return false;
		if (tempo != other.tempo)
			return false;
		if (tipoExemplar != other.tipoExemplar)
			return false;
		return true;
	}

	@Override
	public int compareTo(Object o) {
		return this.tempo - ((DadosEmprestimo) o).tempo;
	}

}
