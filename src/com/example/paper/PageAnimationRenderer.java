package com.example.paper;

import android.animation.ValueAnimator;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.util.Log;
import android.view.animation.DecelerateInterpolator;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created with IntelliJ IDEA.
 * User: marshal
 * Date: 13-3-5
 * Time: 下午2:32
 * To change this template use File | Settings | File Templates.
 */
public class PageAnimationRenderer implements GLSurfaceView.Renderer {

    private float[] projectionMatrix = new float[16];
    private float[] viewMatrix = new float[16];
    private float[] modelMatrix = new float[16];

    private PageMesh pageMesh;

    private float angle;

    public float[] getModelMatrix() {
        return modelMatrix;
    }

    public float[] getProjectionMatrix() {
        return projectionMatrix;
    }

    public float[] getViewMatrix() {
        return viewMatrix;
    }

    public void startAnimation(final GLSurfaceView surfaceView) {
        final ValueAnimator animator = ValueAnimator.ofFloat(0, -1);
        animator.setDuration(3000);
        animator.setStartDelay(500);
        animator.setInterpolator(new DecelerateInterpolator());

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                angle = 90 * (Float) valueAnimator.getAnimatedValue();
                surfaceView.requestRender();
            }
        });

        animator.start();
    }

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        GLES20.glClearColor(0.5f, 0.5f, 0.5f, 0.5f);

        // Position the eye behind the origin.
        final float eyeX = 0.0f;
        final float eyeY = 0.0f;
        final float eyeZ = 2f;

        // We are looking toward the distance
        final float lookX = 0.0f;
        final float lookY = 0.0f;
        final float lookZ = -5.0f;

        // Set our up vector. This is where our head would be pointing were we holding the camera.
        final float upX = 0.0f;
        final float upY = 1.0f;
        final float upZ = 0.0f;

        // Set the view matrix. This matrix can be said to represent the camera position.
        // NOTE: In OpenGL 1, a ModelView matrix is used, which is a combination of a model and
        // view matrix. In OpenGL 2, we can keep track of these matrices separately if we choose.
        Matrix.setLookAtM(this.viewMatrix, 0, eyeX, eyeY, eyeZ, lookX, lookY, lookZ, upX, upY, upZ);

        this.pageMesh = new PageMesh();
    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int width, int height) {
        // Set the OpenGL viewport to the same size as the surface.
        GLES20.glViewport(0, 0, width, height);

        // Create a new perspective projection matrix. The height will stay the same
        // while the width will vary as per aspect ratio.
        final float ratio = (float) width / height;
        final float left = -ratio;
        final float right = ratio;
        final float bottom = -1.0f;
        final float top = 1.0f;
        final float near = 1.0f;
        final float far = 10.0f;

        Matrix.frustumM(this.projectionMatrix, 0, left, right, bottom, top, near, far);
//        Matrix.orthoM(this.projectionMatrix,0,left,right,bottom,top,-10,10);
    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT | GLES20.GL_COLOR_BUFFER_BIT);

        //画上半部分
        Matrix.setIdentityM(this.modelMatrix, 0);
//        Matrix.translateM(this.modelMatrix, 0, 0, 2f, 0);
        Matrix.rotateM(this.modelMatrix, 0, -90, 1, 0, 0);
        Matrix.rotateM(this.modelMatrix, 0, angle, 1f, 0f, 0f);

        this.pageMesh.draw(this);
        Log.d("PageMesh", "angle>>>>" + this.angle);

    }
}
