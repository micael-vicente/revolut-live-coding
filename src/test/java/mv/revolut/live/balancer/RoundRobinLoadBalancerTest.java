package mv.revolut.live.balancer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

class RoundRobinLoadBalancerTest {

    @Test
    void getServer_balancerEmpty_returnOptionalEmpty() {
        LoadBalancer loadBalancer = new RoundRobinLoadBalancer();

        Optional<Server> server = loadBalancer.getServer();

        Assertions.assertTrue(server.isEmpty());
    }

    @Test
    void getServer_balancerHas3_returnFirst1Second2Third3() {
        LoadBalancer loadBalancer = new RoundRobinLoadBalancer();
        loadBalancer.addServer(new Server("1"));
        loadBalancer.addServer(new Server("2"));
        loadBalancer.addServer(new Server("3"));

        Optional<Server> server1 = loadBalancer.getServer();
        Optional<Server> server2 = loadBalancer.getServer();
        Optional<Server> server3 = loadBalancer.getServer();

        Assertions.assertTrue(server1.isPresent());
        Assertions.assertTrue(server2.isPresent());
        Assertions.assertTrue(server3.isPresent());
        Assertions.assertEquals("1", server1.get().address());
        Assertions.assertEquals("2", server2.get().address());
        Assertions.assertEquals("3", server3.get().address());
    }

    @Test
    void getServer_balancerHas3CurrentIs3_goesAroundTo1() {
        LoadBalancer loadBalancer = new RoundRobinLoadBalancer();
        loadBalancer.addServer(new Server("1"));
        loadBalancer.addServer(new Server("2"));
        loadBalancer.addServer(new Server("3"));

        loadBalancer.getServer();
        loadBalancer.getServer();
        Optional<Server> server3 = loadBalancer.getServer();
        Optional<Server> server1 = loadBalancer.getServer();

        Assertions.assertTrue(server3.isPresent());
        Assertions.assertTrue(server1.isPresent());
        Assertions.assertEquals("3", server3.get().address());
        Assertions.assertEquals("1", server1.get().address());
    }
}