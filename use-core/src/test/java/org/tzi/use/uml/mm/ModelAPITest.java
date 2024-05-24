package org.tzi.use.uml.mm;

import org.junit.jupiter.api.Test;
import org.tzi.use.api.UseApiException;
import org.tzi.use.api.UseModelApi;

import static org.junit.jupiter.api.Assertions.*;

public class ModelAPITest {
    @Test
    public void testConstraintCreation() throws UseApiException {
        UseModelApi api = new UseModelApi("UnitTest");
        MPrePostCondition ppc;

        api.createClass("A", false);
        api.createAttribute("A", "foo", "String");
        api.createOperation("A", "bar", new String[0][0], "String");
        ppc = api.createPrePostCondition("A", "bar", "self.foo is defined", "self.foo.isDefined()", true);
        assertEquals("self.foo is defined", ppc.name());

        ppc = api.createPrePostCondition("A", "bar", "foo without self is defined", "foo.isDefined()", true);
        assertEquals("foo without self is defined", ppc.name());

        assertEquals(2, api.getClass("A").operation("bar", true).preConditions().size());

        api.createPrePostCondition("A", "bar", "result can be checked", "result.isDefined()", false);

        assertEquals(1, api.getClass("A").operation("bar", true).postConditions().size());

        Exception ex = assertThrows(UseApiException.class, ()
                -> api.createPrePostCondition("B", "", "", "", true));

        assertTrue(ex.getMessage().contains("Unknown"));
    }
}
