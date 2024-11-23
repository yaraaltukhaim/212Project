// public class InvIndex {
// 
//             LinkedList <Term> Invindex; //mapping the terms to a list of documents containing those terms
// 
//             public InvIndex() {
//                Invindex = new LinkedList <Term>();
//             }//default constructor
//             
//             public int getSize() //size
//             {
//                 return Invindex.size();
//             }// Returns the size of the inverted index
// 
//             public boolean addTerm(int dID, String W) //addNew
//             {
//               Term newTerm = new Term ();
//                // If the index is empty, add the new term directly
//                 if (Invindex.empty()){ 
//                 newTerm.setTermWord(new ProcessedWord (W));
//                 newTerm.markDocumentPresence(dID);//changed yara
//                 Invindex.insert(newTerm);
//                 return true;
//                }else{//checks if the term already exists
//             
//                     Invindex.findFirst();
//                      do {
//                     
//                         if ( Invindex.retrieve().termWord.content.compareTo(W) == 0) {
//                        
//                             newTerm =  Invindex.retrieve();
//                             newTerm.markDocumentPresence(dID);//changed yara
//                             Invindex.update(newTerm);
//                             return false;// Term already exists, no new addition
//                         }
//                        Invindex.findNext();
//                     }while (!Invindex.last());
//                     
//                     // Handle the last term in the index
//                     if ( Invindex.retrieve().termWord.content.compareTo(W) == 0)
//                     {
//                         newTerm =  Invindex.retrieve();
//                         newTerm.markDocumentPresence(dID);
//                         Invindex.update(newTerm);
//                         return false;
//                     }
//                     else{// If the term doesn't exist, add it as new
//    
//                     newTerm.setTermWord(new ProcessedWord(W));
//                     newTerm.markDocumentPresence(dID);
//                     Invindex.insert(newTerm);
//                     }
//                     return true;
//            }
//         } 
// 
//         public boolean TermExists(String W){
//         
//                if (Invindex.empty())
//                    return false;
//                    
//                Invindex.findFirst();
//                // Iterate through all terms in the index
//               for(int i= 0; i<Invindex.size;i++) {
//         // Compare the current term's word with the given word
//         if (Invindex.retrieve().termWord.content.compareTo(W) == 0) 
//             return true;  // Word found, return true
//              Invindex.findNext();
//         
//         }
// 
//     // Return false if the word is not found after checking all terms
//     return false;
//     }
//         
//         public boolean [] searchWithLogic(String query)
//         {
//             if (!query.contains(" OR ") && !query.contains(" AND ")) {// If the query contains neither "OR" nor "AND"
//                 boolean[] result = new boolean[50];
//                 query = query.toLowerCase().trim();//convert all text to lowercase,trim() removes leading and trailing whitespace i can also use .replaceAll("^\\s+|\\s+$", "");
//                 
//            
//                 if (this.TermExists(query)) // Check if the word exists and return documents
//                   result =  this.Invindex.retrieve().getDocumentMapping();
//                 return result;
//                 
//             } else  // If the query contains both "OR" and "AND"
//             
//             if (query.contains(" OR ") && query.contains(" AND ")){
//             //ANDandOR(String query) another solution
//                 String[] split = query.split(" OR ");
//                boolean[] result = AndQuery(split[0]);
//                
//                // Combine results for the remaining terms using OR logic
//             int i = 1;
//             while (i <split.length) {
//               boolean[] result2 = AndQuery(split[i]);
//     
//                  int j = 0;
//               while (j < 50) {
//                 result[j] = result[j] || result2[j];
//                 j++;
//                 }
//               i++;
//               }
//            return result;
//             } else// If the query contains only "AND"
//               if (query.contains(" AND "))
//                 return AndQuery(query);
//  
//                // If the query contains only "OR"
//                 return ORQuery(query);
//           }
//           
//          /*private boolean[] ANDandOR(String query) {
//     String[] split = query.split(" OR ");
//                boolean[] result = AndQuery(split[0]);
//                
//                // Combine results for the remaining terms using OR logic
//             int i = 1;
//             while (i <split.length) {
//               boolean[] result2 = AndQuery(split[i]);
//     
//                  int j = 0;
//               while (j < 50) {
//                 result[j] = result[j] || result2[j];
//                 j++;
//                 }
//               i++;
//               }
//            return result;}*/
//         
//         public boolean [] AndQuery (String query)
//         {
//             String[] fragments = query.split(" AND ");
//             boolean [] b1 = new boolean [50];
//             // Initialize with the documents for the first term
//             if (this.TermExists (fragments[0].toLowerCase().trim()))
//                 b1 = this.Invindex.retrieve().getDocumentMapping();
//             // Combine results for the remaining terms using AND logic
//             for ( int i = 1 ; i< fragments.length ; i++){
// 
//                 boolean [] b2 = new boolean [50];
//                 if (this.TermExists(fragments[i].toLowerCase().trim()))
//                     b2 = this.Invindex.retrieve().getDocumentMapping();
//                 
//                 for ( int j = 0 ; j < 50 ; j++)
//                     b1 [j] = b1[j] && b2[j];
//             }                
// 
//             return b1;
//         }
//         
//         public boolean [] ORQuery(String query){
//        
//             String [] fragments = query.split(" OR ");
//             boolean [] b1 = new boolean [50];
//             
//             if (this.TermExists (fragments[0].toLowerCase().trim()))// Initialize with the documents for the first term
//                 b1 = this.Invindex.retrieve().getDocumentMapping();
// 
//             for ( int i = 1 ; i< fragments.length ; i++){// Combine results for the remaining terms using OR logic
//            
//                 boolean [] b2 = new boolean [50];
//                if (this.TermExists(fragments[i].toLowerCase().trim()))
//                     b2 = this.Invindex.retrieve().getDocumentMapping();
//                 
//                 for ( int j = 0 ; j < 50 ; j++)
//                     b1 [j] = b1[j] || b2[j];
//                
//             }
//             return b1;
//             }
//         
// 
//         public void printDocment(){
//          if (this.Invindex.empty()) {
//             System.out.println("Empty Inverted Index");
//             return; // Exit early if the index is empty
//              }
//            this.Invindex.findFirst();
//              while ( ! this.Invindex.last()){
//               System.out.println(Invindex.retrieve());
//                     this.Invindex.findNext();
//                 }//close while loop
//                 System.out.println(Invindex.retrieve());
//             }
//        
// }

