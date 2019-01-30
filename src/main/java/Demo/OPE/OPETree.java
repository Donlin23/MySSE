package Demo.OPE;

/**
 * @Author: Donlin
 * @Date: Created in 14:51 2018/10/30
 * @Version: 1.0
 * @Description: Demo.OPE tree data struct (based on binary search tree).
 */
public class OPETree {

    Node root;          // the ope tree root
    int treeCount;      // the count of tree nodes
    Node cursor;        // 记录遍历的位置，暂且称之为游标

    OPETree(){
        this.root = null;
        this.cursor = root;
        treeCount = 0;
    }

    OPETree(String ciphertext){
        this.root = new Node(ciphertext);
        this.cursor = root;
        treeCount = 0;
    }

    public void setRootCipher(String ciphertext) {
        if (root == null){
            root = new Node(ciphertext);
        }else {
            this.root.setCiphertext(ciphertext);
        }
    }
    public String getRootCipher() {
        // 首先将游标移动回根节点
        this.cursor = this.root;
        return root.ciphertext;
    }

    // todo: 获取当前游标的左节点
    public String getCursorLeft(){
        cursor = cursor.left;
        if (cursor == null){
            return null;
        }
        return cursor.ciphertext;
    }

    // todo: 获取当前游标的右节点
    public String getCursorRight(){
        cursor = cursor.right;
        if (cursor == null){
            return null;
        }
        return cursor.ciphertext;
    }

    // todo: 在树中插入一个新节点
    public boolean insert(){
        return false;
    }

    /**
     * ope tree需要重新设计一下，要切合server的请求，接下来应该先从server的请求分析一下
     *
     */


    static class Node{
        private String ciphertext;            // AES ciphertext
        //String OPEencode;   // OPEencode
        private Node left;          // left child
        private Node right;         // right child

        Node(String ciphertext){
            this.ciphertext = ciphertext;
            this.left = null;
            this.right = null;
        }

        public void setCiphertext(String ciphertext) {
            this.ciphertext = ciphertext;
        }
        public String getCiphertext() {
            return ciphertext;
        }

        public void setLeft(Node left) {
            this.left = left;
        }
        public Node getLeft() {
            return left;
        }

        public void setRight(Node right) {
            this.right = right;
        }
        public Node getRight() {
            return right;
        }
    }
}
