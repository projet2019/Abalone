package com.abalone_jeu.Controleur;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.abalone_jeu.IHM.View;
import com.abalone_jeu.Model.Boule;
import com.abalone_jeu.Model.Joueur;
import com.abalone_jeu.Model.Partie;
import com.abalone_jeu.Model.Trou;

public class Controle implements KeyListener,MouseListener,ActionListener {
		private static Partie a;
		private boolean b=false;
     	private static View a1;
     	private static int []jou1=new int[2];
     	private int [][] j1 = new int[14][2];
     	private int [][] j2 = new int[14][2];
     	//private int directionCLIC;
    	public void keyPressed(KeyEvent e)
		{	
			
		}

		@Override
		public void keyReleased(KeyEvent arg0) {
		
			switch (arg0.getKeyChar())
			{
					//laterale
				case '4'://System.out.println("touche gauche");
				playCoups(4);
				break;
				
				
				case '6'://System.out.println("touche droite");
				playCoups(1);
					break;
				
				
				case '7'://System.out.println("touche haut gauche");
				playCoups(5);
					break;
				
				
				case '9'://System.out.println("touche haut droite");
				playCoups(0);
					break;
				
				
				case '1'://System.out.println("touche bas gauche");
				playCoups(3);
					break;
					
				case '3'://System.out.println("touche bas droite");
					playCoups(2);
					break;
					
			}
			
		}
		
		public void b1Selected(int direction)				//lorsque 1e boule est selectionne
		{
			b=Joueur.deplacer(a.table.getPion().get(0),direction);
			//deplacement a ete effectue
			if(b==true)
			{
				//rendre toute les bouleJoueur2 inactif et changer de joueur
				a.table.jouer();
				a.table.setNbSelect(0);
				iAgame();
				//System.out.println("deplacement effectue");
			}
			//sinon essayer une autre direction
		}
		
		public void b2Selected(int direction)			//lorsque 2 boule sont selectionne
		{
			if(Joueur.testFleche2(direction))			//deplacement lateral possible pour ces 2 pion
			{
				Joueur.deplacer(a.table.getPion().get(0),direction);
				Joueur.deplacer(a.table.getPion().get(1),direction);
				a.table.jouer();
				a.table.setNbSelect(0);
				iAgame();
				//System.out.println("deplacement lateral possible pour ces 2 pion");
			}
		}
		
		public void b3Selected(int direction)		//lorsque 3 boule sont selectionne
		{
			if(Joueur.testFleche2(direction))			//deplacement lateral possible pour ces 3 pion
			{
				Joueur.deplacer(a.table.getPion().get(0),direction);
				Joueur.deplacer(a.table.getPion().get(1),direction);
				Joueur.deplacer(a.table.getPion().get(2),direction);
				a.table.jouer();
				a.table.setNbSelect(0);
				iAgame();
				//System.out.println("deplacement lateral possible pour ces 3 pion");
			}
		}
		
		public void playCoups(int direction)			//cette fonction permet d'effectue le deplacement pour chaq coup direction choisit par le joueur
		{
		if(a!=null && a.table.getNbSelect()==1)
		{
			b1Selected(direction);
		
		}
		else if(a!=null &&  a.table.getNbSelect()==2)
		{
			b2Selected(direction);
		}
		else if(a!=null &&  a.table.getNbSelect()==3)
		{
			b3Selected(direction);
		}
		else
		{
			System.out.println("pas de boule selectionne");
		}
		}
			
		public void iAgame()
		{
			if(a.table.getState()==false && a.j2.isIaJoueur() && a.table.getTour().equals(a.j2))
			{
				a.j2.calculCoup();
				Joueur.deplacer(Joueur.bP,Joueur.bD);				
				b=false;
				
				Joueur.bD=-20;
				Joueur.bP=null;
				a.table.jouer();
			}
		}

		@Override
		public void keyTyped(KeyEvent arg0) {
			
		}

