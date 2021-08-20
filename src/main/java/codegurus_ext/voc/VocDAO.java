package codegurus_ext.voc;

import java.util.List;
import java.util.Map;

public interface VocDAO {

    List<Map<String, String>> selectVocDbTest();
    void callSpSetAcpt(Map<String, Object> params);
}