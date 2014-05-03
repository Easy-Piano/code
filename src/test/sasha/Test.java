package ru.mipt.cs.easypiano.test.sasha;

/**
 * Created by 1 on 03.05.2014.
 */
public interface Test {
    public final static int SIZE=100000;
    public final static int OFFSET=0;//to skip the beggining
    public final static int OFFSET_F=750000;
    public final static String resoursePath=Test.class.getClassLoader().getResource("//").getPath()+"ru\\mipt\\cs\\easypiano\\resourses\\";
}
