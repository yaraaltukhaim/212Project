// Class to track a term and its frequency in multiple documents
public class TermRank {
    ProcessedWord termWord; // Represents the term
    int[] documentFrequencies; // Tracks frequency of the term in each document // used to be docIDS

    // Default constructor initializes the frequency array and term
    public TermRank() {
        documentFrequencies = new int[50]; // Assume a max of 50 documents
        for (int i = 0; i < documentFrequencies.length; i++) {
            documentFrequencies[i] = 0; // Initialize all frequencies to 0
        }
        termWord = new ProcessedWord(""); // Empty term by default
    }

    // Parameterized constructor to initialize the term and document frequencies
    public TermRank(String word, int[] documentFrequencies) {
        this.termWord = new ProcessedWord(word); // Initialize term
        this.documentFrequencies = new int[documentFrequencies.length];
        // Copy document frequencies
        for (int i = 0; i < documentFrequencies.length; i++) {
            this.documentFrequencies[i] = documentFrequencies[i];
        }
    }

    // Increment the frequency of the term for a specific document
    public void markDocumentPresence(int docID) { //used to be add_docID //incrementFrequencyForDocument
        this.documentFrequencies[docID]++;
    }

    // Setter 
    public void setTermWord(ProcessedWord termWord) { //setVocabulary
        this.termWord = termWord;
    }

    // Getter 
    public ProcessedWord getTermWord() { //getVocabulary
        return termWord;
    }

    // Returns a copy of the document frequency array
    public int[] getDocumentMapping() { //getDocs //getDocumentFrequencies
        int[] copy = new int[documentFrequencies.length];
        // Copy values into the new array
        for (int i = 0; i < copy.length; i++) {
            copy[i] = documentFrequencies[i];
        }
        return copy;
    }

    // String representation of the term and its document frequencies
    @Override
    public String toString() {
        String docs = "";
        for (int i = 0, count = 0; i < documentFrequencies.length; i++) {
            if (documentFrequencies[i] != 0) {
                if (count == 0) {
                    docs += " " + i; // First document ID without a comma
                } else {
                    docs += ", " + i; // Subsequent IDs with a comma
                }
                count++;
            }
        }
        return termWord + "[" + docs + "]"; // Example: "word[0, 2, 5]"
    }
}