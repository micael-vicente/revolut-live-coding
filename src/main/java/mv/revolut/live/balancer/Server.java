package mv.revolut.live.balancer;

import java.util.Objects;

public record Server(String address) {

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Server server = (Server) o;
        return Objects.equals(address, server.address);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(address);
    }
}
