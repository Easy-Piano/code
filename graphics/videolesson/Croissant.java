package graphics.videolesson;

//IVAN
public class Croissant {
    private int note;
    private int startTick;
    private int finishTick;
    private int velocity;

    public void setVelocity(int num){
        velocity = num;
    }
    public void setNote (int num){
        note = num;
    }
    public void setStartTick (int num){
        startTick = num;
    }
    public void setFinishTick (int num){
        finishTick = num;
    }
    public int getVelocity(){
        return velocity;
    }
    public int  getNote (){
        return note;
    }
    public int  getStartTick (){
        return startTick;
    }
    public int  getFinishTick (){
        return finishTick;
    }
}
