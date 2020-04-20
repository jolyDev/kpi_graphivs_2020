package com.my.lab4;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.sun.j3d.utils.geometry.*;
import com.sun.j3d.utils.universe.SimpleUniverse;
import javax.media.j3d.*;
import javax.swing.Timer;
import javax.vecmath.*;

// Vector3f - float, Vector3d - double
public class RubicksCube implements ActionListener {
    private float upperEyeLimit = 8.0f; // 5.0
    private float lowerEyeLimit = 5.0f; // 1.0
    private float farthestEyeLimit = 17.0f; // 6.0
    private float nearestEyeLimit = 10.0f; // 3.0

    private TransformGroup treeTransformGroup;
    private TransformGroup viewingTransformGroup;
    private Transform3D treeTransform3D = new Transform3D();
    private Transform3D viewingTransform = new Transform3D();
    private float angle = 0;
    private float eyeHeight;
    private float eyeDistance;
    private boolean descend = true;
    private boolean approaching = true;

    public static void main(String[] args) {
        new RubicksCube();
    }

    private RubicksCube() {
        Timer timer = new Timer(50, this);
        SimpleUniverse universe = new SimpleUniverse();

        viewingTransformGroup = universe.getViewingPlatform().getViewPlatformTransform();
        universe.addBranchGraph(createSceneGraph());

        eyeHeight = upperEyeLimit;
        eyeDistance = farthestEyeLimit;
        timer.start();
    }

