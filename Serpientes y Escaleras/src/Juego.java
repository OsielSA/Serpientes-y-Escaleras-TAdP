import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class Juego extends JFrame implements ActionListener{
	
	ListaDBL<Casilla> tablero = new ListaDBL<Casilla>();
	Jugador jugadores[];
	int nJugadores;
	int turno=0;
	JPanel tableroPan, menuOP;
	JButton dado;
	JLabel info;
	Color cSerpiente = new Color(64,112,30);
	Color cEscalera = new Color(182,100,56);
	Color cTerminacion = new Color(125,125,125);
	
	public Juego() {
		super("Serpientes y Escaleras");
		HazInterface();
		HazEscuchas();

		
	}
	
	private void HazInterface() {
		setSize(1000,620); 
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		crearTablero();
		Opciones();
		AddJugadores();
		
		setVisible(true);
	}
	
	private void HazEscuchas() {
		dado.addActionListener(this);
	}
	
	private void crearTablero() {
		tableroPan = new JPanel(new GridLayout(10,10,3,3));
		//Crea la lista (el tablero)
		for(int i=0;i<100;i++) 
			tablero.InsertaFin(new Casilla(i+1));
		//Crea escaleras
		for(int i=0;i<5;i++) 
			creaEscalera();
		//Crea serpientes
		for(int i=0;i<5;i++)
			creaSerpiente();

		//Agrega los elemntos de la lista al Panel de tablero
		AddTableroPan();
		add(tableroPan, BorderLayout.CENTER);
	}
	
	public boolean creaEscalera() {
		NodoDBL<Casilla> aux = tablero.getFrente();
		NodoDBL<Casilla> aux2;
		int NoCasilla;
		int avanza;
		while(true) {
			NoCasilla = Rutinas.nextInt(15, 70);
			avanza = Rutinas.nextInt(5, 20);
			
			//Esté while me posiciona en la casilla para generar la escalera
			while(!(aux.Info.NoCasilla == NoCasilla))
				aux = aux.getSig();
			if(aux.Info.tipoCasilla == 'N') {
				int verifica = aux.Info.NoCasilla + avanza;
				aux2 = aux;
				while(!(aux2.Info.NoCasilla == verifica)) {
					aux2 = aux2.getSig();
				}
				
				if(aux2.Info.tipoCasilla == 'N') {
					aux.Info.tipoCasilla = 'E';
					aux.Info.posiciones = avanza;
					aux2.Info.tipoCasilla = 'T';
					aux.Info.AddTipo(cEscalera);
					aux2.Info.AddTipo(cTerminacion);
					return true;
				}
			}
			
		}
	}
	public boolean creaSerpiente() {
		NodoDBL<Casilla> aux = tablero.getFrente();
		NodoDBL<Casilla> aux2;
		int NoCasilla = Rutinas.nextInt(30, 95);
		int retrocede = Rutinas.nextInt(5, 20);
		
		//Esté while me posiciona en la casilla para generar la serpiente
		while(!(aux.Info.NoCasilla == NoCasilla))
			aux = aux.getSig();
		if(aux.Info.tipoCasilla =='N') {
			aux2 = aux;
			int verifica = aux.Info.NoCasilla - retrocede;
			while(!(aux2.Info.NoCasilla == verifica))
				aux2 = aux2.getAnt();
			if(aux2.Info.tipoCasilla == 'N') {
				aux.Info.tipoCasilla = 'S';
				aux.Info.posiciones = retrocede;
				aux2.Info.tipoCasilla = 'T';
				aux.Info.AddTipo(cSerpiente);
				aux2.Info.AddTipo(cTerminacion);
				return true;
			}
		}
		return creaSerpiente();
	}
	public void AddJugadores() {
		//Agrega jugadores
		nJugadores = Rutinas.nextInt(2, 10);
		jugadores = new Jugador[nJugadores];
		for(int i=0;i<jugadores.length;i++) {
			jugadores[i] = new Jugador(i+1,"J"+(i+1)+".png");
			menuOP.add(jugadores[i]);
		}
		
	}
	private void AddTableroPan() {
		int cont=1;
		NodoDBL<Casilla> aux = tablero.getFin();
		NodoDBL<Casilla> aux2;
		while(true){
			if(aux == null)
				return;
			if(cont<=10) {
				tableroPan.add(aux.Info);
			}
			if(cont>10) {
				aux = aux.getSig();
				for(int i=0;i<10;i++)
					aux = aux.getAnt();
				aux2 = aux;
				for(int j=0;j<10;j++) {
					tableroPan.add(aux.Info);
					aux = aux.getSig();
				}
				aux = aux2;
				cont = 0;
			}
			aux = aux.getAnt();
			cont++;
		}
	}
	private void Opciones() {
		menuOP = new JPanel(new GridLayout(0,1));
		dado = new JButton();
		dado.setIcon(Rutinas.AjustarImagen("dados.png", 90, 70));
		info = new JLabel();
		info.setBorder(BorderFactory.createRaisedBevelBorder());
		menuOP.add(dado);
		menuOP.add(info);
		add(menuOP, BorderLayout.EAST);
	}
	
	
	public static void main(String[] args) {
		
//		try {
//			UIManager.setLookAndFeel(new NimbusLookAndFeel());
//		} catch (UnsupportedLookAndFeelException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		new Juego();
	}

	boolean ganador=false;
	@Override
	public void actionPerformed(ActionEvent evt) {
		JButton Btn=(JButton) evt.getSource();
		int mov = Rutinas.nextInt(2, 12);
		if(turno>nJugadores-1)
			turno=0;
		  
		String text = "<html><center>Turno: J"+jugadores[turno].jugador+"<p>Avanza: "+mov+"</center></html>";
		info.setText(text);
		mueveJugador(jugadores[turno], mov);
		jugadores[turno].Actualiza();
		if(jugadores[turno].posicion.Info.NoCasilla == 100) {
			JOptionPane.showMessageDialog(null,"¡Has Ganado!");
			Btn.setEnabled(false);
			Btn.setText("<html><center>Juego<p>Terminado</center></html>");
		}
//		if(ganador) {
//			JOptionPane.showMessageDialog(null,"¡Has Ganado!");
//			Btn.setEnabled(false);
//			Btn.setText("<html><center>Juego<p>Terminado</center></html>");
//		}
		turno++;
	}
	
	
	public void mueveJugador(Jugador j, int movimientos) {
		int n;
		if(j.posicion == null) {
			j.posicion = tablero.getFrente();
			movimientos--;
		}
		
		j.posicion.Info.MuestraJugador(null,0);
		//Desabilito el boton dado
		dado.setEnabled(false);
		ImageIcon ico = j.icoJugador;
		//Entra el ciclo que mueve al jugador por las casillas
		for(int i=0;i<movimientos;i++) {
			if(j.posicion.getSig() == null)
				return;
			
			//Llamo al método Animacion del nodo por el que estoy pasando 
			// y paso como parametro true
			j.posicion.Info.Animacion(true, ico);
			// Actualizo y doy un poco de tiempo
			update(getGraphics());
			try{
				Thread.sleep(200);
			} catch (Exception e){}
			//Llamo nuevamente al metodo, esta vez con false
			j.posicion.Info.Animacion(false, ico);
			
			j.posicion = j.posicion.getSig();
		}
		//termina y vuelvo a habilitar el boton dado
		if(j.posicion.Info.NoCasilla == 100)
			ganador = true;
		dado.setEnabled(true);
		
		
		if(j.posicion.Info.tipoCasilla == 'E') {
			n = j.posicion.Info.posiciones;
			for(int i=0;i<n;i++)
				j.posicion = j.posicion.getSig();
			return;
		}
		
		if(j.posicion.Info.tipoCasilla == 'S') {
			n = j.posicion.Info.posiciones;
			for(int i=0;i<n;i++)
				j.posicion = j.posicion.getAnt();
			return;
		}
		
	}
}
