package ru.mipt.cs.test.sasha;
//SASHA
/**
 * Created by 1 on 03.05.2014.
 */
public interface Test {
    public final static int SIZE=100000;
    public final static int OFFSET=0;//to skip the beggining
    public final static int OFFSET_F=750000;
    public final static String resourcePath =Test.class.getClassLoader().getResource("//").getPath()+"ru\\mipt\\cs\\resourses\\";
    /*extension methods are not supported at this languale level
    public static String getPath(String s){
        String buf=resourcePath;
        buf+=s;
        return buf;
    }*/
}
