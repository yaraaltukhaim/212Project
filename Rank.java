// class to track the frequency of a term in a document

public class Rank {
    ProcessedWord termWord; 
    int frequency; // tracks or counts the number of occurrences of the term in the document

    // Default constructor initializes frequency to 0 and term to an empty word
    public Rank() {
        frequency = 0;
        termWord = new ProcessedWord("");
    }

    // Parameterized constructor to initialize term and frequency
    public Rank(String word, int frequency) {
        this.termWord = new ProcessedWord(word); // Initialize term
        this.frequency = frequency; // Set the frequency
    }

    // Increment the frequency of the term and return the updated value
    public int incrementFrequency() {
        return ++frequency;
    }

    // Setter 
    public void setTermWord(ProcessedWord termWord) {
        this.termWord = termWord;
    }

    // Getter 
    public ProcessedWord getTermWord() {
        return termWord;
    }

    // Getter 
    public int getFrequency() {
        return this.frequency;
    }

    // String representation of the term and its frequency
    @Override
    public String toString() {
        return "(" + termWord + ", " + frequency + ")"; // Example: "(word, 5)"
    }
}