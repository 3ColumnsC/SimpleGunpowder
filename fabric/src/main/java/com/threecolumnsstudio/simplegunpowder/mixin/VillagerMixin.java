package com.threecolumnsstudio.simplegunpowder.mixin;

import com.threecolumnsstudio.simplegunpowder.fabric.FabricGunpowderTrades;
import net.minecraft.entity.passive.VillagerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(VillagerEntity.class)
public abstract class VillagerMixin {

    @Inject(method = "fillRecipes", at = @At("TAIL"))
    private void onFillRecipes(CallbackInfo ci) {
        VillagerEntity self = (VillagerEntity) (Object) this;
        FabricGunpowderTrades.maybeAddGunpowderTrade(self);
    }
}
