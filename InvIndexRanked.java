public class InvIndexRanked {
       
       class frequency//representing the frequency of a term in a documen
    {
        int docID = 0;
        int f = 0;
    }

            LinkedList <TermRank> invertedindex; 
            frequency [] freqs;
            
            public InvIndexRanked() {
                invertedindex  = new LinkedList <TermRank>();
                freqs = new frequency[50];
            }
            
            public int getSize()
            {
                return invertedindex.size();
            }// Returns the size of the inverted index
            
             

        public boolean addTerm(int dID, String W)
            {
              TermRank newTerm = new TermRank ();
               // If the index is empty, add the new term directly
                if (invertedindex.empty()){ 
                newTerm.setTermWord(new ProcessedWord(W));
                newTerm.markDocumentPresence(dID);
               invertedindex .insert(newTerm);
                return true;
               }else{//checks if the term already exists
            
                    invertedindex.findFirst();
                     while ( ! invertedindex.last()) {
                    
                        if ( invertedindex.retrieve().termWord.content.compareTo(W) == 0) {
                       
                            newTerm =  invertedindex.retrieve();
                            newTerm.markDocumentPresence(dID);
                            invertedindex.update(newTerm);
                            return false;// Term already exists, no new addition
                        }
                      invertedindex.findNext();
                    }
                    
                    // Handle the last term in the index
                    if ( invertedindex.retrieve().termWord.content.compareTo(W) == 0)
                    {
                        newTerm =  invertedindex.retrieve();
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
                for ( int i = 0 ; i <invertedindex.size; i++) {
        // Compare the current term's word with the given word
        if (invertedindex.retrieve().termWord.content.compareTo(W) == 0) 
            return true;  // Word found, return true
        // checking the last index
        invertedindex.findNext();
        }

    // Return false if the word is not found after checking all terms
    return false;
        
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
            
                for ( int i = 0 ; i < words.length ; i++)
            {
                if (TermExists(words[i]))
                {
                    int [] docs = invertedindex.retrieve().getDocumentMapping();
                    for ( int j = 0 ; j < docs.length ; j ++)
                    {
                        if (docs[j] != 0)
                        {
                            int index = j;
                            freqs[index].docID = index;
                            freqs[index].f += docs[j];
                        }
                    }
                }
            }
            
            
            mergesort(freqs, 0, freqs.length-1 );
            
            System.out.println("\nDocIDt\tScore");
            for ( int x = 0 ;  freqs[x].f != 0 ; x++)
           
            System.out.println(freqs[x].docID + "\t\t" + freqs[x].f);
           
             
      }//close method

        
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
    }


}//close class 