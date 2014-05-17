package ru.mipt.cs.easypiano.test.ivan;

import ru.mipt.cs.easypiano.graphics.videolesson.Croissant;
import ru.mipt.cs.easypiano.graphics.videolesson.VideoUtils;
//IVAN
public class DecTest {
    public static void main(String[] args){
        Croissant  []  notes;
        String str = "C:\\Users\\Asus\\Documents\\GitHub\\easypiano\\src\\ru.mipt.cs.easypiano.resourses\\yiruma-river_flows_in_you.mid";
        VideoUtils.decodeFile(str);
    }
}
