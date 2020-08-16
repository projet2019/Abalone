package com.abalone_jeu.Model;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

public class Joueur {
	private static int cpteur=0;
	private int idJoueur; 
	private Color couleur;
	private int nBout=0;                        //nombre de boule du joueur stant deja sorti
    private Boule [][] mesBoules=new Boule[3][];              
    private boolean iaJoueur;                //savoir si c'est l'ordi ou pas
    private int niveau=1;                        //si c'est l'ordi,quel est son niveau de difficulte
    private static Hexagone tableDjeu;
	private Joueur adversaire;
	private int poidJoueur;                   //somme ds poidsds trou ou st place boule,permet d savoir kel joueur a l'avantag
	public static Boule bP=null;					//best pion a deplacer
	public static  int bD=-20;						//best deplacement
	private  int bTD=-20;	//best type de deplacement
	private int fP=-20;		//profondeur coups en fleche
	private int dF=-20; 	//direction ds voisin pr le deplacement en fleche
  
	

	public Boule locate(int id)
	{
		for(int i=0;i<mesBoules.length;i++)
			for(int j=0;j<mesBoules[i].length;j++)
			{
				if(mesBoules[i][j].getIdBoule()==id)//on ajoute le pion id dans la liste des pion selectionne s'il existe deja on l'a sort
				{ 
					return mesBoules[i][j];
				}
			}
		for(int i=0;i<mesBoules.length;i++)
			for(int j=0;j<mesBoules[i].length;j++)
			{
				if(mesBoules[i][j].getIdBoule()==id)//on ajoute le pion id dans la liste des pion selectionne s'il existe deja on l'a sort
				{ 
					return mesBoules[i][j];
				}
			}
		return null;
	}

	public boolean select(int id)//on ajoute le pion id dans la liste des pion selectionne si existe deja on l'a sort
	{
		//System.out.println(id);
		if(locate(id)!=null && tableDjeu.nbSelect==0)
		{
			if(tableDjeu.pion.contains(locate(id))) //si elle existe deja on l'a sort
				{	
							//System.out.println("ote");
							tableDjeu.pion.remove(locate(id));
							tableDjeu.nbSelect--;
							tableDjeu.NotifyObserver(false, id);
							return true;
				}
			else		//sinon on l'ajoute
				{
					//System.out.println("ajoute");
					tableDjeu.pion.add(locate(id));
					tableDjeu.nbSelect++;
					tableDjeu.NotifyObserver(true, id);
					return true;
				}
		}
			
		//s'il y'a deja une boule et qu'on veut en selectione pour le deplacement laterale
		else if(locate(id)!=null && (tableDjeu.nbSelect>0 && tableDjeu.nbSelect<3) )
		{
			if(tableDjeu.pion.contains(locate(id)))
			{	
						//System.out.println("ote");
						tableDjeu.pion.remove(locate(id));
						tableDjeu.nbSelect--;
						tableDjeu.NotifyObserver(false, id);
						return true;
			}
			else
			{
				//on ajoute et on verifie l'alignement
				tableDjeu.pion.add(locate(id));
				tableDjeu.nbSelect++;
				if(!alignement())
				{
					tableDjeu.pion.remove(locate(id));
					tableDjeu.nbSelect--;
					//System.out.println("les boules ne sont pas aligne ou ne sont pas du meme joueur");
					return false;
				}
				else
				{
					tableDjeu.NotifyObserver(true, id);
					
					//System.out.println("les boules st alignes");
					return true;
				}
				
			}
			
				
		}
		else if(tableDjeu.nbSelect==3)
		{
			if(tableDjeu.pion.contains(locate(id)))
			{	
						//System.out.println("ote");
						tableDjeu.pion.remove(locate(id));
						tableDjeu.nbSelect--;
						tableDjeu.NotifyObserver(false, id);
						return true;
			}
			else
			{
				//System.out.println("nombre max de boule atteint");
				return false;
				
			}
		}
		return false;
			
	}
	
	public Joueur getAdversaire() {
		return adversaire;
	}



	public void setAdversaire(Joueur adversaire) {
		this.adversaire = adversaire;
	}

	
	public Joueur(Color couleur,boolean ia,Hexagone b,Boule [][] a)
    {
		iaJoueur=ia;
    	idJoueur=cpteur;
    	cpteur++;
    	setTableDjeu(b);
    	this.couleur=couleur;
    	mesBoules=a;
    	initBouleAttribute();
   }
   
    
    
