package unirio.apa.util;

public class DijkstraData {
    private final long time;
    private final Integer[] paths;

    public DijkstraData(Integer[] paths, long time) {
        this.time = time;
        this.paths = paths;
    }

    public long getTime() {
        return time;
    }

    public Integer[] getPaths() {
        return paths;
    }
}
