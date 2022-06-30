import static org.junit.jupiter.api.Assertions.*;

class FriendshipGraphTest {

    @org.junit.jupiter.api.Test
    void addEdge() {
        FriendshipGraph social_network = new FriendshipGraph();
        Person fir = new Person("1");
        social_network.addVertex(fir);
        assertFalse(social_network.addEdge(fir, fir));

        Person sec = new Person("2");
        social_network.addVertex(sec);
        assertTrue(social_network.addEdge(fir, sec));
        assertFalse(social_network.addEdge(fir, sec));
        assertTrue(social_network.addEdge(sec, fir));
    }

    @org.junit.jupiter.api.Test
    void addVertex() {
        FriendshipGraph social_network = new FriendshipGraph();
        Person fir = new Person("1");
        assertTrue(social_network.addVertex(fir));
        assertFalse(social_network.addVertex(fir));
    }

    @org.junit.jupiter.api.Test
    void getDistance() {
        FriendshipGraph social_network = new FriendshipGraph();
        Person fir = new Person("1");
        social_network.addVertex(fir);
        assertEquals(0, social_network.getDistance(fir, fir));

        Person sec = new Person("2");
        social_network.addVertex(sec);
        social_network.addEdge(fir, sec);
        social_network.addEdge(sec, fir);
        assertEquals(1, social_network.getDistance(fir, sec));

        Person trd = new Person("3");
        social_network.addVertex(trd);
        social_network.addEdge(sec, trd);
        social_network.addEdge(trd, sec);
        assertEquals(2, social_network.getDistance(fir, trd));

        Person frt = new Person("4");
        social_network.addVertex(frt);
        assertEquals(-1, social_network.getDistance(fir, frt));
    }
}