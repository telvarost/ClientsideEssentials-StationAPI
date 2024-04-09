//package com.github.telvarost.clientsideessentials.mixin.gamma;
//
//import com.github.telvarost.clientsideessentials.PostProcess;
//import net.minecraft.block.BlockBase;
//import net.minecraft.client.render.PortalTextureBinder;
//import net.minecraft.client.render.TextureBinder;
//import org.spongepowered.asm.mixin.Mixin;
//import org.spongepowered.asm.mixin.Shadow;
//import org.spongepowered.asm.mixin.injection.At;
//import org.spongepowered.asm.mixin.injection.Inject;
//import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
//
//@Mixin(PortalTextureBinder.class)
//public class PortalTextureBinderMixin extends TextureBinder {
//    @Shadow private int updatesRan;
//
//    @Shadow private byte[][] texture;
//
//    public PortalTextureBinderMixin() {
//        super(BlockBase.PORTAL.texture);
//    }
//
//    @Inject(
//            method = "update",
//            at = @At("HEAD"),
//            cancellable = true
//    )
//    public void update(CallbackInfo ci) {
//        ++this.updatesRan;
//        byte[] byArray = this.texture[this.updatesRan & 0x1F];
//        PostProcess pp = PostProcess.instance;
//        for (int i = 0; i < 256; ++i) {
//            int n = byArray[i * 4 + 0] & 0xFF;
//            int n2 = byArray[i * 4 + 1] & 0xFF;
//            int n3 = byArray[i * 4 + 2] & 0xFF;
//            int n4 = byArray[i * 4 + 3] & 0xFF;
//            if (this.render3d) {
//                int n5 = (n * 30 + n2 * 59 + n3 * 11) / 100;
//                int n6 = (n * 30 + n2 * 70) / 100;
//                int n7 = (n * 30 + n3 * 70) / 100;
//                n = n5;
//                n2 = n6;
//                n3 = n7;
//            }
//            if (pp != null) {
//                int n5 = pp.red(n, n2, n3);
//                int n6 = pp.green(n, n2, n3);
//                int n7 = pp.blue(n, n2, n3);
//                n = n5;
//                n2 = n6;
//                n3 = n7;
//            }
//            this.grid[i * 4 + 0] = (byte)n;
//            this.grid[i * 4 + 1] = (byte)n2;
//            this.grid[i * 4 + 2] = (byte)n3;
//            this.grid[i * 4 + 3] = (byte)n4;
//        }
//        ci.cancel();
//    }
//}
