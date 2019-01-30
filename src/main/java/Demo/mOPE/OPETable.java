package Demo.mOPE;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Donlin
 * @Date: Created in 15:07 2018/10/29
 * @Version: 1.0
 * @Description: Demo.OPE Table data struct. 暂时作为一个key-value的存储形式存储在内存中，后续应该做成一个mysql的table持久化到硬盘上
 */
public class OPETable<Key, Value> {
    // Key means DET encrypted result, Value means Demo.OPE encoding
    private Map<Key, Value> opeTable;
    private int count = 0;

    public OPETable(){
        opeTable = new HashMap();
    }

    public Value getValue(Key key){
        return opeTable.get(key);
    }

    public void put(Key key, Value value){
        opeTable.put(key, value);
        count++;
    }

    public Value delete(Key key){
        Value temp = getValue(key);
        opeTable.remove(key);
        count--;
        return temp;
    }

    public int size(){
        return count;
    }

}