    public int getIdJoueur() {
		return idJoueur;
	}


	public void initBouleAttribute()
    {
    	for(int i=0;i<mesBoules.length;i++)
    	{
    		for(int j=0;j<mesBoules[i].length;j++)
    		{
    			//System.out.println(mesBoules[i][j]);
    			mesBoules[i][j].setColor(this.couleur);
    			mesBoules[i][j].setJoueur(this);
    		}
    	}
    }
    

   
    
    public void setNBout(int n)
    {
    	nBout=n;
    }
    public int getNBout()
    {
    	modifyNBout();
    	return nBout;
    }
    public void modifyNBout()
    {
    	int nb=0;
    	for(int i=0;i<mesBoules.length;i++)
    	{
    		for(int j=0;j<mesBoules[i].length;j++)
    		{
    			if(mesBoules[i][j].getEtat()==true)
    			{
    				nb++;
    			}
    		}
    	}
    			
    				
    				
    	nBout=nb;
    	
    }
    
  
   
    
    public Color getColor()
    {
    	return couleur;
    }
    
    public void setColor(Color a)
    {
    	couleur=a;
    }
    
    
    
    
    
	public static void setCpteur(int cpteur) {
		Joueur.cpteur = cpteur;
	}



	public int calculPoidJoueurs()
    {
    		poidJoueur=0;
	    	for(int i=0;i<mesBoules.length;i++)
	    	{
	    		for(int j=0;j<mesBoules[i].length;j++)
	    		{
	    			if(mesBoules[i][j].getEtat()==false)
	    			{
	    				 poidJoueur+=mesBoules[i][j].getTrou().getPoid();
	    			}
	    			
	    		}
	    	}
			return poidJoueur;
    	
    }
    public int getPoid()
    {
    	return poidJoueur;
    }
    
    		public final Boule save(final Boule pion,final int TypeDeplacement,final int direction)
    		{
    			Boule saved=pion;
    			
    				for(int i=0;i<abs(TypeDeplacement)-5;i++)
        				saved=saved.getTrou().voisinage[direction].getBoule();
    			
				return saved;
    		}
    		
    		
    		public void Dejoue(Boule pion,Trou retour)
    		{
    			pion.setTrou(retour);
    		}
    		
    		
   public void meilleurCoups(int level)
   {
	   int ALPHA = -(Integer.MAX_VALUE);
	    int BETA = Integer.MAX_VALUE;

	   int meilleurEval=ALPHA;
	   int evalCoups;
	   ArrayList<Boule> coups=new ArrayList<Boule>();
	   ArrayList<Point> depCordonee=new ArrayList<Point>(); 
	   for(int i=0;i<this.mesBoules.length;i++)
		   for(int j=0;j<this.mesBoules[i].length;j++)
			   if(this.mesBoules[i][j].getEtat()==false)
				   for(int direction=0;direction<6;direction++)
					   if(tableDjeu.regle.getDeplacement(this.mesBoules[i][j].getTrou(), direction)!=-20)
					   {
						   int tD=tableDjeu.regle.getDeplacement(this.mesBoules[i][j].getTrou(), direction);
						   if(tD==-7 || tD==-8 || tD==-9 || tD==-4 || tD==-5 || tD==-6)
						   {
							   bD=direction;
							   bTD=tD;
							   bP=this.mesBoules[i][j];
							   return;
						   }

						 
						   
							   Trou depart=this.mesBoules[i][j].getTrou();
							   tableDjeu.joue(this.mesBoules[i][j], direction, tD);
							   evalCoups=minValue(this.getAdversaire(),ALPHA,BETA,(level-1));
							   tableDjeu.Dejoue(depart, direction, tD);
			
							   if(evalCoups>meilleurEval)
							   {
								   meilleurEval=evalCoups;
								   coups=new ArrayList<Boule>();
								   depCordonee=new ArrayList<Point>();
								   coups.add(depart.getBoule());
								   depCordonee.add(new Point(direction,tD));
								  

							   }
							   else if(evalCoups==meilleurEval)
							   {
								   coups.add(depart.getBoule());
								   depCordonee.add(new Point(direction,tD));
		                        
							   }

							   
						   
					   }
	   Random rnd =  new Random();
	   int n=rnd.nextInt(coups.size());
	   System.out.println(n);
       bP= coups.get(n);
       bTD=depCordonee.get(n).y;
       bD=depCordonee.get(n).x;
       if(bTD==-1)
	   {
		   int pF=-20;
		  
		for(int df=0;df<6 && df!=bD;df++)
		   {
				pF=tableDjeu.testFleche(bP.getTrou(), bD, df);
				if(pF!=-20)
				{
					if(pF==2)
					{
							fP=2;
							dF=df;
							return;
				
					}
					else
					{
						
							fP=3;
							dF=df;
							return;
					
					}
				}		   
			}
		
	   }
   }
   
