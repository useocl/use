package org.tzi.use.autocompletion.parserResultTypes.useCommands;

import java.util.Objects;

/**
 * Represents a specific autocompletion parser result type associated with the USE command "open".
 * It extends {@link ResultTypeUseCommands}.
 */
public class ResultTypeUseOpen implements ResultTypeUseCommands{
    public String path;

    public ResultTypeUseOpen(String path) {
        this.path = path;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResultTypeUseOpen that = (ResultTypeUseOpen) o;
        return Objects.equals(path, that.path);
    }

    @Override
    public String toString() {
        return "ResultTypeUseOpen{" +
                "path='" + path + '\'' +
                '}';
    }
}
