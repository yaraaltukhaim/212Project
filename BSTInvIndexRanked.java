import java.util.function.Function;

public class BSTInvIndexRanked {
            class Count{//representing the frequency of a term in a document
            
                int documentID = 0;
                int freqCount = 0;
            }
    
           BST <Integer, BST <String,Rank>> BSTrank; 
           Count [] freqs = new Count[50];
            
            
            
            public BSTInvIndexRanked() {
                BSTrank = new BST <Integer, BST <String,Rank>>();
                
            }

            public boolean addTerm (int docID, String word)
            {
                 BST <String,Rank> miniRank= new BST <String,Rank>();
                 
                 // If the index is empty, add the new term directly
               if (BSTrank.empty()){
               
                  
                   miniRank.insert(word, new Rank (word,1));
                   
                   BSTrank.insert(docID, miniRank);
                   return true;
               }
               else//checks if the term already exists
               {
                    if (BSTrank.find(docID))
                    {
                         miniRank= BSTrank.retrieve();
                        if (miniRank.find(word))
                        {
                           
                            Rank rank = miniRank.retrieve();
                            rank.incrementFrequency();
                            miniRank.update(rank);
                            BSTrank.update(miniRank);
                            return false;
                        }
                        //  document available , word unavailable 
                        miniRank.insert(word, new Rank (word , 1));
                        BSTrank.update(miniRank);
                        return true;
                    }
                   
                 
                  
                  // document unavailable 
                   miniRank.insert(word, new Rank (word,1));
                   
                   BSTrank.insert(docID, miniRank);
                   return true;
               }
        }

        public boolean found(int docID, String word)
        {
               if (BSTrank.find(docID) )
                  if (BSTrank.retrieve().find(word))
                      return true;
               return false;
        }
        
        public int getrank (int docID, String word)
        {
            int value = 0;
               if (BSTrank.find(docID) )
                  if (BSTrank.retrieve().find(word))
                      return BSTrank.retrieve().retrieve().getFrequency();
               return value;
            
        }
        public void DisplayInverted()
        {
                BSTrank.TraverseT();
        }

        
        public void TF(String str)
        {
            str = str.toLowerCase().trim();
            String [] words = str.split(" ");
            
            int index = 0;
            int docID = 0 ;
            while ( docID < 50 )
            {
                int count = 0 ;
                for ( int j = 0 ;j < words.length ; j++ )
                    count += this.getrank(docID, words[j]);
                if (count > 0)
                {
                    freqs[index] = new Count();
                    freqs[index].documentID = docID;
                    freqs[index].freqCount = count;
                    index ++;
                }//end if 
                docID++;
            }//end while
            
           
            
            mergesort(freqs, 0, index-1 );
                
           
            int x = 0;
            while ( x < index )
            {
                System.out.println(freqs[x].documentID + "\t\t" + freqs[x].freqCount);
            x++;
            }
        }//end TF


    public static void mergesort ( Count [] A , int l , int r ) 
    {
        if ( l >= r )
            return;
        int m = ( l + r ) / 2;
        mergesort (A , l , m ) ;          // Sort first half
        mergesort (A , m + 1 , r ) ;    // Sort second half
        merge (A , l , m , r ) ;            // Merge
    }

    private static void merge ( Count [] A , int l , int m , int r ) 
    {
        Count [] B = new Count [ r - l + 1];
        int i = l , j = m + 1 , k = 0;
    
        while ( i <= m && j <= r )
        {
            if ( A [ i ].freqCount >= A [ j ].freqCount)// Compare frequency counts
                B [ k ++] = A [ i ++];
            else
                B [ k ++] = A [ j ++];
        }
        
        if ( i > m )
            while ( j <= r )// Copy remaining elements from right subarray
                B [ k ++] = A [ j ++];
        else
            while ( i <= m )// Copy remaining elements from left subarray
                B [ k ++] = A [ i ++];
        
        for ( k = 0; k < B . length ; k ++)
            A [ k + l ] = B [ k ];
    }

}
