import java.util.NoSuchElementException;


public class AVLTree<K extends Comparable<K>, T>{ //comparble is a class that has a compareto func cuz we cant compare strings normally so we use it


              class AVLNode<K extends Comparable<K>, T> {
                public K key;
                public T data;
                AVLNode<K,T> parent; // pointer to the parent
                AVLNode<K,T> left; // pointer to left child
                AVLNode<K,T> right; // pointer to right child
                int bf; // balance factor of the node

                    /** Creates a new instance of AVLNode */
                    public AVLNode() {
                            this.key = null;  
                            this.data = null;
                            this.parent = this.left = this.right = null;
                            this.bf = 0;
                    }

                    public AVLNode(K key, T data) {
                            this.key = key  ;  
                            this.data = data;
                            this.parent = this.left = this.right = null;
                            this.bf = 0;
                    }

                    public AVLNode(K key, T data, AVLNode<K,T> p, AVLNode<K,T> l, AVLNode<K,T> r){
                            this.key = key  ;  
                            this.data = data;
                            left = l;
                            right = r;
                            parent = p;
                            bf =0;
                    }

                    public AVLNode<K,T> getLeft()
                    {
                        return left;
                    }

                    public AVLNode<K,T> getRight()
                    {
                        return right;
                    }

                    public T getData()
                    {
                        return data;
                    }
                    
                    @Override
                    public String toString() {
                        return "AVL Node{" + "key=" + key + ", data =" + data + '}';
                    }
            }

        //=============================================================================    
        private AVLNode<K,T> root;
        private AVLNode<K,T>  curr;
        private int count; //she added
        
        public AVLTree() {
                root = curr = null;
                count = 0;
        }

        public boolean empty() {
            return root == null;
        }

        public int size() {
            return count;
        }


         // Removes all elements in the map.
        public void clear()
        {
            root = curr = null;
            count = 0;
        }

        // Return the key and data of the current element
        public T retrieve()
        {
            T data =null;
            if (curr != null)
                data = curr.data;
            return data;
        }

        // Update the data of current element.
        public void update(T e)
        {
            if (curr != null)
                curr.data = e;
        }

        //searches for the key in the AVL, returns the data or null (if not found).
        private T searchTreeHelper(AVLNode<K,T> node, K key) {
                   // Place your code here\\
                    if (node == null)
                        return null;
                    else if (node.key.compareTo(key) ==0) 
                    {
                        curr = node;
                        return node.data;
                    } 
                    else if (node.key.compareTo(key) >0)
                         return searchTreeHelper(node.left, key);
                    else
                         return searchTreeHelper(node.right, key);
        }
        
        // update the balance factor the node
        private void updateBalance(AVLNode<K,T> node) {
                if (node.bf < -1 || node.bf > 1) {
                        rebalance(node);
                        return;
                }

                if (node.parent != null) {
                        if (node == node.parent.left) {
                                node.parent.bf -= 1;
                        } 

                        if (node == node.parent.right) {
                                node.parent.bf += 1;
                        }

                        if (node.parent.bf != 0) {
                                updateBalance(node.parent);
                        }
                }
        }

        // rebalance the tree
        void rebalance(AVLNode<K,T> node) {
                if (node.bf > 0) {
                        if (node.right.bf < 0) {
                                rightRotate(node.right);
                                leftRotate(node);
                        } else {
                                leftRotate(node);
                        }
                } else if (node.bf < 0) {
                        if (node.left.bf > 0) {
                                leftRotate(node.left);
                                rightRotate(node);
                        } else {
                                rightRotate(node);
                        }
                }
        }

        public boolean find(K key) {
                T data = searchTreeHelper(this.root, key);
                if ( data != null)
                    return true;
                return false;
        }

        // rotate left at node x
        void leftRotate(AVLNode<K,T> x) {
            AVLNode<K,T> y = x.right;
            x.right = y.left;
            if (y.left != null) {
                    y.left.parent = x;
            }
            
            y.parent = x.parent;
            if (x.parent == null) {
                    this.root = y;
            } else if (x == x.parent.left) {
                    x.parent.left = y;
            } else {
                    x.parent.right = y;
            }
            y.left = x;
            x.parent = y;

            // update the balance factor
            x.bf = x.bf - 1 - Math.max(0, y.bf);
            y.bf = y.bf - 1 + Math.min(0, x.bf);
        }

