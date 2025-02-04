package mv.revolut.live.balancer;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class RoundRobinLoadBalancer extends LoadBalancer {

    private final AtomicInteger current = new AtomicInteger(0);

    @Override
    public Optional<Server> getServer() {
        if(servers.isEmpty()) return Optional.empty();

        int index = current.getAndUpdate(i -> (i + 1) % servers.size());
        return Optional.of(servers.get(index));
    }
}
