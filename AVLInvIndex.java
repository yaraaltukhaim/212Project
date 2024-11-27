public class AVLInvIndex {
            
            

            AVLTree <String, Term> invertedindexAVL;  

            public AVLInvIndex() {
              invertedindexAVL = new AVLTree <String, Term>();
            }//default constructor
            
            public int getSize()
            {
                return invertedindexAVL.size();
            }// Returns the size of the inverted index

            public boolean addTerm(int dID, String W)
            {
              Term newTerm = new Term ();
               // If the index is empty, add the new term directly
                if (invertedindexAVL.empty()){ 
                newTerm.setTermWord(new ProcessedWord(W));
                newTerm.markDocumentPresence(dID);
                invertedindexAVL.insert(W,newTerm);
                return true;
               }else{//checks if the term already exists
  
                        if (invertedindexAVL.find(W)) {
                            newTerm = invertedindexAVL.retrieve();
                            newTerm.markDocumentPresence(dID);
                            invertedindexAVL.update(newTerm);
                            return false;// Term already exists, no new addition
                        }
   
                    newTerm.setTermWord(new ProcessedWord(W));
                    newTerm.markDocumentPresence(dID);
                    invertedindexAVL.insert(W,newTerm);
                    return true;
           }
        } 
             
              public boolean found(String W)
        {
               return invertedindexAVL.find(W);
        }
        
      public boolean [] AndQuery (String query)
        {
            String[] fragments = query.split(" AND ");
            boolean [] b1 = new boolean [50];
            // Initialize with the documents for the first term
            if (this.found (fragments[0].toLowerCase().trim()))
                b1 = this.invertedindexAVL.retrieve().getDocumentMapping();
            // Combine results for the remaining terms using AND logic
            for ( int i = 1 ; i< fragments.length ; i++){

                boolean [] b2 = new boolean [50];
                if (this.found(fragments[i].toLowerCase().trim()))
                    b2 = this.invertedindexAVL.retrieve().getDocumentMapping();
                
                for ( int j = 0 ; j < 50 ; j++)
                    b1 [j] = b1[j] && b2[j];
            }                

            return b1;
        }//close method and
        
        public boolean [] ORQuery(String query){
       
            String [] fragments = query.split(" OR ");
            boolean [] b1 = new boolean [50];
            
            if (this.found(fragments[0].toLowerCase().trim()))// Initialize with the documents for the first term
                b1 = this.invertedindexAVL.retrieve().getDocumentMapping();

            for ( int i = 1 ; i< fragments.length ; i++){// Combine results for the remaining terms using OR logic
           
                boolean [] b2 = new boolean [50];
               if (this.found(fragments[i].toLowerCase().trim()))
                    b2 = this.invertedindexAVL.retrieve().getDocumentMapping();
                
                for ( int j = 0 ; j < 50 ; j++)
                    b1 [j] = b1[j] || b2[j];
               
            }
            return b1;}
        
        public boolean [] searchWithLogic(String query)
        {
            if (!query.contains(" OR ") && !query.contains(" AND ")) {// If the query contains neither "OR" nor "AND"
                boolean[] result = new boolean[50];
                query = query.toLowerCase().trim();//convert all text to lowercase,trim() removes leading and trailing whitespace i can also use .replaceAll("^\\s+|\\s+$", "");
                
           
                if (this.found(query)) // Check if the word exists and return documents
                  result =  this.invertedindexAVL.retrieve().getDocumentMapping();
                return result;
                
            } else  // If the query contains both "OR" and "AND"
            
            if (query.contains(" OR ") && query.contains(" AND ")){
            //ANDandOR(String query) another solution
                String[] split = query.split(" OR ");
               boolean[] result = AndQuery(split[0]);
               
               // Combine results for the remaining terms using OR logic
           for ( int i = 1 ; i < split .length ; i++) {
            
              boolean[] result2 = AndQuery(split[i]);
    
                for ( int j = 0 ; j < 50 ; j++ )
                result[j] = result[j] || result2[j];
              }
           return result;
            } else// If the query contains only "AND"
              if (query.contains(" AND "))
                return AndQuery(query);
 
               // If the query contains only "OR"
                return ORQuery(query);
          }//close method 

        public void printDocument()
        {
            invertedindexAVL.Traverse();
        }
       
}