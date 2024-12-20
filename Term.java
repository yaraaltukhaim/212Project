// Class to represent a word from the vocabulary and a mapping of whether this word appears in specific documents


public class Term {
    ProcessedWord termWord; // Represents the word or term 
    boolean[] documentPresence; // array to track presence of the term in documents 

    // Default constructor initializes the boolean array and vocabulary
    public Term() {
        documentPresence = new boolean[50]; // Assume a max of 50 documents
        for (int i = 0; i < documentPresence.length; i++) {
            documentPresence[i] = false; // Initialize all to false
        }
        termWord = new ProcessedWord(""); // Empty vocabulary by default
    }

    // Parameterized constructor to initialize the term and document mapping
    public Term(String word, boolean[] documentPresence) {
        this.termWord = new ProcessedWord(word); // Initialize term
        this.documentPresence = new boolean[documentPresence.length];
        // Copy document mapping
        for (int i = 0; i < documentPresence.length; i++) {
            this.documentPresence[i] = documentPresence[i];
        }
    }

    // Mark the term as present in a specific document by ID //takes index changes it to true if found
    public boolean markDocumentPresence(int docID) {  
    
        if (!documentPresence[docID]) {
            this.documentPresence[docID] = true;
            
            return true; // Successfully updated
        }
        return false; // Already marked as true
    }

    // Setter 
        public void setTermWord(ProcessedWord termWord) { //used to be public void setVocabulary(Vocabulary word)
        this.termWord = termWord;
    }

    // Getter 
    public ProcessedWord getTermWord() { //used to be public Vocabulary getVocabulary()
        return termWord;
    }

    // Returns a copy of the document presence array
    public boolean[] getDocumentMapping() { 
        boolean[] copy = new boolean[documentPresence.length];
        // Copy values into the new array
        for (int i = 0; i < copy.length; i++) {
            copy[i] = documentPresence[i];
        }
        return copy;
    }

    // String representation of the term and associated documents
   
    public String toString() {
        String docs = "";
        for (int i = 0, count = 0; i < documentPresence.length; i++) 
            if (count == 0 && documentPresence[i]== true) {
               
                    docs += " " +String.valueOf(i); 
                    count++;// firt document ID without a comma
                } else 
                if (documentPresence[i]== true){
                    docs += ", " +String.valueOf(i); 
                    count++;// rest of IDs with a comma
                }
               
            
        
        return termWord + "[" + docs + "]"; // Example: "word[1, 2, 3]"
    }
}