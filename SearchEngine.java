import java.io.File;
import java.util.Scanner;

public class SearchEngine {//read data
    int tokens = 0;//with repetition
    int vocab = 0;//without repetition (unique)
    
    Index index;
    InvIndex invertedindex;
    BSTInvIndex invertedindexBST;
    AVLInvIndex invertedindexAVL;
    
    IndexRanked  indexranked;
    InvIndexRanked  invertedindexranked;
    BSTInvIndexRanked invertedindexBSTranked;
    AVLInvIndexRanked invertedindexAVLranked;
    
    
    public SearchEngine ()
    {
        index = new Index();
        invertedindex = new InvIndex();
        invertedindexBST = new BSTInvIndex ();
        invertedindexAVL = new AVLInvIndex();
        
        indexranked = new IndexRanked();
        invertedindexranked = new InvIndexRanked();
        invertedindexBSTranked = new BSTInvIndexRanked();
        invertedindexAVLranked = new AVLInvIndexRanked();
    }
    
   
    public void ReadData (String stopFile, String fileName)
    {
            try{
                File stopfile = new File (stopFile);
                Scanner reader = new Scanner (stopfile).useDelimiter("\\Z");
                String stops = reader.next();
                
                stops = stops.replaceAll("\n", " ");//made it one line (one statement)
                
                File docsfile = new File(fileName);
                Scanner reader1 = new Scanner (docsfile);
                String line = reader1.nextLine(); //to ignore the first line
                
                
                int lineID=0;
                
                   while ( lineID <50 ) 
                {
                    line = reader1.nextLine().toLowerCase();
                    
                    int pos = line.indexOf(',');
                    int docID = Integer.parseInt( line .substring(0, pos));

                    String data = line.substring(pos+1, line.length() - pos).trim();//after (,)
                    data = data.substring(0, data.length() - 1);//delete (")

                    data = data.toLowerCase();
                    data =  data.replaceAll("[\']", "");
                    data = data.replaceAll("[^a-zA-Z0-9]", " ").trim() ;// ^ means if not in this set then replace it + trim delete the spaces at start and end

                    String [] words = data.split(" ");

                    for (int i = 0; i < words.length ; i++)
                    {
                        String word = words[i].trim();
                
                        if ( word.compareToIgnoreCase("") != 0)
                            tokens ++;

                        if ( ! stops.contains(word + " ")) // space to not delete(stop) all words that contains word substring (ai->aint)
                        { 
                            this.index.addtoDocument(docID, word);
                            this.invertedindex.addTerm(docID, word);
                            this.invertedindexBST.addTerm(docID, word);
                            this.invertedindexAVL.addTerm(docID, word);

                            this.indexranked.addtoDocument(docID, word);
                            this.invertedindexranked.addTerm(docID, word);
                            this.invertedindexBSTranked.addTerm(docID, word);
                            this.invertedindexAVLranked.addTerm(docID, word);
                        }
                    }

                  
                    lineID++;
                }
                
               
                
                
                 vocab = invertedindexAVL.getSize();
      
               // System.out.println("tokens " + tokens); 
               // System.out.println("vocabs " + vocab);
                
                reader.close();//stopfile
                reader1.close();//docsfile
            }
            catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
    }
    
    public boolean []  BooleanRetrieval(String str , int DSType)
    {
        boolean [] docs = new boolean [50] ;
        for ( int i = 0 ; i < docs.length ; i++)
            docs[i] = false;
  
        switch (DSType)
        {
            case 1 :
                System.out.println(" Boolean_Retrieval using index list");
                docs = this.index.AND_OR_Function (str);
                break;
            case 2:    
                System.out.println(" Boolean_Retrieval using inverted index list");
                docs = this.invertedindex.searchWithLogic(str);
                break;
            case 3:
                System.out.println(" Boolean_Retrieval using BST");
                docs = this.invertedindexBST.SearchWithLogic(str);
                break;
            case 4:
                System.out.println(" Boolean_Retrieval using AVL");
                docs = this.invertedindexAVL.searchWithLogic(str);
                break;
            default :
                System.out.println("Bad data structure");
                
        }
        return docs;
    }
        
    public void IndexRanked(String str)
    {
        this.indexranked.TF(str);
    }

    public void RetrievalInvertedIndexRanked(String str)
    {
        this.invertedindexranked.TF(str);
    }

    public void RetrievalBSTRanked(String str)
    {
        this.invertedindexBSTranked.TF(str);
    }

    public void RetrievalAVLRanked(String str)
    {
        this.invertedindexAVLranked.TF(str);
    }
    
   
    
    public boolean []  TermRetrieval(String str , int DSType)
    {
        boolean [] docs = new boolean [50] ;
        for ( int i = 0 ; i < docs.length ; i++)
            docs[i] = false;

        switch (DSType)
        {
            case 1 :
                docs = index.findDocuments(str);
                break;
            case 2 :
               if (invertedindex.TermExists(str))
                    docs = invertedindex.Invindex.retrieve().getDocumentMapping();
                break;
             case 3:
                if (invertedindexBST.TermExists(str))
                    docs = invertedindexBST.invertedindexBST.retrieve().getDocumentMapping();
                break;
            case 4:
                if (invertedindexAVL.found(str))
                    docs = invertedindexAVL.InvIndexAVL.retrieve().getDocumentMapping();
                break;
            default :
                System.out.println("Bad data structure");
        }
        return docs;
    }
    
    public void DocumentsIndexed()
    {
        System.out.println("All Documents with the number of words in them ");
        int i = 0 ;
         while ( i < 50 )
        {
            int size = index.documents[i].index.size();
            System.out.println("Document# " + i +"  with size(" +  size + ")"  );
            i++;
        }
       
        
    }
    
    public void TokensIndexed()
    {
        System.out.println("All tokens with the documents appear in it ");
        invertedindexBST.invertedindexBST.PrintData();
        invertedindexAVL.InvIndexAVL.PrintData();
    }
    

}
