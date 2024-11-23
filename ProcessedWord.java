// this class is to store processed words after they have been manipulated

public class ProcessedWord { //used to be called Vocabulary
    public String content; // var to store the processed word // used to be called word

        public ProcessedWord() { // default constructor initializing the word to an empty string
        content = "";
    }

    public ProcessedWord(String content) {     // parameterized constructor to initialize the word // used to be called Vocabulary
        this.content = content; //// used to be called word
    }

    @Override //overriding the toString method to return the word
    public String toString() { // the toString() method ensures the word can be easily converted into a string format
        return content;
    }
}