        // rotate right at node x
        void rightRotate(AVLNode<K,T> x) {
                AVLNode<K,T> y = x.left;
                x.left = y.right;
                if (y.right != null) {
                        y.right.parent = x;
                }
                y.parent = x.parent;
                if (x.parent == null) {
                        this.root = y;
                } else if (x == x.parent.right) {
                        x.parent.right = y;
                } else {
                        x.parent.left = y;
                }
                y.right = x;
                x.parent = y;

                // update the balance factor
                x.bf = x.bf + 1 - Math.min(0, y.bf);
                y.bf = y.bf + 1 + Math.max(0, x.bf);
        }

        
        
        public boolean insert(K key, T data) {
            AVLNode<K,T> node = new AVLNode<K,T>(key, data);

            AVLNode<K,T> p = null;
            AVLNode<K,T> current = this.root;

            while (current != null) {
                    p = current;
                    if (node.key.compareTo(current.key) ==0) {
                            return false;
                    }else if (node.key.compareTo(current.key) <0 ) {
                            current = current.left;
                    } else {
                            current = current.right;
                    }
            }
            // p  is parent of current
            node.parent = p;
            if (p == null) {
                    root = node;
                    curr = node;
            } else if (node.key.compareTo(p.key) < 0 ) {
                    p.left = node;
            } else {
                    p.right = node;
            }
            count ++;

            //  re-balance the node if necessary
            updateBalance(node);
            return true;        
        }
        
    public boolean remove(K key) { //we didnt use i thinj
        K k1 = key;
        AVLNode<K,T>  p = root;
        AVLNode<K,T>  q = null; // Parent of p

        while (p != null) 
        {
                if (k1.compareTo(p.key) <0)
                {
                    q =p;
                    p = p.left;
                } 
                else if (k1.compareTo(p.key) >0)
                {    
                    q = p;
                    p = p.right;
                }
                else 
                { // Found the key
                    // Check the three cases
                    if ((p.left != null) && (p.right != null)) 
                    { 
                        // Case 3: two children
                        // Search for the min in the right subtree
                        AVLNode<K,T> min = p.right;
                        q = p;
                        while (min.left != null) 
                        {
                            q = min;
                            min = min.left;
                        }
                        p.key = min.key;
                        p.data = min.data;
                        k1 = min.key;
                        p = min;
                        // Now fall back to either case 1 or 2
                    }
                    // The subtree rooted at p will change here
                    if (p.left != null) 
                    { 
                        // One child
                        p = p.left;
                    } 
                    else 
                    { 
                        // One or no children
                        p = p.right;
                    }
                    if (q == null)
                    { 
                        // No parent for p, root must change
                        root = p;
                        this.updateBalance(p);
                    } 
                    else 
                    {
                        if (k1.compareTo(q.key) <0)
                            q.left = p;
                        else 
                            q.right = p;
                        this.updateBalance(q);
                    }
                    count--;
                    curr = root;
                    return true;    
            } 
        } // end while (p != null) 
        return false;
    }

       //============================================================================
       public void Traverse()
        {
            if (root != null)
                traverseTree(root); //revursive to prunt
        }
        
        private void traverseTree (AVLNode<K,T> node  )
        {
            if (node == null)
                return;
            traverseTree( node.left);
            System.out.println(node.data);
            traverseTree( node.right);
            
        }

        
       //=========================================================================== 
       public void TraverseT() //need this for ranked avl cuz inside the avl the node says 3 but inside it has the words and their rankig

        {
            if (root != null)
                traverseTreeT(root);
        }
        
        private void traverseTreeT (AVLNode<K,T> node) //class tree up to traverse normal tree inside  tree of tree 
        {
            if (node == null)
                return;
            traverseTreeT( node.left );
            if (node.getData() instanceof AVLTree )
            {
                System.out.println( "Node key ==== "+ node.key);
                ((AVLTree <String,Rank>) node.getData()).Traverse();
            }
            else
                System.out.println(node.data);
            
            traverseTreeT( node.right);
            
        }
        
       //=========================================================================== 
       public void PrintData()
        {
            if (root != null)
                PrintData_(root);
        }
        
        private void PrintData_ (AVLNode<K,T> node)
        {
            if (node == null)
                return;
            PrintData_( node.left );
           
           System.out.print(node.key);
            if (node.getData() instanceof Term )
            {
                System.out.print("   docs: ");
                boolean [] docs = ((Term) node.data).getDocumentMapping(); //getdocs
                for ( int i  = 0 ; i < 50 ; i++)
                    if ( docs[i])
                        System.out.print( " " + i + " " );
                System.out.println("");
            }
                
            
            PrintData_( node.right);
            
        }

        
        
}