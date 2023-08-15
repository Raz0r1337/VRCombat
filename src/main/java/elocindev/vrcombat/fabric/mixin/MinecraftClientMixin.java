package elocindev.vrcombat.fabric.mixin;

import net.bettercombat.client.BetterCombatClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import elocindev.vrcombat.fabric.VRCombat;
import org.vivecraft.client.VivecraftVRMod;
import org.vivecraft.client_vr.ClientDataHolderVR;
import org.vivecraft.client_vr.VRData;
import org.vivecraft.client_vr.VRState;
import org.vivecraft.client_vr.gameplay.VRPlayer;
import org.vivecraft.client_vr.provider.VRRenderer;
import org.vivecraft.client_vr.settings.VRSettings;
import org.vivecraft.fabric.VivecraftMod;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
	@Shadow @Nullable public ClientWorld world;

	@Inject(at = @At("HEAD"), method = "tick")
	// This is a hacky solution, but according to Daedelus, the only way to do it is by overriding the boolean every tick
	private void state(CallbackInfo info) {
		//A check if the VR is running could make a world of a difference.
		//VR hot-swapping is really tedious to work with, this solution checks if VR is enabled,
		//And if the player is also currently IN VR, so that the mod comes back when a hotswap to desktop occurs.
		if (ClientDataHolderVR.getInstance().vrSettings.vrEnabled &&
				ClientDataHolderVR.getInstance().vr != null &&
				ClientDataHolderVR.getInstance().vr.isHMDTracking()) {
			BetterCombatClient.ENABLED = false;
		}
	}
}