   public int minValue(Joueur j1,int alpha,int beta,int profondeur)
   {
	  if(profondeur==0)
		  return tableDjeu.fctEval(j1);
	  
	   for(int i=0;i<j1.mesBoules.length;i++)
		   for(int j=0;j<j1.mesBoules[i].length;j++)
			   if(j1.mesBoules[i][j].getEtat()==false)
				   for(int direction=0;direction<6;direction++)
					   {
					   		int tD=tableDjeu.regle.getDeplacement(j1.mesBoules[i][j].getTrou(), direction);
					   		if(tD!=-20 && tD!=-7 && tD!=-8 && tD!=-9)
						   {	

								   Trou depart=j1.mesBoules[i][j].getTrou();
								   tableDjeu.joue(j1.mesBoules[i][j], direction, tD);
								  
								   beta=Math.min(beta,maxValue(j1.getAdversaire(),alpha,beta,(profondeur-1)));
								   tableDjeu.Dejoue(depart, direction, tD);
								   if(alpha>=beta)
								   {
									   return alpha;
								   }
								  
								  
							   }
					   }
						   
					  
	   return beta;
   }		
           
 
   
   public int maxValue(Joueur j1,int alpha,int beta,int profondeur)
   {
	  if(profondeur==0)
		  return tableDjeu.fctEval(j1);
	  
	   for(int i=0;i<j1.mesBoules.length;i++)
		   for(int j=0;j<j1.mesBoules[i].length;j++)
			   if(j1.mesBoules[i][j].getEtat()==false)
				   for(int direction=0;direction<6;direction++)
				   {
					   int tD=tableDjeu.regle.getDeplacement(j1.mesBoules[i][j].getTrou(), direction);
					   if(tD!=-20 && tD!=-7 && tD!=-8 && tD!=-9)
					   {

						  
							   Trou depart=j1.mesBoules[i][j].getTrou();
							   tableDjeu.joue(j1.mesBoules[i][j], direction, tD);
		
							   alpha=Math.max(alpha,minValue(j1.getAdversaire(),alpha,beta,(profondeur-1)));
							   tableDjeu.Dejoue(depart, direction, tD);
							   if(alpha>=beta)
							   {
								   return beta;
							   }
							  
							  
						  
					  }
				   }
					   
	   return alpha;
   }		



public void calculCoup()
{
	int n=(this.niveau);
	meilleurCoups(n);
	if(fP!=-20 && bTD==-1)
	{
		Trou s=bP.getTrou().voisinage[dF];
		if(fP==2)
		{
			tableDjeu.joue(s.getBoule(),bD,bTD);
			fP=-20;
		}
		else if(fP==3)
		{
			
			tableDjeu.joue(s.getBoule(),bD,bTD);
			tableDjeu.joue(s.voisinage[dF].getBoule(),bD,bTD);
			fP=-20;
		}
			
	}


}

public Boule getBouleAt(int i,int j)
{
	return mesBoules[i][j];
}
public boolean isIaJoueur() {
	return iaJoueur;
}



public int getNiveau() {
	return niveau;
}



public void setNiveau(int niveau) {
	this.niveau = niveau;
}



public void setIaJoueur(boolean iaJoueur) {
	this.iaJoueur = iaJoueur;
}

public Hexagone getTable()
{
	return tableDjeu;
}




public boolean  alignement()          
{
	boolean result=false;
	int direction=-1;
		for(int j=0;j<6;j++)//la 2em boule doit etr dan l'un des voisin,de mm proprietair k la boule selectione et de mm couleur
			{
				if((tableDjeu.pion.get(1).getTrou().equals(tableDjeu.pion.get((0)).getTrou().voisinage[j])) && (tableDjeu.pion.get(1).getColor()==tableDjeu.pion.get((0)).getColor()))
					{
						result=true;
						direction=j;
					
					}
			}
		if(result==true && tableDjeu.nbSelect==3) //si le 1er test est vrai on verifie que la 3eme est voisin de la 2em  dan la mm direction ou voisin de la 1ere boule dan la direction oppose
		{								
			result=false;
			if(direction==0 || direction==1 || direction==2)
				{
					if((tableDjeu.pion.get(2).getTrou().equals(tableDjeu.pion.get((1)).getTrou().voisinage[direction])) && (tableDjeu.pion.get(2).getColor()==tableDjeu.pion.get((1)).getColor())
						|| (tableDjeu.pion.get(2).getTrou().equals(tableDjeu.pion.get((0)).getTrou().voisinage[(direction+3)])) && (tableDjeu.pion.get(2).getColor()==tableDjeu.pion.get((0)).getColor()))
					result=true;
				}
			else
			{
				if((tableDjeu.pion.get(2).getTrou().equals(tableDjeu.pion.get((1)).getTrou().voisinage[direction])) && (tableDjeu.pion.get(2).getColor()==tableDjeu.pion.get((1)).getColor())
						|| (tableDjeu.pion.get(2).getTrou().equals(tableDjeu.pion.get((0)).getTrou().voisinage[abs(direction-3)])) && (tableDjeu.pion.get(2).getColor()==tableDjeu.pion.get((0)).getColor()))
					result=true;
			}
				
		}
		return result;
		
	}

public static  boolean testFleche2(int dir)
{
	for(Boule a:tableDjeu.pion)
	{
		if(tableDjeu.regle.getDeplacement(a.getTrou(), dir)!=-1)
			return false;
	}
	return true;
}



public int abs(int i) {
	if(i<0)
	return -i;
	else
		return i;
}
	
//qui realise le deplacement si celui ci est possible.
public static boolean  deplacer(Boule b,int direction)      
{
	boolean done=true;
	switch(tableDjeu.regle.getDeplacement(b.getTrou(),direction))                                
	{ 
	case -1:/*System.out.println("je joue la boule "+b.getIdBoule()+" se trouvant dans le trou "+b.getTrou().getIdTrou()+" en deplacer_1")*/;tableDjeu.regle.deplacer_1(b,direction);break;
	case -2:/*System.out.println("je joue la boule "+b.getIdBoule()+" se trouvant dans le trou "+b.getTrou().getIdTrou()+" en deplacer_2")*/;tableDjeu.regle.deplacer_2(b,direction);break;
	case -3:/*System.out.println("je joue la boule "+b.getIdBoule()+" se trouvant dans le trou "+b.getTrou().getIdTrou()+" en deplacer_3")*/;tableDjeu.regle.deplacer_3(b,direction);break;
	case -4:/*System.out.println("je joue la boule "+b.getIdBoule()+" se trouvant dans le trou "+b.getTrou().getIdTrou()+" en pousser_2_1")*/;tableDjeu.regle.pousser_2_1(b,direction);break;
	case -5:/*System.out.println("je joue la boule "+b.getIdBoule()+" se trouvant dans le trou "+b.getTrou().getIdTrou()+" en pousser_3_1")*/;tableDjeu.regle.pousser_3_1(b,direction);break;
	case -6:/*System.out.println("je joue la boule "+b.getIdBoule()+" se trouvant dans le trou "+b.getTrou().getIdTrou()+" en pousser_3_2")*/;tableDjeu.regle.pousser_3_2(b,direction);break;
	case -7:/*System.out.println("je joue la boule "+b.getIdBoule()+" se trouvant dans le trou "+b.getTrou().getIdTrou()+" en manger_2_1")*/;tableDjeu.regle.manger_2_1(b,direction);break;
	case -8:/*System.out.println("je joue la boule "+b.getIdBoule()+" se trouvant dans le trou "+b.getTrou().getIdTrou()+" en manger_3_1")*/;tableDjeu.regle.manger_3_1(b,direction);break;
	case -9:/*System.out.println("je joue la boule "+b.getIdBoule()+" se trouvant dans le trou "+b.getTrou().getIdTrou()+" en manger_3_2")*/;tableDjeu.regle.manger_3_2(b,direction);break;
	case -20:tableDjeu.regle.Bloquer();/*System.out.println("bloquer")*/;done=false;break;
	default: tableDjeu.regle.Bloquer();/*System.out.println("bloquer")*/;done=false;break;
	}
			
	return done;
}




public void setTableDjeu(Hexagone table) {
	tableDjeu = table;
}	
	
}

 