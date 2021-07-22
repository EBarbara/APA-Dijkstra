import java.util.*;

public class Dijkstra {
    public static void main(String arg[]){

        // Graph a = new Graph(8,11);
        // a.Graph_Example1();
        // a.Print_Adjacency_List();

        Graph a = new Graph(6,9);
        a.Graph_Example2();
        a.Print_Adjacency_List();
        a.Dijkstra_Algorithm("1");

    }

}

class Graph {
    String[] V;
    String[] E;
    Map<String, String[]> adj_list = new HashMap<String, String[]>();
    Map<String, int[]> adj_list_costs = new HashMap<String, int[]>();

    public Graph (int n, int m) {
        V = new String[n];
        for (int i=0;i<n;i++) { V[i]=(String.valueOf(i+1)); }
        E = new String[m];
        for (int i=0;i<n;i++) { E[i]=(String.valueOf(i+1)); }
    }

    public Graph (String[] V_names, String[] E_names) {
        V = new String[V_names.length];
        for (int i=0;i<V_names.length;i++) { V[i]=(String.valueOf(i)); }
        E = new String[E_names.length];
        for (int i=0;i<E_names.length;i++) { E[i]=(String.valueOf(i)); }
    }

    public void Add_Elem_Adj_List (String node, String[] adj_nodes, int[] costs) {
        int index1 = -1;
        for (int i=0;i<V.length;i++) {
            if (V[i].equals(node)) {
                index1=i;
            }
        }

        if (index1!=-1) {
            adj_list.put(node, adj_nodes);
            adj_list_costs.put(node, costs);
        }
    }

    public void Print_Adjacency_List () {
        for (int i=0; i<V.length;i++) {
            String str1= Arrays.toString(adj_list.get(V[i]));
            String str2= Arrays.toString(adj_list_costs.get(V[i]));
            System.out.println("Adjacent Nodes: "+str1+" -------------> costs: "+str2);
        }
    }

    public void Graph_Example1 () {
        Add_Elem_Adj_List("1", new String[]{"2","3"}, new int[] {8, 13});
        Add_Elem_Adj_List("2", new String[]{"1","3","4","5"}, new int[] {8, 5, 9 ,7});
        Add_Elem_Adj_List("3", new String[]{"1","2","5","7","8"}, new int[] {13, 5, 15, 10, 6});
        Add_Elem_Adj_List("4", new String[]{"2","5"}, new int[] {9, 8});
        Add_Elem_Adj_List("5", new String[]{"2","3","4","6"}, new int[] {7, 15, 8, 5});
        Add_Elem_Adj_List("6", new String[]{"5"}, new int[] {5});
        Add_Elem_Adj_List("7", new String[]{"3","8"}, new int[] {10, 17});
        Add_Elem_Adj_List("8", new String[]{"3","7"}, new int[] {6, 17});
    }

    public void Graph_Example2 () {
        Add_Elem_Adj_List("1", new String[]{"2","4","5"}, new int[] {16, 4, 8});
        Add_Elem_Adj_List("2", new String[]{"3"}, new int[] {2});
        Add_Elem_Adj_List("3", new String[]{"3"}, new int[] {1000000});
        Add_Elem_Adj_List("4", new String[]{"5"}, new int[] {3});
        Add_Elem_Adj_List("5", new String[]{"2","6"}, new int[] {7, 1});
        Add_Elem_Adj_List("6", new String[]{"2","3"}, new int[] {5, 6});
    }

    public void Dijkstra_Algorithm (String source_node) {
        int source_index = -1;
        for (int i=0;i<V.length;i++) {
            if (V[i].equals(source_node)) {
                source_index=i;
            }
        }

        if (source_index!=-1) {
            String S = source_node+",";
            int S_length = 0;
            String V_single_string = "";
            for (String v : V) { V_single_string+=v+","; }

            int[] d = new int[V.length];
            for (int i : d) {i=1000000;}
            d[source_index]=0;

            String[] last_node = new String[V.length];

            while(S_length<V.length-1){
                String[] S_vector = S.split(",");

                String S_border_str="";
                String S_adj_nodes_str="";
                for (String s : S_vector) {
                    String[] s_adj_nodes = adj_list.get(s);
                    for(String s_adj_node : s_adj_nodes) {
                        if(!S.contains(s_adj_node)){
                            S_border_str+=s+",";
                            S_adj_nodes_str+=s_adj_node+",";
                        }
                    }
                }
                String[] S_border = S_border_str.split(",");
                String[] S_adj_nodes = S_adj_nodes_str.split(",");

                int d_v=1000000;
                String s_new = "";
                for (String u : S_border) {
                    int ind_v=0;
                    for (String v : adj_list.get(u)) {
                        if(S_adj_nodes_str.contains(v)){
                            int d_v_new = d[get_index(V,u)]+adj_list_costs.get(u)[ind_v];
                            if (d_v_new<d_v) {
                                d_v = d_v_new;
                                s_new = v;
                                d[get_index(V,v)]=d_v;
                                last_node[get_index(V,v)]=u;
                            }
                        }
                        ind_v++;
                    }
                }

                S+=s_new+",";
                S_length++;

            }

            System.out.println("\nSaida do algoritmo dijktra:");
            System.out.println("Conjunto S: ["+S+"]");
            System.out.println("Custos de s ate u: d(u)="+Arrays.toString(d));
            System.out.println("Ultimo no dado u: pred(u)="+Arrays.toString(last_node));

        }

    }

    public int get_index(String[] arr, String elem) {
        int position = 0;
        for (String obj : arr) {
            if (obj.equals(elem)) {
                return position;
            }
            position += 1;
        }
        return 0;
    }
}