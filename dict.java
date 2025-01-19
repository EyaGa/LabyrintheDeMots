import java.io.*;
import java.text.Normalizer;
import java.util.*;
public class dict {
	  private Set<String> words; // Stocke les mots du dictionnaire

	    // Constructeur qui charge les mots depuis un fichier
	    public dict(String filePath) {
	        words = new HashSet<>();
	        loadWordsFromFile(filePath);
	    }

	    // Méthode pour charger les mots depuis un fichier texte
	    private void loadWordsFromFile(String filePath) {
	        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
	            String line;
	            while ((line = br.readLine()) != null) {
	                String word = line.trim().toLowerCase();
	                words.add(word);	            }
	            } catch (IOException e) {
	            System.err.println("Erreur lors du chargement du dictionnaire : " + e.getMessage());
	        }
	    }

	    // Vérifie si un mot est valide
	    public boolean isValidWord(String word) {
	        String lowcaseWord = word.trim().toLowerCase();
	        return words.contains(lowcaseWord);
	    }

	    // Affiche les mots du dictionnaire (optionnel)
	    public void printWords() {
	        words.forEach(System.out::println);
	    }
	    
	    public String getRandomWord() {
	        List<String> wordList = new ArrayList<>(words);
	        Random rand = new Random();
	        // Sélectionner un mot aléatoire
	        String randomWord = wordList.get(rand.nextInt(wordList.size()));
	        // Enlever les accents
	        return removeAccents(randomWord);	    }

	    
	    
	    // Méthode pour enlever les accents d'une chaîne de caractères
	    private String removeAccents(String str) {
	        // Normaliser le texte pour décomposer les caractères accentués
	        String normalized = Normalizer.normalize(str, Normalizer.Form.NFD);
	        // Supprimer les caractères accentués
	        return normalized.replaceAll("[^\\p{ASCII}]", "");
	    }


}


