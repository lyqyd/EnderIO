package crazypants.enderio.conduit.liquid;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import crazypants.enderio.ModObject;
import crazypants.enderio.conduit.AbstractItemConduit;
import crazypants.enderio.conduit.IConduit;
import crazypants.enderio.conduit.ItemConduitSubtype;
import crazypants.enderio.config.Config;
import crazypants.enderio.gui.IAdvancedTooltipProvider;
import crazypants.enderio.gui.TooltipAddera;
import crazypants.util.Lang;

public class ItemLiquidConduit extends AbstractItemConduit implements IAdvancedTooltipProvider {

  private static ItemConduitSubtype[] subtypes = new ItemConduitSubtype[] {
    new ItemConduitSubtype(ModObject.itemLiquidConduit.name(), "enderio:itemLiquidConduit"),
    new ItemConduitSubtype(ModObject.itemLiquidConduit.name() + "Advanced", "enderio:itemLiquidConduitAdvanced"),
    new ItemConduitSubtype(ModObject.itemLiquidConduit.name() + "Ender", "enderio:itemLiquidConduitEnder")

  };

  public static ItemLiquidConduit create() {
    ItemLiquidConduit result = new ItemLiquidConduit();
    result.init(subtypes);
    return result;
  }

  protected ItemLiquidConduit() {
    super(ModObject.itemLiquidConduit);
  }

  @Override
  public Class<? extends IConduit> getBaseConduitType() {
    return ILiquidConduit.class;
  }

  @Override
  public IConduit createConduit(ItemStack stack) {
    if(stack.getItemDamage() == 1) {
      return new AdvancedLiquidConduit();
    } else if(stack.getItemDamage() == 2) {
      return new EnderLiquidConduit();
    }
    return new LiquidConduit();
  }

  @Override
  @SideOnly(Side.CLIENT)
  public void addCommonEntries(ItemStack itemstack, EntityPlayer entityplayer, List list, boolean flag) {
  }

  @Override
  @SideOnly(Side.CLIENT)
  public void addBasicEntries(ItemStack itemstack, EntityPlayer entityplayer, List list, boolean flag) {

  }

  @Override
  @SideOnly(Side.CLIENT)
  public void addDetailedEntries(ItemStack itemstack, EntityPlayer entityplayer, List list, boolean flag) {
    int extractRate;
    int maxIo;

    if(itemstack.getItemDamage() == 0) {
      extractRate = Config.fluidConduitExtractRate;
      maxIo = Config.fluidConduitMaxIoRate;
    } else if(itemstack.getItemDamage() == 1){
      extractRate = Config.advancedFluidConduitExtractRate;
      maxIo = Config.advancedFluidConduitMaxIoRate;
    } else {
      extractRate = Config.enderFluidConduitExtractRate;
      maxIo = Config.enderFluidConduitMaxIoRate;
    }
    String mbt = " " + Lang.localize("fluid.millibucketsTick");
    list.add(Lang.localize("itemLiquidConduit.tooltip.maxExtract") + " " + extractRate + mbt);
    list.add(Lang.localize("itemLiquidConduit.tooltip.maxIo") + " " + maxIo + mbt);

    if(itemstack.getItemDamage() == 0) {
      TooltipAddera.addDetailedTooltipFromResources(list, "enderio.itemLiquidConduit");
    } else if(itemstack.getItemDamage() == 2) {
      TooltipAddera.addDetailedTooltipFromResources(list, "enderio.itemLiquidConduitEnder");      
    }

  }

  @Override
  public boolean shouldHideFacades(ItemStack stack, EntityPlayer player) {
    return true;
  }

}
