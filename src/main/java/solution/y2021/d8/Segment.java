package solution.y2021.d8;

import java.util.Objects;

record Segment(char code) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Segment segment = (Segment) o;
        return code == segment.code;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(code);
    }
}
