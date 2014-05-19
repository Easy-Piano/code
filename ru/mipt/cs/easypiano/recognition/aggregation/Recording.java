package ru.mipt.cs.easypiano.recognition.aggregation;
//SASHA
import ru.mipt.cs.easypiano.test.sasha.Test;

import javax.sound.sampled.*;
import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class Recording {
    // текущий звуковой файл
    private File file;
    // номер файла
    private int suffix = 0;
    // аудиформат
    private AudioFileFormat.Type fileType = AudioFileFormat.Type.WAVE;
    private int MONO = 1;
    // определение формата аудиоданных
    private AudioFormat format = new AudioFormat(
            AudioFormat.Encoding.PCM_SIGNED,44100, 16, MONO, 2, 44100, true);
    // микрофонный вход
    private TargetDataLine mike;

    // создать новый файл
    public File getNewFile() {////////////////////////Test.resoursePath added
        try {
            do {
                // новое название файла
                String filename = "samples_";
                String soundFileName = Test.resoursePath + filename + (suffix++) + "." + fileType.getExtension();
                file = new File(soundFileName);
            } while (!file.createNewFile());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return file;
    }

    // запуск записи
    public void startRecording() {
        new Thread() {
            public void run() {
                // линию соединения
                DataLine.Info info = new DataLine.Info(TargetDataLine.class,format);
                // проверить, поддерживается ли линия
                if (!AudioSystem.isLineSupported(info)) {
                    JOptionPane.showMessageDialog(null, "Line not supported"
                            + info, "Line not supported",
                            JOptionPane.ERROR_MESSAGE);
                }
                try {
                    // получить подходящую линию
                    mike = (TargetDataLine) AudioSystem.getLine(info);
                    // открываем линию соединения с указаным форматом и размером
                    // буфера
                    mike.open(format, mike.getBufferSize());
                    // поток микрофона
                    AudioInputStream sound = new AudioInputStream(mike);
                    // запустить линию соединения
                    mike.start();
                    // записать содержимое потока в файл
                    AudioSystem.write(sound, fileType, file);
                } catch (LineUnavailableException ex) {
                    JOptionPane.showMessageDialog(null, "Line not available"
                            + ex, "Line not available",
                            JOptionPane.ERROR_MESSAGE);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "I/O Error " + ex,
                            "I/O Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }.start();
    }
    // остановка записи
    public void stopRecording() {
        mike.stop();
        mike.close();
    }
    public void mikeStart(){
        mike.start();
    }
    public AudioInputStream getStream(){
        AudioInputStream sound;
        DataLine.Info info = new DataLine.Info(TargetDataLine.class,format);
        if (!AudioSystem.isLineSupported(info)) {
            JOptionPane.showMessageDialog(null, "Line not supported"
                            + info, "Line not supported",
                    JOptionPane.ERROR_MESSAGE);
        }
        try {
            mike = (TargetDataLine) AudioSystem.getLine(info);
            mike.open(format, mike.getBufferSize());
            sound = new AudioInputStream(mike);
            return sound;
        }catch (LineUnavailableException ex) {
            JOptionPane.showMessageDialog(null, "Line not available"
                            + ex, "Line not available",
                    JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
    public double getSampleRate(){
        return mike.getFormat().getSampleRate();
    }
}


