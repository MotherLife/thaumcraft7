package me.alegian.thavma.impl.client;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import me.alegian.thavma.impl.Thavma;
import net.minecraft.client.renderer.RenderType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import static net.minecraft.client.renderer.RenderStateShard.*;

@OnlyIn(Dist.CLIENT)
public class T7RenderTypes {
  public static final RenderType AURA_NODE = RenderType.create(
      Thavma.MODID + "_aura_node", T7VertexFormats.AURA_NODE, VertexFormat.Mode.TRIANGLES, RenderType.SMALL_BUFFER_SIZE, false, true, T7RenderTypes.auraNodeState()
  );

  public static final RenderType TRANSLUCENT_TRIANGLES = RenderType.create(
      Thavma.MODID + "_vis",
      DefaultVertexFormat.POSITION_COLOR,
      VertexFormat.Mode.TRIANGLE_STRIP,
      1536,
      false,
      true,
      RenderType.CompositeState.builder()
          .setShaderState(POSITION_COLOR_SHADER)
          .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
          .setCullState(NO_CULL)
          .createCompositeState(false)
  );

  private static RenderType.CompositeState auraNodeState() {
    return RenderType.CompositeState.builder()
        .setShaderState(T7RenderStateShards.AURA_NODE_SHADER)
        .setTransparencyState(T7RenderStateShards.SIMPLE_TRANSPARENCY)
        .setDepthTestState(LEQUAL_DEPTH_TEST)
        .setTextureState(NO_TEXTURE)
        .setOutputState(TRANSLUCENT_TARGET)
        .setCullState(NO_CULL)
        .createCompositeState(false);
  }
}
