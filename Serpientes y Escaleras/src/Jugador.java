import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Jugador extends JLabel{
	public int jugador;
	public NodoDBL<Casilla> posicion;
	public ImageIcon icoJugador;
	public Jugador(int jugador, String imagen) {
		this.jugador = jugador;
		icoJugador = Rutinas.AjustarImagen(imagen, 30, 30);
//		setText("Jugador "+jugador+" Pos: 0");
		setText("<html>Jugador "+jugador+"<p> -Posición: "+0+"</html>");
	}
	public void Actualiza() {
		String info = "<html>Jugador "+jugador+"<p> -Posición: "+posicion.Info.NoCasilla+"</html>";
		setText(info);
//		setText("Jugador "+jugador+" Pos: "+posicion.Info.NoCasilla);
		posicion.Info.MuestraJugador(icoJugador,1);
	}
}
