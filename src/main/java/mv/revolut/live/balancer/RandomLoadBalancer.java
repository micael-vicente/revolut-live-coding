package mv.revolut.live.balancer;

import java.util.Optional;
import java.util.Random;

public class RandomLoadBalancer extends LoadBalancer {

    private final Random random;

    public RandomLoadBalancer(Random random) {
        super();
        this.random = random;
    }

    @Override
    public Optional<Server> getServer() {
        if(servers.isEmpty()) return Optional.empty();

        int index = random.nextInt(servers.size());

        return Optional.of(servers.get(index));
    }
}
