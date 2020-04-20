package com.my.lab5;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.media.j3d.*;
import javax.swing.Timer;
import javax.vecmath.*;

public class DrumsAnimation implements ActionListener, KeyListener {
    private TransformGroup car;
    private Transform3D transform3D = new Transform3D();

    private float x = 0;
    private float y = 0;

    private int angle;
    private double radius = 1f;
    private double scale = 1;
    private double delta = 0.01;

    private boolean w = false;
    private boolean s = false;
    private boolean a = false;
    private boolean d = false;
    private boolean r = false;

    public DrumsAnimation(TransformGroup boat) {
        this.car = boat;
        this.car.getTransform(this.transform3D);
        angle = (int)getAngle(x, y);
        Timer timer = new Timer(20, this);
        timer.start();
    }

    private void Move() {
        if (w) {
            y += 0.02f;
            angle = (int)getAngle(x, y) + 90;
            System.out.println("angle = " + angle);
        }
        if (s) {
            y -= 0.02f;
            angle = (int)getAngle(x, y) + 90;
            System.out.println("angle = " + angle);
        }
        if (a) {
            x -= 0.02f;
            angle = (int)getAngle(x, y) + 90;
            System.out.println("angle = " + angle);
        }
        if (d) {
            x += 0.02f;
            angle = (int)getAngle(x, y) + 90;
            System.out.println("angle = " + angle);
        }
        if (r) {
            double rotationSpeed = 0.03f;
            Transform3D rotation = new Transform3D();;
//            rotation.rotX(-rotationSpeed);
//            rotation.rotY(-rotationSpeed);
            rotation.rotZ(-rotationSpeed);
            transform3D.mul(rotation);
            x = (float)(radius * Math.sin(Math.toRadians(angle)));
            y = (float)(-radius * Math.cos(Math.toRadians(angle)));
            angle++;
            if ( scale < 0.01 ) {
                delta = -delta;
            } else if (scale > 0.99) {
                delta = -delta;
            }
            scale += delta;
        }

        transform3D.setTranslation(new Vector3f(x, y, 0));


        car.setTransform(transform3D);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Move();
    }

    @Override
    public void keyPressed(KeyEvent ev) {
        switch (ev.getKeyChar()) {
            case 'w': w = true; break;
            case 's': s = true; break;
            case 'a': a = true; break;
            case 'd': d = true; break;
            case 'r': r = true; break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent ev) {
        switch (ev.getKeyChar()) {
            case 'w': w = false; break;
            case 's': s = false; break;
            case 'a': a = false; break;
            case 'd': d = false; break;
            case 'r': r = false; break;
        }
    }

    private double getAngle(double x, double y) {
        radius = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
        switch (getQuadrant(x, y)) {
            case 1:
                return Math.asin(y / Math.hypot(x, y)) * 180 / Math.PI;
            case 2:
                return 180 - Math.asin(y / Math.hypot(x, y)) * 180 / Math.PI;
            case 3:
                return 180 + (-1 * Math.asin(y / Math.hypot(x, y)) * 180 / Math.PI);
            case 4:
                return 360 + Math.asin(y / Math.hypot(x, y)) * 180 / Math.PI;
            default:
                return 0;
        }
    }


    private int getQuadrant(double x, double y) {
        if (x >= 0) {
            return y >= 0 ? 1 : 4;
        } else {
            return y >= 0 ? 2 : 3;
        }
    }
}
