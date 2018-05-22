package missionTCP;
//Name: Shuvrima Alam, UTA ID: 1001085726
//Name: Syed Zaim Zanaruddin, UTA ID:1001106858
//Date: 05/08/2018
import java.util.HashMap;

public class Routing {

    HashMap<Integer, String> agents;

    final String[] routers = {"A", "B", "C", "D", "E", "F", "G", "L"};



    //Various Weights/distance between each router
    final int graph[][] =  {{0, 4, 3, 0, 7, 0, 0, 0},
                            {4, 0, 6, 0, 0, 0, 0, 5},
                            {3, 6, 0, 11, 0, 0, 0, 0},
                            {0, 0, 11, 0, 0, 6, 10, 9},
                            {7, 0, 0, 0, 0, 0, 5, 0},
                            {0, 0, 0, 6, 0, 0, 0, 5},
                            {0, 0, 0, 10, 5, 0, 0, 0},
                            {0, 5, 0, 9, 0, 5, 0, 0}};



    public Routing() {
        agents = new HashMap<>();
        agents.put(0, "Ann");
        agents.put(5, "Jan");
        agents.put(4, "Chan");
    }


    static final int V=8;
    int minDistance(int dist[], Boolean sptSet[])
    {
        // Initialize min value
        int min = Integer.MAX_VALUE, min_index=-1;

        for (int v = 0; v < V; v++)
            if (sptSet[v] == false && dist[v] <= min)
            {
                min = dist[v];
                min_index = v;
            }

        return min_index;
    }


    void printSolution(int dist[], int src, int[] parent, int dest)
    {
        System.out.printf("%s to %s route:", agents.get(src), agents.get(dest));
        System.out.printf("%s",routers[src]);
        printPath(parent, dest);
        System.out.printf("\nThe shortest distance between routers is: %d\n", dist[dest]);
    }

    void printPath(int parent[], int j)
    {
        // Base Case : If j is source
        if (parent[j]==-1)
            return;

        printPath(parent, parent[j]);

        System.out.printf(" -> %s", routers[j]);
    }

    // Function that implements Dijkstra's single source shortest path
    // algorithm for a graph represented using adjacency matrix
    // representation
    void dijkstra(int src, int dest)
    {
        int dist[] = new int[V]; // The output array. dist[i] will hold
        // the shortest distance from src to i

        // sptSet[i] will true if vertex i is included in shortest
        // path tree or shortest distance from src to i is finalized
        Boolean sptSet[] = new Boolean[V];

        int[] parent = new int[V];

        // Initialize all distances as INFINITE and stpSet[] as false
        for (int i = 0; i < V; i++)
        {
            parent[src] = -1;
            dist[i] = Integer.MAX_VALUE;
            sptSet[i] = false;
        }

        // Distance of source vertex from itself is always 0
        dist[src] = 0;

        // Find shortest path for all vertices
        for (int count = 0; count < V-1; count++)
        {
            // Pick the minimum distance vertex from the set of vertices
            // not yet processed. u is always equal to src in first
            // iteration.
            int u = minDistance(dist, sptSet);

            // Mark the picked vertex as processed
            sptSet[u] = true;

            // Update dist value of the adjacent vertices of the
            // picked vertex.
            for (int v = 0; v < V; v++)

                // Update dist[v] only if is not in sptSet, there is an
                // edge from u to v, and total weight of path from src to
                // v through u is smaller than current value of dist[v]
                if (!sptSet[v] && graph[u][v]!=0 &&
                        dist[u] != Integer.MAX_VALUE &&
                        dist[u]+graph[u][v] < dist[v])
                {
                    parent[v] = u;
                    dist[v] = dist[u] + graph[u][v];
                }

        }


        printSolution(dist, src, parent, dest);
    }

}


