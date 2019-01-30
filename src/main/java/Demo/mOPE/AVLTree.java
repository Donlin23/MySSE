package Demo.mOPE;

/**
 * @Author: Donlin
 * @Date: Created in 16:13 2018/12/5
 * @Version: 1.0
 * @Description: 使用AVL tree作为OPE tree的基本结构
 */
public class AVLTree <Key extends Comparable<Key>, Value>{
    private Node root;

    private class Node {
        private Key key;
        private Value value;
        private Node left, right;
        private int N;

        public Node(Key key, Value value, int N){
            this.key = key;this.value = value; this.N = N;
            this.left = null; this.right = null;
        }
    }

    public int size(){
        return size(root);
    }
    private int size(Node x){
        if (x == null)
            return 0;
        return x.N;
    }

    public Value get(Key key){
        return get(root, key);
    }
    private Value get(Node x, Key key){
        if (x == null){
            return null;
        }
        int cmpResult = key.compareTo(x.key);
        if (cmpResult > 0) return get(x.right, key);
        if (cmpResult < 0) return get(x.left, key);
        return x.value;
    }

    public void put(Key key, Value value){
        root = put(root, key, value);
    }
    private Node put(Node x, Key key, Value value){
        if (x == null) return new Node(key, value, 1);
        int cmpResult = key.compareTo(x.key);
        if (cmpResult > 0) x.right = put(x.right, key, value);
        if (cmpResult < 0) x.left = put(x.left, key, value);
        if (cmpResult == 0) x.value = value;
        x.N = size(x.left) + size(x.right) + 1;
        return x;
    }


}
