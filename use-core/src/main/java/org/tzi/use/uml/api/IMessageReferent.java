package org.tzi.use.uml.api;

/**
 * Minimal API contract for objects that can be referenced by a message type
 * (signals or operations). Implemented by mm classes (e.g. MSignalImpl, MOperation).
 */
public interface IMessageReferent {
    String name();
}