public class InvIndex {

    LinkedList<Term> Invindex; // Mapping the terms to a list of documents containing those terms

    public InvIndex() {
        Invindex = new LinkedList<Term>();
    } // Default constructor

    public int getSize() {
        return Invindex.size();
    } // Returns the size of the inverted index

    public boolean addTerm(int dID, String W) {
        Term newTerm = new Term();
        // If the index is empty, add the new term directly
        if (Invindex.empty()) {
            newTerm.setTermWord(new ProcessedWord(W)); // Using ProcessedWord instead of Vocabulary
            newTerm.markDocumentPresence(dID); // Ensure Term aligns with this call
            Invindex.insert(newTerm);
            return true;
        } else { // Checks if the term already exists
            Invindex.findFirst();
            do {
                if (Invindex.retrieve().termWord.content.compareTo(W) == 0) {
                    // Term already exists, update its document presence
                    newTerm = Invindex.retrieve();
                    newTerm.markDocumentPresence(dID); // Ensure Term's method aligns
                    Invindex.update(newTerm);
                    return false; // Term already exists, no new addition
                }
                Invindex.findNext();
            } while (!Invindex.last());

            // Handle the last term in the index
            if (Invindex.retrieve().termWord.content.compareTo(W) == 0) {
                newTerm = Invindex.retrieve();
                newTerm.markDocumentPresence(dID);
                Invindex.update(newTerm);
                return false;
            } else { // If the term doesn't exist, add it as new
                newTerm.setTermWord(new ProcessedWord(W));
                newTerm.markDocumentPresence(dID);
                Invindex.insert(newTerm);
            }
            return true;
        }
    }

    public boolean TermExists(String W) {
        if (Invindex.empty())
            return false;

        Invindex.findFirst();
        // Iterate through all terms in the index
        for (int i = 0; i < Invindex.size(); i++) {
            // Compare the current term's word with the given word
            if (Invindex.retrieve().termWord.content.compareTo(W) == 0)
                return true; // Word found, return true
            Invindex.findNext();
        }

        // Return false if the word is not found after checking all terms
        return false;
    }

    public boolean[] searchWithLogic(String query) {
        if (!query.contains(" OR ") && !query.contains(" AND ")) { // If the query contains neither "OR" nor "AND"
            boolean[] result = new boolean[50];
            query = query.toLowerCase().trim(); // Convert all text to lowercase and trim leading/trailing spaces

            if (this.TermExists(query)) // Check if the word exists and return documents
                result = this.Invindex.retrieve().getDocumentMapping(); // Ensure Term's method aligns
            return result;

        } else if (query.contains(" OR ") && query.contains(" AND ")) { // If the query contains both "OR" and "AND"
            String[] split = query.split(" OR ");
            boolean[] result = AndQuery(split[0]);

            // Combine results for the remaining terms using OR logic
            for (int i = 1; i < split.length; i++) {
                boolean[] result2 = AndQuery(split[i]);

                for (int j = 0; j < 50; j++) {
                    result[j] = result[j] || result2[j];
                }
            }
            return result;

        } else if (query.contains(" AND ")) { // If the query contains only "AND"
            return AndQuery(query);
        }

        // If the query contains only "OR"
        return ORQuery(query);
    }

    public boolean[] AndQuery(String query) {
        String[] fragments = query.split(" AND ");
        boolean[] b1 = new boolean[50];

        // Initialize with the documents for the first term
        if (this.TermExists(fragments[0].toLowerCase().trim()))
            b1 = this.Invindex.retrieve().getDocumentMapping();

        // Combine results for the remaining terms using AND logic
        for (int i = 1; i < fragments.length; i++) {
            boolean[] b2 = new boolean[50];
            if (this.TermExists(fragments[i].toLowerCase().trim()))
                b2 = this.Invindex.retrieve().getDocumentMapping();

            for (int j = 0; j < 50; j++)
                b1[j] = b1[j] && b2[j];
        }
        return b1;
    }

    public boolean[] ORQuery(String query) {
        String[] fragments = query.split(" OR ");
        boolean[] b1 = new boolean[50];

        // Initialize with the documents for the first term
        if (this.TermExists(fragments[0].toLowerCase().trim()))
            b1 = this.Invindex.retrieve().getDocumentMapping();

        for (int i = 1; i < fragments.length; i++) { // Combine results for the remaining terms using OR logic
            boolean[] b2 = new boolean[50];
            if (this.TermExists(fragments[i].toLowerCase().trim()))
                b2 = this.Invindex.retrieve().getDocumentMapping();

            for (int j = 0; j < 50; j++)
                b1[j] = b1[j] || b2[j];
        }
        return b1;
    }

    public void printDocment() {
        if (this.Invindex.empty()) {
            System.out.println("Empty Inverted Index");
            return; // Exit early if the index is empty
        }
        this.Invindex.findFirst();
        while (!this.Invindex.last()) {
            System.out.println(Invindex.retrieve());
            this.Invindex.findNext();
        }
        System.out.println(Invindex.retrieve());
    }
}
