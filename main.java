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
	        int taille = 5; // Taille de la grille (exemple : 5x5)
	        grid grille = new grid(taille, taille);
	        
	        // Nombre de mots à positionner
	        int numberOfWords = 5;

	        // Mots à positionner dans la grille (choisis aléatoirement à partir du dictionnaire)
	        String[] mots = new String[numberOfWords];
	        for (int i = 0; i < numberOfWords; i++) {
	            mots[i] = dict.getRandomWord().toUpperCase();; // Obtenir un mot aléatoire
	        }

	        // Afficher les mots choisis
	        System.out.println("Mots choisis pour la grille :");
	        for (String mot : mots) {
	            System.out.println(mot);
	        }

	        // Tentative de placement des mots dans la grille
	        for (String mot : mots) {
	            if (grille.positionnerMot(mot)) {
	                System.out.println("Le mot \"" + mot + "\" a été placé !");
	            } else {
	                System.out.println("Échec du positionnement du mot : " + mot);
	            }
	        }

	        // Remplir les cases restantes avec des lettres aléatoires
	        grille.remplirLettresAleatoires();

	        // Affichage de la grille après l'ajout des mots et des lettres aléatoires
	        grille.afficherGrille();
	 	   
	 	   
		     // Créer le jeu
	        // Création et exécution du jeu
            jeu jeu = new jeu(grille, dict);

            jeu.setPositions(0, 0, taille - 1, taille - 1);

            // Afficher les positions de départ et de fin
            System.out.println("Position de départ : (0,0)");
            System.out.println("Position de fin : (" + (taille - 1) + "," + (taille - 1) + ")");

            // Permettre au joueur d'entrer son chemin
            Scanner scanner = new Scanner(System.in);
            System.out.println("Entrez votre chemin (suite de lettres, ex: ABCDE) :");
            String chemin = scanner.nextLine();
            System.out.println("Votre chemin est : " + chemin);
            
            jeu.displayGame();
            // Vérifier le score basé sur le chemin
            int score = jeu.calculerScore(chemin);

            // Afficher le score et les résultats
            System.out.println("Votre score est : " + score);
            try {
                // Vérifier si le joueur a gagné
            	  if (jeu.isCheminValide(0, 0, taille - 1, taille - 1)) {
                      System.out.println("Bravo, vous avez respecté les contraintes !");
                  } else {
                      System.out.println("Votre chemin ne respecte pas les contraintes de départ et d'arrivée.");
            } }catch (Exception e) {
                System.err.println("Une erreur est survenue lors de la vérification du jeu : " + e.getMessage());
                e.printStackTrace();
            }

   }
}

