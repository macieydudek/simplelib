package pl.com.bottega.simplelib.model;

public class Reader {
    private final String name;

    public Reader(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Reader reader = (Reader) o;

        return name.equals(reader.name);

    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
