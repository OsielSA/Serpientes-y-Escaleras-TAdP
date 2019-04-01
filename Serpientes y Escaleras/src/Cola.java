
public class Cola <T> {
	private int maximo, frente, fin;
	private T[] c;
	public T Dr;
	
	public Cola(){
		this(10);
	}
	public Cola(int maximo){
		this.maximo=maximo;
		frente=fin=-1;
		Dr=null;
		c = (T[])new Object[maximo];
	}
	
	public boolean Inserta(T dato){
		if(Llena())
			return false;
		c[++fin]=dato;
		if(Vacia())
			frente++;
		return true;
	}
	public boolean Retira(){
		if(Vacia())
			return false;
		Dr = c[frente];
		if(frente==fin)
			frente=fin=-1;
		else
			frente++;
		return true;
	}
	public boolean Llena(){
		return fin==maximo-1;
	}
	public boolean Vacia(){
		return frente==-1;
	}
}
