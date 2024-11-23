// public class IndexRanked { 
//     
//     class Document { //class representing a document and the words it contains
//            LinkedList <String> index; 
//            int documentID;
//             
// 
//             public Document() { // defualt constructor
//                index = new LinkedList <String>();
//                documentID = 0;
//                
//             }
//           public boolean wordExists(String word){  // Checks if a specific word exists in the document
//            
//                if (index.empty())
//                    return false;
// 
//                index.findFirst();
//                int i = 0;
//                while(i < index.size){//open loop
// 
//                    if ( index.retrieve().compareTo(word) == 0)
//                        return true;
//                   index.findNext();
//                   i++;
//                }//close loop
//                return false; // when the word is not found
//            }//close method
//            
//             public void insertNew(String W){ // Adds a new word to the document's word list
//             
//                 index.insert(W);
//             }
//           }
//           class Count {//representing the frequency of a term in a document
// 
//         int documentID ,frequencyCount;  
//     }
//     
//      Document [] documents;
//     Count[] tFrequencies;
//     
//     public IndexRanked() {
//     documents = new Document[50];
//     tFrequencies = new Count[50];
// 
//         for ( int i = 0 ; i < documents.length ; i++){// Initialize each document with a unique ID
//             documents [i] = new Document();  
//             documents [i].documentID = i;
//             }       
//     }
//      public  boolean [] findDocuments(String W){ // Returns a boolean array indicating which documents contain the specified word
//     
//     // Create the flag array dynamically based on the number of documents
//     boolean[] flag = new boolean[50];
// 
//     // Iterate through the documents and update the flags where the word exists
//     for (int i = 0; i < flag.length; i++) {
//         flag[i] = documents[i].wordExists(W); 
//     }
// 
//     return flag;
//     }//close method findDocuments
//    
//    public void addtoDocument ( int dID, String W){// Adds a word to a specific document
//           if (dID >= 0 && dID < documents.length){
//             documents[dID].insertNew(W);
//             }else{ 
//         System.out.println("Invalid document ID: " + dID);    
//         } 
//     }
//     
//      public void TF(String W)
//         {
//             W = W.toLowerCase().trim();
//             String [] words = W.split(" ");
//             tFrequencies = new Count[50];
//             for (int i = 0; i < 50; i++)
//             {
//                 tFrequencies[i] = new Count();
//                 tFrequencies[i].documentID = i;
//                 tFrequencies[i].frequencyCount = 0;
//             }//close for loop
//             
//             for (int docIndex = 0; docIndex < 50; docIndex++)
//             {
//                 for ( int i = 0 ; i < words.length ; i++)
//                 {
//                     documents[docIndex].index.findFirst(); 
//                     int wordCount = 0;
//                     
//                     for (int j = 0; j < documents[docIndex].index.size(); j++){
//                     
//                         if (documents[docIndex].index.retrieve().compareTo(words[i])==0)
//                             wordCount ++;
//                         documents[docIndex].index.findNext();
//                     }
//                     
//                     if (wordCount > 0)
//                         tFrequencies[docIndex].frequencyCount += wordCount;
//                 }
//             }
//             
//             mergesort(tFrequencies, 0, tFrequencies.length - 1);
//             
//             System.out.println("\nDocIDt\tScore");
//             int s = 0; 
//             while (s < tFrequencies.length && tFrequencies[s].frequencyCount != 0) {
//             System.out.println(tFrequencies[s].documentID + "\t\t" + tFrequencies[s].frequencyCount);
//             s++; 
//          }      
//       }//close method
//       
//       public static void mergesort (Count[] tFrequencies , int l , int r ) 
//     {
//         if ( l >= r )
//             return;
//         int m = ( l + r ) / 2;
//         mergesort (tFrequencies, l , m ) ;          // Sort first half
//         mergesort (tFrequencies, m + 1 , r ) ;    // Sort second half
//         merge (tFrequencies, l , m , r ) ;            // Merge
//     }
// 
//    private static void merge (Count[] tFrequencies, int l, int m, int r) {
//     Count[] B = new Count[r - l + 1];
//     int i = l, j = m + 1, k = 0;
// 
//     while (i <= m && j <= r) {
//         if (tFrequencies[i].frequencyCount >= tFrequencies[j].frequencyCount)  // Compare frequency counts
//             B[k++] = tFrequencies[i++];
//         else
//             B[k++] = tFrequencies[j++];
//     }
// 
//     while (i <= m)  // Copy remaining elements from left subarray
//         B[k++] = tFrequencies[i++];
// 
//     while (j <= r)  // Copy remaining elements from right subarray
//         B[k++] = tFrequencies[j++];
// 
//     for (k = 0; k < B.length; k++)
//         tFrequencies[k + l] = B[k];
// }// close method merge
//     
//     public void printDocment (int dID){// Prints all words in a specific document
//     
//         if ( documents[dID].index.empty()){
//             System.out.println("Empty Document");
//             return;
//             }
//             
//             documents[dID].index.findFirst();
//             
//             for ( int i = 0; i< documents[dID].index.size ; i++)
//             {
//                 System.out.print (documents[dID].index.retrieve() + " ");
//                 documents[dID].index.findNext();
//             }//for
//         
//     }// close method print
// 
//      
// }//close class


