package mv.revolut.live.balancer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class LoadBalancer {

    protected final List<Server> servers;
    private final transient Object lock = new Object();

    public LoadBalancer() {
        this.servers = new ArrayList<>();
    }

    public boolean addServer(Server server) {
        synchronized (lock) {
            if(cannotRegisterServer(server)) {
                return false;
            }

            return servers.add(server);
        }
    }

    public abstract Optional<Server> getServer();

    private boolean cannotRegisterServer(Server server) {
        return server == null
            || server.address() == null
            || servers.contains(server)
            || servers.size() >= 10;
    }

}
