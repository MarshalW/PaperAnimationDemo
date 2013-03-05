package com.example.paper;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

public class MyActivity extends Activity {

    private GLSurfaceView surfaceView;

    private PageAnimationRenderer renderer;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.surfaceView = new GLSurfaceView(this);
        this.surfaceView.setEGLContextClientVersion(2);
        this.renderer=new PageAnimationRenderer();
        this.surfaceView.setRenderer(renderer);
        this.surfaceView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);

        this.setContentView(this.surfaceView);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.renderer.startAnimation(this.surfaceView);
    }
}
