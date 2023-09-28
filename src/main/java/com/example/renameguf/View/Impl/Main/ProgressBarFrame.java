package com.example.renameguf.View.Impl.Main;

import com.example.renameguf.View.ProgressFrame;
import lombok.Getter;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.atomic.AtomicInteger;


@Getter
public class ProgressBarFrame extends JFrame implements ProgressFrame {

    private JProgressBar progressBar;
    private AtomicInteger count = new AtomicInteger(0);;
    private Runnable runnable;

    public ProgressBarFrame (Runnable closeProgressBarEvent){
        this.runnable = closeProgressBarEvent;
    }

    @Override
    public void createBar(int size) {

        progressBar = new JProgressBar(0, size);
        progressBar.setValue(0);
        progressBar.setStringPainted(true);

        add(this.progressBar);
        setLocationRelativeTo(null);
        setVisible(true);
        System.out.println(progressBar);
        setSize(800, 90);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                runnable.run();
            }
        });
    }

    @Override
    public void closeBar() {
        progressBar = null;
        count = new AtomicInteger(0);
        dispose();
    }

    @Override
    public void updateBar() {
        progressBar.setValue(count.incrementAndGet());
    }
}
