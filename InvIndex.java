public class InvIndex {

            LinkedList <Term> invertedindex; //mapping the terms to a list of documents containing those terms

            public InvIndex() {
              invertedindex = new LinkedList <Term>();
            }//default constructor
            
            public int getSize()
            {
                return invertedindex.size();
            }// Returns the size of the inverted index

            public boolean addTerm(int dID, String W)
            {
              Term newTerm = new Term ();
               // If the index is empty, add the new term directly
                if (invertedindex.empty()){ 
                newTerm.setTermWord(new ProcessedWord (W));
                newTerm.markDocumentPresence(dID);//changed yara
                invertedindex.insert(newTerm);
                return true;
               }else{//checks if the term already exists
            
                    invertedindex.findFirst();
                      while ( ! invertedindex.last()){//while start
                    
                        if ( invertedindex.retrieve().termWord.content.compareTo(W) == 0) {
                       
                            newTerm =  invertedindex.retrieve();
                            newTerm.markDocumentPresence(dID);//changed yara
                            invertedindex.update(newTerm);
                            return false;// Term already exists, no new addition
                        }
                      invertedindex.findNext();
                      }
                    
                    
                    // Handle the last term in the index
                    if ( invertedindex.retrieve().termWord.content.compareTo(W) == 0)
                    {
                        newTerm = invertedindex.retrieve();
                        newTerm.markDocumentPresence(dID);
                        invertedindex.update(newTerm);
                        return false;
                    }
                    else{// If the term doesn't exist, add it as new
   
                    newTerm.setTermWord(new ProcessedWord(W));
                    newTerm.markDocumentPresence(dID);
                    invertedindex.insert(newTerm);
                    }
                    return true;
           }
        } 

        public boolean TermExists(String W){
        
               if (invertedindex.empty())
                   return false;
                   
               invertedindex.findFirst();
               // Iterate through all terms in the index
              for(int i= 0; i<invertedindex.size;i++) {
        // Compare the current term's word with the given word
        if (invertedindex.retrieve().termWord.content.compareTo(W) == 0) 
            return true;  // Word found, return true
             invertedindex.findNext();
        
        }

    // Return false if the word is not found after checking all terms
    return false;
    }
        
        public boolean [] searchWithLogic(String query)
        {
            if (!query.contains(" OR ") && !query.contains(" AND ")) {// If the query contains neither "OR" nor "AND"
                boolean[] result = new boolean[50];
                query = query.toLowerCase().trim();//convert all text to lowercase,trim() removes leading and trailing whitespace i can also use .replaceAll("^\\s+|\\s+$", "");
                
           
                if (this.TermExists(query)) // Check if the word exists and return documents
                  result =  this.invertedindex.retrieve().getDocumentMapping();
                return result;
                
            } else  // If the query contains both "OR" and "AND"
            
            if (query.contains(" OR ") && query.contains(" AND ")){
            //ANDandOR(String query) another solution
                String[] split = query.split(" OR ");
               boolean[] result = AndQuery(split[0]);
               
               // Combine results for the remaining terms using OR logic
            
            for ( int i = 1 ; i < split.length;i++) {
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
          }
          
       
        
        public boolean [] AndQuery (String query)
        {
            String[] fragments = query.split(" AND ");
            boolean [] b1 = new boolean [50];
            // Initialize with the documents for the first term
            if (this.TermExists (fragments[0].toLowerCase().trim()))
                b1 = this.invertedindex.retrieve().getDocumentMapping();
            // Combine results for the remaining terms using AND logic
            for ( int i = 1 ; i< fragments.length ; i++){

                boolean [] b2 = new boolean [50];
                if (this.TermExists(fragments[i].toLowerCase().trim()))
                    b2 = this.invertedindex.retrieve().getDocumentMapping();
                
                for ( int j = 0 ; j < 50 ; j++)
                    b1 [j] = b1[j] && b2[j];
            }                

            return b1;
        }
        
        public boolean [] ORQuery(String query){
       
            String [] fragments = query.split(" OR ");
            boolean [] b1 = new boolean [50];
            
            if (this.TermExists (fragments[0].toLowerCase().trim()))// Initialize with the documents for the first term
                b1 = this.invertedindex.retrieve().getDocumentMapping();

            for ( int i = 1 ; i< fragments.length ; i++){// Combine results for the remaining terms using OR logic
           
                boolean [] b2 = new boolean [50];
               if (this.TermExists(fragments[i].toLowerCase().trim()))
                    b2 = this.invertedindex.retrieve().getDocumentMapping();
                
                for ( int j = 0 ; j < 50 ; j++)
                    b1 [j] = b1[j] || b2[j];
               
            }
            return b1;
            }
        

        public void printDocment(){
         if (this.invertedindex.empty()) 
            System.out.println("Empty Inverted Index");
          else{   
             
           this.invertedindex.findFirst();
             while ( ! this.invertedindex.last()){
              System.out.println(invertedindex.retrieve());
                    this.invertedindex.findNext();
                }//close while loop
                System.out.println(invertedindex.retrieve());
            }
            }
       
}