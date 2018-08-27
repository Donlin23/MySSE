package Demo;

/**
 * @Author: Donlin
 * @Date: Created in 16:39 2018/7/3
 * @Version: 1.0
 * @Description: B-树demo
 */
public class BTreeDemo <Key extends Comparable<Key>, Value>{
    // max children per B-tree node = M-1
    // (must be even and greater than 2)
    // 树的阶为4，也就是每个结点的儿子最多是M=4，最少是M/2=2 ？
    private static final int M = 4;

    private Node root;      // root of the B-tree
    private int height;     // height of the B-tree
    private int n;          // number of key-value pairs in the B-tree 意味着有n个叶节点？

    // helper B-tree node data type
    private static final class Node{
        private int m;                              // number of children
        private Entry[] children = new Entry[M];    // the array of children

        // create a node with k children
        public Node(int m) {
            this.m = m;
        }
    }

    //internal nodes: only use key and next     内结点使用：key-next
    //external nodes: only use key and value    外结点使用：key-value
    private static class Entry{
        private Comparable key;     // 这个Comparable是<T>泛型
        private Object value;
        private Node next;

        public Entry(Comparable key, Object value, Node next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }

    /**
     * Initializes an empty B-tree
     */
    public BTreeDemo(){
        root = new Node(0);
    }

    /**
     * Return true if this symbol table is empty.
     * @return {@code true}
     */
    public boolean isEmpty(){
        return getN() == 0;
    }

    /**
     * Return the number of key-value pairs in this symbol table.
     * @return
     */
    public int getN(){
        return n;
    }

    /**
     * Return the number of this B-tree (for debugging).
     * @return
     */
    public int getHeight(){
        return height;
    }

    /**
     * Returns the value associated with the given key.
     * @param key the key
     * @return the value associated with the given key if the key in the symbol table
     *      and {@code null} if the key is not in the symbol table
     * @throws NullPointerException if {@code key} is {@code null}
     */
    public Value get(Key key) {
        if (key == null) {
            throw new NullPointerException("key must not be null");
        }
        return search(root, key, height);
    }

    /**
     * Return the search result
     * @param x
     * @param key
     * @param ht
     * @return
     */
    private Value search(Node x, Key key, int ht) {
        Entry[] children = x.children;

        // external node到最底层叶子结点，遍历
        // ht == 0 表示结点是叶结点，遍历找value
        if (ht == 0){
            for (int j = 0; j < x.m; j++){
                if (eq(key, children[j].key)){
                    return (Value) children[j].value;
                }
            }
        }
        // internal node递归查找next地址
        else{
            for (int j = 0; j < x.m; j++){
                if (j+1 == x.m || less(key, children[j+1].key)) {   //判断目标key在不在儿结点数组相邻两个元素之间
                    return search(children[j].next, key, ht-1); //递归查找下一个node
                }
            }
        }
        return null;
    }

    /**
     * Inserts the key-value pair into the symbol table, overwriting the old value
     * with the new value if the key is already in the symbol table.
     * If the value is {@code null}, this effectively deletes the key from the symbol table.
     *
     * @param key
     * @param val
     * @throws NullPointerException
     */
    public void put(Key key, Value val) {
        if (key == null) {
            throw new NullPointerException("key must not be null");
        }
        Node u = insert(root, key, val, height); //分裂后生成的右结点
        n++;
        if (u == null){
            return;
        }
        // 如果有分裂出来的右结点，就重组root(旧的root变成左结点，新的结点变成右结点，重组一个root)
        // need to split root重组root
        Node t = new Node(2);
        t.children[0] = new Entry(root.children[0].key, null, root);
        t.children[1] = new Entry(u.children[0].key, null, u);
        root = t;
        height++;
    }

