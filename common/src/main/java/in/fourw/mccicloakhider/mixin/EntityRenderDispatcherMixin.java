package in.fourw.mccicloakhider.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import in.fourw.mccicloakhider.CloakFilter;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityRenderDispatcher.class)
public abstract class EntityRenderDispatcherMixin {
    @Inject(method = "shouldRender", at = @At("HEAD"), cancellable = true)
    private void hideOwnCloak(CallbackInfoReturnable<Boolean> cir,
                              @Local(argsOnly = true) Entity entity) {
        if (CloakFilter.shouldHide(entity)) {
            cir.setReturnValue(false);
        }
    }
}
