// public class AVLInvIndexRanked {
//             
//             AVLTree<Integer, AVLTree<String,Rank> >AVLrank; 
//             Count[] tFrequencies = new Count[50];
//             
//             public AVLInvIndexRanked(){
//             AVLrank = new AVLTree <Integer, AVLTree <String,Rank>>();
//             }
//             
//              class Count {//representing the frequency of a term in a document
// 
//         int documentID ,frequencyCount;  
//     }
// 
//         public boolean addTerm(int dID, String W)
//             {
//                AVLTree <String,Rank> miniRank= new AVLTree <String,Rank>();
//                
//                // If the index is empty, add the new term directly
//                 if (AVLrank.empty()){ 
//                 
//                 miniRank.insert(W, new Rank (W,1));
//                 AVLrank.insert(dID, miniRank);
//                 return true;
//                }else{//checks if the term already exists
//             
//                     if (AVLrank.find(dID))
//                     {
//                         
//                         if (miniRank.find(W))
//                         {
//                             
//                             Rank rank = miniRank.retrieve();
//                             rank.incrementFrequency();
//                             miniRank.update(rank);
//                             AVLrank.update(miniRank);
//                             return false;
//                         }
//                         //  document available , word unavailable 
//                         miniRank.insert(W, new Rank (W , 1));
//                         AVLrank.update(miniRank);
//                         return true;
//                     }
//                     // document unavailable 
//                    
//                    miniRank.insert(W, new Rank (W,1));
//                    
//                    AVLrank.insert(dID, miniRank);
//                    return true;
//                }
//         } 
// 
// 
//          public boolean found(int dID, String W)
//         {
//                if (AVLrank.find(dID) )
//                   if (AVLrank.retrieve().find(W))
//                       return true;
//                return false;
//         }
//         
//         public int getrank (int dID, String W)
//         {
//             int value = 0;
//                if (AVLrank.find(dID) )
//                   if (AVLrank.retrieve().find(W))
//                       return AVLrank.retrieve().retrieve().getFrequency();
//                return value;
//             
//         }
//         public void printDocument()
//         {
//                 AVLrank.TraverseT();
//         }
// 
//               public void TF(String W)////////////stoped here
//         {
//             W = W.toLowerCase().trim();
//             String [] words = W.split(" ");
//             int index = 0;
//             for (int i = 0; i < 50; i++)
//             {
//               int count = 0 ;
//                 for ( int j = 0 ;j < words.length ; j++ )
//                     count += this.getrank(i, words[j]);
//                 if (count > 0){
// 
//                tFrequencies[index] = new Count();
//                 tFrequencies[index].documentID = i;
//                 tFrequencies[index].frequencyCount = 0;
//                 index++;
//                 }
//             }//close for loop
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
//      private static void merge (Count[] tFrequencies, int l, int m, int r) {
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
// 
// }


public class AVLInvIndexRanked {

    private AVLTree<ProcessedWord, Rank> avlRank; // AVL tree for ranked terms

    public AVLInvIndexRanked() {
        avlRank = new AVLTree<>();
    }

    // Add a term with its document ID
    public boolean addTerm(int docID, String word) {
        Rank rank = avlRank.find(new ProcessedWord(word)) ? avlRank.retrieve() : null;

        if (rank != null) {
            rank.incrementRank(); // Increment rank if term exists
            avlRank.update(rank);
            return false; // Term already exists
        } else {
            Rank newRank = new Rank(word, 1);
            newRank.markDocumentPresence(docID);
            avlRank.insert(new ProcessedWord(word), newRank);
            return true; // New term added
        }
    }

    // Check if a term exists
    public boolean termExists(String word) {
        return avlRank.find(new ProcessedWord(word));
    }

    // Retrieve the rank of a term
    public int getRank(String word) {
        if (termExists(word)) {
            return avlRank.retrieve().getRank();
        }
        return 0; // Term not found
    }

    // Retrieve term frequency
    public void calculateTermFrequency(String word) {
        if (termExists(word)) {
            Rank rank = avlRank.retrieve();
            rank.calculateFrequency();
        } else {
            System.out.println("Term not found in index.");
        }
    }

    // Print data in the AVL tree
    public void printData() {
        avlRank.TraverseT();
    }

    // Merge sort frequencies
    public Frequency[] sortFrequencies(Frequency[] frequencies) {
        if (frequencies.length <= 1) {
            return frequencies;
        }

        int mid = frequencies.length / 2;
        Frequency[] left = new Frequency[mid];
        Frequency[] right = new Frequency[frequencies.length - mid];

        System.arraycopy(frequencies, 0, left, 0, mid);
        System.arraycopy(frequencies, mid, right, 0, frequencies.length - mid);

        return merge(sortFrequencies(left), sortFrequencies(right));
    }

    private Frequency[] merge(Frequency[] left, Frequency[] right) {
        Frequency[] result = new Frequency[left.length + right.length];
        int i = 0, j = 0, k = 0;

        while (i < left.length && j < right.length) {
            if (left[i].compareTo(right[j]) >= 0) {
                result[k++] = left[i++];
            } else {
                result[k++] = right[j++];
            }
        }

        while (i < left.length) {
            result[k++] = left[i++];
        }

        while (j < right.length) {
            result[k++] = right[j++];
        }

        return result;
    }

    // Traverse the AVL tree
    public void traverseAVL() {
        avlRank.Traverse();
    }
}
