public class IndexRanked {

      class frequency//representing the frequency of a term in a document
    {
        int docID = 0;
        int f = 0;
    }
    
    class Document { //class representing a document and the words it contains
           LinkedList <String> index; 
           int docID;
            
            

            public Document() { // defualt constructor
               index = new LinkedList <String>();
               docID = 0;
               
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
           
           
            public void insertNew(String W){ // Adds a new word to the document's word list
            
                index.insert(W);
            }
          }
        
    
     Document [] indexes;
    frequency [] freqs;
    
    public IndexRanked() {
    indexes = new Document[50];
    freqs = new frequency[50];

        for ( int i = 0 ; i < indexes.length ; i++){// Initialize each document with a unique ID
            indexes [i] = new Document();  
            indexes[i].docID  = i;
            }       
    }
     public  boolean [] findDocuments(String W){ // Returns a boolean array indicating which documents contain the specified word
    
    // Create the flag array dynamically based on the number of documents
    boolean[] flag = new boolean[50];
    for (int i = 0; i < flag.length; i++)
    flag[i] = false;

    // Iterate through the documents and update the flags where the word exists
    for (int i = 0; i < flag.length; i++) 
    if (indexes[i].wordExists(W))
        flag[i] = true; 
    

    return flag;
    }//close method findDocuments
   
   public void addDocument ( int dID, String W){// Adds a word to a specific document
           indexes[dID].insertNew(W);
    }
    
     public void TF(String W)
        {
            W = W.toLowerCase().trim();
            String [] words = W.split(" ");
            freqs = new frequency[50];
            for (int i = 0; i < 50; i++)
            {
                freqs[i] = new frequency();
                freqs[i].docID = i;
                freqs[i].f = 0;
            }//close for loop
            
            for (int docIndex = 0; docIndex < 50; docIndex++)
            {
                for ( int i = 0 ; i < words.length ; i++)
                {
                    indexes[docIndex].index.findFirst(); 
                    int wordCount = 0;
                    
                    for (int j = 0; j <  indexes[docIndex].index.size(); j++){
                    
                        if ( indexes[docIndex].index.retrieve().compareTo(words[i])==0)
                            wordCount ++;
                         indexes[docIndex].index.findNext();
                    }
                    
                    if (wordCount > 0)
                         freqs[docIndex].f += wordCount;
                }
            }
            
               mergesort(freqs, 0, freqs.length-1 );
            
            System.out.println("\nDocIDt\tScore");
            for ( int x = 0 ;  freqs[x].f != 0 ; x++)
                System.out.println(freqs[x].docID + "\t\t" + freqs[x].f);
        }
      
       public static void mergesort ( frequency [] A , int l , int r ) 
    {
        if ( l >= r )
            return;
        int m = ( l + r ) / 2;
        mergesort (A , l , m ) ;          // Sort first half
        mergesort (A , m + 1 , r ) ;    // Sort second half
        merge (A , l , m , r ) ;            // Merge
    }


   private static void merge ( frequency [] A , int l , int m , int r ) 
    {
        frequency [] B = new frequency [ r - l + 1];
        int i = l , j = m + 1 , k = 0;
    
        while ( i <= m && j <= r )
        {
            if ( A [ i ].f >= A [ j ].f)
                B [ k ++] = A [ i ++];
            else
                B [ k ++] = A [ j ++];
        }
        
        if ( i > m )
            while ( j <= r )
                B [ k ++] = A [ j ++];
        else
            while ( i <= m )
                B [ k ++] = A [ i ++];
        
        for ( k = 0; k < B . length ; k ++)
            A [ k + l ] = B [ k ];
    }//close method merge
    
    public void printDocment (int dID){// Prints all words in a specific document
    
        if ( indexes[dID].index.empty())
            System.out.println("Empty Document");
            else{

            indexes[dID].index.findFirst();
            
            for ( int i = 0; i< indexes[dID].index.size ; i++)
            {
                System.out.print (indexes[dID].index.retrieve() + " ");
               indexes[dID].index.findNext();
            }//for
            }
        
    }// close method print

     
}//close class