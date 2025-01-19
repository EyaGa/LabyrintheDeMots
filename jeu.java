import java.util.*;

public class jeu {
    private grid grille;
    private dict dictionnaire;
    private int score;
    private int startX, startY; // Point de départ
    private int endX, endY; // Point d'arrivée
    private List<int[]> path; // Chemin parcouru (liste de coordonnées)
    private boolean[][] visited; // Cases déjà parcourues

    // Constructeur
    public jeu(grid grille, dict dictionnaire) {
        this.grille = grille;
        this.dictionnaire = dictionnaire;
        this.score = 0;
        this.path = new ArrayList<>();
        this.visited = new boolean[grille.getGrille().length][grille.getGrille()[0].length];

        // Initialisation des points de départ et d'arrivée
        this.startX = 0; // Exemple : début dans un coin
        this.startY = 0;
        this.endX = grille.getGrille().length - 1; // Exemple : fin dans un autre coin
        this.endY = grille.getGrille()[0].length - 1;

        // Ajouter la case de départ au chemin
        path.add(new int[]{startX, startY});
        visited[startX][startY] = true;
    }

    public void setPositions(int startX, int startY, int endX, int endY) {
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
    }

    // Vérifier si le chemin respecte les contraintes de départ et d'arrivée
    public boolean isCheminValide(int startX, int startY, int endX, int endY) {
        return this.startX == startX && this.startY == startY && this.endX == endX && this.endY == endY;
    }

    // Calculer le score basé sur le chemin donné
    public int calculerScore(String chemin) {
        // Vérifier si le chemin commence à (0, 0) et finit à (taille - 1, taille - 1)
        if (chemin == null || chemin.isEmpty()) {
            return 0;  // Si le chemin est vide, on retourne un score de 0
        }

        // Vérifier si le chemin respecte les positions de départ et d'arrivée
        if (chemin.charAt(0) != grille.getLetterAt(0, 0) || chemin.charAt(chemin.length() - 1) != grille.getLetterAt(endX, endY)) {
            System.out.println("Le chemin ne commence pas à (0,0) ou ne finit pas à (" + (grille.getTaille() - 1) + "," + (grille.getTaille() - 1) + ").");
            return 0;  // Si le chemin ne respecte pas les positions de départ et de fin, retournez un score de 0
        }

        Set<String> motsTrouves = new HashSet<>();
        int score = 0;

        // Parcourir toutes les sous-chaînes possibles du chemin
        for (int i = 0; i < chemin.length(); i++) {
            for (int j = i + 1; j <= chemin.length(); j++) {
                String sousChaine = chemin.substring(i, j);
                if (dictionnaire.isValidWord(sousChaine) && !motsTrouves.contains(sousChaine)) {
                    motsTrouves.add(sousChaine);
                    score++;
                }
            }
        }

        System.out.println("Mots trouvés dans votre chemin : " + motsTrouves);
        return score;
    }

    // Afficher l'état actuel du jeu
    public void displayGame() {
        System.out.println("Grille actuelle :");
        grille.afficherGrille();
        System.out.println("Position de départ : (" + startX + "," + startY + ")");
        System.out.println("Position de fin : (" + endX + "," + endY + ")");    }


}

