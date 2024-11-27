public class Index{

    class frequency
    {
        int docID = 0;
        int f = 0;
    }//representing the frequency of a term in a document

    class Document { //class representing a document and the words it contains
           LinkedList <String> index; 
           int docID;
           
            

            public Document() { // defualt constructor
                 docID = 0;
               index = new LinkedList <String>();
            }

           public boolean wordExists(String word){  // Checks if a specific word exists in the document
           
               if (index.empty())
                   return false;

               index.findFirst();
               for ( int i = 0 ; i < index.size ; i++){//open loop
                if ( index.retrieve().compareTo(word) == 0)
                       return true;
                  index.findNext();
                 
               }//close loop
               return false; // when the word is not found
           }//close method
           
           
            public void insertNew(String word){ // Adds a new word to the document's word list
            
                index.insert(word);
            }
    }//close class 
    
 
  Document [] indexes;
  frequency [] freqs;

   
    public Index() {
        indexes = new Document [50];
        freqs = new frequency [50];
        
        for ( int i = 0 ; i < indexes.length ; i++){// Initialize each document with a unique ID
            indexes [i] = new Document();
            indexes [i].docID = i; 
    }
    }
        
    public void addDocument( int  docID, String data){// Adds a word to a specific document
          indexes[docID].insertNew(data);
    }
   
    public  boolean [] findDocuments(String W){ // Returns a boolean array indicating which documents contain the specified word
    
    // Create the flag array dynamically based on the number of documents
    boolean[] flag = new boolean[50];
    for (int i = 0 ; i < flag.length ; i++)
        flag[i] = false;
    
    for (int i = 0 ; i < flag.length ; i++)
        if (indexes[i].wordExists(W))
            flag[i] = true;
    return flag;
}
       public boolean [] ORQuery (String W)
        {
            String [] orParts = W.split(" OR ");
            boolean [] result = findDocuments(orParts[0].toLowerCase().trim());

            for ( int i = 1 ; i< orParts.length ; i++)
            {
                boolean [] tempResult = findDocuments(orParts[i].toLowerCase().trim());
                for ( int j = 0 ; j <50 ; j++)
                    result [j] = result[j] || tempResult[j];
            }
            return result;
        }

         public boolean [] AndQuery (String W)
        {
            String [] andParts = W.split(" AND ");
            boolean [] result = findDocuments(andParts[0].toLowerCase().trim());

            for ( int i = 1 ; i< andParts.length ; i++)
            {
                boolean [] tempResult = findDocuments(andParts[i].toLowerCase().trim());
                for (int j = 0; j < 50; j++)
                   result[j] = result[j] && tempResult[j];
            }                
            return result;
        }
        
          public boolean [] AND_OR_Function (String W )
        {
            if (! W.contains(" OR ") && ! W.contains(" AND "))// Check if the query contains neither AND nor OR
            {
                 return findDocuments(W.toLowerCase().trim());
            }
            
            else if (W.contains(" OR ") && W.contains(" AND "))
            {
                String [] ANDOR = W.split(" OR ");
                boolean []  result = AndQuery(ANDOR[0]);
               
                for ( int i = 1 ; i < ANDOR.length ; i++  )
                {   
                    boolean [] r2 =AndQuery(ANDOR[i]);
                    for ( int j = 0 ; j <50 ; j++ )
                         result[j] = result[j] || r2[j];
                }
                return result;
            }
            
            else 
             if (W.contains(" AND "))
                return AndQuery (W);
            
            return ORQuery(W);
        }
   
    public void printDocment (int dID){// Prints all words in a specific document
    
        if ( indexes[dID].index.empty()){
            System.out.println("Empty Document");
            return;
            }
            
            indexes[dID].index.findFirst();
            
            for ( int i = 0; i< indexes[dID].index.size ; i++)
            {
                System.out.print (indexes[dID].index.retrieve() + " ");
                indexes[dID].index.findNext();
            }//for
        
    }// close method print


}