package Demo.mOPE;

import java.util.LinkedList;

/**
 * @Author: Donlin
 * @Date: Created in 17:01 2018/8/27
 * @Version: 1.0
 * @Description: Demo.OPE Tree data struct
 */
public class OPETree<Key extends Comparable<Key> , Value> {
    // Key - Value 泛型
    private class TreeNode{
        private Key key;                // This node's cipher value
        private Value ciphertext;        // This node's ciphertext
        //private String encode;
        // todo: 插入一个节点后应该将当前节点加密，加密后应该不会改动了
        // todo: 这个加密后的数据是否需要进行存储在这棵树上，如果需要保存这个数值的AES密文，不如直接保存成Value？值得思考。
        //private byte[] ciphertext;      // Key's encrypt result
        private TreeNode leftNode;      // Left child node, always less than this node
        private TreeNode rightNode;     // Right child node, always lager than this node

        public TreeNode(Key ciphertext, Value opeEncode){
            this.key = ciphertext;
            this.ciphertext = opeEncode;
            this.leftNode = null;
            this.rightNode = null;
        }

        public TreeNode(TreeNode node) {
            this.key = node.key;
            this.ciphertext = node.ciphertext;
            this.leftNode = node.leftNode;
            this.rightNode = node.rightNode;
        }
    }

    private TreeNode root;      // tree root
    private int count;          // the count number of the tree

    public OPETree() {
        this.root = null;
        this.count = 0;
    }

    /*******************
     * public 公有方法 *
     *******************/

    public int size(){
        return count;
    }

    public boolean isEmpty(){
        return count == 0;
    }

    /**
     * 向二分搜索树插入节点
     * @param key
     * @param value
     */
    public void insert(Key key, Value value){
        root = insert(root, key, value);
    }

    /**
     * 判断该树是否存在key
     * @param key
     * @return
     */
    public boolean contain(Key key){
        return contain(root, key);
    }

    /**
     * 根据key，检索该树的OPEEncode
     * @param key
     * @return
     */
    public Value search(Key key){
        return search(root, key);
    }

    /**
     * 对该树进行中序遍历，相当于从小到大输出排序
     */
    public void inOrder(){
        inOrder(root);
    }

    /**
     * 对该树进行层次遍历，OPEEncode可以从层次遍历入手
     */
    public void levelOrder(){
        LinkedList<TreeNode> queue = new LinkedList<TreeNode>();
        queue.add(root);
        while ( !queue.isEmpty() ){
            TreeNode node = queue.remove();
            System.out.println(node.key);
            if (node.leftNode != null){
                queue.add(node.leftNode);
            }
            if (node.rightNode != null){
                queue.add(node.rightNode);
            }
        }
    }

    /**
     * 返回该树中最小的key值
     * @return
     */
    public Key minimum(){
        assert count != 0;
        TreeNode minNode = minimum(root);
        return minNode.key;
    }

    /**
     * 返回该树中最大的key值
     * @return
     */
    public Key maximum(){
        assert count != 0;
        TreeNode maxNode = maximum(root);
        return maxNode.key;
    }

    /**
     * remove the minimum key from this tree
     */
    public void removeMin(){
        if (root != null)
            root = removeMin(root);
    }

    /**
     * remove the maximum key from ths tree
     */
    public void removeMax(){
        if (root != null)
            root = removeMax(root);
    }

    /**
     * remove a tree node which's key equals the key
     * @param key
     */
    public void remove(Key key){
        root = remove(root, key);
    }

    /**
     * Demo.OPE 编码方案，递归遍历整棵树的节点并编码
     */
//    public void opeEncoding(){
//        root = opeEncoding(root, "", "100");
//    }

    /*******************
     * private 辅助函数 *
     ******************/

