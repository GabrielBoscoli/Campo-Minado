package observer;

public interface IObservado {
	/**
	 * Adiciona o objeto observador a uma lista de observadores
	 * @param observador - objeto observador a ser adicionado
	 */
	void add(IObservador observador);
	
	/**
	 * Remove o objeto observador da lista de observadores
	 * @param observador - objeto observador a ser removido
	 */
	void remove(IObservador observador);
	
	/**
	 * Retorna dados de acordo com o parametro passado
	 * @param i - define qual dado retornar
	 * @return Objeto generico com o dado retornado
	 */
	Object get(int i);
}