    private BranchGroup createSceneGraph() {
        BranchGroup objRoot = new BranchGroup();

        // створюємо об'єкт, що будемо додавати до групи
        treeTransformGroup = new TransformGroup();
        treeTransformGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        buildIceCream();
        objRoot.addChild(treeTransformGroup);

        Background background = new Background(new Color3f(0.9f, 0.9f, 0.9f)); // white color
        BoundingSphere sphere = new BoundingSphere(new Point3d(0,0,0), 100000);
        background.setApplicationBounds(sphere);
        objRoot.addChild(background);

        // налаштовуємо освітлення
        BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0),100.0);
        Color3f light1Color = new Color3f(1.0f, 0.5f, 0.4f);
        Vector3f light1Direction = new Vector3f(4.0f, -7.0f, -12.0f);
        DirectionalLight light1 = new DirectionalLight(light1Color, light1Direction);
        light1.setInfluencingBounds(bounds);
        objRoot.addChild(light1);

        // встановлюємо навколишнє освітлення
        Color3f ambientColor = new Color3f(1.0f, 1.0f, 1.0f);
        AmbientLight ambientLightNode = new AmbientLight(ambientColor);
        ambientLightNode.setInfluencingBounds(bounds);
        objRoot.addChild(ambientLightNode);
        return objRoot;
    }


    private TransformGroup getCubeTG(Appearance ap, Vector3f position){
        Box cube = new Box(0.9f, 0.9f, 0.9f, ap);
        Transform3D cubeT = new Transform3D();
        cubeT.setTranslation(position);
        TransformGroup cubeTG = new TransformGroup();
        cubeTG.setTransform(cubeT);
        cubeTG.addChild(cube);
        return cubeTG;
    }

    private void buildIceCream() {
        Box bigCube = new Box(3, 3, 3, Utils.getBodyAppearence());
        Transform3D bigCubeT = new Transform3D();
        bigCubeT.setTranslation(new Vector3f());
        TransformGroup bigCubeTG = new TransformGroup();
        bigCubeTG.setTransform(bigCubeT);
        bigCubeTG.addChild(bigCube);


        bigCubeTG.addChild(getCubeTG(Utils.getBlueCubeAppearence(), new Vector3f(2.2f, 0, 0)));
        bigCubeTG.addChild(getCubeTG(Utils.getBlueCubeAppearence(), new Vector3f(2.2f, 2f, 0)));
        bigCubeTG.addChild(getCubeTG(Utils.getBlueCubeAppearence(), new Vector3f(2.2f, 2f, 2f)));
        bigCubeTG.addChild(getCubeTG(Utils.getBlueCubeAppearence(), new Vector3f(2.2f, -2f, 0)));
        bigCubeTG.addChild(getCubeTG(Utils.getBlueCubeAppearence(), new Vector3f(2.2f, -2f, 2f)));
        bigCubeTG.addChild(getCubeTG(Utils.getBlueCubeAppearence(), new Vector3f(2.2f, -2f, -2f)));
        bigCubeTG.addChild(getCubeTG(Utils.getBlueCubeAppearence(), new Vector3f(2.2f, 0, 2f)));
        bigCubeTG.addChild(getCubeTG(Utils.getBlueCubeAppearence(), new Vector3f(2.2f, 0, -2f)));
        bigCubeTG.addChild(getCubeTG(Utils.getBlueCubeAppearence(), new Vector3f(2.2f, 2f, -2f)));

        bigCubeTG.addChild(getCubeTG(Utils.getYellowCubeAppearence(), new Vector3f(-2.2f, 0, 0)));
        bigCubeTG.addChild(getCubeTG(Utils.getYellowCubeAppearence(), new Vector3f(-2.2f, 2, 0)));
        bigCubeTG.addChild(getCubeTG(Utils.getYellowCubeAppearence(), new Vector3f(-2.2f, 2, 2)));
        bigCubeTG.addChild(getCubeTG(Utils.getYellowCubeAppearence(), new Vector3f(-2.2f, -2, 0)));
        bigCubeTG.addChild(getCubeTG(Utils.getYellowCubeAppearence(), new Vector3f(-2.2f, -2, 2)));
        bigCubeTG.addChild(getCubeTG(Utils.getYellowCubeAppearence(), new Vector3f(-2.2f, -2, -2)));
        bigCubeTG.addChild(getCubeTG(Utils.getYellowCubeAppearence(), new Vector3f(-2.2f, 0, 2)));
        bigCubeTG.addChild(getCubeTG(Utils.getYellowCubeAppearence(), new Vector3f(-2.2f, 0, -2)));
        bigCubeTG.addChild(getCubeTG(Utils.getYellowCubeAppearence(), new Vector3f(-2.2f, 2, -2)));

        bigCubeTG.addChild(getCubeTG(Utils.getGreenCubeAppearence(), new Vector3f(0, -2.2f, 0)));
        bigCubeTG.addChild(getCubeTG(Utils.getGreenCubeAppearence(), new Vector3f(2, -2.2f, 0)));
        bigCubeTG.addChild(getCubeTG(Utils.getGreenCubeAppearence(), new Vector3f(2, -2.2f, 2)));
        bigCubeTG.addChild(getCubeTG(Utils.getGreenCubeAppearence(), new Vector3f(-2, -2.2f, 0)));
        bigCubeTG.addChild(getCubeTG(Utils.getGreenCubeAppearence(), new Vector3f(0, -2.2f, 2)));
        bigCubeTG.addChild(getCubeTG(Utils.getGreenCubeAppearence(), new Vector3f(0, -2.2f, -2)));
        bigCubeTG.addChild(getCubeTG(Utils.getGreenCubeAppearence(), new Vector3f(-2, -2.2f, 2)));
        bigCubeTG.addChild(getCubeTG(Utils.getGreenCubeAppearence(), new Vector3f(2, -2.2f, -2)));
        bigCubeTG.addChild(getCubeTG(Utils.getGreenCubeAppearence(), new Vector3f(-2, -2.2f, -2)));

        bigCubeTG.addChild(getCubeTG(Utils.getOrangeCubeAppearence(), new Vector3f(0, 2.2f, 0)));
        bigCubeTG.addChild(getCubeTG(Utils.getOrangeCubeAppearence(), new Vector3f(2, 2.2f, 0)));
        bigCubeTG.addChild(getCubeTG(Utils.getOrangeCubeAppearence(), new Vector3f(2, 2.2f, 2)));
        bigCubeTG.addChild(getCubeTG(Utils.getOrangeCubeAppearence(), new Vector3f(-2, 2.2f, 0)));
        bigCubeTG.addChild(getCubeTG(Utils.getOrangeCubeAppearence(), new Vector3f(0, 2.2f, 2)));
        bigCubeTG.addChild(getCubeTG(Utils.getOrangeCubeAppearence(), new Vector3f(0, 2.2f, -2)));
        bigCubeTG.addChild(getCubeTG(Utils.getOrangeCubeAppearence(), new Vector3f(-2, 2.2f, 2)));
        bigCubeTG.addChild(getCubeTG(Utils.getOrangeCubeAppearence(), new Vector3f(2, 2.2f, -2)));
        bigCubeTG.addChild(getCubeTG(Utils.getOrangeCubeAppearence(), new Vector3f(-2, 2.2f, -2)));

        bigCubeTG.addChild(getCubeTG(Utils.getRedCubeAppearence(), new Vector3f(0, 0, 2.2f)));
        bigCubeTG.addChild(getCubeTG(Utils.getRedCubeAppearence(), new Vector3f(2, 0, 2.2f)));
        bigCubeTG.addChild(getCubeTG(Utils.getRedCubeAppearence(), new Vector3f(2, 2, 2.2f)));
        bigCubeTG.addChild(getCubeTG(Utils.getRedCubeAppearence(), new Vector3f(-2, 0, 2.2f)));
        bigCubeTG.addChild(getCubeTG(Utils.getRedCubeAppearence(), new Vector3f(0, 2, 2.2f)));
        bigCubeTG.addChild(getCubeTG(Utils.getRedCubeAppearence(), new Vector3f(0, -2, 2.2f)));
        bigCubeTG.addChild(getCubeTG(Utils.getRedCubeAppearence(), new Vector3f(-2, 2, 2.2f)));
        bigCubeTG.addChild(getCubeTG(Utils.getRedCubeAppearence(), new Vector3f(2, -2, 2.2f)));
        bigCubeTG.addChild(getCubeTG(Utils.getRedCubeAppearence(), new Vector3f(-2, -2, 2.2f)));

        bigCubeTG.addChild(getCubeTG(Utils.getWhiteCubeAppearence(), new Vector3f(0, 0, -2.2f)));
        bigCubeTG.addChild(getCubeTG(Utils.getWhiteCubeAppearence(), new Vector3f(2, 0, -2.2f)));
        bigCubeTG.addChild(getCubeTG(Utils.getWhiteCubeAppearence(), new Vector3f(2, 2, -2.2f)));
        bigCubeTG.addChild(getCubeTG(Utils.getWhiteCubeAppearence(), new Vector3f(-2, 0, -2.2f)));
        bigCubeTG.addChild(getCubeTG(Utils.getWhiteCubeAppearence(), new Vector3f(0, 2, -2.2f)));
        bigCubeTG.addChild(getCubeTG(Utils.getWhiteCubeAppearence(), new Vector3f(0, -2, -2.2f)));
        bigCubeTG.addChild(getCubeTG(Utils.getWhiteCubeAppearence(), new Vector3f(-2, 2, -2.2f)));
        bigCubeTG.addChild(getCubeTG(Utils.getWhiteCubeAppearence(), new Vector3f(2, -2, -2.2f)));
        bigCubeTG.addChild(getCubeTG(Utils.getWhiteCubeAppearence(), new Vector3f(-2, -2, -2.2f)));



        treeTransformGroup.addChild(bigCubeTG);

//        Box body2 = Utils.getBody(0.125f, 0.6f);
//        Transform3D body2T = new Transform3D();
//        body2T.setTranslation(new Vector3f(.0f, .0f, 0.25f));
//        TransformGroup body2TG = new TransformGroup();
//        body2TG.setTransform(body2T);
//        body2TG.addChild(body2);
//        treeTransformGroup.addChild(body2TG);

    }

    // ActionListener interface
    @Override
    public void actionPerformed(ActionEvent e) {
        float delta = 0.03f;

        // rotation of the castle
        treeTransform3D.rotZ(angle);
        treeTransformGroup.setTransform(treeTransform3D);
        angle += delta;

        // change of the camera position up and down within defined limits
        if (eyeHeight > upperEyeLimit){
            descend = true;
        }else if(eyeHeight < lowerEyeLimit){
            descend = false;
        }
        if (descend){
            eyeHeight -= delta;
        }else{
            eyeHeight += delta;
        }

        // change camera distance to the scene
        if (eyeDistance > farthestEyeLimit){
            approaching = true;
        }else if(eyeDistance < nearestEyeLimit){
            approaching = false;
        }
        if (approaching){
            eyeDistance -= delta;
        }else{
            eyeDistance += delta;
        }

        Point3d eye = new Point3d(eyeDistance, eyeDistance, eyeHeight); // spectator's eye
        Point3d center = new Point3d(.0f, .0f ,0.1f); // sight target
        Vector3d up = new Vector3d(.0f, .0f, 1.0f);; // the camera frustum
        viewingTransform.lookAt(eye, center, up);
        viewingTransform.invert();
        viewingTransformGroup.setTransform(viewingTransform);
    }
}
