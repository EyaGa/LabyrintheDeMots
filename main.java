import java.util.Scanner;

public class main {
	   public static void main(String[] args) {
		// Chemin vers le fichier dictionnaire
	        String filePath = "resources/dict";
	        // Chargement du dictionnaire
	        dict dict = new dict(filePath);
	        // Affiche les mots chargés
	        /*System.out.println("Mots du dictionnaire :");
	        dict.printWords();
	        // Vérification de quelques mots
	        System.out.println("Le mot 'paix' est valide ? " + dict.isValidWord("paix"));
	        System.out.println("Le mot 'voiture' est valide ? " + dict.isValidWord("voiture"));
	        System.out.println("Le mot 'chien' est valide ? " + dict.isValidWord("chien"));
	        System.out.println("Le mot 'vérité' est valide ? " + dict.isValidWord("vérité"));
	    	  */

		         Map<String, Integer> stats = dict.getStats();
            System.out.println("Statistiques du dictionnaire :");
            System.out.println("Total des mots : " + stats.get("totalWords"));
            System.out.println("Mots courts : " + stats.get("shortWords"));
            System.out.println("Mots moyens : " + stats.get("mediumWords"));
            System.out.println("Mots longs : " + stats.get("longWords"));

            // Configuration de la grille
            Scanner scanner = new Scanner(System.in);
            System.out.println("\nEntrez la taille de la grille (minimum 5) :");
            int taille = Math.max(5, scanner.nextInt());
            
    grid grille = new grid(taille, taille);
            grille.initialiserGrilleAvecMurs();
            // Configuration des mots
            System.out.println("Combien de mots voulez-vous placer ? (maximum " + taille + ") :");
            int nombreMots = Math.min(scanner.nextInt(), taille);
            
            // Sélection et placement des mots
            String[] mots = new String[nombreMots];
            int motsPlaces = 0;
            for (int i = 0; i < nombreMots; i++) {
                String mot = dict.getRandomWord(taille - 1).toUpperCase();
                mots[i] = mot;
                if (grille.positionnerMot(mot)) {
                    System.out.println("Mot placé : " + mot);
                    motsPlaces++;
                } else {
                    System.out.println("Impossible de placer : " + mot);
                }
            }

            if (motsPlaces == 0) {
                System.out.println("Aucun mot n'a pu être placé. Le jeu ne peut pas continuer.");
                return;
            }
            
            // Remplir le reste avec des lettres aléatoires
            grille.remplirGrilleComplete();
            
            // Création du jeu
            jeu jeu = new jeu(grille, dict);
            grille.afficherGrille();

            // Boucle de jeu
            boolean continuer = true;
            try { while (continuer) {
                jeu.displayGame();
                
                // Sélection des points de départ et d'arrivée
                System.out.println("\nEntrez les coordonnées de départ (x y) comment ca x y :");
                int startX = scanner.nextInt();
                int startY = scanner.nextInt();
                
                System.out.println("Entrez les coordonnées d'arrivée (x y) comment ca x y :");
                int endX = scanner.nextInt();
                int endY = scanner.nextInt();
                
                try {
                    jeu.setPositions(startX, startY, endX, endY);
                    
                    scanner.nextLine(); // Vider le buffer
                    System.out.println("Entrez votre chemin (suite de lettres) :");
                    String chemin = scanner.nextLine().toUpperCase();
                    
                    int scoreRound = jeu.calculerScore(chemin);
                    System.out.println("Score pour ce tour : " + scoreRound);
                    
                    System.out.println("\nVoulez-vous continuer ? (O/N) :");
                    continuer = scanner.nextLine().toUpperCase().startsWith("O");
                    
                } catch (IllegalArgumentException e) {
                    System.out.println("Erreur : " + e.getMessage());
                }
            }
            
            // Afficher le résultat final
            System.out.println("\nFin du jeu !");
            System.out.println("Score final : " + jeu.getScoreTotal());
            System.out.println("Mots trouvés : " + jeu.getMotsTrouves());
               }catch (Exception e) {
            System.err.println("Une erreur est survenue : " + e.getMessage());
            e.printStackTrace();
        }
    }
}

