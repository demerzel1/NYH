package wasdev.sample.JsonHolder;

/**
 * Created by demerzel on 2019-07-13.
 */
public class FaceJsonHolder {
    public String status;
    public  A[] predictions;
    public static class A{
        public int age_estimation;
        public double[] detection_box;
    }
}
