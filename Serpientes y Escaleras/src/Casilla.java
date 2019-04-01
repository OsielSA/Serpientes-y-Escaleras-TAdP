import java.awt.Color;

import javax.swing.*;
import javax.swing.border.Border;
public class Casilla extends JLabel{
	
	public int NoCasilla;
	public char tipoCasilla = 'N';
	public int posiciones;
	
	private Border raised = BorderFactory.createRaisedBevelBorder();
	public Casilla(int NoCasilla) {
		this.NoCasilla = NoCasilla;
		
		setBorder(raised);
		setText(""+NoCasilla);
		setOpaque(true);
		setBackground(new Color(204, 204, 255));
	}
	public void AddTipo(Color color) {
		setBackground(color);
		if(tipoCasilla == 'E') {
			setText(NoCasilla+"-"+(NoCasilla+posiciones));
			return;
		}
		if(tipoCasilla == 'S') {
			setText(NoCasilla+"-"+(NoCasilla-posiciones));
			return;
		}	
			
	}
	private int cont = 0;
	private ImageIcon jugadorAnt;
	private ImageIcon jugadorActual = null;
	public void MuestraJugador(ImageIcon jugador,int n) {
		//entró un jugador
		if(n== 1) {
			if(cont == 0) {
				setIcon(jugador);
				jugadorActual = jugador;
				cont++;
				return;
			}
			setIcon(jugador);
			jugadorAnt = jugador;
			return;
		}
		//Salió un jugador
		if(n == 0) {
			if(cont > 0) {
				setIcon(jugadorAnt);
				jugadorActual = jugador;
				cont--;
				return;
			}
			setIcon(null);
			jugadorActual = null;
		}
	}
	//true = entró jugador y false = salió el jugador
	//ico es la imagen del jugador que esta pasando
	public void Animacion(boolean b, ImageIcon ico) {
		if(b) {
			setIcon(ico);
			return;
		}
		setIcon(jugadorActual);
	}
}
