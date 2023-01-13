package HotelMgtSystem;

import java.util.Scanner;

public class Hotel {
    Rooms [][] roos;
    public Hotel(){
        roos =new Rooms[3][10];
        for (int i = 0;i<3;i++){
            for (int j = 0;j<10;j++){
                if (i==0)roos[i][j]=new Rooms(((i+1)*100+j),"标准单间",true);
                if (i==1)roos[i][j]=new Rooms(((i+1)*100+j),"双人间",true);
                if (i==2)roos[i][j]=new Rooms(((i+1)*100+j),"总统套房",true);
            }
        }
    }

    public void look(){
        for(int i = 0;i<3;i++){
            for(int j = 0; j <10;j++){
                System.out.print(roos[i][j]);
            }
            System.out.println();
        }
    }

    public void yuding(int b){
        roos[b/100-1][b%100].setState(false);
    }
    public void tuifang(int b){
        roos[b/100-1][b%100-1].setState(true);
    }
}
