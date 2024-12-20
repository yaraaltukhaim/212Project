public class BSTInvIndex {
            BST < String , Term> invertedindexBST; 
            int count = 0;

            public BSTInvIndex() {
                invertedindexBST= new BST< String , Term> ();
            }

            public int size()
            {
                return invertedindexBST.count;
            }
            
            public boolean addTerm (int docID, String word)
            {
               if (invertedindexBST.empty())
               {
                   count ++;
                   Term t = new Term ();
                   t.setTermWord(new ProcessedWord (word));
                   t.markDocumentPresence(docID);
                   invertedindexBST.insert(word, t);
                   return true;
               }
               else
               {
                    if (invertedindexBST.find(word))
                    {
                        Term t = this.invertedindexBST.retrieve();
                        t.markDocumentPresence(docID);
                        invertedindexBST.update(t);
                        return false;
                        
                    }
                    
                   count ++;
                   Term t = new Term ();
                   t.setTermWord(new ProcessedWord (word));
                   t.markDocumentPresence(docID);
                   invertedindexBST.insert(word, t);
                    return true;
           }
        }

        public boolean TermExists(String word)//found
        {
               return invertedindexBST.find(word);
        }
        
        public void DisplayInverted()
        {
            invertedindexBST.Traverse();
        }
       
        public boolean [] SearchWithLogic (String str )
        {
            if (! str.contains(" OR ") && ! str.contains(" AND "))
            {
                boolean [] r1 = new boolean[50];
                str = str.toLowerCase().trim();
            
                if (this.TermExists (str))
                    r1 =  this.invertedindexBST.retrieve().getDocumentMapping();
                return r1;
            }
            
            else if (str.contains(" OR ") && str.contains(" AND "))
            {
                String [] AND_ORs = str.split(" OR ");//OR less precedence
                boolean []  r1 = AndQuery (AND_ORs[0]);
               
              
              
                for ( int i = 1 ; i < AND_ORs.length ; i++  ){ //open loop
                  
                    boolean [] r2 =AndQuery (AND_ORs[i]);
                    
                    for ( int j = 0 ; j < 50 ; j++ )
                        r1 [j] = r1[j] || r2[j];
                   
                }
                return r1;
            }
            
            else  if (str.contains(" AND "))
                return AndQuery (str);
            
            return OrQuery (str);
        }
        
        public boolean [] AndQuery (String str)
        {
            String [] ANDs = str.split(" AND ");
            boolean [] b1 = new boolean [50];
            
            if (this.TermExists (ANDs[0].toLowerCase().trim()))
                b1 = this.invertedindexBST.retrieve().getDocumentMapping();

          
          for ( int i = 1 ; i< ANDs.length ; i++)
            {
                boolean [] b2 = new boolean [50];
                if (this.TermExists (ANDs[i].toLowerCase().trim()))
                    b2 = this.invertedindexBST.retrieve().getDocumentMapping();
                
                for ( int j = 0 ; j < 50 ; j++)
                    b1 [j] = b1[j] && b2[j];
               
            }
            return b1;
        }
        
        public boolean [] OrQuery (String str)
        {
            String [] ORs = str.split(" OR ");
            boolean [] b1 = new boolean [50];
            
            if (this.TermExists (ORs[0].toLowerCase().trim()))
                b1 = this.invertedindexBST.retrieve().getDocumentMapping();

           
           for ( int i = 1 ; i< ORs.length ; i++)
            {
                boolean [] b2 = new boolean [50];
                if (this.TermExists (ORs[i].toLowerCase().trim()))
                    b2 = this.invertedindexBST.retrieve().getDocumentMapping();
                
                for ( int j = 0 ; j < 50 ; j++)
                    b1 [j] = b1[j] || b2[j];
               
               
            }
            return b1;
        }
   
    
}//close class
