package com.abalone_jeu.Model;

import java.awt.Point;

public class Trou{
	private static int num=0;
	private int idTrou; 
	private boolean occupied;
	private Boule boule;
	public Trou[] voisinage=new Trou[6];          
	private int poid;                             
	private boolean bord;                         
	private Point PositionTrou;							

	public Trou(boolean a)
	{
		super();
		bord=a;
		
	}
	
	public boolean getOccupied()                
	{
		return occupied;
	}
	public void setOccupied(boolean a)                
	{
		occupied=a;
	}
	
	
	public void setVoisinage(Trou[] voisinage) 
	{
		this.voisinage = voisinage;
	}
	public boolean getBord() {             
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
	 * @param positionTrou 
	 */
	public void setPositionTrou(Point positionTrou) {
		PositionTrou = positionTrou;
	}
	
	public Point getPosition() {
		return PositionTrou;
	}
	
	public void setPosition(Point position) {
		PositionTrou = position;
	}
	
	
	
}
