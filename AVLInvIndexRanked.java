public class AVLInvIndexRanked {

           class frequency//representing the frequency of a term in a document
            {
                int docID = 0;
                int f = 0;
            }
            
            AVLTree<Integer, AVLTree<String,Rank> >AVLrank; 
            frequency [] freqs = new frequency[50];
            
            public AVLInvIndexRanked(){
            AVLrank = new AVLTree <Integer, AVLTree <String,Rank>>();
            }
            
       

        public boolean addTerm(int dID, String W)
            {
               AVLTree <String,Rank> miniRank= new AVLTree <String,Rank>();
               
               // If the index is empty, add the new term directly
                if (AVLrank.empty()){ 
                
                miniRank.insert(W, new Rank (W,1));
                AVLrank.insert(dID, miniRank);
                return true;
               }else{//checks if the term already exists
            
                    if (AVLrank.find(dID))
                    {
                        
                        if (miniRank.find(W))
                        {
                            
                            Rank rank = miniRank.retrieve();
                            rank.incrementFrequency();
                            miniRank.update(rank);
                            AVLrank.update(miniRank);
                            return false;
                        }
                        //  document available , word unavailable 
                        miniRank.insert(W, new Rank (W , 1));
                        AVLrank.update(miniRank);
                        return true;
                    }
                    // document unavailable 
                   
                   miniRank.insert(W, new Rank (W,1));
                   
                   AVLrank.insert(dID, miniRank);
                   return true;
               }
        } 


         public boolean found(int dID, String W)
        {
               if (AVLrank.find(dID) )
                  if (AVLrank.retrieve().find(W))
                      return true;
               return false;
        }
        
        public int getrank (int dID, String W)
        {
            int value = 0;
               if (AVLrank.find(dID) )
                  if (AVLrank.retrieve().find(W))
                      return AVLrank.retrieve().retrieve().getFrequency();
               return value;
            
        }
        public void printDocument()
        {
                AVLrank.TraverseT();
        }

              public void TF(String W)////////////stoped here
        {
            W = W.toLowerCase().trim();
            String [] words = W.split(" ");
            int index = 0;
            for (int i = 0; i < 50; i++)
            {
              int count = 0 ;
                for ( int j = 0 ;j < words.length ; j++ )
                    count += this.getrank(i, words[j]);
                if (count > 0){

                    freqs[index] = new frequency();
                    freqs[index].docID = i;
                    freqs[index].f = count;
                    index ++;
                }
            }//close for loop
            
            mergesort(freqs, 0, index-1 );
            
            for ( int x = 0 ; x < index ; x++)
                System.out.println(freqs[x].docID + "\t\t" + freqs[x].f);    
      }//close method

        
      public static void mergesort (frequency [] A , int l , int r ) 
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



}/// close class