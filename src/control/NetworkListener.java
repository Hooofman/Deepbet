package control;

import org.neuroph.core.events.NeuralNetworkEvent;
import org.neuroph.core.events.NeuralNetworkEventListener;

public class NetworkListener implements NeuralNetworkEventListener {
	

	@Override
	public void handleNeuralNetworkEvent(NeuralNetworkEvent arg0) {
		System.out.println(arg0);

	}

}
