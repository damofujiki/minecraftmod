package hinasch.mods.creativeitems.item;

import hinasch.mods.creativeitems.lib.FileObject;
import hinasch.mods.creativeitems.lib.HSLibs;
import hinasch.mods.creativeitems.lib.PairID;
import hinasch.mods.creativeitems.lib.ScanHelper;
import hinasch.mods.creativeitems.lib.UtilNBT;
import hinasch.mods.creativeitems.lib.XYZPos;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockHalfSlab;
import net.minecraft.block.BlockStairs;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.google.common.base.Optional;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemCreativeSpade extends ItemSpade{

	public static final String[] modename = {"Normal","Set StartPoint","Set EndPoint","Fill Mode","Box Mode","Replace Mode"
		,"Spoit Mode","Paste Mode","Writing Mode","Clip Paste","Explode","Normal(For Half&Stairs Mode)","Undo(Paste)"};
	protected final int NORMAL = 0;
	protected final int SP = 1;
	protected final int EP = 2;
	protected final int FILL = 3;
	protected final int BOX = 4;
	protected final int REPLACE = 5;
	protected final int SPOIT = 6;
	protected final int PASTE = 7;
	protected final int WRITE = 8;
	protected final int CLIPPASTE = 9;
	protected final int EXPLODE = 10;
	protected final int NORMAL2 = 11;
	protected final int UNDO = 12;

	protected String mcdir = Minecraft.getMinecraft().mcDataDir.getAbsolutePath();


	public ItemCreativeSpade(int par1, EnumToolMaterial par2EnumToolMaterial) {
		super(par1, par2EnumToolMaterial);

		// TODO 自動生成されたコンストラクター・スタブ
	}

	protected void changeMode(World world,ItemStack is,EntityPlayer ep,int par1){

		int mode = 0;
		if(!world.isRemote){
			UtilNBT.initNBTTag(is);
			if(!UtilNBT.hasKey(is, "mode")){

				UtilNBT.setFreeTag(is, "mode", 0);

			}
			mode = UtilNBT.readFreeTag(is, "mode");
			mode += par1;
			if (mode>=modename.length){
				mode = 0;
			}
			if (mode<0){
				mode = modename.length-1;
			}

			UtilNBT.setFreeTag(is, "mode", mode);


			ep.addChatMessage("change mode:"+modename[mode]);
		}
	}



    

	//ファイルラッパーが未完なためにこれも未完
	protected void clipPaste(World world,ItemStack is,XYZPos xyz){
		if(this.isSetBoundTag(is) && !world.isRemote){
			//			XYZPos start = XYZPos.strapOff(UtilNBT.readFreeStrTag(is, "start"));
			//			XYZPos end = XYZPos.strapOff(UtilNBT.readFreeStrTag(is, "end"));
			//			XYZPos[] swaps = XYZPos.swap(start, end);
			//			ScanHelper scan = new ScanHelper(swaps[0],swaps[1]);
			//			scan.setWorld(world);
			//			PairID blockSet = new PairID();


			FileObject fileobj = new FileObject(mcdir+"\\creativeitem_temp.txt");

			if(Minecraft.getMinecraft().mcDataDir!=null){
				System.out.println("open");



				

				fileobj.openForInput();


				this.setUndoPosition(is, xyz);
				this.loadMapToWorld(xyz, fileobj, world);
			}
			fileobj.close();
			System.out.println("close");
			this.setLastActivity(is, CLIPPASTE);
		}

	}



	protected void fill(World world,ItemStack is,EntityPlayer ep,int act){
		if(!world.isRemote){
			boolean boxmode = UtilNBT.readFreeTag(is, "mode")==BOX;
			boolean replace = UtilNBT.readFreeTag(is, "mode")==REPLACE;
			Optional<ItemStack> stack0 = Optional.fromNullable(ep.inventory.getStackInSlot(0));
			Optional<ItemStack> stack1 = Optional.fromNullable(ep.inventory.getStackInSlot(1));
			if(stack0.isPresent()){
				if(isBlockorBucket(stack0.get())){
					List<Integer> blockdata = this.getStack0BlockData(stack0.get());

					PairID firstBlock = new PairID(blockdata.get(0),blockdata.get(1));

					if(this.isSetBoundTag(is)){

						

						XYZPos start = XYZPos.strapOff(UtilNBT.readFreeStrTag(is, "start"));
						XYZPos end = XYZPos.strapOff(UtilNBT.readFreeStrTag(is, "end"));
						XYZPos[] swaps = XYZPos.swap(start, end);
						ScanHelper scan = new ScanHelper(swaps[0],swaps[1]);
						scan.setWorld(world);
						XYZPos count = swaps[1].subtract(swaps[0]);
						System.out.println("count;"+count.toString());
						System.out.println("swaps[0]"+swaps[0].toString());
						System.out.println("swaps[1]"+swaps[0].toString());
						this.setUndoPosition(is, swaps[0]);
						this.prepareUndo(world, swaps[0], swaps[0].add(count));
						this.setLastActivity(is,act);
						
						if(replace){

							PairID blockReplace = new PairID();
							if(!stack1.isPresent()){
								blockReplace.setData(0, 0);
							}else{
								if(stack1.get().getItem() instanceof ItemBlock){
									blockReplace.id = ((ItemBlock)stack1.get().getItem()).getBlockID();
									blockReplace.metadata = stack1.get().getItemDamage();

								}else{
									return;
								}
							}

							for(;scan.hasNext();scan.next()){
								if(scan.getID()==firstBlock.id && scan.getMetadata()==firstBlock.metadata){
									scan.setBlockHere(blockReplace.id, blockReplace.metadata, 2);
								}
							}
							return;
						}

						for(;scan.hasNext();scan.next()){
							if(boxmode){
								if(scan.isSide()){
									world.setBlock(scan.sx, scan.sy, scan.sz, firstBlock.id,firstBlock.metadata,2);
								}

							}else{
								world.setBlock(scan.sx, scan.sy, scan.sz, firstBlock.id,firstBlock.metadata,2);
							}

						}
					}else{
						ep.addChatMessage("no start or end point");
					}
				}
			}
			if(!stack0.isPresent()){
				if(UtilNBT.readFreeTag(is, "mode")==REPLACE)return;
				if(this.isSetBoundTag(is)){
					XYZPos start = XYZPos.strapOff(UtilNBT.readFreeStrTag(is, "start"));
					XYZPos end = XYZPos.strapOff(UtilNBT.readFreeStrTag(is, "end"));
					XYZPos[] swaps = XYZPos.swap(start, end);
					ScanHelper scan = new ScanHelper(swaps[0],swaps[1]);
					scan.setWorld(world);
					this.setUndoPosition(is, swaps[0]);
					this.prepareUndo(world, swaps[0], swaps[1]);
					this.setLastActivity(is,act);
					ep.addChatMessage("Fill");
					for(;scan.hasNext();scan.next()){
						world.setBlockToAir(scan.sx, scan.sy, scan.sz);
					}
				}else{
					ep.addChatMessage("no start or end point");
				}
			}
		}
	}

	protected Optional<Integer> getActivity(ItemStack is){
		if(UtilNBT.hasKey(is, "lastAct")){
			return Optional.of(UtilNBT.readFreeTag(is, "lastAct"));
		}else{
			return Optional.absent();
		}

	}

	protected int getMode(World world,ItemStack is){
		if(!world.isRemote){
			if(!UtilNBT.hasKey(is, "mode")){
				UtilNBT.setFreeTag(is, "mode", 0);
			}

			return UtilNBT.readFreeTag(is, "mode");
		}
		return 0;
	}

	private List<Integer> getStack0BlockData(ItemStack stack0) {
		List<Integer> blockdata = new ArrayList();
		if(stack0.getItem() instanceof ItemBlock){
			ItemBlock ib = (ItemBlock)stack0.getItem();
			blockdata.add(ib.getBlockID());
			blockdata.add(stack0.getItemDamage());
		}
		if(stack0.getItem() instanceof ItemBucket){
			ItemBucket bucket = (ItemBucket)stack0.getItem();
			blockdata.add(Block.waterStill.blockID);
			blockdata.add(0);
		}
		if(blockdata.isEmpty()){
			blockdata.add(0);
			blockdata.add(0);
		}
		return blockdata;
	}

	private boolean isBlockorBucket(ItemStack itemStack) {
		if(itemStack.getItem() instanceof ItemBlock)return true;
		if(itemStack.getItem() instanceof ItemBucket)return true;
		return false;
	}

	protected boolean isSetBoundTag(ItemStack is){
		if(UtilNBT.hasKey(is, "start") && UtilNBT.hasKey(is, "end"))return true;
		return false;
	}

	protected void loadMapToWorld(XYZPos xyz,FileObject fileobj,World world){
		int mx = Integer.parseInt(fileobj.read().get());
		int my = Integer.parseInt(fileobj.read().get());
		int mz = Integer.parseInt(fileobj.read().get());
		XYZPos endPoint = xyz.add(new XYZPos(mx,my,mz));
		this.prepareUndo(world, xyz, endPoint);
		
		boolean flag = false;
		XYZPos shift = new XYZPos(0,0,0);
		for(int stopper = 0; !flag || stopper<200;stopper += 1){
			Optional<String> line = fileobj.read();
			if(!line.isPresent()){
				flag = true;
			}else{
				String[] data = line.get().split(",");
				System.out.println(data.toString());
				PairID pair = new PairID();

				for(int i=0;i<data.length-1;i+=2){
					if(data[i]!=null && !data[i].equals("")){
						pair.id = Integer.parseInt(data[i]);
					}
					
					if(data.length > i+1){
						if(data[i+1]!=null && data[i+1].equals("")){
							pair.metadata = Integer.parseInt(data[i+1]);
						}
						
					}
					world.setBlock(xyz.x + shift.x, xyz.y + shift.y, xyz.z + shift.z, pair.id,pair.metadata,2);
					shift.x += 1;
					if(shift.x > mx){
						shift.z += 1;
						shift.x = 0;
					}
					if(shift.z > mz){
						shift.y += 1;
						shift.z = 0;
					}
					if(shift.y > my){
						flag = true;
					}
				}
			}

		}
	}

	public void makeExplode(World world,XYZPos xyz,EntityPlayer ep){
		if(!world.isRemote){
			world.createExplosion(ep, xyz.x, xyz.y, xyz.z, 4.0F, true);

		}
		return;
	}


	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
	{


		if(par3EntityPlayer.isSneaking()){
			changeMode(par2World,par1ItemStack,par3EntityPlayer, +1);
			return par1ItemStack;
		}


		return par1ItemStack;
	}

	@Override
	public boolean onItemUse(ItemStack is, EntityPlayer ep, World world, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
	{
		if(ep.isSneaking()){
			changeMode(world,is,ep,+1);
			return true;
		}
		

		XYZPos usepoint = new XYZPos(par4,par5,par6);
		switch(getMode(world,is)){
		case SP:setStartPoint(world, is, ep, usepoint);break;
		case EP:setEndPoint(world, is, ep, usepoint);break;
		case FILL:this.fill(world, is, ep,FILL);break;
		case BOX:this.fill(world, is, ep,BOX);break;
		case REPLACE:this.fill(world, is, ep,REPLACE);break;
		case SPOIT:this.spoit(world,ep,is,usepoint);break;
		case PASTE:this.pasteTo(world, is,usepoint);break;
		case WRITE:this.writeToFile(world, is);break;
		case CLIPPASTE:this.clipPaste(world, is, usepoint);break;
		case EXPLODE:this.makeExplode(world, usepoint, ep);
		case UNDO:this.undo(world,ep, is);
		}


		if(getMode(world,is)==NORMAL || getMode(world,is)==NORMAL2){
			if(ep.inventory.getStackInSlot(0)!=null){
				if(!(ep.inventory.getStackInSlot(0).getItem() instanceof ItemBlock))return false;
				ItemBlock itemblock = (ItemBlock)ep.inventory.getStackInSlot(0).getItem();
				PairID blockSet = new PairID(itemblock.getBlockID(),ep.inventory.getStackInSlot(0).getItemDamage());
				PairID preBlock = new PairID();
				preBlock.getFromWorld(world, par4, par5, par6);
				Block blockPre = Block.blocksList[preBlock.id];
				Block blockPost = Block.blocksList[blockSet.id];


				if(!world.isRemote){
					boolean flag = false;
					boolean normal2 = getMode(world,is)==NORMAL2;
					if(blockPost instanceof BlockHalfSlab && normal2){
						world.setBlock(par4, par5, par6, blockSet.id,blockSet.metadata | 8, 2);
						flag = true;
					}
					if(blockPre instanceof BlockStairs && blockPost instanceof BlockStairs && normal2){
						world.setBlock(par4, par5, par6, blockSet.id,preBlock.metadata, 2);
						flag = true;
					}
					if(!flag){
						world.setBlock(par4, par5, par6, blockSet.id,blockSet.metadata, 2);
					}

				}
				if(!ep.capabilities.isCreativeMode && !world.isRemote){
					HSLibs.dropItem(world, new ItemStack(Block.blocksList[preBlock.id],1,preBlock.metadata), par4, par5, par6);
					ep.inventory.getStackInSlot(0).stackSize --;
					is.damageItem(1, ep);
				}
				return true;
			}
		}
		return false;
	}

	protected void pasteTo(World world,ItemStack is,XYZPos pastePos){
		if(this.isSetBoundTag(is)){
			XYZPos start = XYZPos.strapOff(UtilNBT.readFreeStrTag(is, "start"));
			XYZPos end = XYZPos.strapOff(UtilNBT.readFreeStrTag(is, "end"));
			XYZPos[] swaps = XYZPos.swap(start, end);
			ScanHelper scan = new ScanHelper(swaps[0],swaps[1]);
			scan.setWorld(world);
			PairID blockSet = new PairID();
			XYZPos count = swaps[1].subtract(swaps[0]);
			this.setUndoPosition(is, pastePos);
			this.prepareUndo(world, pastePos, pastePos.add(count));
			this.setLastActivity(is, this.PASTE);
			System.out.println(count.toString());
			
			for(;scan.hasNext();scan.next()){
				blockSet.getFromWorld(world, scan.sx, scan.sy, scan.sz);
				world.setBlock(pastePos.x+scan.getCount().x, pastePos.y+scan.getCount().y, pastePos.z+scan.getCount().z, 
						blockSet.id,blockSet.metadata,2);

			}
		}
	}


	protected void prepareUndo(World world,XYZPos startPos,XYZPos endPoint){
		FileObject fileobjUndo = new FileObject(mcdir+"\\creativeitem_undo.txt");
		fileobjUndo.openForOutput();
		this.saveMapToTemp(fileobjUndo, new ScanHelper(startPos,endPoint), world);
		fileobjUndo.close();
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IconRegister par1IconRegister)
	{
		this.itemIcon = par1IconRegister.registerIcon("iron_shovel");
	}
	protected void saveMapToTemp(FileObject fileobj,ScanHelper scan,World world){
		PairID blockSet = new PairID();
		//String mcdir = Minecraft.getMinecraft().mcDataDir.getAbsolutePath();
		//FileObject fileobj = new FileObject(mcdir+"\\creativeitem_temp.txt");
		fileobj.openForOutput();
		String temp = "";
		fileobj.write(String.valueOf(scan.endX - scan.startX)+"\r\n");
		fileobj.write(String.valueOf(scan.endY - scan.startY)+"\r\n");
		fileobj.write(String.valueOf(scan.endZ - scan.startZ)+"\r\n");
		for(;scan.hasNext();scan.next()){

			blockSet.getFromWorld(world, scan.sx, scan.sy, scan.sz);
			temp += blockSet.toString()+",";
			if(scan.isEndSide()){
				//				if(temp.substring(temp.length()-1).equals(",")){
				//					temp = temp.substring(0, temp.length()-2);
				//					
				//				}
				fileobj.write(temp+"\r\n");
				temp = "";
			}
		}

	}

	protected void setEndPoint(World world,ItemStack is,EntityPlayer ep,XYZPos xyz){

		if(!world.isRemote){
			UtilNBT.setFreeTag(is, "end", xyz.toString());
			ep.addChatMessage("endpoint:"+xyz.toString());
		}


	}

	protected void setLastActivity(ItemStack is,int act){
		UtilNBT.setFreeTag(is, "lastAct", act);
	}

	protected void setStartPoint(World world,ItemStack is,EntityPlayer ep,XYZPos xyz){

		if(!world.isRemote){
			UtilNBT.setFreeTag(is, "start", xyz.toString());
			ep.addChatMessage("startpoint:"+xyz.toString());
		}


	}

	protected void setUndoPosition(ItemStack is,XYZPos xyz){
		UtilNBT.setFreeTag(is, "undoPos", xyz.toString());
	}

	protected void spoit(World par3World, EntityPlayer ep,ItemStack is,XYZPos xyz) {
		// TODO 自動生成されたメソッド・スタブ
		PairID spoit = new PairID();
		spoit.getFromWorld(par3World, xyz.x, xyz.y, xyz.z);
		//UtilNBT.setFreeTag(is, "spoit", spoit.toString());

		ItemStack spoited = new ItemStack(Block.blocksList[spoit.id],1,spoit.metadata);
		HSLibs.dropItem(par3World, spoited, xyz.x, xyz.y, xyz.z);

		return;
	}

	private void undo(World world, EntityPlayer ep2,ItemStack is) {
		if(this.getActivity(is).isPresent() && UtilNBT.hasKey(is, "undoPos")){
			int activity = this.getActivity(is).get();
			if(activity==UNDO){
				return;
			}
			if(activity==CLIPPASTE || activity == PASTE || activity == FILL || activity == REPLACE
					|| activity == BOX){
				XYZPos undoPos = XYZPos.strapOff(UtilNBT.readFreeStrTag(is, "undoPos"));
				FileObject fileobjUndo = new FileObject(mcdir+"\\creativeitem_undo.txt");
				fileobjUndo.openForInput();
				this.loadMapToWorld(undoPos, fileobjUndo, world);
				fileobjUndo.close();
				this.setLastActivity(is, UNDO);
				ep2.addChatMessage("undo succeeded.");
			}
			
			return;
		}
		ep2.addChatMessage("can't undo.");
		

	}

	//ファイルラッパーが未完なためにこれも未完
	protected void writeToFile(World world,ItemStack is){
		if(this.isSetBoundTag(is)){
			XYZPos start = XYZPos.strapOff(UtilNBT.readFreeStrTag(is, "start"));
			XYZPos end = XYZPos.strapOff(UtilNBT.readFreeStrTag(is, "end"));
			XYZPos[] swaps = XYZPos.swap(start, end);
			ScanHelper scan = new ScanHelper(swaps[0],swaps[1]);
			scan.setWorld(world);



			if(Minecraft.getMinecraft().mcDataDir!=null){
				//PairID blockSet = new PairID();
				System.out.println("open");


				FileObject fileobj = new FileObject(mcdir+"\\creativeitem_temp.txt");

				this.saveMapToTemp(fileobj, scan, world);
				fileobj.close();
			}



		}

	}
}

