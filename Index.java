public class Index{

    class Document { //class representing a document and the words it contains
           LinkedList <String> index; 
           int documentID;
            

            public Document() { // defualt constructor
                 documentID = 0;
                index = new LinkedList <String>();
            }

           public boolean wordExists(String word){  // Checks if a specific word exists in the document
           
               if (index.empty())
                   return false;

               index.findFirst();
               int i = 0;
               while(i < index.size){//open loop

                   if ( index.retrieve().compareTo(word) == 0)
                       return true;
                  index.findNext();
                  i++;
               }//close loop
               return false; // when the word is not found
           }//close method
           
            public void insertNew(String W){ // Adds a new word to the document's word list
            
                index.insert(W);
            }
    } 
    
    class Count {//representing the frequency of a term in a document

        int documentID ,frequencyCount;  
    }
      
   
    
    Document [] documents;
    Count[] Frequencies;

   
    public Index() {
        documents = new Document [50];
        Frequencies = new Count[50];
        
        for ( int i = 0 ; i < documents.length ; i++){// Initialize each document with a unique ID
            documents [i] = new Document();
             documents[i].documentID = i; 
    }
    }
        
    public void addtoDocument ( int dID, String W){// Adds a word to a specific document
         if (dID >= 0 && dID < documents.length){
            documents[dID].insertNew(W);
            }else{
            System.out.println("Invalid document ID: " + dID);   
             }
    }
   
    public  boolean [] findDocuments(String W){ // Returns a boolean array indicating which documents contain the specified word
    
    // Create the flag array dynamically based on the number of documents
    boolean[] flag = new boolean[50];

    // Iterate through the documents and update the flags where the word exists
    for (int i = 0; i < flag.length; i++) {
        flag[i] = documents[i].wordExists(W); 
    }

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
    
        if ( documents[dID].index.empty()){
            System.out.println("Empty Document");
            return;
            }
            
            documents[dID].index.findFirst();
            
            for ( int i = 0; i< documents[dID].index.size ; i++)
            {
                System.out.print (documents[dID].index.retrieve() + " ");
                documents[dID].index.findNext();
            }//for
        
    }// close method print


}