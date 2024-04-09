//package com.github.telvarost.clientsideessentials.mixin.gamma;
//
//import com.github.telvarost.clientsideessentials.PostProcess;
//import net.minecraft.block.BlockBase;
//import net.minecraft.client.render.FireTextureBinder;
//import net.minecraft.client.render.TextureBinder;
//import org.spongepowered.asm.mixin.Mixin;
//import org.spongepowered.asm.mixin.Shadow;
//import org.spongepowered.asm.mixin.injection.At;
//import org.spongepowered.asm.mixin.injection.Inject;
//import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
//
//@Mixin(FireTextureBinder.class)
//public class FireTextureBinderMixin extends TextureBinder {
//
//    public FireTextureBinderMixin(int i) {
//        super(BlockBase.FIRE.texture + i * 16);
//    }
//    @Shadow protected float[] currentFireFrame;
//
//    @Shadow protected float[] lastFireFrame;
//
//    // TextureFlamesFX
//    @Inject(
//            method = "update",
//            at = @At("HEAD"),
//            cancellable = true
//    )
//    public void update(CallbackInfo ci) {
//        int n;
//        int n2;
//        int n3;
//        int n4;
//        float f;
//        int n5;
//        for (int i = 0; i < 16; ++i) {
//            for (n5 = 0; n5 < 20; ++n5) {
//                int n6 = 18;
//                f = this.currentFireFrame[i + (n5 + 1) % 20 * 16] * (float)n6;
//                for (n4 = i - 1; n4 <= i + 1; ++n4) {
//                    for (n3 = n5; n3 <= n5 + 1; ++n3) {
//                        n2 = n4;
//                        n = n3;
//                        if (n2 >= 0 && n >= 0 && n2 < 16 && n < 20) {
//                            f += this.currentFireFrame[n2 + n * 16];
//                        }
//                        ++n6;
//                    }
//                }
//                this.lastFireFrame[i + n5 * 16] = f / ((float)n6 * 1.06f);
//                if (n5 < 19) continue;
//                this.lastFireFrame[i + n5 * 16] = (float)(Math.random() * Math.random() * Math.random() * 4.0 + Math.random() * (double)0.1f + (double)0.2f);
//            }
//        }
//        float[] fArray = this.lastFireFrame;
//        this.lastFireFrame = this.currentFireFrame;
//        this.currentFireFrame = fArray;
//        PostProcess pp = PostProcess.instance;
//        for (n5 = 0; n5 < 256; ++n5) {
//            float f2 = this.currentFireFrame[n5] * 1.8f;
//            if (f2 > 1.0f) {
//                f2 = 1.0f;
//            }
//            if (f2 < 0.0f) {
//                f2 = 0.0f;
//            }
//            f = f2;
//            n4 = (int)(f * 155.0f + 100.0f);
//            n3 = (int)(f * f * 255.0f);
//            n2 = (int)(f * f * f * f * f * f * f * f * f * f * 255.0f);
//            n = 255;
//            if (f < 0.5f) {
//                n = 0;
//            }
//            f = (f - 0.5f) * 2.0f;
////            if (this.render3d) {
////                int n7 = (n4 * 30 + n3 * 59 + n2 * 11) / 100;
////                int n8 = (n4 * 30 + n3 * 70) / 100;
////                int n9 = (n4 * 30 + n2 * 70) / 100;
////                n4 = n7;
////                n3 = n8;
////                n2 = n9;
////            }
//            if (null != pp) {
//                int n7 = pp.red(n4, n3, n2);
//                int n8 = pp.green(n4, n3, n2);
//                int n9 = pp.blue(n4, n3, n2);
//                n4 = n7;
//                n3 = n8;
//                n2 = n9;
//            }
//            this.grid[n5 * 4 + 0] = (byte)n4;
//            this.grid[n5 * 4 + 1] = (byte)n3;
//            this.grid[n5 * 4 + 2] = (byte)n2;
//            this.grid[n5 * 4 + 3] = (byte)n;
//        }
//        ci.cancel();
//    }
//}
