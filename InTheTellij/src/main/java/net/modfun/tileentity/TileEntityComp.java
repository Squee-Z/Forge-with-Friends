package net.modfun.tileentity;

import java.util.Arrays;
import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSapling;
import net.minecraft.block.BlockTNT;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagDouble;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TileEntityComp extends TileEntity implements ITickable{
	
	private final int INVALID_VALUE = -1;
	private int ticksLeftTillDisappear = INVALID_VALUE;

	// set by the block upon creation
	public void setTicksLeftTillDisappear(int ticks)
	{
		ticksLeftTillDisappear = ticks;
	}


	@Override
  @Nullable
  public SPacketUpdateTileEntity getUpdatePacket()
  {
		NBTTagCompound nbtTagCompound = new NBTTagCompound();
		writeToNBT(nbtTagCompound);
		int metadata = getBlockMetadata();
		return new SPacketUpdateTileEntity(this.pos, metadata, nbtTagCompound);
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		readFromNBT(pkt.getNbtCompound());
	}


  @Override
  public NBTTagCompound getUpdateTag()
  {
    NBTTagCompound nbtTagCompound = new NBTTagCompound();
    writeToNBT(nbtTagCompound);
    return nbtTagCompound;
  }


  @Override
  public void handleUpdateTag(NBTTagCompound tag)
  {
    this.readFromNBT(tag);
  }

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound parentNBTTagCompound)
	{
		super.writeToNBT(parentNBTTagCompound);

		parentNBTTagCompound.setInteger("ticksLeft", ticksLeftTillDisappear);

		parentNBTTagCompound.setString("testString", testString);

		NBTTagCompound blockPosNBT = new NBTTagCompound();
		blockPosNBT.setInteger("x", testBlockPos.getX());
		blockPosNBT.setInteger("y", testBlockPos.getY());
		blockPosNBT.setInteger("z", testBlockPos.getZ());
		parentNBTTagCompound.setTag("testBlockPos", blockPosNBT);

		NBTTagCompound itemStackNBT = new NBTTagCompound();
		testItemStack.writeToNBT(itemStackNBT);
		parentNBTTagCompound.setTag("testItemStack", itemStackNBT);

		parentNBTTagCompound.setIntArray("testIntArray", testIntArray);

		NBTTagList doubleArrayNBT = new NBTTagList();
		for (double value : testDoubleArray) {
			doubleArrayNBT.appendTag(new NBTTagDouble(value));
		}
		parentNBTTagCompound.setTag("testDoubleArray", doubleArrayNBT);

		NBTTagList doubleArrayWithNullsNBT = new NBTTagList();
		for (int i = 0; i < testDoubleArrayWithNulls.length; ++i) {
			Double value = testDoubleArrayWithNulls[i];
			if (value != null) {
				NBTTagCompound dataForThisSlot = new NBTTagCompound();
				dataForThisSlot.setInteger("i", i+1);
				dataForThisSlot.setDouble("v", value);
				doubleArrayWithNullsNBT.appendTag(dataForThisSlot);
			}
		}
		parentNBTTagCompound.setTag("testDoubleArrayWithNulls", doubleArrayWithNullsNBT);
    return parentNBTTagCompound;
	}


	@Override
	public void readFromNBT(NBTTagCompound parentNBTTagCompound)
	{
		super.readFromNBT(parentNBTTagCompound);

		final int NBT_INT_ID = 3;
		int readTicks = INVALID_VALUE;
		if (parentNBTTagCompound.hasKey("ticksLeft", NBT_INT_ID)) {
			readTicks = parentNBTTagCompound.getInteger("ticksLeft");
			if (readTicks < 0) readTicks = INVALID_VALUE;
		}
		ticksLeftTillDisappear = readTicks;


		String readTestString = null;
		final int NBT_STRING_ID = 8;
		if (parentNBTTagCompound.hasKey("testString", NBT_STRING_ID)) {
			readTestString = parentNBTTagCompound.getString("testString");
		}
		if (!testString.equals(readTestString)) {
			System.err.println("testString mismatch:" + readTestString);
		}

		NBTTagCompound blockPosNBT = parentNBTTagCompound.getCompoundTag("testBlockPos");
		BlockPos readBlockPos = null;
		if (blockPosNBT.hasKey("x", NBT_INT_ID) && blockPosNBT.hasKey("y", NBT_INT_ID) && blockPosNBT.hasKey("z", NBT_INT_ID) ) {
			readBlockPos = new BlockPos(blockPosNBT.getInteger("x"), blockPosNBT.getInteger("y"), blockPosNBT.getInteger("z"));
		}
		if (readBlockPos == null || !testBlockPos.equals(readBlockPos)) {
			System.err.println("testBlockPos mismatch:" + readBlockPos);
		}

		NBTTagCompound itemStackNBT = parentNBTTagCompound.getCompoundTag("testItemStack");
		ItemStack readItemStack = new ItemStack(itemStackNBT);
		if (!ItemStack.areItemStacksEqual(testItemStack, readItemStack)) {
			System.err.println("testItemStack mismatch:" + readItemStack);
		}

		int [] readIntArray = parentNBTTagCompound.getIntArray("testIntArray");
		if (!Arrays.equals(testIntArray, readIntArray)) {
			System.err.println("testIntArray mismatch:" + readIntArray);
		}

		final int NBT_DOUBLE_ID = 6;
		NBTTagList doubleArrayNBT = parentNBTTagCompound.getTagList("testDoubleArray", NBT_DOUBLE_ID);
		int numberOfEntries = Math.min(doubleArrayNBT.tagCount(), testDoubleArray.length);
		double [] readDoubleArray = new double[numberOfEntries];
		for (int i = 0; i < numberOfEntries; ++i) {
			 readDoubleArray[i] = doubleArrayNBT.getDoubleAt(i);
		}
		if (doubleArrayNBT.tagCount() != numberOfEntries || !Arrays.equals(readDoubleArray, testDoubleArray)) {
			System.err.println("testDoubleArray mismatch:" + readDoubleArray);
		}

		final int NBT_COMPOUND_ID = 10;					// see NBTBase.createNewByType()
		NBTTagList doubleNullArrayNBT = parentNBTTagCompound.getTagList("testDoubleArrayWithNulls", NBT_COMPOUND_ID);
		numberOfEntries = Math.min(doubleArrayNBT.tagCount(), testDoubleArrayWithNulls.length);
		Double [] readDoubleNullArray = new Double[numberOfEntries];
		for (int i = 0; i < doubleNullArrayNBT.tagCount(); ++i)	{
			NBTTagCompound nbtEntry = doubleNullArrayNBT.getCompoundTagAt(i);
			int idx = nbtEntry.getInteger("i") - 1;
			if (nbtEntry.hasKey("v", NBT_DOUBLE_ID) && idx >= 0 && idx < numberOfEntries) {
				readDoubleNullArray[idx] = nbtEntry.getDouble("v");
			}
		}
		if (!Arrays.equals(testDoubleArrayWithNulls, readDoubleNullArray)) {
			System.err.println("testDoubleArrayWithNulls mismatch:" + readDoubleNullArray);
		}
	}


	@Override
	public void update() {
		if (!this.hasWorld()) return;
		World world = this.getWorld();
		if (world.isRemote) return;
		if (ticksLeftTillDisappear == INVALID_VALUE) return;
		--ticksLeftTillDisappear;

		if (ticksLeftTillDisappear > 0) return;

		Block [] blockChoices = {Blocks.DIAMOND_BLOCK, Blocks.OBSIDIAN, Blocks.AIR, Blocks.TNT, Blocks.YELLOW_FLOWER, Blocks.SAPLING, Blocks.WATER};
		Random random = new Random();
		Block chosenBlock = blockChoices[random.nextInt(blockChoices.length)];
	  world.setBlockState(this.pos, chosenBlock.getDefaultState());
		if (chosenBlock == Blocks.TNT) {
			Blocks.TNT.onBlockDestroyedByPlayer(world, pos, Blocks.TNT.getDefaultState().withProperty(BlockTNT.EXPLODE, true));
			world.setBlockToAir(pos);
		} else if (chosenBlock == Blocks.SAPLING) {
			BlockSapling blockSapling = (BlockSapling)Blocks.SAPLING;
			blockSapling.generateTree(world, this.pos, blockSapling.getDefaultState(),random);
		}
	}

	private final int [] testIntArray = {5, 4, 3, 2, 1};
	private final double [] testDoubleArray = {1, 2, 3, 4, 5, 6};
	private final Double [] testDoubleArrayWithNulls = {61.1, 62.2, null, 64.4, 65.5};
	private final ItemStack testItemStack = new ItemStack(Items.COOKED_CHICKEN, 23);
	private final String testString = "supermouse";
	private final BlockPos testBlockPos = new BlockPos(10, 11, 12);
}
