// public class BSTInvIndex {
//             BST < String , Term> invertedindexBST; 
//             int count = 0;
// 
//             public BSTInvIndex() {
//                 invertedindexBST = new BST< String , Term> ();
//             }
// 
//             public int size()
//             {
//                 return invertedindexBST.count;
//             }
//             
//             public boolean addTerm (int docID, String word)
//             {
//                if (invertedindexBST.empty())
//                {
//                    count ++;
//                    Term t = new Term ();
//                    t.setTermWord(new ProcessedWord (word));
//                    t.markDocumentPresence(docID);
//                    invertedindexBST.insert(word, t);
//                    return true;
//                }
//                else
//                {
//                     if (this.TermExists(word))//if (  )//invertedindexBST.find(word)
//                     {
//                         Term t = this.invertedindexBST.retrieve();
//                         t.markDocumentPresence(docID);
//                         invertedindexBST.update(t);
//                         return false;
//                         
//                     }
//                     
//                    count ++;
//                    Term t = new Term ();
//                    t.setTermWord(new ProcessedWord (word));
//                    t.markDocumentPresence(docID);
//                    invertedindexBST.insert(word, t);
//                     return true;
//            }
//         }
// 
//         public boolean TermExists(String word)//found
//         {
//                return invertedindexBST.find(word);
//         }
//         
//         public void DisplayInverted()
//         {
//             invertedindexBST.Traverse();
//         }
//        
//         public boolean [] SearchWithLogic (String str )
//         {
//             if (! str.contains(" OR ") && ! str.contains(" AND "))
//             {
//                 boolean [] r1 = new boolean[50];
//                 str = str.toLowerCase().trim();
//             
//                 if (this.TermExists (str))
//                     r1 =  this.invertedindexBST.retrieve().getDocumentMapping();
//                 return r1;
//             }
//             
//             else if (str.contains(" OR ") && str.contains(" AND "))
//             {
//                 String [] AND_ORs = str.split(" OR ");//OR less precedence
//                 boolean []  r1 = AndQuery (AND_ORs[0]);
//                
//               
//               int i=1;
//                 while ( i < AND_ORs.length )
//                 {   
//                     boolean [] r2 =AndQuery (AND_ORs[i]);
//                     
//                     for ( int j = 0 ; j < 50 ; j++ )
//                         r1 [j] = r1[j] || r2[j];
//                     i++;
//                 }
//                 return r1;
//             }
//             
//             else  if (str.contains(" AND "))
//                 return AndQuery (str);
//             
//             return OrQuery (str);
//         }
//         
//         public boolean [] AndQuery (String str)
//         {
//             String [] ANDs = str.split(" AND ");
//             boolean [] b1 = new boolean [50];
//             
//             if (this.TermExists (ANDs[0].toLowerCase().trim()))
//                 b1 = this.invertedindexBST.retrieve().getDocumentMapping();
// 
//           
//            int i =1;
//             while ( i< ANDs.length )
//             {
//                 boolean [] b2 = new boolean [50];
//                 if (this.TermExists (ANDs[i].toLowerCase().trim()))
//                     b2 = this.invertedindexBST.retrieve().getDocumentMapping();
//                 
//                 for ( int j = 0 ; j < 50 ; j++)
//                     b1 [j] = b1[j] && b2[j];
//                 i++;
//             }
//             return b1;
//         }
//         
//         public boolean [] OrQuery (String str)
//         {
//             String [] ORs = str.split(" OR ");
//             boolean [] b1 = new boolean [50];
//             
//             if (this.TermExists (ORs[0].toLowerCase().trim()))
//                 b1 = this.invertedindexBST.retrieve().getDocumentMapping();
// 
//            
//            int i =1;
//             while ( i< ORs.length )
//             {
//                 boolean [] b2 = new boolean [50];
//                 if (this.TermExists (ORs[i].toLowerCase().trim()))
//                     b2 = this.invertedindexBST.retrieve().getDocumentMapping();
//                 
//                 for ( int j = 0 ; j < 50 ; j++)
//                     b1 [j] = b1[j] || b2[j];
//                 i++;
//                
//             }
//             return b1;
//         }
//    
//     
// }
// 


public class BSTInvIndex {

    BST<ProcessedWord, Term> InvIndex; // Mapping terms to documents containing those terms

    public BSTInvIndex() {
        InvIndex = new BST<>();
    } // Default constructor

    public int getSize() {
        return InvIndex.size();
    } // Returns the size of the inverted index

    public boolean addTerm(int docID, String word) {
        Term newTerm = new Term();
        ProcessedWord processedWord = new ProcessedWord(word);

        // Check if the term already exists
        if (InvIndex.find(processedWord)) {
            newTerm = InvIndex.retrieve();
            newTerm.markDocumentPresence(docID); // Update document presence
            InvIndex.update(newTerm);
            return false; // Term already exists
        } else {
            // Add new term
            newTerm.setTermWord(processedWord);
            newTerm.markDocumentPresence(docID);
            InvIndex.insert(processedWord, newTerm);
            return true;
        }
    }

    public boolean termExists(String word) {
        ProcessedWord processedWord = new ProcessedWord(word);
        return InvIndex.find(processedWord);
    }

    public boolean[] searchWithLogic(String query) {
        if (!query.contains(" OR ") && !query.contains(" AND ")) {
            // If the query contains neither "OR" nor "AND"
            boolean[] result = new boolean[50];
            query = query.toLowerCase().trim();

            if (this.termExists(query)) {
                result = InvIndex.retrieve().getDocumentMapping();
            }
            return result;
        } else if (query.contains(" OR ") && query.contains(" AND ")) {
            // Handle both AND and OR
            String[] split = query.split(" OR ");
            boolean[] result = andQuery(split[0]);

            for (int i = 1; i < split.length; i++) {
                boolean[] result2 = andQuery(split[i]);
                for (int j = 0; j < 50; j++) {
                    result[j] = result[j] || result2[j];
                }
            }
            return result;
        } else if (query.contains(" AND ")) {
            return andQuery(query);
        } else {
            return orQuery(query);
        }
    }

    public boolean[] andQuery(String query) {
        String[] fragments = query.split(" AND ");
        boolean[] result = new boolean[50];

        if (this.termExists(fragments[0].toLowerCase().trim())) {
            result = InvIndex.retrieve().getDocumentMapping();
        }

        for (int i = 1; i < fragments.length; i++) {
            boolean[] temp = new boolean[50];
            if (this.termExists(fragments[i].toLowerCase().trim())) {
                temp = InvIndex.retrieve().getDocumentMapping();
            }

            for (int j = 0; j < 50; j++) {
                result[j] = result[j] && temp[j];
            }
        }
        return result;
    }

    public boolean[] orQuery(String query) {
        String[] fragments = query.split(" OR ");
        boolean[] result = new boolean[50];

        if (this.termExists(fragments[0].toLowerCase().trim())) {
            result = InvIndex.retrieve().getDocumentMapping();
        }

        for (int i = 1; i < fragments.length; i++) {
            boolean[] temp = new boolean[50];
            if (this.termExists(fragments[i].toLowerCase().trim())) {
                temp = InvIndex.retrieve().getDocumentMapping();
            }

            for (int j = 0; j < 50; j++) {
                result[j] = result[j] || temp[j];
            }
        }
        return result;
    }

    public void displayInvertedIndex() {
        if (InvIndex.empty()) {
            System.out.println("Empty Inverted Index");
            return;
        }

        InvIndex.Traverse(); // Use BST traversal to print the inverted index
    }
}
