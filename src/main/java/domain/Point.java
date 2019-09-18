package domain;

import java.util.Objects;

public class Point {
    private Integer i;
    private Integer j;

    public Point() {
    }

    public Point(Integer i, Integer j) {
        this.i = i;
        this.j = j;
    }

    public Point(Point point) {
        this.i = point.i;
        this.j = point.j;
    }

    public Point add(Point point) {
        return new Point(this.i + point.i, this.j + point.j);
    }

    public Integer getI() {
        return i;
    }

    public Integer getJ() {
        return j;
    }

    public void setI(Integer i) {
        this.i = i;
    }

    public void setJ(Integer j) {
        this.j = j;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Point point = (Point) o;
        return Objects.equals(i, point.i) &&
                Objects.equals(j, point.j);
    }

    @Override
    public int hashCode() {
        return Objects.hash(i, j);
    }
}
