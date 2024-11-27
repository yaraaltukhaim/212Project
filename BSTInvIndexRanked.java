import java.util.function.Function;

public class BSTInvIndexRanked {
            class frequency{//representing the frequency of a term in a document
                int docID = 0;
                int f = 0;    
               }
    
           BST <Integer, BST <String,Rank>> BSTrank; 
           frequency [] freqs = new frequency[50];
            
            
            
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
            
            for ( int docID = 0 ; docID < 50 ; docID++ )
            {
                int count = 0 ;
                for ( int j = 0 ;j < words.length ; j++ )
                    count += this.getrank(docID, words[j]);
                if (count > 0)
                {
                   freqs[index] = new frequency();
                    freqs[index].docID = docID;
                    freqs[index].f = count;
                    index ++;
                }//end if 
               
            }//end loop
            
           
            
            mergesort(freqs, 0, index-1 );
                
            for ( int x = 0 ; x < index ; x++)
                System.out.println(freqs[x].docID + "\t\t" + freqs[x].f);
        }//end TF


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

}