    private TreeNode insert(TreeNode treeNode, Key key, Value value){
        if (treeNode == null){
            count ++;
            return new TreeNode(key,value);
        }
        if (key.compareTo(treeNode.key) == 0){
            treeNode.ciphertext = value;
        }else if (key.compareTo(treeNode.key) < 0){
            treeNode.leftNode = insert(treeNode.leftNode, key, value);
        }else {
            treeNode.rightNode = insert(treeNode.rightNode, key, value);
        }
        return treeNode;
    }

    private boolean contain(TreeNode treeNode, Key key){
        if (treeNode == null){ return false; }
        if (treeNode.key.compareTo(key) == 0){
            return true;
        }else if (treeNode.key.compareTo(key) < 0){
            return contain(treeNode.leftNode, key);
        }else{
            return contain(treeNode.rightNode, key);
        }
    }

    private Value search(TreeNode treeNode, Key key){
        if (treeNode == null){
            return null;
        }
        if (treeNode.key.compareTo(key) == 0){
            return treeNode.ciphertext;
        }else if (treeNode.key.compareTo(key) < 0){
            return search(treeNode.leftNode, key);
        }else {
            return search(treeNode.rightNode, key);
        }
    }

    private void inOrder(TreeNode treeNode){
        if (treeNode != null){
            inOrder(treeNode.leftNode);
            System.out.println(treeNode.key);
            inOrder(treeNode.rightNode);
        }
    }

    private TreeNode minimum(TreeNode treeNode){
        if (treeNode.leftNode == null){
            return treeNode;
        }
        return minimum(treeNode.leftNode);
    }

    private TreeNode maximum(TreeNode treeNode){
        if (treeNode.rightNode == null){
            return treeNode;
        }
        return maximum(treeNode.rightNode);
    }

    private TreeNode removeMin(TreeNode treeNode){
        if (treeNode.leftNode == null){
            TreeNode node = treeNode.rightNode;
            treeNode.rightNode = null;
            count --;
            return node;
        }
        treeNode.leftNode = removeMin(treeNode.leftNode);
        return treeNode;
    }

    private TreeNode removeMax(TreeNode treeNode){
        if (treeNode.rightNode == null){
            TreeNode node = treeNode.leftNode;
            treeNode.leftNode = null;
            count --;
            return node;
        }
        treeNode.rightNode = removeMax(treeNode.rightNode);
        return treeNode;
    }

    private TreeNode remove(TreeNode treeNode, Key key){
        if (treeNode == null){ return null; }

        if (key.compareTo(treeNode.key) < 0){
            treeNode.leftNode = remove(treeNode.leftNode, key);
            return treeNode;
        }else if (key.compareTo(treeNode.key) > 0){
            treeNode.rightNode = remove(treeNode.rightNode, key);
            return treeNode;
        }else { //remove this node
            // 待删除节点左子树为空的情况
            if (treeNode.leftNode == null){
                count --;
                TreeNode node = treeNode.rightNode;
                treeNode.rightNode = null;
                return node;
            }
            // 待删除节点的右子树为空的情况
            if (treeNode.rightNode == null){
                count --;
                TreeNode node = treeNode.leftNode;
                treeNode.leftNode = null;
                return node;
            }
            // 待删除节点左右子树均不为空的情况
            TreeNode successor = new TreeNode(minimum(treeNode.rightNode));
            count ++;

            successor.rightNode = removeMin(treeNode.rightNode);
            successor.leftNode = treeNode.leftNode;

            treeNode.leftNode = treeNode.rightNode = null;
            count --;

            return successor;
        }
    }

//    private TreeNode opeEncoding(TreeNode node, String prefix, String suffix){
//        if (node == null){ return null; }
//        node.encode = prefix + suffix;
//        if (node.leftNode != null){
//            node.leftNode = opeEncoding(node.leftNode, prefix + "0", suffix.substring(0, suffix.length() - 2));
//        }
//        if (node.rightNode != null){
//            node.rightNode = opeEncoding(node.rightNode, prefix + "1", suffix.substring(0, suffix.length() - 2));
//        }
//        return node;
//    }
}