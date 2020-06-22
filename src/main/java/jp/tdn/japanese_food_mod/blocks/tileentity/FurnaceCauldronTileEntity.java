package jp.tdn.japanese_food_mod.blocks.tileentity;

import jp.tdn.japanese_food_mod.JapaneseFoodMod;
import jp.tdn.japanese_food_mod.blocks.FurnaceCauldronBlock;
import jp.tdn.japanese_food_mod.container.FurnaceCauldronContainer;
import jp.tdn.japanese_food_mod.container.PresserContainer;
import jp.tdn.japanese_food_mod.init.JPBlocks;
import jp.tdn.japanese_food_mod.init.JPItemTags;
import jp.tdn.japanese_food_mod.init.JPItems;
import jp.tdn.japanese_food_mod.init.JPTileEntities;
import jp.tdn.japanese_food_mod.recipes.FurnaceCauldronRecipe;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.RangedWrapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static jp.tdn.japanese_food_mod.init.JPItems.SALT;

public class FurnaceCauldronTileEntity extends TileEntity implements ITickableTileEntity, INamedContainerProvider {
    public static final int INPUT_SLOT = 0;
    public static final int OUTPUT_SLOT = 1;
    public static final int[] RETURN_SLOT = {2, 3, 4};

    private static final String INVENTORY_TAG = "inventory";
    private static final String HEATING_TIME_LEFT_TAG = "heatingTimeLeft";
    private static final String HEATING_MAX_TIME_TAG = "heatingMaxTime";
    private static final String WATER_REMAINING_TAG = "waterRemaining";

    public ItemStackHandler inventory = new ItemStackHandler(5){
        @Override
        public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
            switch (slot){
                case 0:
                    return isInput(stack);
                case 1:
                    return isOutPut(stack);
                case 2:
                case 3:
                case 4:
                    return isReturnOutput(stack);
                default:
                    return false;
            }
        }

