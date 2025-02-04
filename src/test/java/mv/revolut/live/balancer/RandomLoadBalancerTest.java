package mv.revolut.live.balancer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Random;
import java.util.stream.IntStream;

@ExtendWith(MockitoExtension.class)
class RandomLoadBalancerTest {

    @Mock
    private Random mockRandom;

    @Test
    void addServer_addressIsNull_returnsFalse() {
        LoadBalancer loadBalancer = new RandomLoadBalancer(mockRandom);
        Server server = new Server(null);

        boolean wasAdded = loadBalancer.addServer(server);

        Assertions.assertFalse(wasAdded);
    }

    @Test
    void addServer_addressIsFineAndCanBeAdded_returnTrue() {
        LoadBalancer loadBalancer = new RandomLoadBalancer(mockRandom);
        Server server = new Server("https://revolut.com");

        boolean wasAdded = loadBalancer.addServer(server);

        Assertions.assertTrue(wasAdded);
    }

    @Test
    void addServer_balancerAtCapacity_returnFalse() {
        LoadBalancer loadBalancer = new RandomLoadBalancer(mockRandom);

        IntStream.range(0, 11)
            .forEach((i) -> loadBalancer.addServer(new Server("address" + i)));

        Server server = new Server("https://revolut.com");

        boolean wasAdded = loadBalancer.addServer(server);

        Assertions.assertFalse(wasAdded);
    }

    @Test
    void getServer_loadBalancerIsEmpty_returnEmptyOptional() {
        LoadBalancer loadBalancer = new RandomLoadBalancer(mockRandom);

        Optional<Server> server = loadBalancer.getServer();

        Assertions.assertTrue(server.isEmpty());
    }

    @Test
    void getServer_loadBalancerHas10_mockingIndex6ReturnsServerWithAddress6() {
        LoadBalancer loadBalancer = new RandomLoadBalancer(mockRandom);

        IntStream.range(0, 11)
            .forEach((i) -> loadBalancer.addServer(new Server("" + i)));

        Mockito.when(mockRandom.nextInt(10)).thenReturn(6);

        Optional<Server> server = loadBalancer.getServer();

        Assertions.assertTrue(server.isPresent());
        Assertions.assertEquals("6", server.get().address());
    }


}