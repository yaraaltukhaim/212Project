public class InvIndexRanked {
            
            LinkedList <TermRank> InvindexRank; 
            Count[] tFrequencies;
            
            public InvIndexRanked() {
                InvindexRank = new LinkedList <TermRank>();
                tFrequencies = new Count[50];
            }
            
            public int getSize()
            {
                return InvindexRank.size();
            }// Returns the size of the inverted index
            
             class Count {//representing the frequency of a term in a document

        int documentID ,frequencyCount;  
    }

        public boolean addTerm(int dID, String W)
            {
              TermRank newTerm = new TermRank ();
               // If the index is empty, add the new term directly
                if (InvindexRank.empty()){ 
                newTerm.setTermWord(new ProcessedWord(W));
                newTerm.markDocumentPresence(dID);
                InvindexRank.insert(newTerm);
                return true;
               }else{//checks if the term already exists
            
                    InvindexRank.findFirst();
                     do {
                    
                        if ( InvindexRank.retrieve().termWord.content.compareTo(W) == 0) {
                       
                            newTerm =  InvindexRank.retrieve();
                            newTerm.markDocumentPresence(dID);
                            InvindexRank.update(newTerm);
                            return false;// Term already exists, no new addition
                        }
                       InvindexRank.findNext();
                    }while (!InvindexRank.last());
                    
                    // Handle the last term in the index
                    if ( InvindexRank.retrieve().termWord.content.compareTo(W) == 0)
                    {
                        newTerm =  InvindexRank.retrieve();
                        newTerm.markDocumentPresence(dID);
                        InvindexRank.update(newTerm);
                        return false;
                    }
                    else{// If the term doesn't exist, add it as new
   
                    newTerm.setTermWord(new ProcessedWord(W));
                    newTerm.markDocumentPresence(dID);
                    InvindexRank.insert(newTerm);
                    }
                    return true;
           }
        } 


        public boolean TermExists(String W){
        
               if (InvindexRank.empty())
                   return false;
                   
               InvindexRank.findFirst();
               // Iterate through all terms in the index
                for ( int i = 0 ; i <InvindexRank.size; i++) {
        // Compare the current term's word with the given word
        if (InvindexRank.retrieve().termWord.content.compareTo(W) == 0) 
            return true;  // Word found, return true
        // checking the last index
        InvindexRank.findNext();
        }

    // Return false if the word is not found after checking all terms
    return false;
        
}

        public void printDocment(){
         if (this.InvindexRank.empty()) {
            System.out.println("Empty Inverted Index");
            return; // Exit early if the index is empty
             }
           this.InvindexRank.findFirst();
             while ( ! this.InvindexRank.last()){
              System.out.println(InvindexRank.retrieve());
                    this.InvindexRank.findNext();
                }//close while loop
                System.out.println(InvindexRank.retrieve());
            }


              public void TF(String W)
        {
            W = W.toLowerCase().trim();
            String [] words = W.split(" ");
            tFrequencies = new Count[50];
            for (int i = 0; i < 50; i++)
            {
                tFrequencies[i] = new Count();
                tFrequencies[i].documentID = i;
                tFrequencies[i].frequencyCount = 0;
            }//close for loop
            
                for ( int i = 0 ; i < words.length ; i++)
            {
                if (TermExists(words[i]))
                {
                    int [] docs = InvindexRank.retrieve().getDocumentMapping();
                    for ( int j = 0 ; j < docs.length ; j ++)
                    {
                        if (docs[j] != 0)
                        {
                            int index = j;
                            tFrequencies[index].documentID = index;
                            tFrequencies[index].frequencyCount += docs[j];
                        }
                    }
                }
            }
            
            
            mergesort(tFrequencies, 0, tFrequencies.length - 1);
            
            System.out.println("\nDocIDt\tScore");
            int s = 0; 
            while (s < tFrequencies.length && tFrequencies[s].frequencyCount != 0) {
            System.out.println(tFrequencies[s].documentID + "\t\t" + tFrequencies[s].frequencyCount);
            s++; 
         }      
      }//close method

        
      public static void mergesort (Count[] tFrequencies , int l , int r ) 
    {
        if ( l >= r )
            return;
        int m = ( l + r ) / 2;
        mergesort (tFrequencies, l , m ) ;          // Sort first half
        mergesort (tFrequencies, m + 1 , r ) ;    // Sort second half
        merge (tFrequencies, l , m , r ) ;            // Merge
    }

     private static void merge (Count[] tFrequencies, int l, int m, int r) {
    Count[] B = new Count[r - l + 1];
    int i = l, j = m + 1, k = 0;

    while (i <= m && j <= r) {
        if (tFrequencies[i].frequencyCount >= tFrequencies[j].frequencyCount)  // Compare frequency counts
            B[k++] = tFrequencies[i++];
        else
            B[k++] = tFrequencies[j++];
    }

    while (i <= m)  // Copy remaining elements from left subarray
        B[k++] = tFrequencies[i++];

    while (j <= r)  // Copy remaining elements from right subarray
        B[k++] = tFrequencies[j++];

    for (k = 0; k < B.length; k++)
        tFrequencies[k + l] = B[k];
}// close method merge


}