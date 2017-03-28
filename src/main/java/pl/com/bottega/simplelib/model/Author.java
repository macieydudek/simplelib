package pl.com.bottega.simplelib.model;

public class Author {

    private final String name;

    public Author(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Author author = (Author) o;

        return name.equals(author.name);

    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
