import org.junit.*;
import static org.junit.Assert.*;

import java.util.*;

public class CalculationTest {

    @Test
    public void test_testingPlatform_newListIsEmpty() {
        Collection collection = new ArrayList();
        assertTrue(collection.isEmpty());
    }

}