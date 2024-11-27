import java.util.Scanner;

public class Main {
       
    public static Scanner input = new Scanner(System.in);
    public static SearchEngine SE = new SearchEngine();

    // Menu display and user choice
    public static int menu() {
        System.out.println("1. Retrieve a Term.");
        System.out.println("2. Boolean Retrieval.");
        System.out.println("3. Ranked Retrieval.");
        System.out.println("4. Indexed Documents.");
        System.out.println("5. Indexed Tokens.");
        System.out.println("6. Exit.");

        System.out.print("Enter choice: ");
        int choice = input.nextInt();
        input.nextLine(); // consume the leftover newline character
        return choice;
    }

    // Print Boolean results
    public static void printBoolean(boolean[] result) {
        Term t = new Term("", result);
        System.out.println(t);
    }

    // Retrieve Term from different indexing methods
    public static void Retrieval_Term() {
        int choice1;
        System.out.println("################### Retrieval Term ####################");

        System.out.println("1. Index with Lists");
        System.out.println("2. Inverted Index with Lists");
        System.out.println("3. Inverted Index with BST");
        System.out.println("4. Inverted Index with AVL");
        System.out.print("Enter your choice: ");
        choice1 = input.nextInt();
        input.nextLine(); // consume the leftover newline character

        System.out.print("Enter Term: ");
        String str = "";
        str = input.nextLine();

        System.out.print("Result doc IDs: ");
        printBoolean(SE.TermRetrieval(str.trim().toLowerCase(), choice1));
        System.out.println("\n");
    }

    // Perform Boolean retrieval with AND/OR operations
    public static void BooleanRetrieval_menu() {
        System.out.println("################### Boolean Retrieval ####################");
        System.out.println("1. Index with Lists");
        System.out.println("2. Inverted Index with Lists");
        System.out.println("3. Inverted Index with BST");
        System.out.println("4. Inverted Index with AVL");
        System.out.print("Enter your choice: ");
        int choice1 = input.nextInt();
        //input.nextLine(); // consume the leftover newline character

        System.out.print("Enter boolean term (AND / OR): ");
        String str = input.nextLine();
        str = input.nextLine();

        System.out.print("Q#: ");
        System.out.println(str);

        System.out.print("Result doc IDs: ");
        printBoolean(SE.BooleanRetrieval(str.trim().toUpperCase(), choice1));
        System.out.println("\n");
    }

    // Ranked Retrieval method
    public static void RankedRetrieval_menu() {
        System.out.println("######## Ranked Retrieval ######## ");
        System.out.println("1. Index with Lists");
        System.out.println("2. Inverted Index with Lists");
        System.out.println("3. Inverted Index with BST");
        System.out.println("4. Inverted Index with AVL");
        System.out.print("Enter your choice: ");
        int choice1 = input.nextInt();
       // input.nextLine(); // consume the leftover newline character

        System.out.print("Enter term: ");
        String str = input.nextLine();
        str = input.nextLine();

        System.out.println("## Q: " + str);
        System.out.println("DocID\tScore");
        switch (choice1) {
            case 1:
                System.out.println("Get ranked from index list");
                SE.IndexRanked(str);
                break;
            case 2:
                System.out.println("Get ranked from inverted index list");
                SE.RetrievalInvertedIndexRanked(str);
                break;
            case 3:
                System.out.println("Get ranked from BST");
                SE.RetrievalBSTRanked(str);
                break;
            case 4:
                System.out.println("Get ranked from AVL");
                SE.RetrievalAVLRanked(str);
                break;
        }
        System.out.println("\n");
    }

    // Display Indexed Documents (showing document count)
    public static void IndexedDocuments_menu() {
        System.out.println("######## Indexed Documents ######## ");
        System.out.println("Indexed Documents:");
        SE.DocumentsIndexed();
        System.out.println("");
    }

    // Display Indexed Tokens (showing tokens and their document frequency)
    public static void IndexedTokens_menu() {
        System.out.println("######## Indexed Tokens ######## ");
        System.out.println("Tokens:");
        SE.TokensIndexed();
        System.out.println("");
    }

    // Main driver function
    public static void main(String[] args) {
        // Modify paths to point to your actual data
       // SE.ReadData("C:\\Users\\shahad\\OneDrive\\Desktop\\5 stuff\\212\\project1\\data\\stop.txt", "C:\\Users\\shahad\\OneDrive\\Desktop\\5 stuff\\212\\project1\\data\\dataset.csv");
                 SE.ReadData("/Users/yara/Downloads/data/stop.txt", "/Users/yara/Downloads/data/dataset.csv");

        int choice;
        do {
            choice = menu();
            switch (choice) {
                case 1: // Retrieve Term
                    Retrieval_Term();
                    break;
                case 2: // Boolean Retrieval
                    BooleanRetrieval_menu();
                    break;
                case 3: // Ranked Retrieval
                    RankedRetrieval_menu();
                    break;
                case 4: // Indexed Documents
                    IndexedDocuments_menu();
                    break;
                case 5: // Indexed Tokens
                    IndexedTokens_menu();
                    break;
                case 6: // Exit
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Wrong choice, try again!");
            }
        } while (choice != 6);
    }
}
