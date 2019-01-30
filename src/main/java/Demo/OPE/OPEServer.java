package Demo.OPE;

/**
 * @Author: Donlin
 * @Date: Created in 14:49 2018/10/30
 * @Version: 1.0
 * @Description: Demo.OPE Server contains an ope tree and an ope table(map).
 */
public class OPEServer {

    private OPETable<String, byte[]> opeTable;  // AES加密密文-ope encode
    private OPETree opeTree;                    // 建立一棵树，我感觉这棵树的作用是建立一个映射关系，其映射关系保存在opetable中

    public OPEServer(){
        this.opeTree = new OPETree();
        this.opeTable = new OPETable<String, byte[]>();
    }

    /**
     * 首先检查opetable中是否存在这个ciphertext
     * @param ciphertext
     * @return
     */
    public boolean contains(String ciphertext){
        return opeTable.containsKey(ciphertext);
    }

    /**
     * 然后开始获取根节点的密文
     * @return
     */
    public String getRoot(){
        return opeTree.getRootCipher();
    }

    public String getLeft(){
        return opeTree.getCursorLeft();
    }

    public String getRight(){
        return opeTree.getCursorRight();
    }


    // 将当前的这个密文插入到ope tree，并将 密文-opeEncode 插入到ope table中
    public void insert(String currentCipher){

    }


}
