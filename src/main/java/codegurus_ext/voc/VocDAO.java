package codegurus_ext.voc;

import java.util.List;
import java.util.Map;

public interface VocDAO {

    List<Map<String, String>> selectVocDbTest();
    Map<String, Object> callSpSetAcpt(Map<String, Object> params);
    void callSpSetAcpt2(Map<String, Object> params);
    void callSpSetAcpt3(Map<String, Object> params);
}