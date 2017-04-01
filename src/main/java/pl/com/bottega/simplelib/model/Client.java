package pl.com.bottega.simplelib.model;

public class Client {
    private String name;

    public Client(String name) {
        this.name = name;
    }

    Client(){}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Client client = (Client) o;

        return name.equals(client.name);

    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
