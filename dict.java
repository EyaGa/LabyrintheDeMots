import java.io.*;
import java.text.Normalizer;
import java.util.*;
public class dict {
    private Set<String> words;
    private List<String> wordList;


	    // Constructeur qui charge les mots depuis un fichier
    public dict(String filePath) {
        this.words = new HashSet<>();
        loadWordsFromFile(filePath);
        this.wordList = new ArrayList<>(words);
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
	    
	    public String getRandomWord(int maxLength) {
	        Random rand = new Random();
	        int attempts = 0;
	        int maxAttempts = 100;

	        while (!wordList.isEmpty() && attempts < maxAttempts) {
	        	  int index = rand.nextInt(wordList.size());
	              String word = wordList.get(index);

	              if (word.length() <= maxLength) {
	                  // Retirer le mot de la liste pour éviter les répétitions
	                  wordList.remove(index);
	                  return word;
	              }

	              attempts++;
	          }
	        return "mot";
	    }

	    public Map<String, Integer> getStats() {
	        Map<String, Integer> stats = new HashMap<>();
	        stats.put("totalWords", words.size());
	        
	        int shortWords = 0;
	        int mediumWords = 0;
	        int longWords = 0;
	        
	        for (String word : words) {
	            int length = word.length();
	            if (length <= 4) shortWords++;
	            else if (length <= 7) mediumWords++;
	            else longWords++;
	        }
	        
	        stats.put("shortWords", shortWords);
	        stats.put("mediumWords", mediumWords);
	        stats.put("longWords", longWords);
	        
	        return stats;
	    }

}


