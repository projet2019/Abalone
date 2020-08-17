package com.abalone_jeu.Model;

import java.awt.Point;

public class Trou{
	private static int num=0;
	private int idTrou; 
	private boolean occupied;
	private Boule boule;
	public Trou[] voisinage=new Trou[6];          //6 directions permises pour le deplacement d'un pion
	private int poid;                             //Un poids qui permettra d'evaluer un coup par rapport e un autre
	private boolean bord;                         //pour savoir s'il s'agit d'une cellule bord de l'hexagone
	private Point PositionTrou;							//coordonnee du trou

	public Trou(boolean a)
	{
		super();
		bord=a;
		
	}
	/**
	 * verifie si le trou est occupé
	 */
	public Trou()
	{
		idTrou=num;
		num++;
		occupied=false;
	}
	public Boule getBoule()
	{
		return boule;
	}
	public void setBoule(Boule b)
	{
		if(b!=null)
			{
				this.setOccupied(true);
			}
		if(bord==false)
		boule=b;

	}
	
	public boolean getOccupied()                //but:savoir si le trou est occupe ou pas
	{
		return occupied;
	}
	public void setOccupied(boolean a)                //but:changer le statu du trou avec ue valeur
	{
		occupied=a;
	}
	
	
	public void setVoisinage(Trou[] voisinage) 
	{
		this.voisinage = voisinage;
	}
	public boolean getBord() {             //on veut savoir si le trou est un bord ou pas
		return bord;
	}
	public void setBord(boolean bord) {
		this.bord = bord;
	}
	public int getPoid() {
		return poid;
	}
	public void setPoid(int poid) {
		this.poid = poid;
	}
	public static int getNum() {
		return num;
	}
	public static void setNum(int num) {
		Trou.num = num;
	}
	public int getIdTrou() {
		return idTrou;
	}
	public void setIdTrou(int idTrou) {
		this.idTrou = idTrou;
	}
	/**
	 * @return le positionTrou
	 */
	public Point getPositionTrou() {
		return PositionTrou;
	}
	/**
	 * @param positionTrou le positionTrou e definir
	 */
	public void setPositionTrou(Point positionTrou) {
		PositionTrou = positionTrou;
	}
	public Trou[] getVoisinage() {
		return voisinage;
	}
	
	
	public Point getPosition() {
		return PositionTrou;
	}
	
	public void setPosition(Point position) {
		PositionTrou = position;
	}
	
	
	
}
