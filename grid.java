import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class grid {
	   private char[][] grille;
	    private List<String> motsPositionnes;
	    private boolean[][] cellsUsed;

	    public grid(int lignes, int colonnes) {
	        this.grille = new char[lignes][colonnes];
	        this.motsPositionnes = new ArrayList<>();
	        this.cellsUsed = new boolean[lignes][colonnes];

		    }


		    // Initialiser la grille avec des espaces vides
	    public void initialiserGrilleAvecMurs() {
	        for (int i = 0; i < grille.length; i++) {
	            for (int j = 0; j < grille[0].length; j++) {
	                if (Math.random() < 0.3 && grille[i][j] == '\0' ) {
	                    grille[i][j] = '#'; // Placer un mur
	                }
	            }

	        }
	        // S'assurer que la case de départ et celle de fin ne sont pas des murs
	        grille[0][0] = '\0';
	        grille[grille.length - 1][grille.length - 1] = '\0';
	        afficherGrille();
	    }
		    
	    public boolean positionnerMot(String mot) {
	        Random rand = new Random();
	        int maxAttempts = 100; // Nombre maximum d'essais pour placer un mot
	        for (int attempt = 0; attempt < maxAttempts; attempt++) {
	            int startX = rand.nextInt(grille.length);
	            int startY = rand.nextInt(grille[0].length);


	            // Vérifier si le mot peut être placé dans la grille avec une direction aléatoire pour chaque lettre
	            for (int i = 0; i < mot.length(); i++) {
	                // Choisir une direction aléatoire pour chaque lettre
	                int direction = rand.nextInt(8);  // Choisir une direction parmi les 8 possibles
	                int dx = 0, dy = 0;

	                // Calculer les changements dx et dy en fonction de la direction choisie
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

	                // Vérifier si le mot peut être placé à cette position avec la direction choisie
	                if (peutPlacerMot(mot, startX, startY, dx, dy)) {
	                    // Si le mot peut être placé, le mettre dans la grille lettre par lettre
	                    for (int j = 0; j < mot.length(); j++) {
	                        int x = startX + j * dx;
	                        int y = startY + j * dy;
	                        cellsUsed[x][y] = true;
	                        grille[x][y] = mot.charAt(j);  // Placer la lettre dans la grille
	                    }
	                    motsPositionnes.add(mot);  // Ajouter le mot à la liste des mots placés
	                    return true;
	                }
	            }
	        }
	        return false; // Impossible de placer le mot après plusieurs tentatives
	    }
	    
	    
	    private boolean peutPlacerMot(String mot, int startX, int startY, int dx, int dy) {
	        int xFin = startX + (mot.length() - 1) * dx;
	        int yFin = startY + (mot.length() - 1) * dy;

	        // Vérifier si les coordonnées finales sont valides
	        if (xFin < 0 || xFin >= grille.length || yFin < 0 || yFin >= grille[0].length) {
	            return false; // Le mot dépasse les bords de la grille
	        }

	        // Vérifier si chaque case nécessaire est libre
	        for (int i = 0; i < mot.length(); i++) {
	            int x = startX + i * dx;
	            int y = startY + i * dy;
	            if (grille[x][y] != '\0' && grille[x][y] != mot.charAt(i)) {
	                return false; // La case est déjà occupée ou contient un mur
	            }
	        }
	        return true;
	    }

	    public void remplirGrilleComplete() {
	        Random rand = new Random();
	        for (int i = 0; i < grille.length; i++) {
	            for (int j = 0; j < grille[i].length; j++) {
	                if (grille[i][j] == '\0') {
	                    grille[i][j] = (char) ('A' + rand.nextInt(26));
	                }
	            }
	        }
	    }
	    // Afficher la grille pour visualisation
	    public void afficherGrille() {
	        String color = "\u001B[32m";  // Couleur verte pour les lettres des mots
	        String reset = "\u001B[0m";   // Réinitialisation de la couleur

	        for (int i = 0; i < grille.length; i++) {
	            for (int j = 0; j < grille[i].length; j++) {
	            	  char letter = grille[i][j];
	                  String letterStr = String.valueOf(letter);

	                  // Supprimer les accents de la lettre
	                  String cleanLetter = removeAccents(letterStr);

	                  if (cellsUsed[i][j]) {
	                      System.out.print(color + cleanLetter + reset + " "); // Lettre colorée
	                  } else {
	                      System.out.print(cleanLetter + " "); // Lettre normale
	                  }
	            }
	            System.out.println();
	        }
	    }
	    
	    
	    // Méthode pour enlever les accents d'une chaîne de caractères
	    private String removeAccents(String str) {
	        // Normaliser le texte pour décomposer les caractères accentués
	        String normalized = Normalizer.normalize(str, Normalizer.Form.NFD);
	        // Supprimer les caractères accentués
	        return normalized.replaceAll("[^\\p{ASCII}]", "");
	    }
  
  public boolean estMur(int x, int y) {
      return grille[x][y] == '-';
  }

  public List<String> getMotsPositionnes() {
      return motsPositionnes;
  }

  public char[][] getGrille() {
      return grille;
  }

  public char getLetterAt(int x, int y) {
      return grille[x][y];
  }

  public int getTaille() {
      return grille.length;
  }
}
