package observer;

public interface IObservador {
	/**
	 * Notifica o observador de atualizações no observado
	 * 
	 * @param observador - o observador a ser notificado
	 */
	void notify(IObservado observado);
}