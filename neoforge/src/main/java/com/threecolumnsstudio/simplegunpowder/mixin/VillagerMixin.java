package com.threecolumnsstudio.simplegunpowder.mixin;

import com.threecolumnsstudio.simplegunpowder.neoforge.NeoForgeGunpowderTrades;
import net.minecraft.world.entity.npc.Villager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Villager.class)
public class VillagerMixin {

    @Inject(method = "updateTrades", at = @At("TAIL"))
    private void onUpdateTrades(CallbackInfo ci) {
        Villager self = (Villager) (Object) this;
        NeoForgeGunpowderTrades.maybeAddGunpowderTrade(self);
    }
}
