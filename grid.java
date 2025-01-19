import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class grid {
	   private char[][] grille;
	    private List<String> motsPositionnes;

	    // Constructeur de la grille
	    public grid(int lignes, int colonnes) {
	        this.grille = new char[lignes][colonnes];
	        this.motsPositionnes = new ArrayList<>();
	        initialiserGrille();
	    }

	    
	    
	    // Initialiser la grille avec des espaces vides
	    private void initialiserGrille() {
	        for (int i = 0; i < grille.length; i++) {
	            for (int j = 0; j < grille[i].length; j++) {
	                grille[i][j] = '\0';  // Caractère nul pour les cases vides
	            }
	        }
	    }
	    
	    
	    
	    // Méthode pour positionner un mot dans la grille
	    public boolean positionnerMot(String mot) {
	        Random rand = new Random();
	        int maxAttempts = 100;  // Nombre maximum d'essais pour un mot
	        for (int attempt = 0; attempt < maxAttempts; attempt++) {
	            int direction = rand.nextInt(8);  // Choisir une direction parmi les 8 directions possibles
	            int startX = rand.nextInt(grille.length);
	            int startY = rand.nextInt(grille[0].length);

	            // Calculer les coordonnées finales en fonction de la direction
	            int dx = 0, dy = 0;
	            switch (direction) {
	                case 0: dx = 1; dy = 0; break;  // Droite
	                case 1: dx = -1; dy = 0; break; // Gauche
	                case 2: dx = 0; dy = 1; break;  // Bas
	                case 3: dx = 0; dy = -1; break; // Haut
	                case 4: dx = 1; dy = 1; break;  // Bas-Droite
	                case 5: dx = -1; dy = 1; break; // Bas-Gauche
	                case 6: dx = 1; dy = -1; break; // Haut-Droite
	                case 7: dx = -1; dy = -1; break; // Haut-Gauche
	            }

	            // Vérifier si le mot peut être placé dans la grille à la position donnée
	            if (peutPlacerMot(mot, startX, startY, dx, dy)) {
	                // Si l'espace est disponible, place le mot
	                for (int i = 0; i < mot.length(); i++) {
	                    grille[startX + i * dx][startY + i * dy] = mot.charAt(i);
	                }
	                motsPositionnes.add(mot);  // Ajouter le mot à la liste des mots positionnés
	                return true;  // Le mot a été placé
	            }
	        }
	        return false;  // Impossible de placer le mot après plusieurs tentatives
	    }
	    
	    

	    // Vérifier si le mot peut être placé dans la grille à une position donnée
	    private boolean peutPlacerMot(String mot, int startX, int startY, int dx, int dy) {
	        int xFin = startX + (mot.length() - 1) * dx;
	        int yFin = startY + (mot.length() - 1) * dy;

	        // Vérifier si la dernière position du mot est à l'intérieur de la grille
	        if (xFin < 0 || xFin >= grille.length || yFin < 0 || yFin >= grille[0].length) {
	            return false;  // Le mot dépasse les bords de la grille
	        }

	        // Vérifier que les cases où le mot doit être placé sont vides
	        for (int i = 0; i < mot.length(); i++) {
	            int x = startX + i * dx;
	            int y = startY + i * dy;

	            // Vérifier que la case est vide avant d'y placer le mot
	            if (grille[x][y] != '\0') {
	                return false;  // La case est déjà occupée
	            }
	        }
	        return true;  // Le mot peut être placé
	    }
	    
	    
	    // Remplir les cases restantes avec des lettres aléatoires
	    public void remplirLettresAleatoires() {
	        Random rand = new Random();
	        for (int i = 0; i < grille.length; i++) {
	            for (int j = 0; j < grille[i].length; j++) {
	                if (grille[i][j] == '\0') {  // Si la case est vide
	                    grille[i][j] = (char) ('A' + rand.nextInt(26)); // Remplir avec une lettre aléatoire
	                }
	            }
	        }
	    }

	    // Afficher la grille pour visualisation
	    public void afficherGrille() {
	        for (int i = 0; i < grille.length; i++) {
	            for (int j = 0; j < grille[i].length; j++) {
	                System.out.print(grille[i][j] + " ");
	            }
	            System.out.println();
	        }
	    }

	    public List<String> getMotsPositionnes() {
	        return motsPositionnes;
	    }

	    public char[][] getGrille() {
	        return grille;
	    }
	    // Retourner la lettre à la position (x, y)
	    public char getLetterAt(int x, int y) {
	        return grille[x][y];  // Retourne le caractère à la position donnée
	    }

	    // Retourner la taille de la grille
	    public int getTaille() {
	        return grille.length;  // Retourne le nombre de lignes (ou colonnes, car la grille est carrée)
	    }

}
