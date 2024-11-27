import java.io.File;
import java.util.Scanner;

public class SearchEngine {
    int tokens = 0;
    int vocab = 0;
    
    Index index;
    InvIndex invertedindex;
    BSTInvIndex invertedindexBST;
    AVLInvIndex invertedindexAVL;
    
    IndexRanked  indexranked;
    InvIndexRanked  invertedindexranked;
    BSTInvIndexRanked  invertedindexBSTranked;
    AVLInvIndexRanked  invertedindexAVLranked;
    
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
                
                stops = stops.replaceAll("\n", " ");
                
                File docsfile = new File(fileName);
                Scanner reader1 = new Scanner (docsfile);
                String line = reader1.nextLine();
                
                for ( int lineID = 0 ; lineID <50 ; lineID ++ ) 
                {
                    line = reader1.nextLine().toLowerCase();
                    
                    int pos = line.indexOf(',');
                    int docID = Integer.parseInt( line .substring(0, pos));

                    String data = line.substring(pos+1, line.length() - pos).trim();
                    data = data.substring(0, data.length() - 1);

                    data = data.toLowerCase();
                    data =  data.replaceAll("[\']", " ");
                    data = data.replaceAll("[^a-zA-Z0-9]", " ").trim() ;

                    String [] words = data.split(" "); // --1

                    for (int i = 0; i < words.length ; i++)
                    {
                        String word = words[i].trim(); //--2
                
                        if ( word.compareToIgnoreCase("") != 0)
                            tokens ++;

                        if ( ! stops.contains(word + " ")) //--3
                        { 
                            this.index.addDocument(docID, word);
                            this.invertedindex.addTerm(docID, word);
                            this.invertedindexBST.addTerm(docID, word);
                            this.invertedindexAVL.addTerm(docID, word);

                            this.indexranked.addDocument(docID, word);
                            this.invertedindexranked.addTerm(docID, word);
                            this.invertedindexBSTranked.addTerm(docID, word);
                            this.invertedindexAVLranked.addTerm(docID, word);
                        }
                    }
                 }
                    
                
               vocab = invertedindexAVL.invertedindexAVL.size();
      
                System.out.println("tokens " + tokens);
                System.out.println("vocabs " + vocab);
                
                reader.close();
                reader1.close();
            }//close try
            catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
    }//close method
    
    public boolean []  BooleanRetrieval(String str , int DSType)
    {
        boolean [] docs = new boolean [50] ;
        for ( int i = 0 ; i < docs.length ; i++)
            docs[i] = false;
  
        switch (DSType)
        {
            case 1 :
                System.out.println(" Boolean_Retrieval using index list");
                docs = this.index.AND_OR_Function(str);
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
                    docs = invertedindex.invertedindex.retrieve().getDocumentMapping();
                break;
             case 3:
                if (invertedindexBST.TermExists(str))
                    docs = invertedindexBST.invertedindexBST.retrieve().getDocumentMapping();
                break;
            case 4:
                if (invertedindexAVL.found(str))
                    docs = invertedindexAVL.invertedindexAVL.retrieve().getDocumentMapping();
                break;
            default :
                System.out.println("Bad data structure");
        }
        return docs;
    }
    
    public void DocumentsIndexed()
    {
        System.out.println("All Documents with the number of words in them ");
        for ( int i = 0 ; i < 50 ; i++ )
        {
            int size = index.indexes[i].index.size();
            System.out.println("Document# " + i +"  with size(" +  size + ")"  );
        }
        
    }
    
    public void TokensIndexed()
    {
        System.out.println("All tokens with the documents appear in it ");
        invertedindexBST.invertedindexBST.PrintData();
        invertedindexAVL. invertedindexAVL.PrintData();
    }
}
