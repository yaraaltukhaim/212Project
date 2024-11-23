// public class AVLInvIndex {
// 
//             AVLTree <String, Term> InvIndexAVL;  
// 
//             public AVLInvIndex() {
//               InvIndexAVL = new AVLTree <String, Term>();
//             }//default constructor
//             
//             public int getSize()
//             {
//                 return InvIndexAVL.size();
//             }// Returns the size of the inverted index
// 
//             public boolean addTerm(int dID, String W)
//             {
//               Term newTerm = new Term ();
//                // If the index is empty, add the new term directly
//                 if (InvIndexAVL.empty()){ 
//                 newTerm.setTermWord(new ProcessedWord(W));
//                 newTerm.markDocumentPresence(dID);
//                 InvIndexAVL.insert(W,newTerm);
//                 return true;
//                }else{//checks if the term already exists
//   
//                         if (InvIndexAVL.find(W)) {
//                             newTerm =  InvIndexAVL.retrieve();
//                             newTerm.markDocumentPresence(dID);
//                             InvIndexAVL.update(newTerm);
//                             return false;// Term already exists, no new addition
//                         }
//    
//                     newTerm.setTermWord(new ProcessedWord(W));
//                     newTerm.markDocumentPresence(dID);
//                     InvIndexAVL.insert(W,newTerm);
//                     return true;
//            }
//         } 
//              
//               public boolean found(String W)
//         {
//                return InvIndexAVL.find(W);
//         }
//         
//       public boolean [] AndQuery (String query)
//         {
//             String[] fragments = query.split(" AND ");
//             boolean [] b1 = new boolean [50];
//             // Initialize with the documents for the first term
//             if (this.found (fragments[0].toLowerCase().trim()))
//                 b1 = this.InvIndexAVL.retrieve().getDocumentMapping();
//             // Combine results for the remaining terms using AND logic
//             for ( int i = 1 ; i< fragments.length ; i++){
// 
//                 boolean [] b2 = new boolean [50];
//                 if (this.found(fragments[i].toLowerCase().trim()))
//                     b2 = this.InvIndexAVL.retrieve().getDocumentMapping();
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
//             if (this.found(fragments[0].toLowerCase().trim()))// Initialize with the documents for the first term
//                 b1 = this.InvIndexAVL.retrieve().getDocumentMapping();
// 
//             for ( int i = 1 ; i< fragments.length ; i++){// Combine results for the remaining terms using OR logic
//            
//                 boolean [] b2 = new boolean [50];
//                if (this.found(fragments[i].toLowerCase().trim()))
//                     b2 = this.InvIndexAVL.retrieve().getDocumentMapping();
//                 
//                 for ( int j = 0 ; j < 50 ; j++)
//                     b1 [j] = b1[j] || b2[j];
//                
//             }
//             return b1;}
//         
//         public boolean [] searchWithLogic(String query)
//         {
//             if (!query.contains(" OR ") && !query.contains(" AND ")) {// If the query contains neither "OR" nor "AND"
//                 boolean[] result = new boolean[50];
//                 query = query.toLowerCase().trim();//convert all text to lowercase,trim() removes leading and trailing whitespace i can also use .replaceAll("^\\s+|\\s+$", "");
//                 
//            
//                 if (this.found(query)) // Check if the word exists and return documents
//                   result =  this.InvIndexAVL.retrieve().getDocumentMapping();
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
//         public void printDocument()
//         {
//             InvIndexAVL.Traverse();
//         }
//        
// }


public class AVLInvIndex {

    AVLTree<String, Term> InvIndexAVL;

    public AVLInvIndex() {
        InvIndexAVL = new AVLTree<>();
    }

    public int getSize() {
        return InvIndexAVL.size();
    }

    public boolean addTerm(int dID, String word) {
        if (InvIndexAVL.empty()) {
            Term term = new Term();
            term.setTermWord(new ProcessedWord(word)); // Aligns with your Term class
            term.markDocumentPresence(dID);
            InvIndexAVL.insert(word, term);
            return true;
        } else {
            if (InvIndexAVL.find(word)) {
                Term term = InvIndexAVL.retrieve();
                term.markDocumentPresence(dID);
                InvIndexAVL.update(term);
                return false; // Term already exists
            }
            Term newTerm = new Term();
            newTerm.setTermWord(new ProcessedWord(word));
            newTerm.markDocumentPresence(dID);
            InvIndexAVL.insert(word, newTerm);
            return true;
        }
    }

    public boolean found(String word) {
        return InvIndexAVL.find(word);
    }

    public boolean[] AndQuery(String query) {
        String[] fragments = query.split(" AND ");
        boolean[] b1 = new boolean[50];
        if (this.found(fragments[0].toLowerCase().trim())) {
            b1 = this.InvIndexAVL.retrieve().getDocumentMapping(); // Ensure alignment with your Term class
        }

        for (int i = 1; i < fragments.length; i++) {
            boolean[] b2 = new boolean[50];
            if (this.found(fragments[i].toLowerCase().trim())) {
                b2 = this.InvIndexAVL.retrieve().getDocumentMapping();
            }
            for (int j = 0; j < 50; j++) {
                b1[j] = b1[j] && b2[j];
            }
        }

        return b1;
    }

    public boolean[] ORQuery(String query) {
        String[] fragments = query.split(" OR ");
        boolean[] b1 = new boolean[50];
        if (this.found(fragments[0].toLowerCase().trim())) {
            b1 = this.InvIndexAVL.retrieve().getDocumentMapping();
        }

        for (int i = 1; i < fragments.length; i++) {
            boolean[] b2 = new boolean[50];
            if (this.found(fragments[i].toLowerCase().trim())) {
                b2 = this.InvIndexAVL.retrieve().getDocumentMapping();
            }
            for (int j = 0; j < 50; j++) {
                b1[j] = b1[j] || b2[j];
            }
        }

        return b1;
    }

    public boolean[] searchWithLogic(String query) {
        if (!query.contains(" OR ") && !query.contains(" AND ")) {
            boolean[] result = new boolean[50];
            query = query.toLowerCase().trim();
            if (this.found(query)) {
                result = this.InvIndexAVL.retrieve().getDocumentMapping();
            }
            return result;
        } else if (query.contains(" OR ") && query.contains(" AND ")) {
            String[] split = query.split(" OR ");
            boolean[] result = AndQuery(split[0]);
            for (int i = 1; i < split.length; i++) {
                boolean[] result2 = AndQuery(split[i]);
                for (int j = 0; j < 50; j++) {
                    result[j] = result[j] || result2[j];
                }
            }
            return result;
        } else if (query.contains(" AND ")) {
            return AndQuery(query);
        }
        return ORQuery(query);
    }

    public void printDocument() {
        InvIndexAVL.Traverse(); // Ensure Traverse prints correctly
    }
}
