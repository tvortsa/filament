/*
 * Copyright (C) 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.android.filament.livewallpaper

import android.animation.ValueAnimator
import android.app.Service
import android.graphics.Color
import android.graphics.PixelFormat
import android.opengl.Matrix
import android.service.wallpaper.WallpaperService
import android.util.Log
import android.view.*
import android.view.animation.LinearInterpolator
import com.google.android.filament.*
import com.google.android.filament.View
import com.google.android.filament.android.DisplayHelper
import com.google.android.filament.android.UiHelper
import com.google.android.filament.gltfio.*
import com.google.android.filament.utils.*
import java.nio.ByteBuffer
import java.util.Arrays.copyOf

class FilamentLiveWallpaper : WallpaperService() {
    // Load the library for the utility layer, which in turn loads gltfio and the Filament core.
    companion object {
        init {
            Utils.init()
        }
    }

    override fun onCreateEngine(): Engine {
        return FilamentWallpaperEngine()
    }

    private inner class FilamentWallpaperEngine : Engine() {

        // UiHelper is provided by Filament to manage SurfaceHolder
        private lateinit var uiHelper: UiHelper
        // DisplayHelper is provided by Filament to manage the display
        private lateinit var displayHelper: DisplayHelper
        // Choreographer is used to schedule new frames
        private lateinit var choreographer: Choreographer

        private lateinit var modelViewer: ModelViewer

        // Engine creates and destroys Filament resources
        // Each engine must be accessed from a single thread of your choosing
        // Resources cannot be shared across engines
        private lateinit var engine: com.google.android.filament.Engine
        // A renderer instance is tied to a single surface (SurfaceView, TextureView, etc.)
        private lateinit var renderer: Renderer
        // A scene holds all the renderable, lights, etc. to be drawn
        private lateinit var scene: Scene
        // A view defines a viewport, a scene and a camera for rendering
        private lateinit var view: View
        // Should be pretty obvious :)
        private lateinit var camera: Camera

        @Entity var light: Int? = null

        // A swap chain is Filament's representation of a surface
        private var swapChain: SwapChain? = null

        // Performs the rendering and schedules new frames
        private val frameScheduler = FrameCallback()

        // We'll use this ValueAnimator to smoothly cycle the background between hues.
        private val animator = ValueAnimator.ofFloat(0.0f, 360.0f)

        private var asset: FilamentAsset? = null

        private lateinit var assetLoader: AssetLoader
        private lateinit var resourceLoader: ResourceLoader

        private lateinit var assetAnimator: Animator

        override fun onCreate(surfaceHolder: SurfaceHolder) {
            super.onCreate(surfaceHolder)
            surfaceHolder.setSizeFromLayout()
            surfaceHolder.setFormat(PixelFormat.RGBA_8888)

            choreographer = Choreographer.getInstance()

            modelViewer = ModelViewer(surfaceHolder, this@FilamentLiveWallpaper)

            //displayHelper = DisplayHelper(this@FilamentLiveWallpaper)

            setupUiHelper()
            setupFilament()
            setupView()
            setupScene()

            // Load the glTF asset
            // assetLoader = AssetLoader(engine, MaterialProvider(engine), EntityManager.get())
            // resourceLoader = ResourceLoader(engine, false, true)

            // Always add a direct light source since it is required for shadowing.
            // We highly recommend adding an indirect light as well.

            // light = EntityManager.get().create()

            /*
            val (r, g, b) = Colors.cct(6_500.0f)
            LightManager.Builder(LightManager.Type.DIRECTIONAL)
                    .color(r, g, b)
                    .intensity(10000.0f)
                    .direction(-0.1f, -0.3f, -0.7f)
                    .castShadows(true)
                    .build(engine, light!!)

            scene.addEntity(light!!)
             */

            // TODO: move to createRenderables()
            val buffer = assets.open("models/scene.gltf").use { input ->
                val bytes = ByteArray(input.available())
                input.read(bytes)
                ByteBuffer.wrap(bytes)
            }

            modelViewer.loadModelGltfAsync(buffer) { uri -> readCompressedAsset("models/$uri") }
            modelViewer.transformToUnitCube()

            /*
            asset = assetLoader.createAssetFromBinary(buffer)
            asset?.let { asset ->
                resourceLoader.loadResources(asset)
                assetAnimator = asset.animator
                asset.releaseSourceData()

                scene.addEntities(asset.entities)

                asset.cameraEntities.firstOrNull()?.let { camera ->
                    val cam = engine.getCameraComponent(camera)
                    //view.camera = cam

                    val pm = cam?.getCullingProjectionMatrix(null)
                    val n = cam?.near
                    val f = cam?.cullingFar

                    if (n != null && f != null) {
                        Log.d("Live wallpaper", n.toString())
                        Log.d("Live wallpaper", f.toString())
                    }

                    if (pm != null) {
                        Log.d("Live wallpaper", "here 0")
                        Log.d("Live wallpaper", pm[0].toString())
                        Log.d("Live wallpaper", pm[1].toString())
                        Log.d("Live wallpaper", pm[2].toString())
                        Log.d("Live wallpaper", pm[3].toString())
                        Log.d("Live wallpaper", "here 1")
                        Log.d("Live wallpaper", pm[4].toString())
                        Log.d("Live wallpaper", pm[5].toString())
                        Log.d("Live wallpaper", pm[6].toString())
                        Log.d("Live wallpaper", pm[7].toString())
                        Log.d("Live wallpaper", "here 2")
                        Log.d("Live wallpaper", pm[8].toString())
                        Log.d("Live wallpaper", pm[9].toString())
                        Log.d("Live wallpaper", pm[10].toString())
                        Log.d("Live wallpaper", pm[11].toString())
                        Log.d("Live wallpaper", "here 3")
                        Log.d("Live wallpaper", pm[12].toString())
                        Log.d("Live wallpaper", pm[13].toString())
                        Log.d("Live wallpaper", pm[14].toString())
                        Log.d("Live wallpaper", pm[15].toString())
                        Log.d("Live wallpaper", "here 4")
                        Log.d("Live wallpaper", "------------")
                    }

                    //Log.d("Live wallpaper sample", pm.toString())

                    if (cam != null && pm != null) {
                        val npm = pm.clone()

                        val m = perspective(50.0f, 1.0f, 0.1f, 1.00f)
                        val ma = m.toFloatArray()
                        Log.d("Live wallpaper", "here 0")
                        Log.d("Live wallpaper", ma[0].toString())
                        Log.d("Live wallpaper", ma[1].toString())
                        Log.d("Live wallpaper", ma[2].toString())
                        Log.d("Live wallpaper", ma[3].toString())
                        Log.d("Live wallpaper", "here 1")
                        Log.d("Live wallpaper", ma[4].toString())
                        Log.d("Live wallpaper", ma[5].toString())
                        Log.d("Live wallpaper", ma[6].toString())
                        Log.d("Live wallpaper", ma[7].toString())
                        Log.d("Live wallpaper", "here 2")
                        Log.d("Live wallpaper", ma[8].toString())
                        Log.d("Live wallpaper", ma[9].toString())
                        Log.d("Live wallpaper", ma[10].toString())
                        Log.d("Live wallpaper", ma[11].toString())
                        Log.d("Live wallpaper", "here 3")
                        Log.d("Live wallpaper", ma[12].toString())
                        Log.d("Live wallpaper", ma[13].toString())
                        Log.d("Live wallpaper", ma[14].toString())
                        Log.d("Live wallpaper", ma[15].toString())
                        Log.d("Live wallpaper", "here 4")

                        /*
                        val ben = doubleArrayOf(
                                2.777, 0.0, 0.0, 0.0,
                                0.0, 2.777, 0.0, 0.0,
                                0.0, 0.0, -1.002, -1.0,
                                0.0, 0.0, -0.222, 0.0
                        )

                         */
                        val ben = doubleArrayOf(
                                6.777, 0.0, 0.0, 0.0,
                                0.0, 6.777, 0.0, 0.0,
                                0.0, 0.788, -1.002, -1.0,
                                0.0, 0.0, -0.222, 0.0
                        )

                        //cam.setProjection(Camera.Projection.PERSPECTIVE, -0.05, 0.05, 0.03, 0.13, 0.1, 100.0)
                        //cam.setProjection(Camera.Projection.PERSPECTIVE, -0.05, 0.05, 0.03, 0.13, 0.1, 100.0)

                        //cam.setProjection(40.0, 1.0, 0.1, 100.0, Camera.Fov.VERTICAL)
                        cam.setCustomProjection(ben, 0.1, 100.0)
                        val scaling = cam?.getScaling(null)
                        if (scaling != null) {
                            Log.d("Live wallpaper scaling", scaling[0].toString())
                            Log.d("Live wallpaper scaling", scaling[1].toString())
                            Log.d("Live wallpaper scaling", scaling[2].toString())
                            Log.d("Live wallpaper scaling", scaling[3].toString())
                        }
                        //cam.setCustomProjection(ben, 0.1, 100.0)
                        //val r = cam.getProjectionMatrix(null)
                        /*
                         */
                    }
                    view.camera = cam

                }
            }
             */
        }

        override fun onTouchEvent(event: MotionEvent?) {
            super.onTouchEvent(event)
            event?.let { modelViewer.onTouchEvent(it) }
        }

        private fun readCompressedAsset(assetName: String): ByteBuffer {
            val input = assets.open(assetName)
            val bytes = ByteArray(input.available())
            input.read(bytes)
            return ByteBuffer.wrap(bytes)
        }

        private fun setupUiHelper() {
            uiHelper = UiHelper(UiHelper.ContextErrorPolicy.DONT_CHECK)
            uiHelper.renderCallback = SurfaceCallback()
            uiHelper.attachTo(surfaceHolder)
        }

        private fun setupFilament() {
            engine = com.google.android.filament.Engine.create()
            renderer = engine.createRenderer()
            scene = engine.createScene()
            view = engine.createView()
            camera = engine.createCamera()
        }

        private fun setupView() {
            scene.skybox = Skybox.Builder().color(floatArrayOf(1.0f, 1.0f, 1.0f, 1.0f)).build(engine)

            // NOTE: Try to disable post-processing (tone-mapping, etc.) to see the difference
            // view.isPostProcessingEnabled = false

            // Tell the view which camera we want to use
            view.camera = camera

            // Tell the view which scene we want to render
            view.scene = scene

            val bo = View.BloomOptions()
            bo.enabled = true
            bo.highlight = 10.0f
            bo.strength = 5.0f
            view.bloomOptions = bo
        }

        private fun setupScene() {
            // Set the exposure on the camera, this exposure follows the sunny f/16 rule
            camera.setExposure(16.0f, 1.0f / 125.0f, 100.0f)

            startAnimation()
        }

        private fun startAnimation() {
            // Animate the color of the Skybox.
            animator.interpolator = LinearInterpolator()
            animator.duration = 10000
            animator.repeatMode = ValueAnimator.RESTART
            animator.repeatCount = ValueAnimator.INFINITE
            /*
            animator.addUpdateListener { a ->
                val hue = a.animatedValue as Float
                val color = Color.HSVToColor(floatArrayOf(hue, 1.0f, 1.0f))
                scene.skybox?.setColor(floatArrayOf(
                        Color.red(color)   / 255.0f,
                        Color.green(color) / 255.0f,
                        Color.blue(color)  / 255.0f,
                        1.0f))
            }
             */
            animator.start()
        }

        override fun onVisibilityChanged(visible: Boolean) {
            super.onVisibilityChanged(visible)
            if (visible) {
                choreographer.postFrameCallback(frameScheduler)
                animator.start()
            } else {
                choreographer.removeFrameCallback(frameScheduler)
                animator.cancel()
            }
        }

        override fun onDestroy() {
            super.onDestroy()

            // Stop the animation and any pending frame
            choreographer.removeFrameCallback(frameScheduler)
            animator.cancel()

            // Always detach the surface before destroying the engine
            uiHelper.detach()

            // Cleanup all resources
            engine.destroyRenderer(renderer)
            engine.destroyView(view)
            engine.destroyScene(scene)
            engine.destroyCamera(camera)

            // Destroying the engine will free up any resource you may have forgotten
            // to destroy, but it's recommended to do the cleanup properly
            engine.destroy()
        }

        inner class FrameCallback : Choreographer.FrameCallback {
            private val startTime = System.nanoTime()

            override fun doFrame(frameTimeNanos: Long) {
                // Schedule the next frame
                choreographer.postFrameCallback(this)

                /*
                if (assetAnimator.animationCount > 0) {
                    val elapsedTimeSeconds = (frameTimeNanos - startTime).toDouble() / 1_000_000_000 / 4.0
                    assetAnimator.applyAnimation(0, elapsedTimeSeconds.toFloat())
                }
                 */

                modelViewer.render(frameTimeNanos)

                // This check guarantees that we have a swap chain
                /*
                if (uiHelper.isReadyToRender) {
                    // If beginFrame() returns false you should skip the frame
                    // This means you are sending frames too quickly to the GPU
                    if (renderer.beginFrame(swapChain!!, frameTimeNanos)) {
                        renderer.render(view)
                        renderer.endFrame()
                    }
                }
                 */
            }
        }

        inner class SurfaceCallback : UiHelper.RendererCallback {
            override fun onNativeWindowChanged(surface: Surface) {
                swapChain?.let { engine.destroySwapChain(it) }
                swapChain = engine.createSwapChain(surface)
                /*
                val display =
                        (application.getSystemService(Service.WINDOW_SERVICE) as WindowManager)
                        .defaultDisplay
                displayHelper.attach(renderer, display)
                 */
            }

            override fun onDetachedFromSurface() {
                //displayHelper.detach()
                swapChain?.let {
                    engine.destroySwapChain(it)
                    // Required to ensure we don't return before Filament is done executing the
                    // destroySwapChain command, otherwise Android might destroy the Surface
                    // too early
                    engine.flushAndWait()
                    swapChain = null
                }
            }

            override fun onResized(width: Int, height: Int) {
                val aspect = width.toDouble() / height.toDouble()
                camera.setProjection(45.0, aspect, 0.1, 20.0, Camera.Fov.VERTICAL)

                view.camera?.setScaling(doubleArrayOf(1.0 * 1.5, aspect * 1.5, 1.0, 1.0))

                view.viewport = Viewport(0, 0, width, height)
            }
        }
    }
}