package com.example.paper;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.view.SurfaceView;

/**
 * Created with IntelliJ IDEA.
 * User: marshal
 * Date: 13-3-5
 * Time: 下午2:30
 * To change this template use File | Settings | File Templates.
 */
public class PageAnimationView extends GLSurfaceView{

    private PageAnimationRenderer renderer;

    public PageAnimationView(Context context) {
        super(context);
        this.init();
    }

    public PageAnimationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init();
    }

    private void init(){
        this.renderer=new PageAnimationRenderer();

        //使用OpenGL ES 2.0
        this.setEGLContextClientVersion(2);
        this.setEGLConfigChooser(8, 8, 8, 8, 16, 0);
        this.setRenderer(this.renderer);
        this.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }
}