        @Override
        protected void onContentsChanged(int slot) {
            super.onContentsChanged(slot);
            FurnaceCauldronTileEntity.this.markDirty();
        }
    };

    private final LazyOptional<ItemStackHandler> inventoryCapabilityExternal = LazyOptional.of(() -> this.inventory);
    private final LazyOptional<IItemHandlerModifiable> inventoryCapabilityExternalUpAndSides = LazyOptional.of(() -> new RangedWrapper(this.inventory, INPUT_SLOT, INPUT_SLOT + 1));
    private final LazyOptional<IItemHandlerModifiable> inventoryCapabilityExternalDown = LazyOptional.of(() -> new RangedWrapper(this.inventory, OUTPUT_SLOT, OUTPUT_SLOT + 1));

    public int heatingTimeLeft = -1;
    public int maxHeatingTime = -1;
    public int waterRemaining = 0;
    public int maxWater = 1000;
    public int needWater = 100;
    private boolean lastActive = false;

    public FurnaceCauldronTileEntity(){
        super(JPTileEntities.FURNACE_CAULDRON);
    }

    private boolean isInput(ItemStack stack){
        if(stack.isEmpty()){
            return false;
        }
        return getRecipe(stack).isPresent();
    }

    private boolean isOutPut(ItemStack stack){
        return stack.getItem() == SALT;
    }

    private boolean isReturnOutput(final ItemStack stack){
        return stack.getItem() == Items.GLASS_BOTTLE || stack.getItem() == JPItems.CUP || stack.getItem() == Items.BUCKET || stack.getItem() == JPItems.BITTERN;
    }

    private Optional<FurnaceCauldronRecipe> getRecipe(final ItemStack input){
        return getRecipe(new Inventory(input));
    }

    private Optional<FurnaceCauldronRecipe> getRecipe(final IInventory inventory){
        return Objects.requireNonNull(world).getRecipeManager().getRecipe(FurnaceCauldronRecipe.RECIPE_TYPE, inventory, world);
    }

    private Optional<ItemStack> getResult(final ItemStack input){
        final Inventory dummyInventory = new Inventory(input);
        return getRecipe(dummyInventory).map(recipe -> recipe.getCraftingResult(dummyInventory));
    }

    @Override
    public void tick() {
        if(world == null || world.isRemote) {
            return;
        }
        boolean isActive = false;

        final ItemStack input = inventory.getStackInSlot(INPUT_SLOT);
        if(!input.isEmpty() && canAddWater()){
            addWater(input);
            //JapaneseFoodMod.LOGGER.info(waterRemaining);
            if (input.hasContainerItem()) {
                insertOrDropContainerItem(input, INPUT_SLOT);
                input.shrink(1);
                inventory.setStackInSlot(INPUT_SLOT, input);
            }
        }

        if(waterRemaining >= needWater){
            isActive = true;
            if(heatingTimeLeft == -1){
                heatingTimeLeft = maxHeatingTime = getHeatingTime(input);
            }else{
                --heatingTimeLeft;
                if(heatingTimeLeft <= 0){
                    waterRemaining -= needWater;
                    inventory.insertItem(OUTPUT_SLOT, new ItemStack(SALT), false);
                    insertOrDropItem(new ItemStack(JPItems.BITTERN));
                    heatingTimeLeft = -1;
                }
            }
        }else{
            heatingTimeLeft = maxHeatingTime = -1;
        }

        if(lastActive != isActive){
            this.markDirty();
            lastActive = isActive;
        }

        FurnaceCauldronBlock block = (FurnaceCauldronBlock) world.getBlockState(pos).getBlock();
        block.setWaterLevel(world, pos, world.getBlockState(pos), waterRemaining, maxWater);
    }

    private void insertOrDropItem(final ItemStack stack){
        int index;
        boolean canInsertItem = false;
        for(index = 0; index < RETURN_SLOT.length && !(canInsertItem = inventory.insertItem(RETURN_SLOT[index], stack, true).isEmpty()); ++index){
            //JapaneseFoodMod.LOGGER.info(RETURN_SLOT[index]);
        }
        if(canInsertItem){
            inventory.insertItem(RETURN_SLOT[index], stack, false);
        }else{
            InventoryHelper.spawnItemStack(world, pos.getX(), pos.getY(), pos.getZ(), stack);
        }
    }

    private void insertOrDropContainerItem(final ItemStack stack, final int slot){
        int index;
        final ItemStack containerItem = stack.getContainerItem();
        boolean canInsertContainerItemIntoReturnSlot = false;
        for(index = 0; index < RETURN_SLOT.length && !(canInsertContainerItemIntoReturnSlot = inventory.insertItem(RETURN_SLOT[index], containerItem, true).isEmpty()); ++index);

        if(canInsertContainerItemIntoReturnSlot){
            inventory.insertItem(RETURN_SLOT[index], containerItem, false);
        }else {
            final boolean canInsertContainerItemIntoSlot = inventory.insertItem(slot, containerItem, true).isEmpty();
            if (canInsertContainerItemIntoSlot) {
                inventory.insertItem(slot, containerItem, false);
            } else {
                InventoryHelper.spawnItemStack(Objects.requireNonNull(world), pos.getX(), pos.getY(), pos.getZ(), containerItem);
            }
        }
    }

    private int getHeatingTime(ItemStack input){
        return getRecipe(input).map(FurnaceCauldronRecipe::getCookTime).orElse(1000);
    }

    public boolean canAddWater(){
        return waterRemaining < maxWater;
    }

    public void addWater(ItemStack interact){
        int plus = 0;
        if (Items.WATER_BUCKET.equals(interact.getItem())) {
            plus = 500;
        }else if(JPItems.CUP_WITH_WATER.equals(interact.getItem())){
            plus = 50;
        }
        this.waterRemaining = Math.min(maxWater, waterRemaining + plus);
    }

    public int getWaterRemaining() {
        return waterRemaining;
    }

    public int getMaxWater() {
        return maxWater;
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if(cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY){
            return inventoryCapabilityExternal.cast();
        }

        switch (Objects.requireNonNull(side)){

            case DOWN:
                return inventoryCapabilityExternalDown.cast();
            case UP:
            case NORTH:
            case SOUTH:
            case WEST:
            case EAST:
                return inventoryCapabilityExternalUpAndSides.cast();
        }

        return super.getCapability(cap, side);
    }

    @Override
    public void read(CompoundNBT compound) {
        super.read(compound);
        this.inventory.deserializeNBT(compound.getCompound(INVENTORY_TAG));
        this.heatingTimeLeft = compound.getInt(HEATING_TIME_LEFT_TAG);
        this.maxHeatingTime = compound.getInt(HEATING_MAX_TIME_TAG);
        this.waterRemaining = compound.getInt(WATER_REMAINING_TAG);
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);
        compound.put(INVENTORY_TAG, this.inventory.serializeNBT());
        compound.putInt(HEATING_TIME_LEFT_TAG, this.heatingTimeLeft);
        compound.putInt(HEATING_MAX_TIME_TAG, this.maxHeatingTime);
        compound.putInt(WATER_REMAINING_TAG, this.waterRemaining);
        return compound;
    }

    @Override
    public CompoundNBT getUpdateTag() {
        return this.write(new CompoundNBT());
    }

    @Override
    public void remove() {
        super.remove();
        inventoryCapabilityExternal.invalidate();
    }

    @Nonnull
    @Override
    public ITextComponent getDisplayName() {
        return new TranslationTextComponent(JPBlocks.FURNACE_CAULDRON.getTranslationKey());
    }

    @Nonnull
    @Override
    public Container createMenu(int windowId, @Nonnull PlayerInventory inventory, @Nonnull PlayerEntity player) {
        return new FurnaceCauldronContainer(windowId, inventory, this);
    }
}