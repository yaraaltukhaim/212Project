

public class BST<K extends Comparable<K>, T>{ //(comparable)compareTo function for objects and string

   // class BSTMapNode

   class BSTNode<K extends Comparable<K>, T> {
        public K key;
        public T data;
        public BSTNode<K,T> left, right;

      
        public BSTNode(K k, T val) {
                key = k;
                data = val;
                left = right = null;
        }

        public BSTNode(K k, T val, BSTNode<K,T> l, BSTNode<K,T> r) {
                key = k;
                data = val;
                left = l;
                right = r;
        }
    }
    
        BSTNode<K, T> root; 
        BSTNode<K, T > curr;
        int count ;
                
        public BST()
        {
            root = curr = null;
            count = 0;
        }
        
        
        public int size()
        {
            return count;
        }

        
        public boolean empty()
        {
            return root == null;
        }

        
        public void clear()
        {
            root = curr = null;
            count = 0;
        }

        // return key and data of the current element
        public T retrieve()
        {
            T data =null;
            if (curr != null)
                data = curr.data;
            return data;
        }

        // data of the current element
        public void update(T e)
        {
            if (curr != null)
                curr.data = e;
        }


        
        public boolean find(K key)
        {
            BSTNode<K,T> p = root;

            if(empty())
                    return false;

            while(p != null) {
                    if(p.key.compareTo(key) == 0) {
                            curr = p;
                            return true;
                    }
                    else if(key.compareTo(p.key) < 0)
                            p = p.left;
                    else
                            p = p.right;
            }
            return false;//if null
        }


      
        
        public boolean insert(K key, T data)
        {

            if(empty())
            {
                curr = root = new BSTNode <K, T> ( key, data);
                count ++;
                return true;
            }
            BSTNode<K,T> par = null;
            BSTNode<K,T> child  = root;
            
            while(child != null) {
                    if(child.key.compareTo(key) == 0) {
                            return false;//if found
                    }
                    else if(key.compareTo(child.key) < 0)
                    {
                        par = child;
                        child = child.left;
                    }
                    else
                    {
                        par = child;
                        child = child.right;
                    }
            }
           
            if(key.compareTo(par.key) < 0)
            {
                par.left = new BSTNode <K, T> ( key, data);
                curr = par.left;
            }
            
            else
            {
                par.right = new BSTNode <K, T> ( key, data);
                curr = par.right;
            }
            count ++;
            return true;
        }

       
        public boolean remove(K key)
        {
            Boolean removed = false; 
            BSTNode<K,T> p;
            
            p = remove_aux(key, root, removed);
            root = p;
            
            if (curr.key.compareTo(key) == 0)
                curr = root;
            if (removed)
                count -- ;
            
            return removed;
        }
    
        private BSTNode<K,T> remove_aux(K key, BSTNode<K,T> p, boolean flag) 
        {
            BSTNode<K,T> q, child = null;
            if(p == null)
                    return null;
            if(key.compareTo(p.key ) < 0)
                    p.left = remove_aux(key, p.left, flag); //go left
            else if(key.compareTo(p.key) > 0)
                    p.right = remove_aux(key, p.right, flag); //go right
            else {
                    
                    flag = true;
                    if (p.left != null && p.right != null)
                    { //two children
                            q = find_min(p.right);
                            p.key = q.key;
                            p.data = q.data;
                            p.right = remove_aux(q.key, p.right, flag);
                    }
                    else 
                    {
                            if (p.right == null) //one child
                                    child = p.left;
                            else if (p.left == null) //one child
                                    child = p.right;
                            return child;
                    }
                }
            return p;
        }
        private BSTNode<K,T> find_min(BSTNode<K,T> p)
        {
            if(p == null)
                    return null;

            while(p.left != null){
                    p = p.left;
            }
            return p;
        }
        
        public void Traverse()//print method
        {
            if (root != null)
                traverseTree(root);
        }
        
        private void traverseTree (BSTNode<K,T> node  )//recusive method
        {
            if (node == null)
                return;
            traverseTree( node.left);
            System.out.println(node.data);
            traverseTree( node.right);
            
        }

       
        
       public void TraverseT()
        {
            if (root != null)
                traverseTreeT(root);
        }
        
        private void traverseTreeT (BSTNode<K,T> node)
        {
            if (node == null)
                return;
            traverseTreeT( node.left );
            if (node.data instanceof BST )
            {
                System.out.println( "Node key ==== "+ node.key);
                ((BST <String,Rank>) node.data).Traverse();
            }
            else
                System.out.println(node.data);
            
            traverseTreeT( node.right);
            
        }
        
        public void PrintData()
        {
            if (root != null)
                PrintData_(root);
        }
        
        private void PrintData_ (BSTNode<K,T> node)
        {
            if (node == null)
                return;
            PrintData_( node.left );
           
           System.out.print(node.key);
            if (node.data instanceof Term )
            {
                System.out.print("   docs: ");
                boolean [] docs = ((Term) node.data).getDocumentMapping();
                for ( int i  = 0 ; i < 50 ; i++)
                    if ( docs[i])
                        System.out.print( " " + i + " " );
                System.out.println("");
            }
            PrintData_( node.right);
        }

}

























