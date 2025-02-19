package com.github.telvarost.clientsideessentials.mixin;

import net.minecraft.item.ItemStack;
import net.minecraft.item.SlabBlockItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/** - All credit for the code in this class goes to Dany and his mod UniTweaks
 *  See: https://github.com/DanyGames2014/UniTweaks
 */
@Mixin(SlabBlockItem.class)
public class StoneSlabItemMixin {

    @Inject(method = "getTranslationKey", at = @At("HEAD"), cancellable = true)
    public void preventCrash(ItemStack stack, CallbackInfoReturnable<String> cir){
        if(stack.getDamage() >= net.minecraft.block.SlabBlock.names.length){
            cir.setReturnValue(null);
        }
    }
}