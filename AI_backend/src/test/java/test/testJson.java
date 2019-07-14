package test;
import com.google.gson.Gson;
import wasdev.sample.JsonHolder.*;
import static org.junit.Assert.*;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by demerzel on 2019-07-13.
 */

public class testJson{

    double dis(int x1, int y1, int x2, int y2){
        return Math.sqrt((x2-x1) * (x2-x1) + (y2-y1) * (y2-y1));
    }

    @Test
    public void testJson() throws IOException {
        String json = "";
        BufferedReader br = new BufferedReader(new FileReader("/Users/demerzel/get-started-tomcat/facceJson.json"));
        String line = "";
        while ((line = br.readLine()) != null) {
            json += line;
        }
        br.close();
        System.out.println(json);
        FaceJsonHolder faceJsonHolder = new Gson().fromJson(json, FaceJsonHolder.class);

        json = "";
        br = new BufferedReader(new FileReader("/Users/demerzel/get-started-tomcat/poseJson.json"));
        line = "";
        while ((line = br.readLine()) != null) {
            json += line;
        }
        br.close();
        System.out.println(json);
        PoseJsonHolder poseJsonHolder = new Gson().fromJson(json, PoseJsonHolder.class);
        System.out.println(poseJsonHolder.predictions[0].human_id);

        List<PoseJsonHolder> poseJsonHolderList = new ArrayList<>();

        if(poseJsonHolderList.size()<5){
            poseJsonHolderList.add(poseJsonHolder);
        }else{
            poseJsonHolderList.remove(0);
            poseJsonHolderList.add(poseJsonHolder);
        }
        if(!faceJsonHolder.status.equals("ok"))
            return;
        int peopleAge = faceJsonHolder.predictions[0].age_estimation;
        boolean needHelpFlag = false;
        if(peopleAge>10){
            if(poseJsonHolderList.size()>1){
                if(poseJsonHolderList.size()<5){

                }else{
                    double[] rate = new double[5];
                    int[][] allx = new int[5][5];
                    int[][] ally = new int[5][5];


                    for(int i = 0; i < 5; ++i){
                        int xNeck = 0,yNeck = 0,xHip = 0, yHip = 0;
                        int xShoulder = 0, yShoulder = 0;
                        for(int j = 0; j < poseJsonHolderList.get(i).predictions[0].body_parts.length; ++i){
                            if(poseJsonHolderList.get(i).predictions[0].body_parts[j].part_id==0){
                                xNeck = poseJsonHolderList.get(i).predictions[0].body_parts[j].x;
                                yNeck = poseJsonHolderList.get(i).predictions[0].body_parts[j].y;
                            }
                            if(poseJsonHolderList.get(i).predictions[0].body_parts[j].part_id==11){
                                xHip = poseJsonHolderList.get(i).predictions[0].body_parts[j].x;
                                yHip = poseJsonHolderList.get(i).predictions[0].body_parts[j].y;
                            }
                            if(poseJsonHolderList.get(i).predictions[0].body_parts[j].part_id==5){
                                xShoulder = poseJsonHolderList.get(i).predictions[0].body_parts[j].x;
                                yShoulder = poseJsonHolderList.get(i).predictions[0].body_parts[j].y;
                            }
                            allx[0][j] = xNeck;
                            ally[0][j] = yNeck;
                            allx[1][j] = xShoulder;
                            ally[1][j] = yShoulder;
                            allx[2][j] = xHip;
                            ally[2][j] = yHip;
                        }
                        boolean[] flag = new boolean[8];
                        for(int j = 0; j <8; ++j)
                            flag[j] = false;
                        for(int j = 0; j < 3; ++j){
                            for(int k = 0; k < 5; ++k){
                                if(allx[j][k] != 0 && flag[j] == false){
                                    for(int kk  = 0; kk < 5; ++kk){
                                        if(allx[j][kk] == 0)
                                            allx[j][kk] = allx[j][k];
                                    }
                                    flag[j] = true;
                                }
                            }
                        }
                        for(int j = 0; j < 3; ++j){
                            for(int k = 0; k < 5; ++k){
                                if(ally[j][k] != 0 && flag[j+3] == false){
                                    for(int kk  = 0; kk < 5; ++kk){
                                        if(ally[j][kk] == 0)
                                            ally[j][kk] = ally[j][k];
                                    }
                                    flag[j+3] = true;
                                }
                            }
                        }
                        for(int j = 0; j< 6; ++i){
                            if(flag[j] != true){
                                System.out.println("pose failed");
                            }
                        }
                        for(int j = 0; j < 5; ++j){
                            rate[j] = dis(allx[0][j], ally[0][j], allx[1][j], ally[1][j])/dis(allx[0][j], ally[0][j], allx[2][j], ally[2][j]);
                        }
                        double limit = 0.2;
                        for(int j = 0; j < 5; ++j){
                            if(j == 0 )
                                continue;
                            if(rate[j] >0.7 && rate[j-1] >0.7){
                                needHelpFlag = true;
                            }
                        }
                    }
                }
            }
        }
        if(needHelpFlag == true){

        }




    }
}

