package com.threecolumnsstudio.simplegunpowder.mixin;

import com.threecolumnsstudio.simplegunpowder.SimpleGunpowderTrades;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.npc.villager.Villager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Villager.class)
public class VillagerMixin {

    @Inject(method = "updateTrades", at = @At("TAIL"))
    private void onUpdateTrades(ServerLevel level, CallbackInfo ci) {
        Villager self = (Villager) (Object) this;
        SimpleGunpowderTrades.maybeAddGunpowderTrade(self);
    }
}
