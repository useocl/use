package org.tzi.use;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;
import org.tzi.use.api.UseApiException;
import org.tzi.use.api.UseModelApi;
import org.tzi.use.api.UseSystemApi;
import org.tzi.use.api.impl.UseSystemApiNative;
import org.tzi.use.uml.mm.MModel;
import org.tzi.use.uml.ocl.value.Value;

public class OCLExpressionIT {

    @Test
    void testExpression() {
        UseModelApi modelApi = new UseModelApi("Integration tests");
        UseSystemApi sysAPI = new UseSystemApiNative(modelApi.getModel());

        Value result = null;
        try {
            result = sysAPI.evaluate("1 + 1");
        } catch (UseApiException e) {
            fail("Evaluation failed....");
        }

        assertEquals("2", result.toString());
        assertTrue(result.isInteger());
    }
}
