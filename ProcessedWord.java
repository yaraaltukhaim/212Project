// this class is to store processed words after they have been manipulated

public class ProcessedWord { 
    public String content; // var to store the processed word 

        public ProcessedWord() { // default constructor initializing the word to an empty string
        content = "";
    }

    public ProcessedWord(String content) {     // parameterized constructor to initialize the word 
        this.content = content;
    }

    
    public String toString() { // the toString() method ensures the word can be easily converted into a string format
        return content;
    }
}