    /**
     *
     * @param h
     * @param key
     * @param val
     * @param ht
     * @return
     */
    private Node insert(Node h, Key key, Value val, int ht){
        int j;
        Entry t = new Entry(key, val, null); // 这是一个键值对（外部结点）

        // external node外部结点，也是叶子结点，在树的最底层，存的是内容value
        if (ht == 0){
            for (j = 0; j < h.m; j++){
                if (less(key, h.children[j].key)){
                    break;
                }
            }
        }
        // internal node内部结点，存的是next地址
        else {
            for (j = 0; j < h.m; j++){
                if ((j+1 == h.m) || less(key, h.children[j+1].key)){
                    Node u = insert(h.children[j++].next, key, val, ht-1);
                    if (u == null){
                        return null;
                    }
                    t.key = u.children[0].key;
                    t.next = u;
                    break;
                }
            }
        }

        // 调整i结点的位置，插入新的结点
        for (int i = h.m; i > j; i--){
            h.children[i] = h.children[i-1];
        }
        h.children[j] = t;
        h.m++; // 当前结点数组长度+1
        if (h.m < M){ //如果超出了定义好的树的阶，就分裂结点
            return null;
        }
        else{  //分裂结点
            return split(h);
        }
    }

    /**
     * Split node in half
     * @param h
     * @return
     */
    private Node split(Node h){
        Node t = new Node(M/2);
        h.m = M/2;
        for (int j = 0; j < M/2; j++) {
            t.children[j] = h.children[M/2+j];
        }
        return t;
    }

    /**
     * Returns a string representation of  this B-tree (fro debugging).
     * @return
     */
    public String toString(){
        return toString(root, height, "") + "\n";
    }

    /**
     *
     * @param h
     * @param ht
     * @param indent
     * @return
     */
    private String toString(Node h, int ht, String indent) {
        StringBuilder s = new StringBuilder();
        Entry[] children = h.children;

        if (ht == 0){
            for (int j = 0; j < h.m; j++){
                s.append(indent + children[j].key + " " + children[j].value + "\n");
            }
        }
        else{
            for (int j = 0; j < h.m; j++){
                if (j > 0){
                    s.append(indent + "(" + children[j].key + ")\n");
                }
                s.append(toString(children[j].next, ht-1, indent + "   "));
            }
        }
        return s.toString();
    }

    /**
     * Comparison functions - make Comparable instead of key to avoid casts
     * @param k1
     * @param k2
     * @return
     */
    private boolean less(Comparable k1, Comparable k2){
        return k1.compareTo(k2) < 0;
    }

    /**
     *
     * @param k1
     * @param k2
     * @return
     */
    private boolean eq(Comparable k1, Comparable k2){
        return k1.compareTo(k2) == 0;
    }

    // 测试用
    public static void main(String[] args)
    {
        BTreeDemo<String, String> st = new BTreeDemo();

        st.put("15", "128.112.136.12");
        st.put("45", "128.112.136.11");
        st.put("78",  "128.112.128.15");
        st.put("95",     "130.132.143.21");
        st.put("12",   "209.052.165.60");
        st.put("3",    "17.112.152.32");
        st.put("6",    "207.171.182.16");
        st.put("5",     "66.135.192.87");
        st.put("15",     "64.236.16.20");
        st.put("45",    "216.239.41.99");
        st.put("494",   "199.239.136.200");
        st.put("944",  "207.126.99.140");
        st.put("955",     "143.166.224.230");
        st.put("23",   "66.35.250.151");
        st.put("30",     "199.181.135.201");
        st.put("959",   "63.111.66.11");
        st.put("1000",    "216.109.118.65");


//        System.out.println("cs.princeton.edu: " + st.get("www.cs.princeton.edu"));
//        System.out.println("hardvardsucks.com: " + st.get("www.harvardsucks.com"));
//        System.out.println("simpsons.com:   " + st.get("www.simpsons.com"));
//        System.out.println("apple.com:     " + st.get("www.apple.com"));
//        System.out.println("ebay.com:     " + st.get("www.ebay.com"));
//        System.out.println("dell.com:     " + st.get("www.dell.com"));
        System.out.println();

        System.out.println("size:  " + st.getN());
        System.out.println("height: " + st.getHeight());
        System.out.println(st);
        System.out.println();
    }
}