public class IndexRanked {

    class Document { 
        LinkedList<String> index; // List of words in the document
        int documentID;

        public Document() { 
            index = new LinkedList<String>();
            documentID = 0;
        }

        public boolean wordExists(String word) {  
            if (index.empty()) 
                return false;

            index.findFirst();
            for (int i = 0; i < index.size(); i++) {
                if (index.retrieve().compareTo(word) == 0) 
                    return true;
                index.findNext();
            }
            return false; 
        }

        public void insertNew(String word) { 
            index.insert(word);
        }
    }

    class Count { 
        int documentID;
        int frequencyCount;
    }

    Document[] documents; 
    Count[] tFrequencies;

    public IndexRanked() {
        documents = new Document[50]; 
        tFrequencies = new Count[50];

        for (int i = 0; i < documents.length; i++) { 
            documents[i] = new Document();
            documents[i].documentID = i;
        }
    }

    public boolean[] findDocuments(String word) { 
        boolean[] flag = new boolean[50]; 

        for (int i = 0; i < flag.length; i++) { 
            flag[i] = documents[i].wordExists(word); 
        }

        return flag;
    }

    public void addtoDocument(int dID, String word) { 
        if (dID >= 0 && dID < documents.length) { 
            documents[dID].insertNew(word); 
        } else { 
            System.out.println("Invalid document ID: " + dID); 
        }
    }

    public void TF(String words) { 
        words = words.toLowerCase().trim(); 
        String[] wordArray = words.split(" "); 

        tFrequencies = new Count[50]; 

        for (int i = 0; i < 50; i++) { 
            tFrequencies[i] = new Count();
            tFrequencies[i].documentID = i;
            tFrequencies[i].frequencyCount = 0; 
        }

        for (int docIndex = 0; docIndex < 50; docIndex++) { 
            for (String word : wordArray) { 
                documents[docIndex].index.findFirst(); 
                int wordCount = 0; 

                for (int j = 0; j < documents[docIndex].index.size(); j++) { 
                    if (documents[docIndex].index.retrieve().compareTo(word) == 0) 
                        wordCount++;
                    documents[docIndex].index.findNext(); 
                }

                if (wordCount > 0) 
                    tFrequencies[docIndex].frequencyCount += wordCount; 
            }
        }

        mergesort(tFrequencies, 0, tFrequencies.length - 1); 

        System.out.println("\nDocID\tScore"); 
        int s = 0; 
        while (s < tFrequencies.length && tFrequencies[s].frequencyCount != 0) { 
            System.out.println(tFrequencies[s].documentID + "\t\t" + tFrequencies[s].frequencyCount); 
            s++; 
        }
    }

    public static void mergesort(Count[] tFrequencies, int l, int r) { 
        if (l >= r) 
            return;

        int m = (l + r) / 2; 
        mergesort(tFrequencies, l, m); 
        mergesort(tFrequencies, m + 1, r); 
        merge(tFrequencies, l, m, r); 
    }

    private static void merge(Count[] tFrequencies, int l, int m, int r) { 
        Count[] temp = new Count[r - l + 1]; 
        int i = l, j = m + 1, k = 0; 

        while (i <= m && j <= r) { 
            if (tFrequencies[i].frequencyCount >= tFrequencies[j].frequencyCount) 
                temp[k++] = tFrequencies[i++]; 
            else 
                temp[k++] = tFrequencies[j++]; 
        }

        while (i <= m) 
            temp[k++] = tFrequencies[i++]; 

        while (j <= r) 
            temp[k++] = tFrequencies[j++]; 

        System.arraycopy(temp, 0, tFrequencies, l, temp.length); 
    }

    public void printDocment(int dID) { 
        if (documents[dID].index.empty()) { 
            System.out.println("Empty Document"); 
            return; 
        }

        documents[dID].index.findFirst(); 
        for (int i = 0; i < documents[dID].index.size(); i++) { 
            System.out.print(documents[dID].index.retrieve() + " "); 
            documents[dID].index.findNext(); 
        }
    }
}
