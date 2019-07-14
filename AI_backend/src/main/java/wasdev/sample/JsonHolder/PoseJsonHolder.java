package wasdev.sample.JsonHolder;

/**
 * Created by demerzel on 2019-07-13.
 */
public class PoseJsonHolder {
    public String status;
    public A[] predictions;
    public static class  A{
        public int human_id;
        public B[] pose_lines;
        public static class B{
            public int[] line;
        }
        public C[] body_parts;
        public static class C{
            public int part_id;
            public String part_name;
            public double score;
            public int x;
            public int y;
        }
    }
}
