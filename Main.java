import java.util.Scanner;
public class Main {
       
     public static Scanner input = new Scanner (System.in);
     public static SearchEngine SE = new SearchEngine();
     
    
   
    
    public static int menu()
    {
        System.out.println("1. Boolean Retrieval. ");//docids
        System.out.println("2. Ranked Retrieval.");//docids and number of repetition in each doc
        System.out.println("3. Indexed Documents.");//number of documents
        System.out.println("4. Indexed Tokens.");//tokens
        System.out.println("5. Exist.");
        
        System.out.println("enter choice");
        int choice = input.nextInt();
        return choice;
    }

    public static void printBoolean(boolean [] result)
    {
        Term t = new Term ("", result);
        System.out.println(t);
    }

      public static void Retrieval_Term()
    {
        int choice1 ;
        System.out.println("################### Retrieval Term ####################");
        
        System.out.println("1. index");
        System.out.println("2. inverted index");
        System.out.println("3. inverted index using BST");
        System.out.println("4. inverted index using AVL");
        System.out.println("enter your choice");
        choice1 = input.nextInt();
        
        System.out.println("Enter Term :");
        String str = "";
        str = input.next();
        
        System.out.print("Result doc IDs: ");
        printBoolean(SE.TermRetrieval(str.trim().toLowerCase(), choice1 ));
        System.out.println("\n");

    }
    
        public static void BooleanRetrieval_menu()
    {
        System.out.println("################### Boolean Retrieval ####################");
        System.out.println("1. index");
        System.out.println("2. inverted index");
        System.out.println("3. inverted index using BST");
        System.out.println("4. inverted index using AVL");
        System.out.println("enter your choice");
        int choice1 = input.nextInt();

        System.out.println("Enter boolean term ( AND / OR) : ");
        String str = input.nextLine();
        str = input.nextLine();
            
        System.out.print("Q#: ");
        System.out.println(str);

        System.out.print("Result doc IDs: ");
        printBoolean(SE.BooleanRetrieval(str.trim().toUpperCase(), choice1 ));
        System.out.println("\n");
    
    }

     public static void RankedRetrieval_menu()
    {
        System.out.println("######## Ranked Retrieval ######## ");
        System.out.println("1. index");
        System.out.println("2. inverted index");
        System.out.println("3. inverted index using BST");
        System.out.println("4. inverted index using AVL");
        System.out.println("enter your choice");
        int choice1 = input.nextInt();

        System.out.print("enter term: ");
        String str = input.nextLine();
        str = input.nextLine();

        System.out.println("## Q: " + str);
        System.out.println("DocIDt\tScore");
        switch (choice1)
        {
            case 1:
                System.out.println("get ranked from index list");
                SE.IndexRanked(str);
                break;
            case 2:
                System.out.println("get ranked from inverted index list");
                SE.RetrievalInvertedIndexRanked(str);
                break;
            case 3:
                System.out.println("get ranked from BST");
                SE.RetrievalBSTRanked(str);
                break;
            case 4:
                System.out.println("get ranked from AVL");
                SE.RetrievalAVLRanked(str);
                break;
        }
        System.out.println("\n");
    }
    
    public static void IndexedDocuments_menu()
    {
        System.out.println("######## Indexed Documents ######## ");
        System.out.println("Indexed Documents " );
        SE.DocumentsIndexed();
        System.out.println("");
    }
    
     public static void IndexedTokens_menu()
    {
        System.out.println("######## Indexed Tokens ######## ");
        System.out.println("tokens " );
        SE.TokensIndexed();
        System.out.println("");
    }
    
    public static void main(String[] args) {

        SE.ReadData("C:\\Users\\ghaliah\\Downloads\\data\\stop.txt", "C:\\Users\\ghaliah\\Downloads\\data\\dataset.CSV");

        
        int choice;
        
        do {
                choice = menu();
                switch (choice)
                {
                    //term Retrieval
                    case 1:
                            Retrieval_Term();
                            break;

                    //docIDs
                    case 2:
                            BooleanRetrieval_menu();
                            break;
                            
                    //docids and number of repetition in each doc
                    case 3:
                            RankedRetrieval_menu();
                            break;
                    
                    //number of documents
                    case 4:
                            IndexedDocuments_menu();
                            break;
                    
                    //number of tokens 
                    case 5:
                            IndexedTokens_menu();
                            break;
                     
                    case 6://Exit
                            break;
                            
                    default:       
                            System.out.println("Wrong choice, try again!");
                }
        } while (choice != 6);
    }
    
}
