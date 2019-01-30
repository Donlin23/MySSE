package Demo.OPE;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Donlin
 * @Date: Created in 14:53 2018/10/30
 * @Version: 1.0
 * @Description: Demo.OPE table data struct (achieve on HashMap).
 */
public class OPETable<Key, Value> {     // Key means the DET encrypted result, Value means the Demo.OPE encoding in the Demo.OPE tree
    private Map<Key, Value> opeMap;     // the main hash map
    private int mapSize;               // the size of ope table

    public OPETable(){
        this.opeMap = new HashMap<Key, Value>();
        this.mapSize = 0;
    }

    public Value get(Key key){
        return opeMap.get(key);
    }

    public void put(Key key, Value value){
        opeMap.put(key, value);
        mapSize++;
    }

    public Value remove(Key key){
        Value temp = opeMap.remove(key);
        mapSize--;
        return temp;
    }

    public boolean containsKey(Key key){
        return opeMap.containsKey(key);
    }

    public int getSize(){
        return mapSize;
    }
}
