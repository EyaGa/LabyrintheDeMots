import java.util.*;

public class jeu {
    private grid grille;
    private dict dictionnaire;
    private int startX, startY;
    private int endX, endY;
    private List<String> motsTrouves;
    private int scoreTotal;
    private Map<String, int[]> cheminParcouru;
    private static final int[][] DIRECTIONS = {
        {1, 0}, {-1, 0}, {0, 1}, {0, -1},
        {1, 1}, {-1, 1}, {1, -1}, {-1, -1}
    };

    public jeu(grid grille, dict dictionnaire) {
        this.grille = grille;
        this.dictionnaire = dictionnaire;
        this.motsTrouves = new ArrayList<>();
        this.scoreTotal = 0;
        this.cheminParcouru = new HashMap<>();
    }

    public void setPositions(int startX, int startY, int endX, int endY) {
        if (isValidPosition(startX, startY) && isValidPosition(endX, endY)) {
            this.startX = startX;
            this.startY = startY;
            this.endX = endX;
            this.endY = endY;
        } else {
            throw new IllegalArgumentException("Position invalide : les coordonnées doivent être dans la grille");
        }
    }

    private boolean isValidPosition(int x, int y) {
        return x >= 0 && x < grille.getTaille() && y >= 0 && y < grille.getTaille();
    }

    public boolean isCheminValide(String chemin) {
        if (chemin == null || chemin.isEmpty()) {
            return false;
        }

        if (chemin.charAt(0) != grille.getLetterAt(startX, startY) ||
            chemin.charAt(chemin.length() - 1) != grille.getLetterAt(endX, endY)) {
            System.out.println("Le chemin ne commence pas ou ne finit pas aux bonnes positions");
            return false;
        }

        return verifierContinuiteChemin(chemin);
    }

    private boolean verifierContinuiteChemin(String chemin) {
        cheminParcouru.clear();
        int currentX = startX;
        int currentY = startY;
        cheminParcouru.put(chemin.substring(0, 1), new int[]{currentX, currentY});

        for (int i = 1; i < chemin.length(); i++) {
            boolean lettreConnectee = false;
            char lettreActuelle = chemin.charAt(i);

            for (int[] direction : DIRECTIONS) {
                int newX = currentX + direction[0];
                int newY = currentY + direction[1];

                if (isValidPosition(newX, newY) && 
                    !grille.estMur(newX, newY) &&  // Vérification des murs
                    grille.getLetterAt(newX, newY) == lettreActuelle &&
                    !estPositionDejaUtilisee(newX, newY)) {
                    
                    currentX = newX;
                    currentY = newY;
                    cheminParcouru.put(chemin.substring(0, i + 1), new int[]{currentX, currentY});
                    lettreConnectee = true;
                    break;
                }
            }

            if (!lettreConnectee) {
                System.out.println("Chemin discontinu ou bloqué par un mur à la position " + (i + 1));
                return false;
            }
        }

        return true;
    }

    private boolean estPositionDejaUtilisee(int x, int y) {
        for (int[] position : cheminParcouru.values()) {
            if (position[0] == x && position[1] == y) {
                return true;
            }
        }
        return false;
    }

    public int calculerScore(String chemin) {
        if (!isCheminValide(chemin)) {
            return 0;
        }

        Set<String> nouveauxMots = new HashSet<>();
        int scoreRound = 0;

        for (int i = 0; i < chemin.length(); i++) {
            for (int j = i + 2; j <= chemin.length(); j++) {
                String sousChaine = chemin.substring(i, j).toLowerCase();
                if (dictionnaire.isValidWord(sousChaine) && !motsTrouves.contains(sousChaine)) {
                    nouveauxMots.add(sousChaine);
                    int points = calculerPointsMot(sousChaine);
                    scoreRound += points;
                    motsTrouves.add(sousChaine);
                    System.out.println("Mot trouvé : " + sousChaine + " (+" + points + " points)");
                }
            }
        }

        if (!nouveauxMots.isEmpty()) {
            scoreTotal += scoreRound;
            System.out.println("\nNouveaux mots trouvés ce tour : " + nouveauxMots);
            System.out.println("Points gagnés ce tour : " + scoreRound);
            System.out.println("Score total : " + scoreTotal);
        } else {
            System.out.println("Aucun nouveau mot trouvé dans ce chemin");
        }

        return scoreRound;
    }

    private int calculerPointsMot(String mot) {
        int longueur = mot.length();
        int points = switch (longueur) {
            case 2 -> 2;
            case 3 -> 3;
            case 4 -> 5;
            case 5 -> 8;
            default -> 10 + (longueur - 5) * 3;
        };

        String lettresRares = "JKQWXYZ";
        for (char c : mot.toUpperCase().toCharArray()) {
            if (lettresRares.indexOf(c) != -1) {
                points += 2;
            }
        }

        return points;
    }

    public void displayGame() {
        System.out.println("\n=== État actuel du jeu ===");
        System.out.println("\nGrille :");
        grille.afficherGrille();
        
        System.out.println("\nPosition de départ : (" + startX + "," + startY + ")");
        System.out.println("Position d'arrivée : (" + endX + "," + endY + ")");
        System.out.println("\nScore total : " + scoreTotal);
        
        if (!motsTrouves.isEmpty()) {
            System.out.println("\nMots trouvés (" + motsTrouves.size() + ") :");
            motsTrouves.forEach(mot -> System.out.println("- " + mot));
        }
        
        System.out.println("\nLettre de départ : " + grille.getLetterAt(startX, startY));
        System.out.println("Lettre d'arrivée : " + grille.getLetterAt(endX, endY));
    }

    public List<String> getMotsTrouves() {
        return new ArrayList<>(motsTrouves);
    }

    public int getScoreTotal() {
        return scoreTotal;
    }

    public Map<String, int[]> getCheminParcouru() {
        return new HashMap<>(cheminParcouru);
    }
}
