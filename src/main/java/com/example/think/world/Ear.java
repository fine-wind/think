package com.example.think.world;

import javax.sound.sampled.*;

public class Ear {

    public void startRecording() throws LineUnavailableException {
        AudioFormat audioFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100.0F, 16, 2, 4, 44100.0F, false);
        DataLine.Info info = new DataLine.Info(TargetDataLine.class, audioFormat);

        if (!AudioSystem.isLineSupported(info)) {
            throw new IllegalStateException("Line not supported");
        }

        TargetDataLine targetDataLine = (TargetDataLine) AudioSystem.getLine(info);
        targetDataLine.open(audioFormat);
        targetDataLine.start();

        int BUFFER_SIZE = 16384;
        byte[] buffer = new byte[BUFFER_SIZE];
        int count = targetDataLine.read(buffer, 0, buffer.length);
        if (count > 0) {
            System.out.println(count);
            for (byte b : buffer) {
                System.out.print(b);
            }
        }
    }


    public static void main(String[] args) throws Exception {
        new Ear().startRecording();
    }
}