		@Override
		public void mouseClicked(MouseEvent arg0) {
			int c=a1.getEchiquier().getIDB(arg0.getSource());
			if((a!=null && a.table.getState()==false) && (a.table.getTour().equals(a.j1) && c>=14)&& arg0.getButton()==1)
			{
				a.j1.select(c);
			}
			else if((a!=null  && a.table.getState()==false) &&(a.table.getTour().equals(a.j2) && a.j2.isIaJoueur()==false && c<14)&& arg0.getButton()==1)
			{
				a.j2.select(c);
			}
			else if(arg0.getButton()==3 && a!=null && a.table.getNbSelect()>=1)
			{
				a.table.setNbSelect(0);

			}
			
		}
		


		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Stub de la methode genere automatiquement
			
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Stub de la methode genere automatiquement
			
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Stub de la methode genere automatiquement
			
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Stub de la methode genere automatiquement
			
		}

		public static void nouvellePartie()
		{
			if(a==null)
			{	a=new Partie();
				a1.setTable(a.table);
				a.table.setTour(a.j1);
				a1.newGame();
			}
			else
			{
				Trou.setNum(0);
				Boule.setCpteur(0);
				Joueur.setCpteur(0);
				a=null;
				a=new Partie();
				a1.setTable(a.table);
				a.table.setTour(a.j1);
				a.table.nouvellePartie();
				a1.newGame();
			}
		}
		
		public void level(int i)
		{
			
				a.j2.setIaJoueur(true);
				a.j2.setNiveau(i);
				a1.iaPlayer();
				
			
		}
		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			switch(arg0.getActionCommand())
			{
		
			case"Niveau 1"://System.out.println("Niveau 1");
			nouvellePartie();
			level(1);
			break;
			
			
			case"Niveau 2"://System.out.println("Niveau 2");
			nouvellePartie();
			level(2);
			
			break;
			
			case"Niveau 3"://System.out.println("Niveau 2");
				nouvellePartie();
				level(3);
				
				break;
				
			case"Niveau 4"://System.out.println("Niveau 2");
				nouvellePartie();
				level(4);
				
				break;
			
			case"Niveau 5"://System.out.println("Niveau 2");
				nouvellePartie();
				level(5);
				
				break;
				
			case"Niveau 6"://System.out.println("Niveau 2");
				nouvellePartie();
				level(6);
				
				break;
			
			
			case"Humain vs Humain"://System.out.println("Humain vs Humain");
				nouvellePartie();
				

			break;
			
			case"Sauvegarder la Partie"://System.out.println("Sauvegarder la Partie");
			
			if(a!=null)
			{
				save();
				//System.out.println("sauvegarde terminee...");
			}
			
			break;
			
			
			case"Charger une Partie"://System.out.println("Charger une Partie");
			nouvellePartie();
			load();
			
					
			//System.out.println("Chargement termine");
			break;
			
			case"Quitter"://System.out.println("Quitter");
			if(a!=null)
			save();
			System.exit(0);
			
			break;
			}
			
		
			
		}

		
		public static void main(String[] args)
		{
		
			try {
				UIManager.setLookAndFeel("com.jtattoo.plaf.texture.TextureLookAndFeel");
				} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException
				| UnsupportedLookAndFeelException e1) {
				e1.printStackTrace();};
				
		EventQueue.invokeLater(new Runnable(){
				public void run()
				{
					View window = new View();
					a1=window;
					window.getFrame().setVisible(true);
					
					
				}
			});
		
	
		}
		
		
		public void save()
		{
			int i=0;
			if(a.table.getTour().equals(a.j1))
			{
				jou1[0]=5;
				if(a.j2.isIaJoueur())
				jou1[1]=a.j2.getNiveau();
				else
				jou1[1]=-1;
			}
			else
			{
				if(a.j2.isIaJoueur())
				{
					jou1[1]=a.j2.getNiveau();
				}
				else
				{
					jou1[1]=5;
				}
				jou1[0]=-1;
				
			}
			
			for(Boule [] c : a.table.BouleJoueur1())
			{
				for(Boule d : c)
				{
					j1[i][0]=d.getIdBoule();
					j1[i][1]=d.getTrou().getIdTrou();
					i++;
				}
			}
			i=0;
			for(Boule [] c : a.table.bouleJoueur2())
			{
				for(Boule d : c)
				{
					j2[i][0]=d.getIdBoule();
					j2[i][1]=d.getTrou().getIdTrou();
					i++;
				}
			}
			write_Fichier(j1,j2,jou1,"fich.txt");
		}
		
		public void load()
		{
			
			read_Fichier(j1, j2, jou1,"fich.txt");
			if(jou1[1]==-1 && jou1[0]==5)		//ici ls 2 joueur st humain et ce le tour du joueur1
			{
				a.table.setTour(a.j1);
				
			}
			else if(jou1[1]==5 && jou1[0]==-1)		//ici ls 2 joueur st humain et ce le tour du joueur2
			{
				a.table.setTour(a.j2);
			}
			else if(jou1[0]==5 && jou1[1]!=-1)		//ici ce le tour du joueur 1 et le joueur2 est la machine
			{
				a.table.setTour(a.j1);
				a.j2.setIaJoueur(true);
				level(jou1[1]);
			}
			else if(jou1[1]!=-1 && jou1[0]==-1)			//ici ce le tour du joueur2 ki e machine
			{
				a.table.setTour(a.j2);
				a.j2.setIaJoueur(true);
				level(jou1[1]);
				iAgame();		
			}
			else
				System.out.println("\nprobleme de chargement");
		
			
			for(Boule [] v:a.table.BouleJoueur1())
			{
				for(Boule d : v)
				{
					for(int i1=0; i1<14; i1++)
							   if(d.getIdBoule()==j1[i1][0])
							   {
								   d.setTrou(a.table.getTrouByID(j1[i1][1]));
							   }
				}
			}
			
			for(Boule [] v:a.table.bouleJoueur2())
			{
				for(Boule d : v)
				{
					for(int i1=0; i1<14; i1++)
							   if(d.getIdBoule()==j2[i1][0])
							   {
								   d.setTrou(a.table.getTrouByID(j2[i1][1]));
							   }
				}
			}
			
		}
		
		
		
		public void write_Fichier(int [][] joueur_1, int [][] joueur_2, int [] j_1, String nomFichier){
			File f= null;  //Pour la premiere ecriture si le fichier n'existe pas cette ligne permet de le creer sinon elle ecrit
			                                   //directement dans le fichier
			 
			try
			{
				f= new File(nomFichier);
			    PrintWriter ecrire = new PrintWriter (new BufferedWriter (new FileWriter (f)));
			        ecrire.println(j_1[0]);
			    for(int i=0; i<joueur_1.length; i++)
			    {
			        ecrire.println (joueur_1[i][0]);
			        ecrire.println (joueur_1[i][1]);
			    }
			        ecrire.println(j_1[1]);
			   for(int i=0; i<joueur_2.length; i++)
				{
					   ecrire.println(joueur_2[i][0]);
					   ecrire.println(joueur_2[i][1]);
				}
			    ecrire.close();
			}
			catch (IOException  exception)
			{
			    System.out.println ("Erreur lors de la lecture : " + exception.getMessage());
			}
		}
		
		public void read_Fichier(int [][] joueur_1, int [][] joueur_2, int [] j_1, String nomFichier) {
			//lecture du fichier texte	
			InputStream fich=null;
			try{
				fich=new FileInputStream(nomFichier); 
				InputStreamReader fichiLu=new InputStreamReader(fich);
				BufferedReader br=new BufferedReader(fichiLu);
				
				j_1[0]=Integer.parseInt(br.readLine());
				 
				for(int i1=0; i1<joueur_1.length; i1++)
				{
					joueur_1[i1][0] = Integer.parseInt(br.readLine());
					joueur_1[i1][1]=Integer.parseInt(br.readLine());
				}
				
				 j_1[1]=Integer.parseInt(br.readLine());
				
				for(int i1=0; i1<joueur_2.length; i1++)
				{
					joueur_2[i1][0] = Integer.parseInt(br.readLine());
					joueur_2[i1][1]=Integer.parseInt(br.readLine());
				}
				br.close(); 
			}	
			    catch (IOException exception)
			    {
			        System.out.println ("Erreur lors de la lecture : " + exception.getMessage());
			    }

		}
}
