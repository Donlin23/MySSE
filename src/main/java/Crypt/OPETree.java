package Crypt;

/**
 * @Author: Donlin
 * @Date: Created in 17:01 2018/8/27
 * @Version: 1.0
 * @Description: OPE tree
 */
public class OPETree {

}

class TreeNode{
    private String ciphertext;      // This node's cipher value
    private String OPEEncoding;     // This node's OPE encoding
    private TreeNode leftNode;      // Left child node, always less than this node
    private TreeNode rightNode;     // Right child node, always lager than this node

    public TreeNode(String ciphertext){
        this.ciphertext = ciphertext;
        this.OPEEncoding = "";
        this.leftNode = null;
        this.rightNode = null;
    }

    // todo: 返回该节点的OPE编码
    // todo: 更新该节点的OPE编码
    // todo: 中序遍历输出该节点的子树
}